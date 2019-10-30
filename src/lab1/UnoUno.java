package lab1;

class Hebra extends Thread{
    int miID;
    public Hebra (int miID){
        this.miID = miID;
    }
    public void run(){
        for (int i = 0; i < 1000; i++)
            System.out.println(miID);
    }
}

public class UnoUno{
    public static void main(String[] args){
        (new Hebra(0)).start();
        (new Hebra(1)).start();
    }
}

