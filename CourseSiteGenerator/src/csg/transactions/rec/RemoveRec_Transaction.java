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
public class RemoveRec_Transaction implements jTPS_Transaction{
    CSGApp app;
    Recitation rec;
    
    public RemoveRec_Transaction(CSGApp app, Recitation rec){
        this.app = app;
        this.rec = rec;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToRecTab();
        RecData recData = app.getCSGData().getRecData();
        recData.deleteRecTransaction(rec);
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToRecTab();
        RecData recData = app.getCSGData().getRecData();
        recData.addRec(rec.getSection(), rec.getInstructor(), rec.getTime(), rec.getLocation(), rec.getTa1(), rec.getTa2());
    }
    
}