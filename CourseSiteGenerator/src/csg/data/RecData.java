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
    
}
