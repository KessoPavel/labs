package Parsers;

import Data.Dossier;
import Data.Person;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kesso on 23.04.17.
 */
public class DOMParser implements Parser {
    private static final Logger log = Logger.getLogger(DOMParser.class);

    public Person readPerson(String path){
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
        }

        Document document = null;
        try {
            document = documentBuilder.parse(path);
        } catch (SAXException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        if(document == null){
            return null;
        }

        Node root = document.getDocumentElement();
        NodeList dossierNode = root.getChildNodes();

        Person person = new Person();

        for(int i = 0; i < dossierNode.getLength(); i++){
            Node dossierProp = dossierNode.item(i);

            if(dossierProp.getNodeName().equals("Name")){
                NamedNodeMap attributes = dossierProp.getAttributes();
                Node firstName = attributes.getNamedItem("FirstName");
                if(firstName != null)
                    person.setFirstName(firstName.getNodeValue());
                else
                    person.setFirstName("");

                Node secondName = attributes.getNamedItem("SecondName");
                if(secondName != null)
                    person.setSecondName(secondName.getNodeValue());
                else
                    person.setSecondName("");
                continue;
            }

            if(dossierProp.getNodeName().equals("Sex")){
                person.setSex(dossierProp.getChildNodes().item(0).getNodeValue());
                continue;
            }

            if(dossierProp.getNodeName().equals("WorkPlace")){
                NamedNodeMap attributes = dossierProp.getAttributes();
                Node workPlace = attributes.getNamedItem("WorkPlace");
                if(workPlace != null)
                    person.getWorkPalace().setWorkPlace(workPlace.getNodeValue());
                else
                    person.getWorkPalace().setWorkPlace("");

                Node position = attributes.getNamedItem("Position");
                if(position != null)
                    person.getWorkPalace().setPosition(position.getNodeValue());
                else
                    person.getWorkPalace().setPosition("");
                continue;
            }

            if(dossierProp.getNodeName().equals("StudyPlace")){
                NamedNodeMap attributes = dossierProp.getAttributes();
                Node institution = attributes.getNamedItem("Institution");
                if(institution != null)
                    person.getStudyPlace().setEducationalInstitution(institution.getNodeValue());
                else
                    person.getStudyPlace().setEducationalInstitution("");

                Node start = attributes.getNamedItem("Start");
                if(start != null)
                    person.getStudyPlace().setYearOfReceipt(start.getNodeValue());
                else
                    person.getStudyPlace().setYearOfReceipt("");

                Node end = attributes.getNamedItem("End");
                if(start != null)
                    person.getStudyPlace().setYearOfEnding(end.getNodeValue());
                else
                    person.getStudyPlace().setYearOfEnding("");

                continue;
            }

            if(dossierProp.getNodeName().equals("Note")){
                person.setNote(dossierProp.getChildNodes().item(0).getNodeValue());
                continue;
            }
        }
        return person;
    }

    public void writeDossier(Dossier dossier) {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e.getMessage());
        }

        Document document = null;
        try {
            document = documentBuilder.parse("Dossiers/" + dossier.getID());
        } catch (SAXException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Node root = null;
        if(document == null){
            File file = new File("Dossiers/" + dossier.getID());
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            document = documentBuilder.newDocument();
        }
        root = document.createElement("Dossier");

        Element name = document.createElement("Name");
        name.setAttribute("FirstName",dossier.getPerson().getFirstName());
        name.setAttribute("SecondName",dossier.getPerson().getSecondName());
        root.appendChild(name);

        Element sex = document.createElement("Sex");
        sex.setTextContent(dossier.getPerson().getSex());
        root.appendChild(sex);

        if(!dossier.getPerson().getWorkPalace().isEmpty()){
            Element workPlace = document.createElement("WorkPlace");
            if(!dossier.getPerson().getWorkPalace().getWorkPlace().equals(""))
                workPlace.setAttribute("WorkPlace",dossier.getPerson().getWorkPalace().getWorkPlace());
            if(!dossier.getPerson().getWorkPalace().getPosition().equals(""))
                workPlace.setAttribute("Position",dossier.getPerson().getWorkPalace().getPosition());
            root.appendChild(workPlace);
        }

        if(!dossier.getPerson().getStudyPlace().isEmpty()){
            Element studyPlace = document.createElement("StudyPlace");
            if(!dossier.getPerson().getStudyPlace().getEducationalInstitution().equals(""))
                studyPlace.setAttribute("Institution", dossier.getPerson().getStudyPlace().getEducationalInstitution());
            if(!dossier.getPerson().getStudyPlace().getYearOfReceipt().equals(""))
                studyPlace.setAttribute("Start", dossier.getPerson().getStudyPlace().getYearOfReceipt());
            if(!dossier.getPerson().getStudyPlace().getYearOfEnding().equals(""))
                studyPlace.setAttribute("End", dossier.getPerson().getStudyPlace().getYearOfEnding());
            root.appendChild(studyPlace);
        }

        if(!dossier.getPerson().getNote().equals("")){
            Element note = document.createElement("Note");
            note.setTextContent(dossier.getPerson().getNote());
            root.appendChild(note);
        }

        //document.adoptNode(root);


        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            log.error(e.getMessage());
        }
        DOMSource domSource = new DOMSource(root);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("Dossiers/" + dossier.getID());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        StreamResult result = new StreamResult(fileOutputStream);
        try {
            transformer.transform(domSource,result);
        } catch (TransformerException e) {
            log.error(e.getMessage());
        }
    }
}
