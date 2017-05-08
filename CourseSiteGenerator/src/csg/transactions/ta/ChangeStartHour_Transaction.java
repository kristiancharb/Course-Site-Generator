/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.ta;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.CSGApp;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.workspace.TATab;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import csg.file.TimeSlot;
/**
 *
 * @author kristiancharbonneau
 */
public class ChangeStartHour_Transaction implements jTPS_Transaction{
    CSGApp app;
    int oldStartTime;
    int newStartTime;
    ArrayList<TimeSlot> officeHoursList;
    
    public ChangeStartHour_Transaction(CSGApp app, int oldStartTime, int newStartTime, ArrayList<TimeSlot> officeHoursList){
        this.app = app;
        this.oldStartTime = oldStartTime;
        this.newStartTime = newStartTime;
        this.officeHoursList = officeHoursList;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.changeStartHour(newStartTime);
        TATab taTab = app.getCSGWorkspace().getTATab();
        taTab.reloadComboBoxes(data);
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.changeStartHour(oldStartTime);
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
        taTab.reloadComboBoxes(data);
        
    }
    
}
