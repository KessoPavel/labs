package Client;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Created by kesso on 14.04.17.
 */
public class Client {
    private static final Logger log = Logger.getLogger(Client.class);

    private Socket server;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private BufferedReader systemReader;

    public Client(int port){
        try {
            server = new Socket("localhost",port);
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
        log.info("client start work");
        try{
            objectInputStream = new ObjectInputStream(server.getInputStream());
            objectOutputStream = new ObjectOutputStream(server.getOutputStream());

            systemReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }
        log.info("io treads created");
        return;
    }

    public boolean startSession(String type){
        Message start = new Message();
        start.message = type;
        start.dossiers = null;

        try {
            objectOutputStream.writeObject(start);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Message answer = null;
        try {
            answer = (Message)objectInputStream.readObject();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        if(answer.message.equals("ok")){
            return true;
        }
        return false;
    }

    public void sendMessage(Message question){
        try {
            objectOutputStream.writeObject(question);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendAdminMessage(AdminMessage question){
        try {
            objectOutputStream.writeObject(question);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public Message waitMessage(){
        Message answer = null;
        try {
            answer = (Message)objectInputStream.readObject();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return answer;
    }

    public AdminMessage waitAdminMessage(){
        AdminMessage answer = null;

        try {
            answer = (AdminMessage)objectInputStream.readObject();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return answer;
    }

    public void endWork(){
        try {
            objectOutputStream.close();
            objectInputStream.close();
            server.close();
            systemReader.close();
        } catch (IOException e) {
            log.error("close error");
        }
        log.info("end work");
    }
}
