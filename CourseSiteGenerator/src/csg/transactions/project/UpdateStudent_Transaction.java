/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.project;
import jtps.jTPS_Transaction;
import csg.CSGApp;
import csg.data.Student;
import csg.data.ProjectData;
/**
 *
 * @author kristiancharbonneau
 */
public class UpdateStudent_Transaction implements jTPS_Transaction{
    CSGApp app;
    Student oldStud;
    Student newStud;
    
    public UpdateStudent_Transaction(CSGApp app, Student oldStud, Student newStud){
        this.app = app;
        this.oldStud = oldStud;
        this.newStud = newStud;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.updateStudent(oldStud, newStud.getFirstName(), newStud.getLastName(), newStud.getTeam(), newStud.getRole());
        projData.removeStudentTransaction(oldStud);
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.updateStudent(newStud, oldStud.getFirstName(), oldStud.getLastName(), oldStud.getTeam(), oldStud.getRole());
        projData.removeStudentTransaction(newStud);
    }
    
    
}
