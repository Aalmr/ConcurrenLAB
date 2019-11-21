package lab6;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.counting;
import java.util.Map;
import java.util.stream.*;
import java.util.function.*;
import static java.util.stream.Collectors.*;
import java.util.Comparator.*;


class MiHebra extends Thread{
  private int miId;
  private ArrayList<String> arrayLineas;
  private HashMap<String,Integer>  hmCuentaPalabras;
  private int numHebras;

  public MiHebra(int miId, ArrayList<String> arrayLineas, HashMap<String,Integer>  hmCuentaPalabras, int numHebras){
   this.miId=miId;
   this.arrayLineas=arrayLineas;
   this.hmCuentaPalabras=hmCuentaPalabras;
   this.numHebras=numHebras;
  }

  public void run(){
    String palabraActual;
    for( int i = miId; i < arrayLineas.size(); i+=numHebras) {
      // Procesa la linea "i".
      String[] palabras = arrayLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[j].trim();
        if (palabraActual.length() > 0) {
          EjemploPalabraMasUsada1a.contabilizaPalabraConcurrente(hmCuentaPalabras, palabraActual);
        }
      }
    }
  }
}
class MiHebra_2 extends Thread{
  private int miId;
  private ArrayList<String> arrayLineas;
  private Hashtable<String,Integer>  hmCuentaPalabras;
  private int numHebras;

  public MiHebra_2(int miId, ArrayList<String> arrayLineas, Hashtable<String,Integer>  hmCuentaPalabras, int numHebras){
    this.miId=miId;
    this.arrayLineas=arrayLineas;
    this.hmCuentaPalabras=hmCuentaPalabras;
    this.numHebras=numHebras;
  }

  public void run(){
    String palabraActual;
    for( int i = miId; i < arrayLineas.size(); i+=numHebras) {
      // Procesa la linea "i".
      String[] palabras = arrayLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[j].trim();
        if (palabraActual.length() > 0) {
          EjemploPalabraMasUsada1a.contabilizaPalabraTable(hmCuentaPalabras, palabraActual);
        }
      }
    }
  }
}

class MiHebra_3 extends Thread{
  private int miId;
  private ArrayList<String> arrayLineas;
  private ConcurrentHashMap<String,Integer> hmCuentaPalabras;
  private int numHebras;

  public MiHebra_3(int miId, ArrayList<String> arrayLineas, ConcurrentHashMap<String,Integer> hmCuentaPalabras, int numHebras){
    this.miId=miId;
    this.arrayLineas=arrayLineas;
    this.hmCuentaPalabras=hmCuentaPalabras;
    this.numHebras=numHebras;
  }

  public void run(){
    String palabraActual;
    for( int i = miId; i < arrayLineas.size(); i+=numHebras) {
      // Procesa la linea "i".
      String[] palabras = arrayLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[j].trim();
        if (palabraActual.length() > 0) {
          EjemploPalabraMasUsada1a.contabilizaPalabraConHashMap(hmCuentaPalabras, palabraActual);
        }
      }
    }
  }
}

class MiHebra_4 extends Thread{
  private int miId;
  private ArrayList<String> arrayLineas;
  private ConcurrentHashMap<String,Integer> hmCuentaPalabras;
  private int numHebras;

  public MiHebra_4(int miId, ArrayList<String> arrayLineas, ConcurrentHashMap<String,Integer> hmCuentaPalabras, int numHebras){
    this.miId=miId;
    this.arrayLineas=arrayLineas;
    this.hmCuentaPalabras=hmCuentaPalabras;
    this.numHebras=numHebras;
  }

  public void run(){
    String palabraActual;
    for( int i = miId; i < arrayLineas.size(); i+=numHebras) {
      // Procesa la linea "i".
      String[] palabras = arrayLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[j].trim();
        if (palabraActual.length() > 0) {
          EjemploPalabraMasUsada1a.contabilizaPalabraConHashMap2(hmCuentaPalabras, palabraActual);
        }
      }
    }
  }
}

class MiHebra_5 extends Thread{
  private int miId;
  private ArrayList<String> arrayLineas;
  private ConcurrentHashMap<String,AtomicInteger> hmCuentaPalabras;
  private int numHebras;

  public MiHebra_5(int miId, ArrayList<String> arrayLineas, ConcurrentHashMap<String, AtomicInteger> hmCuentaPalabras, int numHebras){
    this.miId=miId;
    this.arrayLineas=arrayLineas;
    this.hmCuentaPalabras=hmCuentaPalabras;
    this.numHebras=numHebras;
  }

