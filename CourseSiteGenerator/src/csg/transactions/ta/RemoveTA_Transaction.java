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
import csg.file.TimeSlot;
import java.util.ArrayList;
/**
 *
 * @author kristiancharbonneau
 */
public class RemoveTA_Transaction implements jTPS_Transaction{
    CSGApp app;
    TeachingAssistant ta;
    ArrayList<TimeSlot> TAHoursList;
    
    public RemoveTA_Transaction(CSGApp initApp, TeachingAssistant initTA, ArrayList<TimeSlot> initTAHoursList){
        TAHoursList = initTAHoursList;
        app = initApp;
        ta = initTA;
    }
    
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        if(ta != null)
            data.removeTATransaction(ta);
       
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        if(ta != null){
            data.addTA(ta.getGrad().get(), ta.getName(), ta.getEmail());
            for(TimeSlot ts: TAHoursList){
                String name = ts.getName();
                String day = ts.getDay();
                String time = ts.getTime();
                data.addOfficeHoursReservation(day, time, name);
            }
        }
        
    }
    
    @Override
    public String toString(){
        return ta.toString();
    }
    
}
