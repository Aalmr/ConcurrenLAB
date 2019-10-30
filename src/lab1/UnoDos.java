package lab1;

class Corrible implements Runnable{
    int miID;
    public Corrible (int miID){
        this.miID = miID;
    }

    public void run(){
        for (int i = 0; i < 1000; i++)
            System.out.println(miID);
    }
}

public class UnoDos{
    public static void main(String[] args){
        (new Thread(new Corrible(0))).run();
        (new Thread(new Corrible(1))).run();
    }
}
