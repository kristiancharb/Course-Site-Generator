/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;
import csg.CSGApp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public void resetSchedData(){
        items.clear();
        startingMon = null;
        endingFri = null;
    }
    public void changeStartMon(LocalDate newVal){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        String startMon = newVal.format(formatter);
        startingMon = startMon;
    }
    public void changeEndFri(LocalDate newVal){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        String endFri = newVal.format(formatter);
        endingFri = endFri;
    }
    public void addItem(String type, String date, String time, String title, String topic, String link, String criteria){
        ScheduleItem i = new ScheduleItem(type, date, title, topic, link, criteria, time);
        items.add(i);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void updateItem(ScheduleItem item, String type, String date, String time, String title, String topic, String link, String criteria){
        items.remove(item);
        ScheduleItem i = new ScheduleItem(type, date, title, topic, link, criteria, time);
        items.add(i);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void removeItem(ScheduleItem item){
        if(item != null){
            items.remove(item);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }
    
}
