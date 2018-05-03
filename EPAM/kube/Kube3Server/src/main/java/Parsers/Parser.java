package Parsers;

import Data.Dossier;
import Data.Person;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by kesso on 23.04.17.
 */
public interface Parser {
    public Person readPerson(String path);
    public void writeDossier(Dossier dossier);
}
