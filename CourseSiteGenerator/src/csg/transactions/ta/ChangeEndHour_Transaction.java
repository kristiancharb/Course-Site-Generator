/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.ta;
import jtps.jTPS_Transaction;
import csg.data.TAData;
import csg.CSGApp;
import csg.file.TimeSlot;
import csg.workspace.TATab;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import csg.file.TimeSlot;
/**
 *
 * @author kristiancharbonneau
 */
public class ChangeEndHour_Transaction implements jTPS_Transaction{
    CSGApp app;
    int oldEndTime;
    int newEndTime;
    ArrayList<TimeSlot> officeHoursList;
    
    public ChangeEndHour_Transaction(CSGApp app, int oldEndTime, int newEndTime, ArrayList<TimeSlot> officeHoursList){
        this.app = app;
        this.oldEndTime = oldEndTime;
        this.newEndTime = newEndTime;
        this.officeHoursList = officeHoursList;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.changeEndHour(newEndTime);
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.changeEndHour(oldEndTime);
        TATab taTab = app.getCSGWorkspace().getTATab();
        GridPane officeHoursGridPane = taTab.getOfficeHoursGridPane();
        officeHoursGridPane.getChildren().clear();
        taTab.reloadOfficeHoursGrid(data);
        
        for(int i = 0; i < officeHoursList.size(); i++){
            String name = officeHoursList.get(i).getName();
            String day = officeHoursList.get(i).getDay();
            String time = officeHoursList.get(i).getTime();
            
            data.addOfficeHoursReservation(day, time, name);
        }
        
    }
    
}