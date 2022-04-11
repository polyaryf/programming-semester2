package multithread.task;
import java.util.Scanner;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        System.out.println("TEST MESSAGE");
                        sleep(1000);
                    }
                }catch (InterruptedException e){
                    System.out.println(e);
                }
            }
        });
        myThread.start();
        if(scanner.nextLine() == ""){
            myThread.stop();
        }
    }
}
