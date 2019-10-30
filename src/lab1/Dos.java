package lab1;

class Sumatory extends Thread{
    int myID, start, end;
    int sum = 0;
    public Sumatory(int myID, int start, int end){
        this.myID = myID;
        this.start = start;
        this.end = end;
    }
    public void run(){
        for (int i = start; i <= end; i++)
            sum += i;
        System.out.printf("Soy hebra %d, la suma es %d\n", myID, sum);
    }
}

public class Dos {
    public static void main (String[] args){
        System.out.println("Hola, soy el programa principal");
        (new Sumatory(0, 1, 1000000)).start();
        (new Sumatory(1, 1000001, 2000000)).start();
    }
}
