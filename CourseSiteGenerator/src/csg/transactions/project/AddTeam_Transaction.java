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
/**
 *
 * @author kristiancharbonneau
 */
public class AddTeam_Transaction implements jTPS_Transaction{
    CSGApp app;
    Team team;
    
    public AddTeam_Transaction(CSGApp app, Team team){
        this.app = app;
        this.team = team;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.addTeam(team.getName(), team.getColor(), team.getTextColor(), team.getLink());
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.removeTeamTransaction(team);
    }
    
}
