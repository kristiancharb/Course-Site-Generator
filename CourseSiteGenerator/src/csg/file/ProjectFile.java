/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;
import csg.CSGApp;
import csg.data.ProjectData;
import csg.data.Student;
import csg.data.Team;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
/**
 *
 * @author kristiancharbonneau
 */
public class ProjectFile {
    CSGApp app;
    
    public ProjectFile(CSGApp app){
        this.app = app;
    }
    
    public JsonObject makeProjectJson(ProjectData projectData) {
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = projectData.getTeams();
        for (Team t : teams) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add("name", t.getName())
                    .add("color", t.getColor())
                    .add("textColor", t.getTextColor())
                    .add("link", t.getLink())
                    .build();
            teamsArrayBuilder.add(tsJson);
        }
        JsonArray teamsArray = teamsArrayBuilder.build();
        
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = projectData.getStudents();
        for (Student s : students) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add("firstName", s.getFirstName())
                    .add("lastName", s.getLastName())
                    .add("team", s.getTeam())
                    .add("role", s.getRole())
                    .build();
            studentsArrayBuilder.add(tsJson);
        }
        JsonArray studentsArray = studentsArrayBuilder.build();

        JsonObject schedJson = Json.createObjectBuilder()
                .add("teams", teamsArray)
                .add("students", studentsArray).build();
        return schedJson;
    }
    
    public void loadProjectData(ProjectData projData, JsonObject json){
        JsonArray teamsArray = json.getJsonArray("teams");
        for(int i = 0; i < teamsArray.size(); i++){
            JsonObject jsonObj = teamsArray.getJsonObject(i);
            String name = jsonObj.getString("name");
            String color = jsonObj.getString("color");
            String textColor = jsonObj.getString("textColor");
            String link = jsonObj.getString("link");
            Team t = new Team(name, color, textColor, link);
            projData.getTeams().add(t);
        }
        
        JsonArray studArray = json.getJsonArray("students");
        for(int i = 0; i < studArray.size(); i++){
            JsonObject jsonObj = studArray.getJsonObject(i);
            String firstName = jsonObj.getString("firstName");
            String lastName = jsonObj.getString("lastName");
            String team = jsonObj.getString("team");
            String role = jsonObj.getString("role");
            Student s = new Student(firstName, lastName, team, role);
            projData.getStudents().add(s);
        }
        
    }
    
}
