/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kristiancharbonneau
 */
public class CDData {

    CSGApp app;
    CSGData data;

    String subject;
    String semester;
    String number;
    String year;
    String title;
    String instructorName;
    String instructorHome;
    String exportDirPath;

    String siteTemplateDir;
    ObservableList<SiteTemplate> siteTemplates;
    ObservableList<String> stylesheets;

    String bannerImagePath;
    String leftFootPath;
    String rightFootPath;
    String styleSheet;

    public CDData(CSGApp app, CSGData data) {
        this.app = app;
        this.data = data;

        subject = "";
        semester = "";
        number = "";
        year = "";
        title = "";
        instructorName = "";
        instructorHome = "";
        exportDirPath = "";
        siteTemplateDir = "";
        styleSheet = "";
        bannerImagePath = "";
        leftFootPath = "";
        rightFootPath = "";

        siteTemplates = FXCollections.observableArrayList();
        stylesheets = FXCollections.observableArrayList();
         
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + "/work/css";
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        for(File f: listOfFiles){
            if(f.getName().contains(".css"))
            stylesheets.add(f.getName());
        }
        
        
        
    }

    public String getSubject() {
        return subject;
    }
    public ObservableList getStylesheets(){
        return stylesheets;
    }

    public String getSemester() {
        return semester;
    }

    public String getNumber() {
        return number;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorHome() {
        return instructorHome;
    }

    public String getExportDirPath() {
        return exportDirPath;
    }

    public String getSiteTemplateDir() {
        return siteTemplateDir;
    }

    public String getBannerImagePath() {
        return bannerImagePath;
    }

    public String getLeftFootPath() {
        return leftFootPath;
    }

    public String getRightFootPath() {
        return rightFootPath;
    }

    public String getStyleSheet() {
        return styleSheet;
    }

    public void setSubject(String s) {
        subject = s;
    }

    public void setSemester(String s) {
        semester = s;
    }

    public void setNumber(String s) {
        number = s;
    }

    public void setYear(String s) {
        year = s;
    }

    public void setTitle(String s) {
        title = s;
    }

    public void setInstructorName(String s) {
        instructorName = s;
    }

    public void setInstructorHome(String s) {
        instructorHome = s;
    }

    public void setExportDirPath(String s) {
        exportDirPath = s;
    }

    public void setSiteTemplateDir(String s) {
        siteTemplateDir = s;
    }

    public void setBannerImagePath(String s) {
        bannerImagePath = s;
    }

    public void setLeftFootPath(String s) {
        leftFootPath = s;
    }

    public void setRightFootPath(String s) {
        rightFootPath = s;
    }

    public void setStyleSheet(String s) {
        styleSheet = s;
    }

    public ObservableList getSiteTemplates() {
        return siteTemplates;
    }

    public void resetCDData() {
        siteTemplates.clear();
        subject = "";
        semester = "";
        number = "";
        year = "";
        title = "";
        instructorName = "";
        instructorHome = "";
        exportDirPath = "";
        siteTemplateDir = "";
        styleSheet = "";
        bannerImagePath = "";
        leftFootPath = "";
        rightFootPath = "";
    }

    public void changeExportDir(String filePath) {
        exportDirPath = filePath;
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void changeTemplateDir(String filePath) {
        siteTemplateDir = filePath;
        
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        siteTemplates.clear();
        File jsFolder = new File(filePath + "/js");
        File[] listOfJSFiles = jsFolder.listFiles();
        
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].getName().equals("index.html")){
                for(int j = 0; j < listOfJSFiles.length; j++){
                    if(listOfJSFiles[j].getName().equalsIgnoreCase("HomeBuilder.js")){
                        siteTemplates.add(new SiteTemplate("Home", "index.html", "HomeBuilder.js"));                      
                    }           
                }
            }
            if(listOfFiles[i].getName().equals("syllabus.html")){
                for(int j = 0; j < listOfJSFiles.length; j++){
                    if(listOfJSFiles[j].getName().equalsIgnoreCase("SyllabusBuilder.js")){
                        siteTemplates.add(new SiteTemplate("Syllabus", "syllabus.html", "SyllabusBuilder.js"));                      
                    }           
                }
            }
            if(listOfFiles[i].getName().equals("schedule.html")){
                for(int j = 0; j < listOfJSFiles.length; j++){
                    if(listOfJSFiles[j].getName().equalsIgnoreCase("ScheduleBuilder.js")){
                        siteTemplates.add(new SiteTemplate("Schedule", "schedule.html", "ScheduleBuilder.js"));                      
                    }           
                }
            }
            if(listOfFiles[i].getName().equals("hws.html")){
                for(int j = 0; j < listOfJSFiles.length; j++){
                    if(listOfJSFiles[j].getName().equalsIgnoreCase("HWsBuilder.js")){
                        siteTemplates.add(new SiteTemplate("HWs", "hws.html", "HWsBuilder.js"));                      
                    }           
                }
            }
            if(listOfFiles[i].getName().equals("projects.html")){
                for(int j = 0; j < listOfJSFiles.length; j++){
                    if(listOfJSFiles[j].getName().equalsIgnoreCase("ProjectsBuilder.js")){
                        siteTemplates.add(new SiteTemplate("Projects", "projects.html", "ProjectsBuilder.js"));                      
                    }           
                }
            }
        }
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void changeBannerImage(String filePath){
        String[] paths = filePath.split("/");
        String imagePath = paths[paths.length-1];
        System.out.println(imagePath);
        bannerImagePath = imagePath;
        
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

}
