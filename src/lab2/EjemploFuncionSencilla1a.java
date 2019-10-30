package lab2;

// ============================================================================
class EjemploFuncionSencilla1a2 {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int     n, numHebras;
    long    t1, t2;
    double  tt, sumaX, sumaY;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <tamanyo>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
      n         = Integer.parseInt( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      n         = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    // Crea los vectores.
    double vectorX[] = new double[ n ];
    double vectorY[] = new double[ n ];

    //
    // Implementacion secuencial.
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    t1 = System.nanoTime();
    for( int i = 0; i < n; i++ ) {
      vectorY[ i ] = evaluaFuncion( vectorX[ i ] );
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo secuencial (seg.):                    " + tt );
    //// imprimeResultado( vectorX, vectorY );
    // Comprueba el resultado. 
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );

    //Paralelo cíclico
    inicializaVectorX(vectorX);
    inicializaVectorY(vectorY);
    EvaluaCiclica[] hebrasC= new EvaluaCiclica[numHebras];
    long t3 =System.nanoTime();
    for(int i =0; i< numHebras; i++){
      hebrasC[i]=new EvaluaCiclica(i, vectorX, vectorY, numHebras, n);
      hebrasC[i].start();
    }
    for(int i =0; i< numHebras; i++){
      try{
        hebrasC[i].join();
      }catch (InterruptedException e){
        e.printStackTrace();
      }
    }
    long t4 =System.nanoTime();
    double tt2 = ( ( double ) ( t4 - t3 ) ) / 1.0e9;
    System.out.println( "Tiempo paralelo ciclico(seg.):                    " + tt2 );
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );

    //Paralelo por Bloques
    inicializaVectorX(vectorX);
    inicializaVectorY(vectorY);
    EvaluaBloques[] hebrasB= new EvaluaBloques[numHebras];
    long t5 =System.nanoTime();
    for(int i =0; i< numHebras; i++){
      hebrasB[i]=new EvaluaBloques(i, vectorX, vectorY, numHebras, n);
      hebrasB[i].start();
    }
    for(int i =0; i< numHebras; i++){
      try{
        hebrasB[i].join();
      }catch (InterruptedException e){
        e.printStackTrace();
      }
    }
    long t6 =System.nanoTime();
    double tt3 = ( ( double ) ( t6 - t5 ) ) / 1.0e9;
    System.out.println( "Tiempo paralelo bloques (seg.):                    " + tt3);
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );

    System.out.println( "Fin del programa." );
  }

  // --------------------------------------------------------------------------
  static void inicializaVectorX( double vectorX[] ) {
    if( vectorX.length == 1 ) {
      vectorX[ 0 ] = 0.0;
    } else {
      for( int i = 0; i < vectorX.length; i++ ) {
        vectorX[ i ] = 10.0 * ( double ) i / ( ( double ) vectorX.length - 1 );
      }
    }
  }

  // --------------------------------------------------------------------------
  static void inicializaVectorY( double vectorY[] ) {
    for( int i = 0; i < vectorY.length; i++ ) {
      vectorY[ i ] = 0.0;
    }
  }

  // --------------------------------------------------------------------------
  static double sumaVector( double vector[] ) {
    double  suma = 0.0;
    for( int i = 0; i < vector.length; i++ ) {
      suma += vector[ i ];
    }
    return suma;
  }

  // --------------------------------------------------------------------------
  static double evaluaFuncion( double x ) {
    return x * (3/2);
  }

  // --------------------------------------------------------------------------
  static void imprimeVector( double vector[] ) {
    for( int i = 0; i < vector.length; i++ ) {
      System.out.println( " vector[ " + i + " ] = " + vector[ i ] );
    }
  }

  // --------------------------------------------------------------------------
  static void imprimeResultado( double vectorX[], double vectorY[] ) {
    for( int i = 0; i < Math.min( vectorX.length, vectorY.length ); i++ ) {
      System.out.println( "  i: " + i + 
                          "  x: " + vectorX[ i ] +
                          "  y: " + vectorY[ i ] );
    }
  }

}

