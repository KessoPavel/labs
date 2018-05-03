package Logic;

import DAO.JDBCBookDAO;
import DAO.JDBCDocumentDAO;
import DAO.JDBCFilmDAO;
import DAO.JDBCSongDAO;
import Documents.*;
import Interface.TableItem;
import User.User;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.print.Doc;
import javax.xml.crypto.Data;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by kesso on 5.3.17.
 * class for work with data
 */
public class DataLogic {
    private static final Logger log = Logger.getLogger(DataLogic.class);

    /**
     * called at the first start
     * to add to the file data already available in the directory
     * @param dataArray
     * @param dir directory
     * @param creator admin e
     */
    public static void createDataArrayFromDir(DataArray dataArray, String dir, String creator){
        File mainDir = new File(dir);
        if(!mainDir.isDirectory()){
            log.error("Directory is incorrect");
            return;
        }

        File listsFile[] = mainDir.listFiles();
        for(File a: listsFile){
            if(a.isFile()){
                String type = fileType(a);
                if (type.equals("Document")) {
                    dataArray.documents.add(new Document(a,creator, ""));
                }
                if(type.equals("Book")){
                    dataArray.books.add(new Book(a,creator,"","","",""));
                }
                if(type.equals("Song")){
                    dataArray.songs.add(new Song(a,creator,"", "","","",0));
                }
                if(type.equals("Film")){
                    dataArray.films.add(new Film(a,creator,"","","",""));
                }
            }
        }

    }

