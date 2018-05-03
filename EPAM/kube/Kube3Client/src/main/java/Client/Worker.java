package Client;

import Data.Dossier;
import Users.User;

import java.util.ArrayList;

/**
 * Created by kesso on 27.04.17.
 */
public class Worker {
    private Client client;
    public Worker(int port){
        client = new Client(port);
    }
    public ArrayList<Dossier> work(Message question){
        if(client.startSession("start")){
            client.sendMessage(question);
            Message answer = client.waitMessage();
            return Handler.handle(answer);
        }
        return null;
    }

    public ArrayList<User> work(AdminMessage question){
        if(client.startSession("astart")){
            client.sendAdminMessage(question);
            AdminMessage answer = client.waitAdminMessage();
            return Handler.handle(answer);
        }
        return null;
    }

    public void end(){
        client.endWork();
    }
}
