package Logic;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by kesso on 5.3.17.
 */
public class MainLogic {
    private static final Logger log = Logger.getLogger(MainLogic.class);

    /**
     * determines whether this is the first start
     * @return
     */
    public static String startWork() {
        try {
            File settingsFile = new File("settings");
            if (settingsFile.exists()) {

                Scanner settingsFileScanner = new Scanner(settingsFile);
                if (settingsFileScanner.hasNextLine()) {
                    settingsFileScanner.close();
                    return settingsFileScanner.nextLine();
                }
                settingsFileScanner.close();
            }
        } catch (FileNotFoundException ex) {
            log.error("Was unable to open the settings file");
            return null;
        }
        return null;
    }


    public static void createSettingsFile(String dir) {
        File settingsFile = new File("settings");
        try {
            settingsFile.createNewFile();
            PrintWriter out = new PrintWriter(settingsFile.getAbsoluteFile());
            out.print(dir);
            out.close();
            File typeFile = new File("type");
            typeFile.createNewFile();
            PrintWriter tout = new PrintWriter(typeFile.getAbsoluteFile());
            tout.print("Book\n.pdf\nSong\n.mp3\nFilm\n.avi\n.mkv\n.mp4");
            tout.close();
        }catch (IOException ex){
            log.error("File creating error");
            return;
        }
    }

    /**
     * hashing of the password
     * @param st
     * @return
     */
    public static String coding(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }
}
