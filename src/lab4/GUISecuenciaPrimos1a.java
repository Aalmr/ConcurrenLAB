package lab4;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class HebraTrabajadora extends Thread{
  boolean flag;
  JTextField txfMensajes;
  ZonaIntercambio intercambio;
  HebraTrabajadora(JTextField txfMensajes, ZonaIntercambio intercambio){
    flag=true;
    this.txfMensajes=txfMensajes;
    this.intercambio=intercambio;
  }
  public void stopNow(){
    flag=false;
  }
  public void run(){
    int cont=0;
    while(flag){
      if(GUISecuenciaPrimos1a.esPrimo(cont)) {
        int finalCont = cont;
        SwingUtilities.invokeLater(() -> {
          txfMensajes.setText(Integer.toString(finalCont));
        });
        try {
          Thread.sleep(intercambio.getMiliseconds());
        }catch (InterruptedException e){
          e.printStackTrace();
        }
      }
      cont++;
    }
  }
}
class ZonaIntercambio{
    private volatile long miliseconds;
    public ZonaIntercambio(long valor){
      this.miliseconds=valor;
    }

    void setMiliseconds(long newM){
        miliseconds=newM;
    }
    long getMiliseconds(){
        return miliseconds;
    }

}
// ===========================================================================
public class GUISecuenciaPrimos1a {
// ===========================================================================
  JFrame      container;
  JPanel      jpanel;
  JTextField  txfMensajes;
  JButton     btnComienzaSecuencia, btnCancelaSecuencia;
  JSlider     sldEspera;
  HebraTrabajadora hebra;
  ZonaIntercambio intercambio;

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    GUISecuenciaPrimos1a gui = new GUISecuenciaPrimos1a();
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        gui.go();
      }
    });
  }

  // -------------------------------------------------------------------------
  public void go() {
    // Constantes.
    final int valorMaximo = 1000;
    final int valorMedio  = 500;

    // Variables.
    JPanel  tempPanel;

    // Crea el JFrame principal.
    container = new JFrame( "GUI Secuencia de Primos 1a" );

    // Consigue el panel principal del Frame "container".
    jpanel = ( JPanel ) container.getContentPane();
    jpanel.setLayout( new GridLayout( 3, 1 ) );

    // Crea e inserta la etiqueta y el campo de texto para los mensajes.
    txfMensajes = new JTextField( 20 );
    txfMensajes.setEditable( false );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( new JLabel( "Secuencia: " ) );
    tempPanel.add( txfMensajes );
    jpanel.add( tempPanel );

    // Crea e inserta los botones de Comienza secuencia y Cancela secuencia.
    btnComienzaSecuencia = new JButton( "Comienza secuencia" );
    btnCancelaSecuencia = new JButton( "Cancela secuencia" );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( btnComienzaSecuencia );
    tempPanel.add( btnCancelaSecuencia );
    jpanel.add( tempPanel );

    // Crea e inserta el slider para controlar el tiempo de espera.
    sldEspera = new JSlider( JSlider.HORIZONTAL, 0, valorMaximo , valorMedio );
    tempPanel = new JPanel();
    tempPanel.setLayout( new BorderLayout() );
    tempPanel.add( new JLabel( "Tiempo de espera: " ) );
    tempPanel.add( sldEspera );
    jpanel.add( tempPanel );
    
    // Activa inicialmente los 2 botones.
    btnComienzaSecuencia.setEnabled( true );
    btnCancelaSecuencia.setEnabled( false );

    // Anyade codigo para procesar el evento del boton de Comienza secuencia.
    btnComienzaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          btnComienzaSecuencia.setEnabled( false );
          btnCancelaSecuencia.setEnabled( true);
          hebra=new HebraTrabajadora(txfMensajes, intercambio);
          System.out.println("Hebra creada");
          hebra.start();
        }
    } );

    // Anyade codigo para procesar el evento del boton de Cancela secuencia.
    btnCancelaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          btnComienzaSecuencia.setEnabled( true );
          btnCancelaSecuencia.setEnabled( false );
          hebra.stopNow();
        }
    } );
    intercambio=new ZonaIntercambio(valorMedio);
    // Anyade codigo para procesar el evento del slider " Espera " .
    sldEspera.addChangeListener( new ChangeListener() {
      public void stateChanged( ChangeEvent e ) {
        JSlider sl = ( JSlider ) e.getSource();
        if ( ! sl.getValueIsAdjusting() ) {
          long tiempoMilisegundos = ( long ) sl.getValue();
          intercambio.setMiliseconds(tiempoMilisegundos);
        }
      }
    } );

    // Fija caracteristicas del container.
    container.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    container.pack();
    container.setResizable( false );
    container.setVisible( true );

    System.out.println( "% End of routine: go.\n" );
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

