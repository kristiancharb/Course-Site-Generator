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
/**
 *
 * @author kristiancharbonneau
 */
public class ToggleTACell_Transaction implements jTPS_Transaction{
    CSGApp app;
    String cellKey;
    String taName;
    
    public ToggleTACell_Transaction(CSGApp app, String cellKey, String taName){
        this.app = app;
        this.cellKey = cellKey;
        this.taName = taName;
    }
    
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.toggleTAOfficeHours(cellKey, taName);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
       
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.toggleTAOfficeHours(cellKey, taName);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    
    
}
