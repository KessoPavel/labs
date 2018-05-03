package REST;

import Archive.Archive;
import Archive.ArchiveLogic;
import Archive.DAO.JDBCDossierDAO;
import Data.Dossier;
import Parsers.DOMParser;
import ZipPackage.Zip;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by kesso on 29.04.17.
 */
public class RestHandler {
    Archive archive = new Archive();

    public void handle(String question) {
        if (question.startsWith("GET")) {
            System.out.print(this.get(question));
        }
        if (question.startsWith("PUT")) {
            //проврека /dossiers/
            this.put();
        }
        if(question.startsWith("DELETE")){
            this.delete(question);
        }
    }

    private String get(String question) {//"/dossiers/{id}/{fname}{sname}..."
        String answer = "\n";
        String[] q = question.split("/");

        if (q[1].equals("dossiers") || q[1].equals("Dossiers")) {
            if (q.length == 2) {
                ArrayList<Dossier> dossiers = this.getDossiers();
                answer = "\n";
                for (Dossier d : dossiers) {
                    answer += "id : " + d.getID() +
                            "\n\tName : " + d.getPerson().getFirstName() + " " + d.getPerson().getSecondName() + "\n";
                }
                return answer;
            } else {
                //return from id
                Dossier dossier = this.getFromId(q[2]);
                if (q.length == 4) {
                    if (q[3].equals("first name")) {
                        answer = "id : " + dossier.getID() +
                                " first name : " + dossier.getPerson().getFirstName() + "\n";
                        return answer;
                    }
                    if (q[3].equals("second name")) {
                        answer = "id : " + dossier.getID() +
                                " second name : " + dossier.getPerson().getSecondName() + "\n";
                        return answer;
                    }
                    if (q[3].equals("sex")) {
                        answer = "id : " + dossier.getID() +
                                " sex : " + dossier.getPerson().getSex() + "\n";
                        return answer;
                    }
                    if (q[3].equals("work place")) {
                        answer = "id : " + dossier.getID() +
                                " work place : " + dossier.getPerson().getWorkPalace().getWorkPlace() + "\n";
                        return answer;
                    }
                    if (q[3].equals("position")) {
                        answer = "id : " + dossier.getID() +
                                " position : " + dossier.getPerson().getWorkPalace().getPosition() + "\n";
                        return answer;
                    }
                    if (q[3].equals("institution")) {
                        answer = "id : " + dossier.getID() +
                                " institution : " + dossier.getPerson().getStudyPlace().getEducationalInstitution() + "\n";
                        return answer;
                    }
                    if (q[3].equals("startEd")) {
                        answer = "id : " + dossier.getID() +
                                " start ed. : " + dossier.getPerson().getStudyPlace().getYearOfReceipt() + "\n";
                        return answer;
                    }
                    if (q[3].equals("endEd")) {
                        answer = "id : " + dossier.getID() +
                                " end ed. : " + dossier.getPerson().getStudyPlace().getYearOfEnding() + "\n";
                        return answer;
                    }
                } else {
                    answer = "Id : " + dossier.getID() + "\n";
                    answer += "\tFirst name : " + dossier.getPerson().getFirstName() + "\n";
                    answer += "\tSecond name : " + dossier.getPerson().getSecondName() + "\n";
                    answer += "\tSex : " + dossier.getPerson().getSex() + "\n";
                    answer += "\tWork place : \n\t\tWork place: " + dossier.getPerson().getWorkPalace().getWorkPlace()
                            + "\n\t\tPosition : " + dossier.getPerson().getWorkPalace().getPosition() + "\n";
                    answer += "\tStudy place : \n\t\tInstitution : " + dossier.getPerson().getStudyPlace().getEducationalInstitution() +
                            "\n\t\tYear of receipt : " + dossier.getPerson().getStudyPlace().getYearOfReceipt() +
                            "\n\t\tYear of ending : " + dossier.getPerson().getStudyPlace().getYearOfEnding() + "\n";
                }
            }
        }
        return answer;

    }

    private void put(){
        Scanner in = new Scanner(System.in);
        Dossier dossier = new Dossier();
        String temp;
        System.out.println("\nNew dossier : ");
        System.out.print("\n\tFirst name : ");
        temp = in.nextLine();
        dossier.getPerson().setFirstName(temp);

        System.out.print("\tSecond name : ");
        temp = in.nextLine();
        dossier.getPerson().setSecondName(temp);

        System.out.print("\tSex : ");
        temp = in.nextLine();
        dossier.getPerson().setSex(temp);

        System.out.print("\tWork Place : ");
        temp = in.nextLine();
        dossier.getPerson().getWorkPalace().setWorkPlace(temp);

        System.out.print("\tPosition : ");
        temp = in.nextLine();
        dossier.getPerson().getWorkPalace().setPosition(temp);

        System.out.print("\tInstitution : ");
        temp = in.nextLine();
        dossier.getPerson().getStudyPlace().setEducationalInstitution(temp);

        System.out.print("\tYear of receipt : ");
        temp = in.nextLine();
        dossier.getPerson().getStudyPlace().setYearOfReceipt(temp);

        System.out.print("\tYear of ending : ");
        temp = in.nextLine();
        dossier.getPerson().getStudyPlace().setYearOfEnding(temp);

        JDBCDossierDAO dossierDAO = new JDBCDossierDAO();
        try {
            dossierDAO.createTable();
            dossierDAO.insert(dossier);
            dossierDAO.closeDataBase();
        } catch (SQLException e) {
            return;
        }

        //save from file
        DOMParser parser = new DOMParser();
        parser.writeDossier(dossier);

        //архивация  и удаление
        Zip.toZipAndDelete(dossier);
    }

    private void delete(String question){
        String[] q = question.split("/");
        if (q[1].equals("dossiers") || q[1].equals("Dossiers")){
            if(q.length > 2){
                Dossier dossier = this.getFromId(q[2]);
                JDBCDossierDAO dossierDAO = new JDBCDossierDAO();
                try {
                    dossierDAO.createTable();
                    dossierDAO.delete(dossier);
                    dossierDAO.closeDataBase();
                } catch (SQLException e) {
                    return;
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
            }
        }
    }

    private Dossier getFromId(String id) {
        ArchiveLogic.setDossiers(archive);
        Dossier dossier = new Dossier();
        dossier.setID(Integer.parseInt(id));
        return ArchiveLogic.getDossiers(archive, dossier).get(0);
    }

    private ArrayList<Dossier> getDossiers() {
        ArchiveLogic.setDossiers(archive);
        return archive.getDossiers();
    }
}

