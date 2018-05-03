package Server;

import Archive.Archive;
import Archive.ArchiveLogic;
import Archive.DAO.JDBCDossierDAO;
import Client.AdminMessage;
import Client.Message;
import Data.Dossier;
import Parsers.DOMParser;
import Users.DAO.JDBCUserDAO;
import Users.User;
import Users.UserLogic;
import ZipPackage.Zip;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kesso on 24.04.17.
 */
public class Handler {
    public static Message handle(Archive archive, Message question) {
        Message answer = new Message();

        if(question.message.equals("show")){
            ArchiveLogic.setDossiers(archive);
            answer.dossiers = Handler.show(archive, question.dossiers.get(0));
            answer.message = "ok";
            return answer;
        }

        if(question.message.equals("add")){
            if(Handler.add(archive, question.dossiers.get(0)))
                answer.message = "ok";
            else
                answer.message = "error";
            return answer;
        }

        if(question.message.equals("delete")){
            if(Handler.delete(archive, question.dossiers.get(0)))
                answer.message = "ok";
            else
                answer.message = "error";
            return answer;
        }

        if(question.message.equals("change")){
            if(Handler.change(archive, question.dossiers.get(0)))
                answer.message = "ok";
            else
                answer.message = "error";
            return answer;
        }
        answer.message = "error";
        return answer;
    }

    public static AdminMessage adminHandle(AdminMessage question){
        AdminMessage answer = new AdminMessage();
        answer.message = "error";
        answer.users = null;
        answer.password = "";
        if(question.message.equals("show")){
            answer.users = Handler.showUser();
            answer.message = "ok";
            return answer;
        }
        if(question.message.equals("add")){
            if(Handler.addUser(question.users.get(0),question.password)) {
                answer.message = "ok";
                answer.users = question.users;
            }
            return answer;
        }
        if(question.message.equals("change")){
            if(Handler.changeUser(question.users.get(0))){
                answer.message = "ok";
                answer.users = question.users;
            }
            return answer;
        }
        if(question.message.equals("check")){
            answer.users = new ArrayList<User>();
            answer.users.add(Handler.entranceCheck(question.users.get(0),question.password));
            if(answer.users != null)
                answer.message = "ok";
            return answer;
        }
        if(question.message.equals("delete")){
            Handler.deleteUser(question.users.get(0));
            answer.message = "ok";
        }
        return answer;
    }

    public static ArrayList<Dossier> show(Archive archive, Dossier dossier){
        ArrayList<Dossier> dossiers;
        dossiers = ArchiveLogic.getDossiers(archive, dossier);
        return  dossiers;
    }

    public static boolean add(Archive archive, Dossier dossier) {
        //save from database
        JDBCDossierDAO dossierDAO = new JDBCDossierDAO();
        try {
            dossierDAO.createTable();
            dossierDAO.insert(dossier);
            dossierDAO.closeDataBase();
        } catch (SQLException e) {
            return false;
        }

        //save from file
        DOMParser parser = new DOMParser();
        parser.writeDossier(dossier);

        //архивация  и удаление
        Zip.toZipAndDelete(dossier);


        //add to archive
        archive.addDossier(dossier);

        return true;
    }

    public static boolean delete(Archive archive, Dossier dossier){
        //delete from database
        JDBCDossierDAO dossierDAO = new JDBCDossierDAO();
        try {
            dossierDAO.createTable();
            dossierDAO.delete(dossier);
            dossierDAO.closeDataBase();
        } catch (SQLException e) {
            return false;
        }
        //delete file
        File file = new File("Dossiers/z" + dossier.getID());
        file.delete();
        //delete from archive
        for(Dossier d : archive.getDossiers()){
            if(d.equals(dossier)){
                archive.getDossiers().remove(d);
                break;
            }
        }
        return true;
    }

    public static boolean change(Archive archive, Dossier dossier){
        //update file
        DOMParser parser = new DOMParser();
        //разархивировать файл
        File zFile = new File("Dossiers/z" + dossier.getID());
        Zip.fromZIP(zFile,"Dossiers/"+dossier.getID());

        parser.writeDossier(dossier);
        //удалить архив
        zFile.delete();
        //заархивировать новый файл
        Zip.toZipAndDelete(dossier);
        //update archive
        archive.updateDossier(dossier);
        return true;
    }

    public static boolean addUser(User user, String password){
        UserLogic.saveFromDataBase(user,password);
        return true;
    }

    public static boolean changeUser(User user){
        JDBCUserDAO userDAO = new JDBCUserDAO();
        try {
            userDAO.createTable();
            userDAO.changeUser(user);
            userDAO.closeDataBase();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public static User entranceCheck(User user, String password){
        return UserLogic.entranceCheck(user.geteMail(),password);
    }

    public static ArrayList<User> showUser(){
       ArrayList<User> users = null;
        try {
            JDBCUserDAO userDAO = new JDBCUserDAO();
            userDAO.createTable();
            users = userDAO.select();
            userDAO.closeDataBase();
        } catch (SQLException e) {
            return null;
        }
       return users;
    }

    private static void deleteUser(User user){
        JDBCUserDAO userDAO = new JDBCUserDAO();
        try {
            userDAO.createTable();
            userDAO.delete(user);
            userDAO.closeDataBase();
        } catch (SQLException e) {
            return;
        }
    }
}
