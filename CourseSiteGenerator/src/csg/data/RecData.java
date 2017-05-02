/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;
import csg.CSGApp;
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
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        
    }
    
    public void updateRec(Recitation rec, String section, String instr, String time, String location, String ta1, String ta2){
        recitations.remove(rec);
        
        Recitation newRec = new Recitation(section, instr, time, location, ta1, ta2);  
        recitations.add(newRec);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void deleteRec(Recitation rec){
        if(rec != null){
            recitations.remove(rec);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }
}
