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
        siteTemplates = FXCollections.observableArrayList();
    }

    public String getSubject() {
        return subject;
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

}
