package lab2;

class ImprimeBloques extends Thread{
  int miID;
  int numHebras;
  int n;

  public ImprimeBloques(int miID, int numHebras, int n){
    this.miID = miID;
    this.numHebras = numHebras;
    this.n = n;
  }

  public void run(){
    int elemsPorHebra = (int)Math.ceil((double)n/numHebras);
    int principio = elemsPorHebra * miID;
    int fin = Math.min(principio + elemsPorHebra, n);
    for (int i=principio; i<fin; i++){
      System.out.println(i);
    }
  }
}

// ============================================================================
class Lab2UnoDos {
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
      (new ImprimeBloques(i, numHebras, n)).start();
    }

  }
}

