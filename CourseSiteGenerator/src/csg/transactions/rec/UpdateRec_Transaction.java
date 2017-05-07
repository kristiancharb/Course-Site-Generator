/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.rec;
import jtps.jTPS_Transaction;
import csg.CSGApp;
import csg.data.RecData;
import csg.data.Recitation;
/**
 *
 * @author kristiancharbonneau
 */
public class UpdateRec_Transaction implements jTPS_Transaction{
    CSGApp app;
    Recitation oldRec;
    Recitation newRec;
    
    public UpdateRec_Transaction(CSGApp app, Recitation oldRec, Recitation newRec){
        this.app = app;
        this.oldRec = oldRec;
        this.newRec = newRec;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToRecTab();
        RecData recData = app.getCSGData().getRecData();
        recData.updateRec(oldRec, newRec.getSection(), newRec.getInstructor(), newRec.getTime(), newRec.getLocation(),
                newRec.getTa1(), newRec.getTa2());
        recData.deleteRecTransaction(oldRec);
        
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToRecTab();
        RecData recData = app.getCSGData().getRecData();
        recData.updateRec(newRec, oldRec.getSection(), oldRec.getInstructor(), oldRec.getTime(), oldRec.getLocation(),
                oldRec.getTa1(), oldRec.getTa2());
        recData.deleteRecTransaction(newRec);
    }
    
}
