package lab2;

class ImprimeCiclico extends Thread{
  int miID;
  int numHebras;
  int n;

  public ImprimeCiclico(int miID, int numHebras, int n){
    this.miID = miID;
    this.numHebras = numHebras;
    this.n = n;
  }

  public void run(){
    for (int i=miID; i<n; i+=numHebras){
      System.out.println(i);
    }
  }
}

// ============================================================================
class UnoUno {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int  n, numHebras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <n>" );
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

    for (int i=0; i<numHebras; i++){
      (new ImprimeCiclico(i, numHebras, n)).start();
    }

  }
}