  public void run(){
    String palabraActual;
    for( int i = miId; i < arrayLineas.size(); i+=numHebras) {
      // Procesa la linea "i".
      String[] palabras = arrayLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[j].trim();
        if (palabraActual.length() > 0) {
          EjemploPalabraMasUsada1a.contabilizaPalabraConHashMap3(hmCuentaPalabras, palabraActual);
        }
      }
    }
  }
}

// ============================================================================
class EjemploPalabraMasUsada1a {
// ============================================================================

  // -------------------------------------------------------------------------
  public static void main( String[] args ) {
    long                     t1, t2;
    double                   tt;
    int                      numHebras;
    String                   nombreFichero, palabraActual;
    ArrayList<String>        arrayLineas;
    HashMap<String,Integer>  hmCuentaPalabras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <fichero>" );
      System.exit( -1 );
    }
    try {
      numHebras     = Integer.parseInt( args[ 0 ] );
      nombreFichero = args[ 1 ];
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      nombreFichero = "";
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    // Lectura y carga de lineas en "arrayLineas".
    arrayLineas = readFile( nombreFichero );
    System.out.println( "Numero de lineas leidas: " + arrayLineas.size() );
    System.out.println();

    //
    // Implementacion secuencial sin temporizar.
    //
    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
    for( int i = 0; i < arrayLineas.size(); i++ ) {
      // Procesa la linea "i".
      String[] palabras = arrayLineas.get( i ).split( "\\W+" );
      for( int j = 0; j < palabras.length; j++ ) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[ j ].trim();
        if( palabraActual.length() > 0 ) {
          contabilizaPalabra( hmCuentaPalabras, palabraActual );
        }
      }
    }

