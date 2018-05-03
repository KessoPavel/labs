package Documents;

import User.User;
import sun.security.util.Length;

import java.io.File;

/**
 * Created by kesso on 4.3.17.
 */

/**
 * lass Document contains information about loaded document
 */
public class Document {
    private String name;
    private long length;
    private String resolution;
    private String creator;
    private String description;

    public Document(){}

    public Document(String name, long length, String resolution, String creator, String description) {
        this.name = name;
        this.length = length;
        this.resolution = resolution;
        this.creator = creator;
        int n = this.name.lastIndexOf(".");
        this.resolution = this.name.substring(n);
        this.description = description;
    }

    public Document(String dir, String creator, String description){
        File file = new File(dir);
        this.name = file.getName();
        this.length = file.length();
        this.creator = creator;
        int n = this.name.lastIndexOf(".");
        this.resolution = this.name.substring(n);
        this.description = description;
    }

    public Document(File file, String creator, String description) {
        this.name = file.getName();
        this.length = file.length();
        this.creator = creator;
        int n = this.name.lastIndexOf(".");
        this.resolution = this.name.substring(n);
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length){
        this.length = length;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution){
        this.resolution = resolution;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String eMail){
        this.creator = eMail;
    }

    public String toString(){
        return  this.name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
