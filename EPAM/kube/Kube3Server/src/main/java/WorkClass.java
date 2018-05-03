import Archive.Archive;
import Client.AdminMessage;
import Client.Message;
import Server.*;

/**
 * Created by kesso on 27.04.17.
 */
public class WorkClass {
    private Archive archive;
    private Server server;

    public WorkClass(int port){
        this.archive = new Archive();
        this.server = new Server(port);
    }

    public void work(){
        server.waitConnection();

        while(true){
            String type;

            type = server.startSession();

            if(type.equals("error")){
                server.waitConnection();
                continue;
            }
            if(type.equals("start")){
                Message question = server.waitMessage();
                Message answer = Handler.handle(archive,question);
                server.sendMessage(answer);
            }
            if(type.equals("astart")){
                AdminMessage question = server.waitAdminMessage();
                AdminMessage answer = Handler.adminHandle(question);
                if(answer == null)
                    break;
                server.sendAdminMessage(answer);
            }
        }
        server.endWork();
    }
}