    //
    // Implementacion secuencial.
    //
    t1 = System.nanoTime();
    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
    for( int i = 0; i < arrayLineas.size(); i++ ) {
      // Procesa la linea "i".
      String[] palabras = arrayLineas.get( i ).split( "\\W+" );
      for( int j = 0; j < palabras.length; j++ ) {
        // Procesa cada palabra de la linea "i", si es distinta de blancos.
        palabraActual = palabras[ j ].trim();
        if( palabraActual.length() > 0 ) {
          contabilizaPalabra( hmCuentaPalabras, palabraActual );
        }
      }
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. secuencial: " );
    imprimePalabraMasUsadaYVeces( hmCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt );
    System.out.println( "Num. elems. tabla hash: " + hmCuentaPalabras.size() );
    System.out.println();


    //
    // Implementacion paralela 1: Uso de synchronizedMap.
    //
    t1 = System.nanoTime();
    HashMap<String, Integer> maCuentaPalabras=new HashMap<>();
    MiHebra[] hebras= new MiHebra[numHebras];
    for(int i=0; i<numHebras; i++){
      hebras[i]=new MiHebra(i, arrayLineas, maCuentaPalabras, numHebras);
      hebras[i].start();
    }
    for(int i=0; i<numHebras; i++){
      try{
        hebras[i].join();
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    double tt2 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. HashMap: " );
    imprimePalabraMasUsadaYVeces( maCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt2  + " , Incremento " + tt/tt2);
    System.out.println( "Num. elems. HashMap: " + maCuentaPalabras.size() );
    System.out.println();

    //
    // Implementacion paralela 2: Uso de Hashtable.
    //

    t1 = System.nanoTime();
    Hashtable<String, Integer> taCuentaPalabras=new Hashtable<>();
    MiHebra_2[] hebras2= new MiHebra_2[numHebras];
    for(int i=0; i<numHebras; i++){
      hebras2[i]=new MiHebra_2(i, arrayLineas, taCuentaPalabras, numHebras);
      hebras2[i].start();
    }
    for(int i=0; i<numHebras; i++){
      try{
        hebras2[i].join();
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    double tt3 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. tabla hash: " );
    imprimePalabraMasUsadaYVeces( taCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt3  + " , Incremento " + tt/tt3);
    System.out.println( "Num. elems. tabla hash: " + taCuentaPalabras.size() );
    System.out.println();

    //
    // Implementacion paralela 3: Uso de ConcurrentHashMap
        t1 = System.nanoTime();
    ConcurrentHashMap<String, Integer> conCuentaPalabras=new ConcurrentHashMap<>();
    MiHebra_3[] hebras3= new MiHebra_3[numHebras];
    for(int i=0; i<numHebras; i++){
      hebras3[i]=new MiHebra_3(i, arrayLineas, conCuentaPalabras, numHebras);
      hebras3[i].start();
    }
    for(int i=0; i<numHebras; i++){
      try{
        hebras3[i].join();
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    double tt4 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. ConcurrentHashMap: " );
    imprimePalabraMasUsadaYVeces( taCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt4  + " , Incremento " + tt/tt4);
    System.out.println( "Num. elems. ConcurrentHashMap: " + conCuentaPalabras.size() );
    System.out.println();

    //
    // ...

    //
    // Implementacion paralela 4: Uso de ConcurrentHashMap 
    //
    // ...
        t1 = System.nanoTime();
    conCuentaPalabras=new ConcurrentHashMap<>();
    MiHebra_4[] hebras4= new MiHebra_4[numHebras];
    for(int i=0; i<numHebras; i++){
      hebras4[i]=new MiHebra_4(i, arrayLineas, conCuentaPalabras, numHebras);
      hebras4[i].start();
    }
    for(int i=0; i<numHebras; i++){
      try{
        hebras4[i].join();
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    double tt5 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. ConcurrentHashMap no-cerrojos: " );
    imprimePalabraMasUsadaYVeces( conCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt5  + " , Incremento " + tt/tt5);
    System.out.println( "Num. elems. ConcurrentHashMap no-cerrojos: " + conCuentaPalabras.size() );
    System.out.println();


    //
    // Implementacion paralela 5: Uso de ConcurrentHashMap
    //
    // ...
        t1 = System.nanoTime();
    ConcurrentHashMap<String, AtomicInteger> AtomicCuentaPalabras=new ConcurrentHashMap<>();
    MiHebra_5[] hebras5= new MiHebra_5[numHebras];
    for(int i=0; i<numHebras; i++){
      hebras5[i]=new MiHebra_5(i, arrayLineas, AtomicCuentaPalabras, numHebras);
      hebras5[i].start();
    }
    for(int i=0; i<numHebras; i++){
      try{
        hebras5[i].join();
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    double tt6 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. ConcurrentHashMap no-cerrojos: " );
    imprimePalabraMasUsadaYVecesAtomic( AtomicCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt6  + " , Incremento " + tt/tt6);
    System.out.println( "Num. elems. ConcurrentHashMap no-cerrojos: " + conCuentaPalabras.size() );
    System.out.println();
    /*
    //
    // Implementacion paralela 6: Uso de ConcurrentHashMap 
    //
    // ...
*/
    //
    // Implementacion paralela 7: Uso de Streams
    //
    // ...

    t1 = System.nanoTime();
    Map<String , Long> stCuentaPalabras = arrayLineas.parallelStream()
            .filter(s -> s!=null )
            .map(s -> s.split("\\W+"))
            .flatMap(Arrays:: stream)
            .map(String::trim)
            .filter(s -> (s.length() > 0))
            .collect(groupingBy(s -> s , counting ()));
    t2 = System.nanoTime();
    double tt7 = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implemen. Streams: " );
    imprimePalabraMasUsadaYVecesStreamLong( stCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt7  + " , Incremento " + tt/tt7);
    System.out.println( "Num. elems. Streams: " + stCuentaPalabras.size() );
    System.out.println();


    System.out.println( "Fin de programa." );
  }

  // -------------------------------------------------------------------------
  public static ArrayList<String> readFile( String fileName ) {
    BufferedReader    br; 
    String            linea;
    ArrayList<String> data = new ArrayList<String>();

    try {
      br = new BufferedReader( new FileReader( fileName ) );
      while( ( linea = br.readLine() ) != null ) {
        //// System.out.println( "Leida linea: " + linea );
        data.add( linea );
      }
      br.close(); 
    } catch( FileNotFoundException ex ) {
      ex.printStackTrace();
    } catch( IOException ex ) {
      ex.printStackTrace();
    }
    return data;
  }

  // -------------------------------------------------------------------------
  public static void contabilizaPalabra( 
                         HashMap<String,Integer> cuentaPalabras,
                         String palabra ) {
    Integer numVeces = cuentaPalabras.get( palabra );
    if( numVeces != null ) {
      cuentaPalabras.put( palabra, numVeces+1 );
    } else {
      cuentaPalabras.put( palabra, 1 );
    }
  }
  public static synchronized void contabilizaPalabraConHashMap(
          ConcurrentHashMap<String,Integer> cuentaPalabras,
          String palabra ) {
    Integer numVeces = cuentaPalabras.get( palabra );
    if( numVeces != null ) {
      cuentaPalabras.put( palabra, numVeces+1 );
    } else {
      cuentaPalabras.put( palabra, 1 );
    }
  }

  public static void contabilizaPalabraConHashMap2(
          ConcurrentHashMap<String,Integer> cuentaPalabras,
          String palabra ) {
    Integer contadorActual;
    boolean modif;
    contadorActual =cuentaPalabras.putIfAbsent(palabra, 1);
    if(contadorActual!=null){
      do{
        contadorActual=cuentaPalabras.get(palabra);
        modif=cuentaPalabras.replace(palabra, contadorActual, contadorActual+1);
      }while(!modif);
    }
  }

  public static void contabilizaPalabraConHashMap3(
          ConcurrentHashMap<String,AtomicInteger> cuentaPalabras,
          String palabra ) {
    AtomicInteger actual;
    actual =cuentaPalabras.putIfAbsent(palabra, new AtomicInteger(1));
    if(actual!=null){
        cuentaPalabras.get(palabra).getAndIncrement();
    }
  }


  public synchronized static void contabilizaPalabraTable(
          Hashtable<String,Integer> cuentaPalabras,
          String palabra ) {
    Integer numVeces = cuentaPalabras.get( palabra );
    if( numVeces != null ) {
      cuentaPalabras.put( palabra, cuentaPalabras.get( palabra )+1 );
    } else {
      cuentaPalabras.put( palabra, 1 );
    }
  }

  public synchronized static void contabilizaPalabraConcurrente(HashMap<String,Integer> cuentaPalabras,
          String palabra ) {
    Integer numVeces = cuentaPalabras.get( palabra );
    if( numVeces != null ) {
      cuentaPalabras.put( palabra, numVeces+1 );
    } else {
      cuentaPalabras.put( palabra, 1 );
    }
  }
  // --------------------------------------------------------------------------
  static void imprimePalabraMasUsadaYVeces(
                  Map<String, Integer> cuentaPalabras ) {
    ArrayList<Map.Entry> lista = 
        new ArrayList<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    int    numVecesPalabraMasUsada = 0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      int numVeces = ( Integer ) lista.get( i ).getValue();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " + 
                         "veces: " + numVecesPalabraMasUsada + " )" );
  }
  static void imprimePalabraMasUsadaYVecesAtomic(
          Map<String,AtomicInteger> cuentaPalabras ) {
    ArrayList<Map.Entry> lista =
            new ArrayList<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    int numVecesPalabraMasUsada=0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      AtomicInteger numVecesAtomic = ( AtomicInteger ) lista.get( i ).getValue();
      int numVeces=numVecesAtomic.intValue();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " +
            "veces: " + numVecesPalabraMasUsada + " )" );
  }


  static void imprimePalabraMasUsadaYVecesStreamLong(
          Map<String,Long> cuentaPalabras ) {
    ArrayList<Map.Entry> lista =
          new ArrayList<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    long    numVecesPalabraMasUsada = 0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      long numVeces = ( Long ) lista.get( i ).getValue();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " +
            "veces: " + numVecesPalabraMasUsada + " )" );  }
  // --------------------------------------------------------------------------
  static void printCuentaPalabrasOrdenadas(
                  HashMap<String,Integer> cuentaPalabras ) {
    int             i, numVeces;
    List<Map.Entry> list = new ArrayList<Map.Entry>( cuentaPalabras.entrySet() );

    // Ordena por valor.
    Collections.sort( 
        list,
        new Comparator<Map.Entry>() {
            public int compare( Map.Entry e1, Map.Entry e2 ) {
              Integer i1 = ( Integer ) e1.getValue();
              Integer i2 = ( Integer ) e2.getValue();
              return i2.compareTo( i1 );
            }
        }
    );
    // Muestra contenido.
    i = 1;
    System.out.println( "Veces Palabra" );
    System.out.println( "-----------------" );
    for( Map.Entry e : list ) {
      numVeces = ( ( Integer ) e.getValue () ).intValue();
      System.out.println( i + " " + e.getKey() + " " + numVeces );
      i++;
    }
    System.out.println( "-----------------" );
  }
}


