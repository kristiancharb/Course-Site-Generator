/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;
import csg.CSGApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kristiancharbonneau
 */
public class ProjectData {
    CSGApp app;
    CSGData data;
    
    ObservableList<Student> students;
    ObservableList<Team> teams;
    
    public ProjectData(CSGApp app, CSGData data){
        this.app = app;
        this.data = data;
        
        students = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
    
    }
    
    public ObservableList getStudents(){
        return students;
    }
    
    public ObservableList getTeams(){
        return teams;
    }
    
}
