package lab7;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
class Tarea{
  boolean esVeneno;
  int codPueblo;
  public Tarea(int codPueblo, boolean esVeneno) {
    this.codPueblo=codPueblo;
    this.esVeneno=esVeneno;
  }
  public boolean getVeneno(){
    return esVeneno;
  }
  public int getCodPueblo(){
    return codPueblo;
  }
}

class HebraTemperatura extends Thread {
  BlockingQueue<Tarea> cola;
  String fecha;
  PuebloMaximaMinima MaxMin;

  public HebraTemperatura(BlockingQueue<Tarea> cola, String fecha, PuebloMaximaMinima MaxMin) {
    this.cola = cola;
    this.fecha = fecha;
    this.MaxMin = MaxMin;
  }

  public void run() {
    try {
      Tarea e = cola.take();
      while (!e.getVeneno()) {
        EjemploTemperaturaProvincia.ProcesaPueblo(fecha, e.getCodPueblo(), this.MaxMin, false);
        e = cola.take();
      }
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }
}
class EjemploTemperaturaProvincia {
  public static void main(String[] args) {
    int                numHebras, codProvincia, desp;
    long               t1, t2, tt[];
    double             ts, tp;
    PuebloMaximaMinima MaxMin;

    
    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 3 ) {
      System.out.println( "ERROR: numero de argumentos incorrecto.");
      System.out.println( "Uso: java programa <numHebras> <provincia> <desplazamiento>" );
      System.exit( -1 );
    }
    try {
      numHebras    = Integer.parseInt( args[ 0 ] );
      codProvincia = Integer.parseInt( args[ 1 ] );
      desp         = Integer.parseInt( args[ 2 ] );
    } catch( NumberFormatException ex ) {
      numHebras    = -1;
      codProvincia = -1;
      desp         = -1;
      System.out.println( "ERROR: Numero de entrada incorrecto." );
      System.exit( -1 );
    }
    if (numHebras <= 0) {
      System.out.println( "ERROR: El numero de Hebras debe ser un numero entero mayor que 0." );
      System.exit( -1 );
    }
    if ((codProvincia < 1) || (codProvincia > 50)) {
      System.out.println( "ERROR: El codigo de la provincia debe ser un numero entero " +
                          "comprendido entre 1 y 50." );
      System.exit( -1 );
    }
    if ((desp < 0) || (desp > 6)) {
      System.out.println( "ERROR: El desplazamiento debe ser un numero entero comprendido " +
                          "entre 0 y 6." );
      System.exit( -1 );
    }
    
    System.out.println();
    System.out.println( "Obtiene el pueblo de una provincia con mayor diferencia " + 
                        "de temperatura." );
    
    // Seleccion del dia elegido
    String fecha;
    Calendar c = Calendar.getInstance();
    Integer dia, mes, anyo;
    HebraTemperatura[] hebrast= new HebraTemperatura[numHebras];
    
    c.add(Calendar.DAY_OF_MONTH, desp);
    dia = c.get(Calendar.DATE);
    mes = c.get(Calendar.MONTH) + 1;
    anyo = c.get(Calendar.YEAR);
    
    fecha = String.format("%02d", anyo) + "-" + String.format("%02d", mes) + "-" +
            String.format("%02d", dia);
    System.out.println(fecha);
    
    //
    // Implementacion secuencial sin temporizar.
    //

    MaxMin = new PuebloMaximaMinima();
    obtenMayorDiferencia_SecuencialAFichero (fecha, codProvincia, MaxMin);
    System.out.println( "  Pueblo: " + MaxMin.damePueblo() + " , Maxima = " +
                        MaxMin.dameTemperaturaMaxima() + " , Minima = " +
                        MaxMin.dameTemperaturaMinima() );

    
    //
    // Implementacion secuencial.
    //
    System.out.println();
    t1 = System.nanoTime();
    MaxMin = new PuebloMaximaMinima();
    obtenMayorDiferencia_SecuencialDeFichero (fecha, codProvincia, MaxMin);
    t2 = System.nanoTime();
    ts = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion secuencial.                           " );
    System.out.println( " Tiempo(s): " + ts );
    System.out.println( "  Pueblo: " + MaxMin.damePueblo() + " , Maxima = " +
                           MaxMin.dameTemperaturaMaxima() + " , Minima = " +
                           MaxMin.dameTemperaturaMinima() );

