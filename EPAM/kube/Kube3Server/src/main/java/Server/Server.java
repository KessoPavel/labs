package Server;

import Client.AdminMessage;
import Client.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kesso on 24.04.17.
 */
public class Server {
    private static final Logger log = Logger.getLogger(Server.class);
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Server(int i){
        try {
            this.serverSocket = new ServerSocket(i);
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }

    public void waitConnection() {
        log.info("Wait for a client");
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
        log.info("Client connected");
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }

    public String startSession(){
        Message question = null;
        while(true) {
            try {
                question = (Message)inputStream.readObject();
            } catch (IOException e) {
                log.error(e.getMessage());

            } catch (ClassNotFoundException e) {
                log.error(e.getMessage());
            }

            try {
                if (question.message.equals("start")) {
                    Message answer = new Message();
                    answer.message = "ok";
                    answer.dossiers = null;
                    try {
                        outputStream.writeObject(answer);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                    return "start";
                }
                if (question.message.equals("astart")) {
                    Message answer = new Message();
                    answer.message = "ok";
                    answer.dossiers = null;
                    try {
                        outputStream.writeObject(answer);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                    return "astart";
                }
            }catch (NullPointerException ex){
                return "error";
            }
        }
    }


    public AdminMessage waitAdminMessage(){
        AdminMessage question = null;

        try {
            question = (AdminMessage)inputStream.readObject();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return question;
    }

    public Message waitMessage(){
        Message question = null;
        try {
            question = (Message) inputStream.readObject();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return question;
    }

    public void sendMessage(Message answer){
        try {
            outputStream.writeObject(answer);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendAdminMessage(AdminMessage answer){
        try {
            outputStream.writeObject(answer);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void endWork(){
        try {
            inputStream.close();
            outputStream.close();
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
