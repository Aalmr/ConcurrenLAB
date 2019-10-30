package lab3;

import java.util.concurrent.atomic.AtomicInteger;

class HebraCiclica extends Thread{
  int miId;
  long[] vectorNumeros;
  int numHebras;
  public HebraCiclica(int miId, long[] vectorNumeros, int numHebras){
    this.miId=miId;
    this.vectorNumeros=vectorNumeros;
    this.numHebras=numHebras;
  }
  public void run(){
    for(int i=miId; i<vectorNumeros.length; i+=numHebras){
      if(EjemploMuestraPrimosEnVector2a.esPrimo(vectorNumeros[i]))
        System.out.println("  Encontrado primo: " + vectorNumeros[i]);
    }
  }
}
class HebraBloques extends Thread{
  int miId;
  long[] vectorNumeros;
  int numHebras;
  public HebraBloques(int miId, long[] vectorNumeros, int numHebras){
    this.miId=miId;
    this.vectorNumeros=vectorNumeros;
    this.numHebras=numHebras;
  }
  public void run(){
    int elementosHebra = vectorNumeros.length/numHebras;
    int init= elementosHebra*miId;
    int fin=Math.min(elementosHebra, vectorNumeros.length-init);
    for(int i=init; i<fin; i++){
      if(EjemploMuestraPrimosEnVector2a.esPrimo(vectorNumeros[i]))
        System.out.println("  Encontrado primo: " + vectorNumeros[i]);
    }
  }
}
class HebraDinamica extends Thread{
  static AtomicInteger index=new AtomicInteger(0);
  long[] vectorNumeros;
  int numHebras;
  public HebraDinamica(long[] vectorNumeros, int numHebras){
    this.vectorNumeros=vectorNumeros;
    this.numHebras=numHebras;
  }
  public void run(){
    int cont=index.incrementAndGet();
    while (cont<vectorNumeros.length){
      if(EjemploMuestraPrimosEnVector2a.esPrimo(vectorNumeros[cont]))
        System.out.println("  Encontrado primo: " + vectorNumeros[cont]);
      cont=index.incrementAndGet();
    }
  }
}

// ===========================================================================
public class EjemploMuestraPrimosEnVector2a {
// ==========================================================================
  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    int     numHebras;
    long    t1, t2;
    double  ts, tc, tb, td;
    long    vectorNumeros[] = {
                200000033L, 200000039L, 200000051L, 200000069L, 
                200000081L, 200000083L, 200000089L, 200000093L, 
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
            };
    //// long    vectorNumeros[] = {
                //// 200000033L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                //// 200000039L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                //// 200000051L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                //// 200000069L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                //// 200000081L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                //// 200000083L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                //// 200000089L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                //// 200000093L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
            //// };
    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 1 ) {
      System.err.println( "Uso: java programa <numHebras>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }
    //
    // Implementacion secuencial.
    //
   /* System.out.println( "" );
    System.out.println( "Implementacion secuencial." );
    t1 = System.nanoTime();
    for( int i = 0; i < vectorNumeros.length; i++ ) {
      if( esPrimo( vectorNumeros[ i ] ) ) {
        System.out.println( "  Encontrado primo: " + vectorNumeros[ i ] );
      }
    }
    t2 = System.nanoTime();
    ts = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo secuencial (seg.):                    " + ts );



    //
    // Implementacion paralela ciclica.
    //
    System.out.println( "" );
    System.out.println( "Implementacion paralela ciclica." );
    t1 = System.nanoTime();
    // Gestion de hebras para la implementacion paralela ciclica
    // ....
    HebraCiclica[] hebrasCiclicas= new HebraCiclica[numHebras];
    for(int i = 0;i<numHebras; i++ ){
      hebrasCiclicas[i]=new HebraCiclica(i, vectorNumeros, numHebras);
      hebrasCiclicas[i].start();
    }
    for(int i = 0;i<numHebras; i++ ){
      try {
        hebrasCiclicas[i].join();
      }catch (InterruptedException e ){
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tc = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    double incremento= tc-ts;
    System.out.println( "Tiempo paralela ciclica (seg.):              " + tc );
    System.out.println( "Incremento paralela ciclica:                 " + incremento);
    //
    // Implementacion paralela por bloques.
    //
    System.out.println( "" );
    System.out.println( "Implementacion paralela por bloques." );
    t1 = System.nanoTime();
    HebraBloques[] hebrasBloques= new HebraBloques[numHebras];
    for(int i = 0;i<numHebras; i++ ){
      hebrasBloques[i]=new HebraBloques(i, vectorNumeros, numHebras);
      hebrasBloques[i].start();
    }
    for(int i = 0;i<numHebras; i++ ){
      try {
        hebrasBloques[i].join();
      }catch (InterruptedException e ){
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tb = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    incremento= tb-ts;
    System.out.println( "Tiempo paralela en bloques (seg.):              " + tb );
    System.out.println( "Incremento paralela en bloques:                 " + incremento);
    //
    // Implementacion paralela dinamica.
    //
    // ....

    */
    System.out.println( "" );
    System.out.println( "Implementacion paralela ciclica." );
    t1 = System.nanoTime();
    HebraDinamica[] hebrasDinamicas= new HebraDinamica[numHebras];
    for(int i = 0;i<numHebras; i++ ){
      hebrasDinamicas[i]=new HebraDinamica(vectorNumeros, numHebras);
      hebrasDinamicas[i].start();
    }
    for(int i = 0;i<numHebras; i++ ){
      try {
        hebrasDinamicas[i].join();
      }catch (InterruptedException e ){
        e.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    td = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    //incremento= td-ts;
    System.out.println( "Tiempo paralela ciclica (seg.):              " + td );
    //System.out.println( "Incremento paralela ciclica:                 " + incremento);
  }

  // -------------------------------------------------------------------------
  static boolean esPrimo( long num ) {
    boolean primo;
    if( num < 2 ) {
      primo = false;
    } else {
      primo = true;
      long i = 2;
      while( ( i < num )&&( primo ) ) { 
        primo = ( num % i != 0 );
        i++;
      }
    }
    return( primo );
  }
}
