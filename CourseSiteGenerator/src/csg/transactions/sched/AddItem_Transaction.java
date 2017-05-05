/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.sched;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.CSGApp;
import csg.data.ScheduleItem;
import csg.data.SchedData;
/**
 *
 * @author kristiancharbonneau
 */
public class AddItem_Transaction implements jTPS_Transaction{
    CSGApp app;
    ScheduleItem item;
    
    public AddItem_Transaction(CSGApp app, ScheduleItem item){
        this.app = app;
        this.item = item;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToSchedTab();
        SchedData schedData = app.getCSGData().getSchedData();
        schedData.addItem(item.getType(), item.getDate(), item.getTime(), item.getTitle(),item.getTopic(),
                item.getLink(), item.getCriteria());
        
        
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToSchedTab();
        SchedData schedData = app.getCSGData().getSchedData();
        schedData.removeItemTransaction(item);
    }
}
