/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;
import csg.CSGApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.data.Student;
import csg.data.Team;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
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
    
    public void exportTeamsAndStudents(CSGData csgData, String filePath) throws Exception{
        ProjectData projectData = csgData.getProjectData();
        
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = projectData.getTeams();
        for(Team t: teams){
            String textColor = "";
            if(t.getTextColor().equals("000000"))
                textColor = "black";
            else if(t.getTextColor().equals("ffffff"))
                textColor = "white";
            else 
                textColor = t.getTextColor();
                       
            JsonObject json = Json.createObjectBuilder()
                    .add("name", t.getName())
                    .add("red", t.getRed())
                    .add("green", t.getGreen())
                    .add("blue", t.getBlue())
                    .add("text_color", textColor)
                    .build();
            teamsArrayBuilder.add(json);
        }
        JsonArray teamsArray = teamsArrayBuilder.build();
        
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = projectData.getStudents();
        for(Student s: students){
            JsonObject json = Json.createObjectBuilder()
                    .add("lastName", s.getLastName())
                    .add("firstName", s.getFirstName())
                    .add("team", s.getTeam())
                    .add("role", s.getRole())
                    .build();
            studentsArrayBuilder.add(json);
        }
        JsonArray studentsArray = studentsArrayBuilder.build();
        
        JsonObject json = Json.createObjectBuilder()
                .add("teams", teamsArray)
                .add("students", studentsArray)
                .build();
        
                // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(json);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(json);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    public void exportProjects(CSGData csgData, String filePath) throws Exception{
        ProjectData projectData = csgData.getProjectData();
       
        JsonArrayBuilder projectsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = projectData.getTeams();
        ObservableList<Student> students = projectData.getStudents();
        for(Team t: teams){
            String name = t.getName();
            JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
            for(Student s: students){
                if(s.getTeam().equals(name)){
                    studentsArrayBuilder.add(s.getFirstName() + " " + s.getLastName());
                } 
            }
            JsonArray studentsArray = studentsArrayBuilder.build();
            
            JsonObject project = Json.createObjectBuilder()
                    .add("name", name)
                    .add("students", studentsArray)
                    .add("link", t.getLink())
                    .build();
            
            projectsArrayBuilder.add(project);
            
        }
        JsonArray projectsArray = projectsArrayBuilder.build();
        JsonObject json = Json.createObjectBuilder()
                .add("semester", "Spring 2016")
                .add("projects", projectsArray)
                .build();
        
        JsonArrayBuilder jsonArr = Json.createArrayBuilder();
        jsonArr.add(json);
        JsonObject work = Json.createObjectBuilder()
                .add("work", jsonArr).build();
        
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(work);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(work);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();       
    }
    
}
