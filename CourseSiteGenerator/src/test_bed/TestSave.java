/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;
import csg.data.CSGData;
import csg.CSGApp;
import djf.AppTemplate;
import csg.data.CDData;
import csg.data.TeachingAssistant;
import csg.data.TAData;
import properties_manager.PropertiesManager;
import csg.data.RecData;
import csg.data.SchedData;
import csg.data.ProjectData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.SiteTemplate;
import csg.data.Student;
import csg.data.Team;
import csg.file.CSGFile;
import csg.file.TimeSlot;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kristiancharbonneau
 */
public class TestSave {
    
    
    
    public void fillTAData(CSGData csgData){
        TAData taData = csgData.getTAData();
        taData.getTeachingAssistants().addAll(
                new TeachingAssistant("John Doe", "jdoe@gmail.com"),
                new TeachingAssistant("Jane Doe", "jane@gmail.com"),
                new TeachingAssistant("Bob", "bob@yahoo.com"));
        taData.getOfficeHoursList().add(new TimeSlot("MONDAY", "2_30am", "Bob"));
        taData.getOfficeHoursList().add(new TimeSlot("TUESDAY", "4_00am", "John Doe"));
        taData.getOfficeHoursList().add(new TimeSlot("MONDAY", "4_30am", "Bob"));            

        taData.setStartHour(1);
        taData.setEndHour(12);
        
    }
    
    public void fillCDData(CSGData csgData){
        CDData cdData = csgData.getCDData();
        cdData.setSubject("CSE");
        cdData.setYear("2017");
        cdData.setNumber("219");
        cdData.setSemester("Fall");
        cdData.setInstructorName("McKenna");
        cdData.setInstructorHome("mckenna.com");
        cdData.setTitle("Comp Sci III");
        cdData.setSiteTemplateDir("\\templates\\CSE219");
        cdData.setExportDirPath("CSE219\\Summer2017\\public");
        cdData.setBannerImagePath("yale.png");
        cdData.setLeftFootPath("yale.png");
        cdData.setRightFootPath("yale.png");
        cdData.setStyleSheet("seawolf.css");
        cdData.getSiteTemplates().addAll(
                new SiteTemplate("Home", "index.html", "HomeBuilder.js"),
                new SiteTemplate("Syllabus", "syllabus.html", "SyllabusBuilder.js"),
                new SiteTemplate("Schedule", "schedule.html", "ScheduleBuilder.js"),
                new SiteTemplate("HWs", "hws.html", "HWsBuilder"),
                new SiteTemplate("Projects", "projects.html", "ProjectBuilder.js"));
    }
    
    public void fillRecData(CSGData csgData){
        RecData recData = csgData.getRecData();
        recData.getRecitations().addAll(
                new Recitation("R02", "McKenna", "Wed 3:30pm - 4:23pm", "Old CS 2114",
                "Jane Doe", "Joe Shmo"),
                new Recitation("R05", "Banerjee", "Tues 5:30pm-6:23pm", "Old CS 2114",
                "", ""));
    }
    
    public void fillSchedData(CSGData csgData){
        SchedData schedData = csgData.getSchedData();
        schedData.setStartingMon("23.04.2017");
        schedData.setEndingFri("30.06.2017");
        schedData.getItems().addAll(
                new ScheduleItem("Holiday", "2/9/17", "Snow Day", ""),
                new ScheduleItem("Lecture", "2/14/17", "Lecture 3", "Event Programming"),
                new ScheduleItem("Holiday", "3/3/17", "Spring Break", ""));
    }
    
    public void fillProjectData(CSGData csgData){
        ProjectData projData = csgData.getProjectData();
        projData.getTeams().addAll(
                new Team("Atomic Comics", "552211", "ffffff", "atomiccomic.com"),
                new Team("C4 Comics", "235511", "ffffff", "c4comic.com"));
        projData.getStudents().addAll(new Student("Joe", "Shmo", "Atomic Comics", "Lead Designer"),
                new Student("Jane", "Doe", "Atomic Comics", "Lead Programmer"),
                new Student("Bob", "Johnson", "C4 Comics", "Lead Designer"));
    }
    
    public static void main(String[] args){
        TestSave testSave = new TestSave();
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        csgApp.setDataComponent(csgData);
        csgApp.setFileComponent(csgFile);
        testSave.fillCDData(csgData);
        testSave.fillTAData(csgData);
        testSave.fillRecData(csgData);
        testSave.fillSchedData(csgData);
        testSave.fillProjectData(csgData);

        try{
            csgApp.getCSGFile().saveData(csgApp.getCSGData(),System.getProperty("user.dir") + "/work/SiteSaveTest");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    
}
