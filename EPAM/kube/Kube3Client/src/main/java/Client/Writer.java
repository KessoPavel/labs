package Client;

import Data.Dossier;
import Users.User;

import java.util.ArrayList;

/**
 * Created by kesso on 27.04.17.
 */
public class Writer {
    public static Message write(Dossier dossier, String comand){
        Message question = new Message();
        question.dossiers = new ArrayList<Dossier>();
        question.dossiers.add(dossier);
        question.message = comand;
        return  question;
    }

    public static AdminMessage aWrite(User user, String comand, String password){
        AdminMessage adminMessage = new AdminMessage();
        adminMessage.message = comand;
        adminMessage.users = new ArrayList<User>();
        adminMessage.users.add(user);
        adminMessage.password = password;
        return adminMessage;
    }
}
