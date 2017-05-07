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
public class RemoveStudent_Transaction implements jTPS_Transaction{
    CSGApp app;
    Student student;
    
    public RemoveStudent_Transaction(CSGApp app, Student student){
        this.app = app;
        this.student = student;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.removeStudentTransaction(student);
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.addStudent(student.getFirstName(), student.getLastName(), student.getTeam(), student.getRole());
    }
    
    
}
