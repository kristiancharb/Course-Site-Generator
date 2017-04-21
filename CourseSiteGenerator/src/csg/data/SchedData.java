/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;
import csg.CSGApp;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author kristiancharbonneau
 */
public class SchedData {
    CSGApp app;
    CSGData data;
    
    String startingMon;
    String endingFri;
    ObservableList<ScheduleItem> items;
    
    public SchedData(CSGApp app, CSGData data){
        this.app = app;
        this.data = data;
        
        items = FXCollections.observableArrayList();
        
        
    }
    
    public ObservableList getItems(){
        return items;
    }
    public String getStartingMon(){
        return startingMon;
    }
    public String getEndingFri(){
        return endingFri;
    }
    public void setStartingMon(String s){
        startingMon = s;
    }
    public void setEndingFri(String s){
        endingFri = s;
    }
    
}
