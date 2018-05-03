package Archive;


import Archive.DAO.JDBCDossierDAO;
import Data.Dossier;
import Parsers.DOMParser;
import ZipPackage.Zip;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kesso on 23.04.17.
 */
public class ArchiveLogic {
    private static final Logger log = Logger.getLogger(ArchiveLogic.class);


    public static ArrayList<Dossier> getDossiers(Archive archive, Dossier dossier){
        ArrayList<Dossier> answer = new ArrayList<Dossier>();
        for(Dossier dos : archive.getDossiers()){
            if(dos.equals(dossier))
                answer.add(dos);
        }
        return answer;
    }

    synchronized public static void setDossiers(Archive archive){
        JDBCDossierDAO dossierDAO = new JDBCDossierDAO();
        try {
            dossierDAO.createTable();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        archive.setDossiers(dossierDAO.select());
        try {
            dossierDAO.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        DOMParser parser = new DOMParser();
        for(Dossier dossier : archive.getDossiers()){
            //разархивировать файл
            File zFile = new File("Dossiers/z" + dossier.getID());
            Zip.fromZIP(zFile,"Dossiers/"+dossier.getID());

            dossier.setPerson(parser.readPerson("Dossiers/" + dossier.getID()));
            if(dossier.getPerson() == null){
                archive.getDossiers().remove(dossier);
                try {
                    dossierDAO.createTable();
                    dossierDAO.delete(dossier);
                    dossierDAO.closeDataBase();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
            //удалить файл
            File file = new File("Dossiers/" + dossier.getID());
            file.delete();
        }
    }
}
