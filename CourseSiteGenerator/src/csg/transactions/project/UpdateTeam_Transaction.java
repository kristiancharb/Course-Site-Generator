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
public class UpdateTeam_Transaction implements jTPS_Transaction{
    CSGApp app;
    Team oldTeam;
    Team newTeam;
    
    public UpdateTeam_Transaction(CSGApp app, Team oldTeam, Team newTeam){
        this.app = app;
        this.oldTeam = oldTeam;
        this.newTeam = newTeam;
    }
    
    @Override
    public void doTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.updateTeam(oldTeam, newTeam.getName(), newTeam.getColor(), newTeam.getTextColor(), newTeam.getLink());
        projData.removeTeamTransaction(oldTeam);
    }
    
    @Override
    public void undoTransaction(){
        app.getCSGWorkspace().jumpToProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        projData.updateTeam(newTeam, oldTeam.getName(), oldTeam.getColor(), oldTeam.getTextColor(), oldTeam.getLink());
        projData.removeTeamTransaction(newTeam);
    }
}
