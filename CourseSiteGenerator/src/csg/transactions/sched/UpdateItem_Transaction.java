/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.sched;
import jtps.jTPS_Transaction;
import csg.CSGApp;
import csg.data.ScheduleItem;
import csg.data.SchedData;
/**
 *
 * @author kristiancharbonneau
 */
public class UpdateItem_Transaction implements jTPS_Transaction{
    CSGApp app;
    ScheduleItem oldItem;
    ScheduleItem newItem;
    
    public UpdateItem_Transaction(CSGApp app, ScheduleItem oldItem, ScheduleItem newItem){
        this.app = app;
        this.oldItem = oldItem;
        this.newItem = newItem;
        
    }
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToSchedTab();
        SchedData schedData = app.getCSGData().getSchedData();
        schedData.removeItemTransaction(oldItem);
        schedData.addItem(newItem.getType(), newItem.getDate(), newItem.getTime(), newItem.getTitle(),
                newItem.getTopic(), newItem.getLink(), newItem.getCriteria());
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToSchedTab();
        SchedData schedData = app.getCSGData().getSchedData();
        schedData.removeItemTransaction(newItem);
        schedData.addItem(oldItem.getType(), oldItem.getDate(), oldItem.getTime(), oldItem.getTitle(),
                oldItem.getTopic(), oldItem.getLink(), oldItem.getCriteria());
    }
    
}
