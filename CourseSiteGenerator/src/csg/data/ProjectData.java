/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import java.util.ArrayList;
import java.util.Collections;
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

    public ProjectData(CSGApp app, CSGData data) {
        this.app = app;
        this.data = data;

        students = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();

    }

    public ObservableList getStudents() {
        return students;
    }

    public ObservableList getTeams() {
        return teams;
    }

    public void addTeam(String name, String color, String textColor, String link) {
        Team t = new Team(name, color, textColor, link);
        teams.add(t);
        Collections.sort(teams);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void updateTeam(Team t, String name, String color, String textColor, String link) {
        Team newT = new Team(name, color, textColor, link);
        for(Student s: students){
            if(s.getTeam().equals(t.getName())){
                s.setTeam(name);
            }
        }
        teams.remove(t);
        teams.add(newT);
        Collections.sort(teams);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        app.getCSGWorkspace().getProjectTab().reloadProjectTab();
        
    }

    public void removeTeam(Team t) {
        if (t != null) {
            removeStudentsFromTeam(t);
            teams.remove(t);
            Collections.sort(teams);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
            
            
        }
    }
    
    public void addStudent(String firstName, String lastName, String team, String role){
        Student s = new Student(firstName, lastName, team, role);
        students.add(s);
        Collections.sort(students);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void updateStudent(Student s, String firstName, String lastName, String team, String role){
        Student newS = new Student(firstName, lastName, team, role);
        students.remove(s);
        students.add(newS);
        Collections.sort(students);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void removeStudent(Student s){
        if(s != null){
            students.remove(s);
            Collections.sort(students);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }
    
    public void removeTeamTransaction(Team team){
        Team toRemove = new Team();
        for(Team t: teams){
            if(t.equals(team))
                toRemove = t;
        }
        teams.remove(toRemove);
        Collections.sort(teams);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        
    }
    
    public void removeStudentTransaction(Student s){
       Student toRemove = new Student();
       for(Student student: students){
           if(s.equals(student)){
               toRemove = student;
           }
       }
       students.remove(toRemove);
       Collections.sort(students);
       app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void removeStudentsFromTeam(Team t){
        ArrayList<Student> toRemove = new ArrayList<>();
        for(Student s: students){
            if(s.getTeam().equals(t.getName())){
                toRemove.add(s);
            }
        }
        students.removeAll(toRemove);
    }
    
    public ArrayList<Student> getStudentsInTeam(Team t){
        ArrayList<Student> sList = new ArrayList<>();
        for(Student s: students){
            if(s.getTeam().equals(t.getName())){
                sList.add(s);
            }
        }
        return sList;
    }
    
    

}
