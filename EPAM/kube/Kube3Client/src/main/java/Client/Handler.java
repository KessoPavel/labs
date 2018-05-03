package Client;

import Data.Dossier;
import Users.User;

import java.util.ArrayList;

/**
 * Created by kesso on 27.04.17.
 */
public class Handler {
    public static ArrayList<Dossier> handle(Message answer){
        ArrayList<Dossier> dossiers = new ArrayList<Dossier>();

        if(answer.message.equals("ok")){
            if(answer.dossiers != null) {
                dossiers.addAll(answer.dossiers);
            }
        }
        else
            dossiers = null;
        return  dossiers;
    }

    public static ArrayList<User> handle(AdminMessage answer){
        if(answer.message.equals("ok")){
            return answer.users;
        }
        return null;
    }
}
