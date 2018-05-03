package Archive;

import Data.Dossier;

import java.util.ArrayList;

/**
 * Created by kesso on 23.04.17.
 */
public class Archive {
    private ArrayList<Dossier> dossiers;

    public Archive(){
        dossiers = new ArrayList<Dossier>();
        ArchiveLogic.setDossiers(this);
    }

    public void updateDossier(Dossier dossier){
        for(Dossier d : dossiers){
            if(d.getID() == dossier.getID()){
                dossiers.remove(d);
                dossiers.add(dossier);
                return;
            }
        }
    }

    public void addDossier(Dossier dossier){
        this.dossiers.add(dossier);
    }

    public void addAllDossier(ArrayList<Dossier> dossier){
        this.dossiers.addAll(dossier);
    }

    public ArrayList<Dossier> getDossiers() {
        return dossiers;
    }

    public void setDossiers(ArrayList<Dossier> dossiers) {
        this.dossiers = dossiers;
    }
}
