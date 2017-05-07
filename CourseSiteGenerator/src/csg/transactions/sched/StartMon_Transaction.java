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
import csg.workspace.ChangeMondayListener;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

/**
 *
 * @author kristiancharbonneau
 */
public class StartMon_Transaction implements jTPS_Transaction {

    CSGApp app;
    LocalDate oldVal;
    LocalDate newVal;

    public StartMon_Transaction(CSGApp app, LocalDate oldVal, LocalDate newVal) {
        this.app = app;
        this.oldVal = oldVal;
        this.newVal = newVal;
    }

    @Override
    public void doTransaction() {
        app.getCSGWorkspace().jumpToSchedTab();
        SchedData schedData = app.getCSGData().getSchedData();

        schedData.changeStartMon(newVal);
        app.getCSGWorkspace().getSchedTab().reloadSchedTab();


    }

    @Override
    public void undoTransaction() {
        app.getCSGWorkspace().jumpToSchedTab();
        SchedData schedData = app.getCSGData().getSchedData();
        
        schedData.changeStartMon(oldVal);

        app.getCSGWorkspace().getSchedTab().reloadSchedTab();

    }

}