    /**
     * save dataArray from database
     * @param dataArray array frm saving
     */
    public static void saveDataArrayFromDataBase(DataArray dataArray) {
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();
            JDBCDocumentDAO documentDAO = new JDBCDocumentDAO();
            documentDAO.setConnection(dataBaseLogic.getConnection());
            documentDAO.createTable(); //убрать в создание базы данных при первом запуске
            for(Document document : dataArray.documents){
                documentDAO.insert(document);
            }

            JDBCSongDAO songDAO = new JDBCSongDAO();
            songDAO.setConnection(dataBaseLogic.getConnection());
            songDAO.createTable();
            for(Song song : dataArray.songs ){
                songDAO.insert(song);
            }

            JDBCFilmDAO filmDAO = new JDBCFilmDAO();
            filmDAO.setConnection(dataBaseLogic.getConnection());
            filmDAO.createTable();
            for(Film film : dataArray.films){
                filmDAO.insert(film);
            }

            JDBCBookDAO bookDAO = new JDBCBookDAO();
            bookDAO.setConnection(dataBaseLogic.getConnection());
            bookDAO.createTable();
            for(Book book: dataArray.books){
                bookDAO.insert(book);
            }
            dataBaseLogic.closeDataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.error("error work with database");
        }
    }

    /**
     * created a filling data array from database
     * @return created data array
     */
    public static DataArray readDataArrayFromDataBase() {
        DataArray dataArray = new DataArray();
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();

            JDBCDocumentDAO documentDAO = new JDBCDocumentDAO();
            documentDAO.setConnection(dataBaseLogic.getConnection());
            dataArray.documents = documentDAO.select();

            JDBCSongDAO songDAO = new JDBCSongDAO();
            songDAO.setConnection(dataBaseLogic.getConnection());
            dataArray.songs = songDAO.select();

            JDBCFilmDAO filmDAO = new JDBCFilmDAO();
            filmDAO.setConnection(dataBaseLogic.getConnection());
            dataArray.films = filmDAO.select();

            JDBCBookDAO bookDAO = new JDBCBookDAO();
            bookDAO.setConnection(dataBaseLogic.getConnection());
            dataArray.books = bookDAO.select();

            dataBaseLogic.closeDataBase();
        }
        catch(SQLException ex){
            log.error("DataBase reding error");
        }
        return dataArray;
    }

    /**
     * search a type Type document in a array
     * @param array
     * @param name
     * @param <Type>
     * @return search document
     */
    public static <Type> Type getForName(ArrayList<Type> array, String name){
        for(Type t : array){
            if(t.toString().equals(name)){
                return t;
            }
        }
        return null;
    }

    /**
     * save document changes from array and database
     * @param document
     * @param description
     */
    public static void saveDocumentChange(Document document, String description) {
        if (document.getDescription().equals(description)) {
            return;
        }
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();

            JDBCDocumentDAO documentDAO = new JDBCDocumentDAO();
            documentDAO.setConnection(dataBaseLogic.getConnection());
            documentDAO.update(document,description);

            dataBaseLogic.closeDataBase();
        }
        catch (SQLException ex){
            log.error(ex.getMessage());
        }
        document.setDescription(description);
    }

    /**
     * save book changes from array and database
     * @param book
     * @param bookName
     * @param autor
     * @param genre
     * @param description
     */
    public static void saveBookChange(Book book, String bookName, String autor, String genre,
                                      String description){
        if(book.getBookName().equals(bookName) && book.getAutor().equals(autor) &&
                book.getGenre().equals(genre) &&
                book.getDescription().equals(description)){
            return;
        }
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();

            JDBCBookDAO bookDAO = new JDBCBookDAO();
            bookDAO.setConnection(dataBaseLogic.getConnection());
            bookDAO.update(book, bookName, autor, genre, description);

            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        book.setBookName(bookName);
        book.setAutor(autor);
        book.setGenre(genre);
        book.setDescription(description);
    }

    /**
     * save song changes from array and database
     * @param song
     * @param songName
     * @param executor
     * @param album
     * @param year
     * @param description
     */
    public static void saveSongChange(Song song, String songName, String executor,String album,
                                      int year, String description){
        if(song.getSongName().equals(songName) && song.getExecutor().equals(executor) &&
                song.getAlbum().equals(album) && song.getYear() == year &&
                song.getDescription().equals(description)){
            return;
        }

        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();

            JDBCSongDAO songDAO = new JDBCSongDAO();
            songDAO.setConnection(dataBaseLogic.getConnection());
            songDAO.update(song,songName,executor,album,year,description);

            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        song.setSongName(songName);
        song.setExecutor(executor);
        song.setAlbum(album);
        song.setYear(year);
        song.setDescription(description);
    }

    /**
     * save film changes from array and database
     * @param film
     * @param filmName
     * @param producer
     * @param genre
     * @param description
     */
    public static void saveFilmChange(Film film, String filmName, String producer,
                                      String genre, String description){
        if(film.getFilmName().equals(filmName) && film.getProducer().equals(producer) &&
                film.getGenre().equals(genre) && film.getDescription().equals(description)){
            return;
        }

        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();

            JDBCFilmDAO filmDAO = new JDBCFilmDAO();
            filmDAO.setConnection(dataBaseLogic.getConnection());
            filmDAO.update(film,filmName,producer,genre,description);

            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        film.setFilmName(filmName);
        film.setProducer(producer);
        film.setGenre(genre);
        film.setDescription(description);
    }

    /**
     * deleted document from array, dir anf database
     * @param dataArray
     * @param document
     * @param dir
     */
    public static void deleteDocument(DataArray dataArray, Document document, String dir){
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();
            JDBCDocumentDAO documentDAO = new JDBCDocumentDAO();
            documentDAO.setConnection(dataBaseLogic.getConnection());
            documentDAO.delete(document);

            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        dataArray.documents.remove(document);
        File doc = new File(dir + "/" +document.getName());
        doc.delete();
    }

    public static void deleteBook(DataArray dataArray, Book book, String dir){
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();
            JDBCBookDAO bookDAO = new JDBCBookDAO();
            bookDAO.setConnection(dataBaseLogic.getConnection());
            bookDAO.delete(book);

            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        dataArray.books.remove(book);
        File doc = new File(dir + "/"  + book.getName());
        doc.delete();
    }

    public static void deleteSong(DataArray dataArray, Song song, String dir){
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();
            JDBCSongDAO songDAO = new JDBCSongDAO();
            songDAO.setConnection(dataBaseLogic.getConnection());
            songDAO.delete(song);

            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        dataArray.songs.remove(song);
        File doc = new File(dir + "/" + song.getName());
        doc.delete();
    }

    public static void deleteFilm(DataArray dataArray, Film film, String dir){
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();
            JDBCFilmDAO filmDAO = new JDBCFilmDAO();
            filmDAO.setConnection(dataBaseLogic.getConnection());
            filmDAO.delete(film);

            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        dataArray.films.remove(film);
        File doc = new File(dir + "/" + film.getName());
        doc.delete();
    }


    /**
     * adding file from database and dir
     * @param dataArray
     * @param document
     * @param file
     * @param dir
     * @param user
     * @return
     */
    public static boolean addDocument(DataArray dataArray, Document document, File file, String dir, User user){
        if(user.checkLoad(file)) {

            try {
                dataArray.documents.add(document);
                DataBaseLogic dataBaseLogic = new DataBaseLogic();
                JDBCDocumentDAO documentDAO = new JDBCDocumentDAO();
                documentDAO.setConnection(dataBaseLogic.getConnection());
                documentDAO.insert(document);

                dataBaseLogic.closeDataBase();

                DataLogic.copyFile(file,dir);
            } catch (SQLException e) {
                log.error(e.getMessage());
                return false;
            }
            catch (IOException ex){
                log.error(ex.getMessage());
                return false;
            }
            user.addLoad(file);
            return true;
        }
        return false;
    }

    public static boolean addBook(DataArray dataArray, Book book, File file, String dir, User user) {
        if(user.checkLoad(file)) {

            try {
                dataArray.books.add(book);
                DataBaseLogic dataBaseLogic = new DataBaseLogic();
                JDBCBookDAO bookDAO = new JDBCBookDAO();
                bookDAO.setConnection(dataBaseLogic.getConnection());
                bookDAO.insert(book);

                dataBaseLogic.closeDataBase();

                DataLogic.copyFile(file,dir);
            } catch (SQLException e) {
                log.error(e.getMessage());
                return false;
            }
            catch (IOException ex){
                log.error(ex.getMessage());
                return false;
            }
            user.addLoad(file);
            return true;
        }
        return false;
    }

    public static boolean addSong(DataArray dataArray, Song song,File file, String dir, User user) {
        if(user.checkLoad(file)) {

            try {
                dataArray.songs.add(song);
                DataBaseLogic dataBaseLogic = new DataBaseLogic();
                JDBCSongDAO songDAO = new JDBCSongDAO();
                songDAO.setConnection(dataBaseLogic.getConnection());
                songDAO.insert(song);

                dataBaseLogic.closeDataBase();

                DataLogic.copyFile(file,dir);
            } catch (SQLException e) {
                log.error(e.getMessage());
                return false;
            }
            catch (IOException ex){
                log.error(ex.getMessage());
                return false;
            }
            user.addLoad(file);
            return true;
        }
        return false;
    }

    public static boolean addFilm(DataArray dataArray, Film film, File file, String dir, User user) {
        if(user.checkLoad(file)) {

            try {
                dataArray.films.add(film);
                DataBaseLogic dataBaseLogic = new DataBaseLogic();
                JDBCFilmDAO filmDAO = new JDBCFilmDAO();
                filmDAO.setConnection(dataBaseLogic.getConnection());
                filmDAO.insert(film);

                dataBaseLogic.closeDataBase();

                DataLogic.copyFile(file,dir);
            } catch (SQLException e) {
                log.error(e.getMessage());
                return false;
            }
            catch (IOException ex){
                log.error(ex.getMessage());
                return false;
            }
            user.addLoad(file);
            return true;
        }
        return false;
    }

    /**
     * copy file from dir
     * @param file
     * @param dir
     * @throws IOException
     */
    public static void copyFile(File file, String dir) throws IOException {
        File cop = new File(dir + "/" + file.getName());

        InputStream is = null;
        OutputStream os = null;
        is = new FileInputStream(file);
        os = new FileOutputStream(cop);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        is.close();
        os.close();

    }

    public static String fileLength(Long length){
        Double l = new Double(length);
        if(l >= 1024){
            l /= 1024;
            if(l>=1024){
                l /= 1024;
                if(l >= 1024) {
                    l /= 1024;
                    return String.format("%.2f GB", l);
                }
                return String.format("%.2f MB",l);
            }
            return String.format("%.2f KB",l);
        }
        return length.toString() + " B";
    }

    public static String fileType(File file) {
        if(!file.isFile())
            return null;
        String fileName = file.getName();
        String type = null;
        String resolution = null;
        try {
            File typeFile = new File("type");
            Scanner fileScanner = new Scanner(typeFile);

            while(fileScanner.hasNextLine()){
                String temp = fileScanner.nextLine();
                if(temp.startsWith(".")) {
                    resolution = temp;
                }
                else {
                    type = temp;
                }
                if(resolution != null){
                    if(fileName.endsWith(resolution)) {
                        return type;
                    }
                }
            }
        }catch (FileNotFoundException ex){
            log.error("With the types of the file is not found");
            return null;
        }
        return "Document";
    }

    /**
     * search data in the table
     * @param search
     * @param dataArray
     * @param data
     */
    public static void searchData(String search, DataArray dataArray,
                                  ObservableList<TableItem> data){
        DataArray newData = new DataArray();
        for(Document doc : dataArray.documents){
            if(doc.getName().contains(search)){
                newData.documents.add(doc);
            }
        }
        for(Book doc : dataArray.books){
            if(doc.getName().contains(search)){
                newData.books.add(doc);
            }
        }
        for(Song doc : dataArray.songs){
            if(doc.getName().contains(search)){
                newData.songs.add(doc);
            }
        }
        for(Film doc : dataArray.films){
            if(doc.getName().contains(search)){
                newData.films.add(doc);
            }
        }
        TableLogic.setData(newData,data);
    }

    public static void searchDocuments(String search, ArrayList<Document> documents,
                                       ObservableList<TableItem> data, boolean[] flags){
        ArrayList<Document> newData = new ArrayList<Document>();
        if(flags[0]){
            for(Document doc : documents){
                if(doc.getName().contains(search)){
                    newData.add(doc);
                }
            }
        }
        if(flags[2]){
            for(Document doc : documents){
                if(doc.getResolution().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[3]){
            for(Document doc : documents){
                if(doc.getCreator().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        TableLogic.setDocument(newData,data);
    }

    public static void searchBooks(String search, ArrayList<Book> books,
                                       ObservableList<TableItem> data, boolean[] flags){
        ArrayList<Book> newData = new ArrayList<Book>();
        if(flags[0]){
            for(Book doc : books){
                if(doc.getName().contains(search)){
                    newData.add(doc);
                }
            }
        }
        if(flags[2]){
            for(Book doc : books){
                if(doc.getResolution().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[3]){
            for(Book doc : books){
                if(doc.getBookName().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[4]){
            for(Book doc : books){
                if(doc.getAutor().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[5]){
            for(Book doc : books){
                if(doc.getGenre().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[6]){
            for(Book doc : books){
                if(doc.getCreator().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        TableLogic.setBook(newData,data);
    }

    public static void searchSongs(String search, ArrayList<Song> songs,
                                       ObservableList<TableItem> data, boolean[] flags){
        ArrayList<Song> newData = new ArrayList<Song>();
        if(flags[0]){
            for(Song doc : songs){
                if(doc.getName().contains(search)){
                    newData.add(doc);
                }
            }
        }
        if(flags[2]){
            for(Song doc : songs){
                if(doc.getResolution().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[3]){
            for(Song doc : songs){
                if(doc.getSongName().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[4]){
            for(Song doc : songs){
                if(doc.getExecutor().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[5]){
            for(Song doc : songs){
                if(doc.getAlbum().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }

        if(flags[6]){
            for(Song doc : songs){
                if((new Integer(doc.getYear())).toString().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[7]){
            for(Song doc : songs){
                if(doc.getCreator().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        TableLogic.setSong(newData,data);
    }

    public static void searchFilms(String search, ArrayList<Film> films,
                                       ObservableList<TableItem> data, boolean[] flags){
        ArrayList<Film> newData = new ArrayList<Film>();
        if(flags[0]){
            for(Film doc : films){
                if(doc.getName().contains(search)){
                    newData.add(doc);
                }
            }
        }
        if(flags[2]){
            for(Film doc : films){
                if(doc.getResolution().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[3]){
            for(Film doc : films){
                if(doc.getFilmName().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[4]){
            for(Film doc : films){
                if(doc.getProducer().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[5]){
            for(Film doc : films){
                if(doc.getGenre().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        if(flags[6]){
            for(Film doc : films){
                if(doc.getCreator().contains(search) && !newData.contains(doc)){
                    newData.add(doc);
                }
            }
        }
        TableLogic.setFilm(newData,data);
    }

}