    // Implementacion con BlockingQueue
    BlockingQueue<Tarea> cola= new LinkedBlockingQueue<>();
    t1 = System.nanoTime();
    for(int i=0; i<numHebras;i++){
     hebrast[i]=new HebraTemperatura(cola, fecha, MaxMin);
     hebrast[i].start();
    }
    obtenMayorDiferencia_ParalelaDeFichero (codProvincia, MaxMin, cola);

    for(int i=0; i<numHebras;i++){
      try {
        cola.put(new Tarea(-1, true));
      }catch (InterruptedException e){
        e.printStackTrace();
      }
    }

    for(int i=0; i<numHebras;i++){
      try {
        hebrast[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }

    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println();
    System.out.print( "Implementacion BlockingQueue.     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp );
    System.out.println( "  Pueblo: "+  MaxMin.damePueblo() + " , Maxima = " +
            MaxMin.dameTemperaturaMaxima() + " , Minima = " +
            MaxMin.dameTemperaturaMinima() );

    class TareaPool implements Runnable{
      String fecha;
      PuebloMaximaMinima MaxMin;
      int codPueblo;
      public TareaPool(String fecha, PuebloMaximaMinima MaxMin, int codPueblo){
        this.fecha=fecha;
        this.MaxMin=MaxMin;
        this.codPueblo=codPueblo;
      }
      @Override
      public void run() {
        EjemploTemperaturaProvincia.ProcesaPueblo(fecha, codPueblo, this.MaxMin, false);
      }
    }

    //
    // Implementacion paralela: Thread Pool con Gestion Propia.
    //

    System.out.println();
    t1 = System.nanoTime();
    ExecutorService exec= Executors.newFixedThreadPool(numHebras);

    File           archivo = null;
    FileReader     fr      = null;
    BufferedReader br      = null;
    archivo = new File ("codPueblos.txt");
    try {
      fr = new FileReader(archivo);
      br = new BufferedReader(fr);
      String linea;
      while ((linea = br.readLine()) != null) {
        exec.execute(new TareaPool(fecha, MaxMin, Integer.parseInt(linea)));
      }
      exec.shutdown();
      while(!exec.isTerminated()){}
    }catch (Exception e){
      e.printStackTrace();
    }
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela: Gestion Propia.     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp);
    System.out.println( "  Pueblo: "+  MaxMin.damePueblo() + " , Maxima = " +
            MaxMin.dameTemperaturaMaxima() + " , Minima = " +
            MaxMin.dameTemperaturaMinima() );


    //
    // Implementacion paralela: Thread Pool con awaitTermination.
    //
    //


    System.out.println();
    t1 = System.nanoTime();
    ExecutorService exec3= Executors.newFixedThreadPool(numHebras);

    File           archivo3 = null;
    FileReader     fr3      = null;
    BufferedReader br3      = null;
    archivo3 = new File ("codPueblos.txt");
    try {
      fr3 = new FileReader(archivo3);
      br3 = new BufferedReader(fr3);
      String linea;
      while ((linea = br3.readLine()) != null) {
        exec3.execute(new TareaPool(fecha, MaxMin, Integer.parseInt(linea)));
      }
    }catch (Exception e){
      e.printStackTrace();
    }

    exec3.shutdown();
    try{
      while (!exec3.awaitTermination(2L, TimeUnit.MILLISECONDS));
    } catch (InterruptedException e1){
      e1.printStackTrace();
    }


    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela: await Termination.     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp);
    System.out.println( "  Pueblo: "+  MaxMin.damePueblo() + " , Maxima = " +
            MaxMin.dameTemperaturaMaxima() + " , Minima = " +
            MaxMin.dameTemperaturaMinima() );

    //
    // Implementacion paralela: Thread Pool con Future.
    //

    System.out.println();
    t1 = System.nanoTime();

    Future<PuebloMaximaMinima> f;
    exec=Executors.newFixedThreadPool(numHebras);
    ArrayList<Future<PuebloMaximaMinima>> temp=new ArrayList<>();

    class TareaFuture implements Callable<PuebloMaximaMinima>{
      String fecha;
      int codPueblo;
      public TareaFuture (String fecha, int codPueblo) {
        this.fecha = fecha;
        this.codPueblo = codPueblo;
      }
      @Override
      public PuebloMaximaMinima call(){
        PuebloMaximaMinima MaxMinFuture=new PuebloMaximaMinima();
        EjemploTemperaturaProvincia.ProcesaPueblo(fecha, codPueblo, MaxMinFuture, false);
        return MaxMinFuture;
      }
    }
    try {
      fr = new FileReader(archivo);
      br = new BufferedReader(fr);
      String linea;
      while ((linea = br.readLine()) != null) {
        f=exec.submit(new TareaFuture(fecha,Integer.parseInt(linea)));
        temp.add(f);
      }
      exec.shutdown();
      int diferencia=0;
      for(int i = 0; i < temp.size(); i++){
        f=temp.get(i);
        PuebloMaximaMinima candidato=f.get();
        MaxMin.actualizaMaxMin(candidato.damePueblo(), candidato.dameCodigo(), candidato.dameTemperaturaMaxima(), candidato.dameTemperaturaMinima());
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion Future:     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp);
    System.out.println( "  Pueblo: "+  MaxMin.damePueblo() + " , Maxima = " +
            MaxMin.dameTemperaturaMaxima() + " , Minima = " +
            MaxMin.dameTemperaturaMinima() );
  }
  
  // --------------------------------------------------------------------------
  public static void obtenMayorDiferencia_SecuencialAFichero (String fecha, int codProvincia,
                                                              PuebloMaximaMinima MaxMin) {
    FileWriter  fichero = null;
    PrintWriter pw      = null;
    
    // Verifica todas los codigos de pueblos y escribe el fichero  "codPueblos.txt"
    try
    {
      // Apertura del fichero y creacion de FileWriter para poder
      // hacer una escritura comoda (disponer del metodo println()).
      fichero = new FileWriter("codPueblos.txt");
      pw = new PrintWriter(fichero);
    
      for (int i=codProvincia*1000; i<(codProvincia+1)*1000; i++){
        if (ProcesaPueblo(fecha, i, MaxMin, false) == true) {
          pw.println(i);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // Se aprovecha el finally para asegurar el cierre del fichero,
        // tanto si todo va bien como si salta una excepcion.
        if (null != fichero)
          fichero.close();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
  }

  // --------------------------------------------------------------------------
  
  public static void obtenMayorDiferencia_SecuencialDeFichero (String fecha, int codProvincia,
                                                                  PuebloMaximaMinima MaxMin) {
    File           archivo = null;
    FileReader     fr      = null;
    BufferedReader br      = null;

    // Procesa el fichero "codPueblos.txt"
    try
    {
      // Apertura del fichero y creacion de BufferedReader para poder
      // hacer una lectura comoda (disponer del metodo readLine()).
      archivo = new File ("codPueblos.txt");
      fr = new FileReader (archivo);
      br = new BufferedReader(fr);

      String linea;
      while( ( linea = br.readLine() ) != null ) {
        int codPueblo = Integer.parseInt(linea);
        ProcesaPueblo(fecha, codPueblo, MaxMin, false);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      // Se aprovecha el finally para asegurar el cierre del fichero,
      // tanto si todo va bien como si salta una excepcion.
      try{
        if( null != fr ){
          fr.close();
        }
      }catch (Exception e2){
        e2.printStackTrace();
      }
    }
  }

  public static void obtenMayorDiferencia_ParalelaDeFichero (int codProvincia,
                                                               PuebloMaximaMinima MaxMin, BlockingQueue<Tarea> cola) {
    File           archivo = null;
    FileReader     fr      = null;
    BufferedReader br      = null;

    // Procesa el fichero "codPueblos.txt"
    try
    {
      // Apertura del fichero y creacion de BufferedReader para poder
      // hacer una lectura comoda (disponer del metodo readLine()).
      archivo = new File ("codPueblos.txt");
      fr = new FileReader (archivo);
      br = new BufferedReader(fr);

      String linea;
      while( ( linea = br.readLine() ) != null ) {
        Tarea t= new Tarea(Integer.parseInt(linea), false);
        cola.put(t);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      // Se aprovecha el finally para asegurar el cierre del fichero,
      // tanto si todo va bien como si salta una excepcion.
      try{
        if( null != fr ){
          fr.close();
        }
      }catch (Exception e2){
        e2.printStackTrace();
      }
    }
  }
  // --------------------------------------------------------------------------
  public static boolean ProcesaPueblo (String fecha, int codPueblo, PuebloMaximaMinima MaxMin,
                                       boolean imprime) {
    URL            url;
    InputStream    is = null;
    BufferedReader br;
    String         line, poblacion = new String (), provincia = new String ();
    int            state, num[]=new int[2];
    boolean        res = false;
    
    // Procesamiento de la informacion XML asociada a codPueblo
    // Actualizacion de MaxMin de acuerdo a los valores obtenidos
    try {
      String urlStr = "http://www.aemet.es/xml/municipios/localidad_" +
                      String.format("%05d",codPueblo)+ ".xml";
      url = new URL(urlStr);
      is  = url.openStream();  // throws an IOException
      br  = new BufferedReader(new InputStreamReader(is));
      if (imprime) System.out.println(urlStr);

      state = 0;      
      while (((line = br.readLine()) != null) && (state < 6)) {
        //        System.out.println (line);
        if ((state == 0) && (line.contains ("nombre"))) {
          poblacion=line.split(">")[1].split("<")[0].split("/")[0];
          state++;
        } else if ((state == 1) && (line.contains ("provincia"))) {
          provincia=line.split(">")[1].split("<")[0].split("/")[0];
          state++;
        } else if ((state == 2) && (line.contains (fecha))) {
          state++;
        } else if ((state == 3) && (line.contains ("temperatura"))) {
          state++;
        } else if ((state > 3) && ((line.contains ("maxima")) || (line.contains ("minima")))) {
          num[state-4] = Integer.parseInt (line.split(">")[1].split("<")[0]);
          state++;
        }
      }
      // System.out.println("(" + codPueblo + ") " + poblacion + "(" + provincia + ") => " +
      //                    "(" + num[0] + " , " + num[1] + ")");
      MaxMin.actualizaMaxMin (poblacion, codPueblo, num[0], num[1]);
      res = true;
    } catch (MalformedURLException mue) {
      mue.printStackTrace();
    } catch (IOException ioe) {
      //      ioe.printStackTrace();
    } finally {
      try {
        if (is != null) is.close();
      } catch (IOException ioe) {
        // nothing to see here
      }
    }
    return res;
  }
}

// ============================================================================
class PuebloMaximaMinima {
// ============================================================================
  String poblacion;
  int    codigo, max, min;
  
  
  // --------------------------------------------------------------------------
  public PuebloMaximaMinima() {
    poblacion = null;
    codigo    = -1;
    max       = -1;
    min       = -1;
  }
  
  // --------------------------------------------------------------------------
  public synchronized void actualizaMaxMin( String poblacion, int codigo, int max, int min ) {
    if ((this.poblacion == null) || ((this.max-this.min) < (max-min)) ||
        (((this.max-this.min) == (max-min)) && (this.min > min)) ||
        (((this.max-this.min) == (max-min)) && (this.min == min) && (this.codigo > codigo))
        ) {
      //      (((this.max-this.min) == (max-min)) && (this.max < max))) {
      this.poblacion = poblacion;
      this.codigo = codigo;
      this.max = max;
      this.min = min;
    }
  }
  
  // --------------------------------------------------------------------------
  public String damePueblo() {
    return this.poblacion + "(" + this.codigo + ")";
  }
  
  // --------------------------------------------------------------------------
  public int dameCodigo() {
    return this.codigo;
  }
  
  // --------------------------------------------------------------------------
  public int dameTemperaturaMaxima() {
    return this.max;
  }
  
  // --------------------------------------------------------------------------
  public int dameTemperaturaMinima() {
    return this.min;
  }
}


