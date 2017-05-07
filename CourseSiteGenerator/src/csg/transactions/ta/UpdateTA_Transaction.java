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
public class UpdateTA_Transaction implements jTPS_Transaction{
    CSGApp app;
    TeachingAssistant oldTA;
    String newName;
    String newEmail;
    
    public UpdateTA_Transaction(CSGApp app, TeachingAssistant oldTA, String newName, String newEmail){
        this.app = app;
        this.oldTA = oldTA;
        this.newName = newName;
        this.newEmail = newEmail;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        data.updateTA(oldTA, oldTA.getGrad().get(), newName, newEmail);
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToTATab();
        TAData data = app.getCSGData().getTAData();
        TeachingAssistant newTA = new TeachingAssistant(newName, newEmail);
        data.updateTA(newTA, oldTA.getGrad().get(), oldTA.getName(), oldTA.getEmail());
    }
    
    
}
