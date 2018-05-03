import REST.RestHandler;

import java.util.Scanner;

/**
 * Created by kesso on 29.04.17.
 */
public class RestThread extends Thread {
    RestHandler restHandler = new RestHandler();
    public void run(){
        this.work();
    }

    private void work(){
        System.out.println("Hello. Server is started.\n");
        Scanner in = new Scanner(System.in);
        String question;

        while(true){
            question = in.nextLine();
            restHandler.handle(question);
        }
    }
}
