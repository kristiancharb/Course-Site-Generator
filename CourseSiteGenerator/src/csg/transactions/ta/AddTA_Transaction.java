/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.ta;
import java.util.ArrayList;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.CSGApp;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.file.TimeSlot;
/**
 *
 * @author kristiancharbonneau
 */
public class AddTA_Transaction implements jTPS_Transaction{
    CSGApp app;
    TeachingAssistant ta;
    ArrayList<TimeSlot> TAHoursList;
    
    public AddTA_Transaction(CSGApp initApp, TeachingAssistant initTA, ArrayList<TimeSlot> initTAHoursList){
        app = initApp;
        ta = initTA;
        TAHoursList = initTAHoursList;
        
    }
    
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.addTA(ta.getName(), ta.getEmail());
        for(TimeSlot ts: TAHoursList){
            String name = ts.getName();
            String day = ts.getDay();
            String time = ts.getTime();
            data.addOfficeHoursReservation(day, time, name);
        }
       
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        TAHoursList = TimeSlot.buildTATimeSlotList(data, ta);
        data.removeTATransaction(ta);
    }
    
    @Override
    public String toString(){
        return ta.toString();
    }
    
}
