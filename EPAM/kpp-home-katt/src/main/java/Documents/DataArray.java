package Documents;

import java.util.ArrayList;

/**
 * Created by kesso on 5.3.17.
 */

/**
 * contains documetn, song, film, book ArrayLists
 */
public class DataArray {
        public ArrayList<Document> documents;
        public ArrayList<Song> songs;
        public ArrayList<Film> films;
        public ArrayList<Book> books;

        public DataArray(){
            this.documents = new ArrayList<Document>();
            this.songs = new ArrayList<Song>();
            this.films= new ArrayList<Film>();
            this.books = new ArrayList<Book>();
        }

        public boolean equals(DataArray dataArray){
            if(!this.documents.equals(dataArray.documents) ||
                    !this.songs.equals(dataArray.songs) ||
                    !this.films.equals(dataArray.films) ||
                    !this.books.equals(dataArray.books)){
                return false;
            }
            return true;
        }
}
