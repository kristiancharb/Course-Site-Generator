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
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void updateTeam(Team t, String name, String color, String textColor, String link) {
        Team newT = new Team(name, color, textColor, link);
        teams.remove(t);
        teams.add(newT);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void removeTeam(Team t) {
        if (t != null) {
            teams.remove(t);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }
    
    public void addStudent(String firstName, String lastName, String team, String role){
        Student s = new Student(firstName, lastName, team, role);
        students.add(s);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void updateStudent(Student s, String firstName, String lastName, String team, String role){
        Student newS = new Student(firstName, lastName, team, role);
        students.remove(s);
        students.add(newS);
    }
    
    public void removeStudent(Student s){
        if(s != null){
            students.remove(s);
            app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        }
    }

}
