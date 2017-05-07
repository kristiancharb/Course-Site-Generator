/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;
import csg.CSGApp;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author kristiancharbonneau
 */
public class RecData {
    CSGApp app;
    CSGData data;
    
    ObservableList<Recitation> recitations;
    
    public RecData(CSGApp app, CSGData data){
        this.app = app;
        this.data = data;
        
        recitations = FXCollections.observableArrayList();
        
    }
    
    public ObservableList getRecitations(){
        return recitations;
    }
    
    public void addRec(String section, String instr, String time, String location, String ta1, String ta2){
        Recitation rec = new Recitation(section, instr, time, location, ta1, ta2);
        
        recitations.add(rec);
        Collections.sort(recitations);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        
    }
    
    public void updateRec(Recitation rec, String section, String instr, String time, String location, String ta1, String ta2){
        recitations.remove(rec);
        
        Recitation newRec = new Recitation(section, instr, time, location, ta1, ta2);  
        recitations.add(newRec);
        Collections.sort(recitations);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void deleteRec(Recitation rec){
        if(rec != null){
            recitations.remove(rec);
            Collections.sort(recitations);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }
    public void deleteRecTransaction(Recitation rec){
        Recitation toRemove = new Recitation();
        for(Recitation r: recitations){
            if(r.equals(rec))
                toRemove = r;
        }
        recitations.remove(toRemove);
        Collections.sort(recitations);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void removeTAFromRecs(String taName){
        for(Recitation r: recitations){
            if(r.ta1.equals(taName))
                r.setTa1("");
            if(r.ta2.equals(taName))
                r.setTa2("");  
        }
        Collections.sort(recitations);
        app.getCSGWorkspace().getRecTab().getRecTable().refresh();
    }
    
    public void updateTAinRecs(String oldTA, String newTA){
        for(Recitation r: recitations){
            if(r.ta1.equals(oldTA))
                r.setTa1(newTA);
            if(r.ta2.equals(oldTA))
                r.setTa2(newTA );  
        }
        Collections.sort(recitations);
        app.getCSGWorkspace().getRecTab().getRecTable().refresh();
        
    }
}
