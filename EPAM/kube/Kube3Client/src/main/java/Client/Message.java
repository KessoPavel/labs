package Client;

import Data.Dossier;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kesso on 24.04.17.
 */
public class Message implements Serializable {
    public String message = "";
    public ArrayList<Dossier> dossiers = null;
}
