/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.CDData;
import csg.data.RecData;
import csg.data.Recitation;
import csg.data.SchedData;
import csg.data.ScheduleItem;
import csg.data.ProjectData;
import csg.data.Team;
import csg.data.Student;
import csg.data.SiteTemplate;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGFileTest {
    
    public CSGFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadData method, of class CSGFile.
     */
    /*
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");
        AppDataComponent data = null;
        String filePath = "";
        CSGFile instance = null;
        instance.loadData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveData method, of class CSGFile.
     */
    /*
    @Test
    public void testSaveData() throws Exception {
        System.out.println("saveData");
        AppDataComponent data = null;
        String filePath = "";
        CSGFile instance = null;
        instance.saveData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
    @Test
    public void testLoadCDData() throws Exception {
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        CDData cdData = csgData.getCDData();
        CDFile cdFile = csgFile.getCDFile();
        JsonArray jsonArray = csgFile.loadJSONFile(System.getProperty("user.dir") + "/work/SiteSaveTest");
        cdFile.loadCDData(cdData, jsonArray.getJsonObject(1));
        assertTrue(cdData.getSubject().equals("CSE"));
        assertTrue(cdData.getNumber().equals("219"));
        assertTrue(cdData.getYear().equals("2017"));
        assertTrue(cdData.getTitle().equals("Comp Sci III"));
        assertTrue(cdData.getInstructorHome().equals("mckenna.com"));
        assertTrue(cdData.getInstructorName().equals("McKenna"));
        assertTrue(cdData.getExportDirPath().equals("CSE219\\Summer2017\\public"));
        assertTrue(cdData.getSiteTemplateDir().equals("\\templates\\CSE219"));
        assertTrue(cdData.getBannerImagePath().equals("yale.png"));
        assertTrue(cdData.getLeftFootPath().equals("yale.png"));
        assertTrue(cdData.getRightFootPath().equals("yale.png"));
        assertTrue(cdData.getStyleSheet().equals("seawolf.css"));
        
        ArrayList<SiteTemplate> templates = new ArrayList<>();
        templates.add(new SiteTemplate("Home", "index.html", "HomeBuilder.js"));
        templates.add(new SiteTemplate("Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        templates.add(new SiteTemplate("Schedule", "schedule.html", "ScheduleBuilder.js"));
        templates.add(new SiteTemplate("HWs", "hws.html", "HWsBuilder"));
        templates.add(new SiteTemplate("Projects", "projects.html", "ProjectBuilder.js"));
        ObservableList<SiteTemplate> loadTemp = cdData.getSiteTemplates();
        for(int i = 0; i < templates.size(); i++){
            assertTrue(loadTemp.get(i).getUse().getValue().equals(templates.get(i).getUse().getValue()));
            assertTrue(loadTemp.get(i).getFileName().equals(templates.get(i).getFileName()));
            assertTrue(loadTemp.get(i).getNavbarTitle().equals(templates.get(i).getNavbarTitle()));
            assertTrue(loadTemp.get(i).getScript().equals(templates.get(i).getScript()));
        } 
    }
    
    @Test
    public void testLoadTAData() throws Exception{
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        TAData taData = csgData.getTAData();
        TAFile taFile = csgFile.getTAFile();
        JsonArray jsonArray = csgFile.loadJSONFile(System.getProperty("user.dir") + "/work/SiteSaveTest");
        taFile.loadTAData(taData, jsonArray.getJsonObject(0));
        
        assertTrue(taData.getStartHour() == 1);
        assertTrue(taData.getEndHour() == 12);
        
        ObservableList<TeachingAssistant> loadTas = taData.getTeachingAssistants();
        ObservableList<TeachingAssistant> tas = FXCollections.observableArrayList();
        tas.addAll(
                new TeachingAssistant("John Doe", "jdoe@gmail.com"),
                new TeachingAssistant("Jane Doe", "jane@gmail.com"),
                new TeachingAssistant("Bob", "bob@yahoo.com"));
        Collections.sort(tas);
        
        for(int i = 0; i < tas.size(); i++){
            assertTrue(loadTas.get(i).getGrad().getValue().equals(tas.get(i).getGrad().getValue()));
            assertTrue(loadTas.get(i).getName().equals(tas.get(i).getName()));
            assertTrue(loadTas.get(i).getEmail().equals(tas.get(i).getEmail()));
        }
        
        ArrayList<TimeSlot> loadHoursList = taData.getOfficeHoursList();
        ArrayList<TimeSlot> hoursList = new ArrayList<>();
        hoursList.add(new TimeSlot("MONDAY", "2_30am", "Bob"));
        hoursList.add(new TimeSlot("TUESDAY", "4_00am", "John Doe"));
        hoursList.add(new TimeSlot("MONDAY", "4_30am", "Bob")); 
        
        for(int i = 0; i < hoursList.size(); i++){
            assertTrue(loadHoursList.get(i).getDay().equals(hoursList.get(i).getDay()));
            assertTrue(loadHoursList.get(i).getTime().equals(hoursList.get(i).getTime()));
            assertTrue(loadHoursList.get(i).getName().equals(hoursList.get(i).getName()));
        }
    }
    
    @Test 
    public void testLoadRecData() throws Exception{
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        RecData recData = csgData.getRecData();
        RecFile recFile = csgFile.getRecFile();
        JsonArray jsonArray = csgFile.loadJSONFile(System.getProperty("user.dir") + "/work/SiteSaveTest");
        recFile.loadRecData(recData, jsonArray.getJsonObject(2));
        
        ObservableList<Recitation> loadRecs = recData.getRecitations();
        ObservableList<Recitation> recs = FXCollections.observableArrayList();
        recs.addAll(
                new Recitation("R02", "McKenna", "Wed 3:30pm - 4:23pm", "Old CS 2114",
                "Jane Doe", "Joe Shmo"),
                new Recitation("R05", "Banerjee", "Tues 5:30pm-6:23pm", "Old CS 2114",
                "", ""));
        
        for(int i = 0; i < recs.size(); i++){
            assertTrue(loadRecs.get(i).getInstructor().equals(recs.get(i).getInstructor()));
            assertTrue(loadRecs.get(i).getLocation().equals(recs.get(i).getLocation()));
            assertTrue(loadRecs.get(i).getSection().equals(recs.get(i).getSection()));
            assertTrue(loadRecs.get(i).getTa1().equals(recs.get(i).getTa1()));
            assertTrue(loadRecs.get(i).getTa2().equals(recs.get(i).getTa2()));
            assertTrue(loadRecs.get(i).getTime().equals(recs.get(i).getTime()));
        }
    }
    
    @Test
    public void testLoadSchedData() throws Exception{
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        SchedData schedData = csgData.getSchedData();
        SchedFile schedFile = csgFile.getSchedFile();
        JsonArray jsonArray = csgFile.loadJSONFile(System.getProperty("user.dir") + "/work/SiteSaveTest");
        schedFile.loadSchedData(schedData, jsonArray.getJsonObject(3));
        
        assertTrue(schedData.getStartingMon().equals("23.04.2017"));
        assertTrue(schedData.getEndingFri().equals("30.06.2017"));
        
        ObservableList<ScheduleItem> loadItems = schedData.getItems();
        ObservableList<ScheduleItem> items =FXCollections.observableArrayList();
        items.addAll(
                new ScheduleItem("Holiday", "2/9/17", "Snow Day", ""),
                new ScheduleItem("Lecture", "2/14/17", "Lecture 3", "Event Programming"),
                new ScheduleItem("Holiday", "3/3/17", "Spring Break", ""));
        
        for(int i = 0; i < items.size(); i++){
            assertTrue(loadItems.get(i).getTitle().equals(items.get(i).getTitle()));
            assertTrue(loadItems.get(i).getDate().equals(items.get(i).getDate()));
            assertTrue(loadItems.get(i).getTopic().equals(items.get(i).getTopic()));
            assertTrue(loadItems.get(i).getType().equals(items.get(i).getType()));
        }
        
    }
    
    @Test
    public void testLoadProjectData() throws Exception {
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        ProjectData projectData = csgData.getProjectData();
        ProjectFile projectFile = csgFile.getProjectFile();
        JsonArray jsonArray = csgFile.loadJSONFile(System.getProperty("user.dir") + "/work/SiteSaveTest");
        projectFile.loadProjectData(projectData, jsonArray.getJsonObject(4));
        
        ObservableList<Team> teams = FXCollections.observableArrayList();
        teams.addAll(new Team("Atomic Comics", "552211", "ffffff", "atomiccomic.com"),
                new Team("C4 Comics", "235511", "ffffff", "c4comic.com"));
        ObservableList<Team> loadTeams = projectData.getTeams();
        
        for(int i = 0; i < teams.size(); i++){
            assertTrue(loadTeams.get(i).getName().equals(teams.get(i).getName()));
            assertTrue(loadTeams.get(i).getColor().equals(teams.get(i).getColor()));
            assertTrue(loadTeams.get(i).getTextColor().equals(teams.get(i).getTextColor()));
            assertTrue(loadTeams.get(i).getLink().equals(teams.get(i).getLink()));
        }
        
        ObservableList<Student> students = FXCollections.observableArrayList();
        students.addAll(new Student("Joe", "Shmo", "Atomic Comics", "Lead Designer"),
                new Student("Jane", "Doe", "Atomic Comics", "Lead Programmer"),
                new Student("Bob", "Johnson", "C4 Comics", "Lead Designer"));
        ObservableList<Student> loadStudents = projectData.getStudents();
        
        for(int i = 0; i < students.size(); i++){
            assertTrue(loadStudents.get(i).getFirstName().equals(students.get(i).getFirstName()));
            assertTrue(loadStudents.get(i).getLastName().equals(students.get(i).getLastName()));
            assertTrue(loadStudents.get(i).getTeam().equals(students.get(i).getTeam()));
            assertTrue(loadStudents.get(i).getRole().equals(students.get(i).getRole()));
        }
    }
    
    


    
}
