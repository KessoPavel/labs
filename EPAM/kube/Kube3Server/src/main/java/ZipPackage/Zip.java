package ZipPackage;

import Data.Dossier;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by kesso on 28.04.17.
 */
public class Zip {
    public static void toZipAndDelete(Dossier dossier){
        File file = new File("Dossiers/"+dossier.getID());
        Zip.toZip(file,"Dossiers/z"+dossier.getID());
        file.delete();
    }

    public static void toZip(File file, String name) {
        File ZIPFile = new File(name);

        try(ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(ZIPFile));
            FileInputStream fis = new FileInputStream(file);){

            ZipEntry entry1 = new ZipEntry(file.getName()/*file.getPath()*/);
            zOut.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zOut.write(buffer);
            // закрываем текущую запись для новой записи
            zOut.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static File fromZIP(File ZIPFile, String fileName) {

        File file = new File(fileName);

        ZipFile zip = null;
        try {
            zip = new ZipFile(ZIPFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration entries = zip.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            try {
                write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return file;
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}
