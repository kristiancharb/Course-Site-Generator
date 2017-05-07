/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions.project;
import jtps.jTPS_Transaction;
import csg.CSGApp;
import csg.data.Team;
import csg.data.ProjectData;
import csg.data.Student;
import java.util.ArrayList;


/**
 *
 * @author kristiancharbonneau
 */
public class RemoveTeam_Transaction implements jTPS_Transaction{
    CSGApp app;
    Team team;
    ArrayList<Student> studentsInTeam;
    
    public RemoveTeam_Transaction(CSGApp app, Team team, ArrayList<Student> studentsInTeam){
        this.app = app;
        this.team = team;
        this.studentsInTeam = studentsInTeam;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.removeTeamTransaction(team);
        for(Student s: studentsInTeam){
            projData.removeStudentTransaction(s);
        }
        
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.addTeam(team.getName(), team.getColor(), team.getTextColor(), team.getLink());
        for(Student s: studentsInTeam){
            projData.addStudent(s.getFirstName(), s.getLastName(), s.getTeam(), s.getRole());
        }
    }
}
