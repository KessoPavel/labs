/**
 * Created by kesso on 29.04.17.
 */
public class UserThread extends Thread {
    public void run(){
        WorkClass workClass = new WorkClass(2222);
        workClass.work();
    }
}
