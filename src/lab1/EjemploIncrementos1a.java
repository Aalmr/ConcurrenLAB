package lab1;

class Sumatorio extends Thread{
  CuentaIncrementos1a cuenta;
  int miID;

  public Sumatorio(int miID, CuentaIncrementos1a cuenta){
    this.cuenta = cuenta;
    this.miID = miID;
  }

  public void run(){
    System.out.println("Empieza a contar");
    for (int i=0; i<1000000;i++){
      cuenta.incrementaContador();
    }

    System.out.println("Termina de contar");
  }
}

// ============================================================================
class CuentaIncrementos1a {
// ============================================================================
  long contador = 0;

  // --------------------------------------------------------------------------
  void incrementaContador() {
    contador++;
  }

  // --------------------------------------------------------------------------
  long dameContador() {
    return( contador );
  }
}


// ============================================================================
class EjemploIncrementos1a {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int  numHebras;

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

    System.out.println( "numHebras: " + numHebras );
    CuentaIncrementos1a cuenta = new CuentaIncrementos1a();
    Sumatorio[] hebras = new Sumatorio[numHebras];
    System.out.println(cuenta.dameContador());
    for (int i = 0; i < numHebras; i++){
      hebras[i] = new Sumatorio(i, cuenta);
      hebras[i].start();
    }

    for (int i = 0; i < numHebras; i++){
      try{
        hebras[i].join();
      }
      catch (InterruptedException e){
        e.printStackTrace();
      }
    }

    System.out.println(cuenta.dameContador());
  }
}

