/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import java.util.Collections;
import properties_manager.PropertiesManager;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import jtps.jTPS;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGWorkspace extends AppWorkspaceComponent{
    CSGApp app;
    CSGController controller;
    
    TabPane tabs;
    CDTab cdTab; 
    TATab taTab;
    RecTab recTab;
    SchedTab schedTab;
    ProjectTab projectTab;
    
    static jTPS jTPS;
    
    
    
    
    public CSGWorkspace(CSGApp initApp){
        app = initApp;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        controller = new CSGController(initApp);
        
        jTPS = new jTPS();
        tabs = new TabPane();
        cdTab = new CDTab(initApp, controller, this); 
        taTab = new TATab(initApp, controller, this);
        recTab = new RecTab(initApp, controller, this);
        schedTab = new SchedTab(initApp, controller, this);
        projectTab = new ProjectTab(initApp, controller, this);
        tabs.getTabs().addAll(cdTab, taTab, recTab, schedTab, projectTab);
        workspace = new BorderPane();
        ((BorderPane) workspace).setCenter(tabs);
        
        workspace.setOnKeyPressed(e -> {
            if((e.getCode() == KeyCode.Z) && (e.isControlDown())){
                jTPS.undoTransaction();
                
            }
            if((e.getCode() == KeyCode.Y) && (e.isControlDown())){
                jTPS.doTransaction();
                
            }
        });
        
           
    }
    
    public TATab getTATab(){
        return taTab;
    }
    
    public TabPane getTabs(){
        return tabs;
    }
    
    public CDTab getCDTab(){
        return cdTab;
    }
    
    public RecTab getRecTab(){
        return recTab;
    }
    
    public SchedTab getSchedTab(){
        return schedTab;
    }
    
    public ProjectTab getProjectTab(){
        return projectTab;
    }
    public jTPS getJTPS(){
        return jTPS;
    }
    public void jumpToTATab(){
        tabs.getSelectionModel().select(taTab);
    }
    public void jumpToSchedTab(){
        tabs.getSelectionModel().select(schedTab);
    }
    public void jumpToProjectTab(){
        tabs.getSelectionModel().select(projectTab);
    }
    public void jumpToRecTab(){
        tabs.getSelectionModel().select(recTab);
    }
    
    
    @Override
    public BorderPane getWorkspace(){
        return (BorderPane)workspace;
    }
    
    @Override
    public void resetWorkspace(){
        taTab.resetTATab();
        controller.handleClearRec();
        controller.handleClearTeam();
        controller.handleClearStudent();
        
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent){ 
        CSGData csgData = (CSGData)dataComponent;
        taTab.reloadTATab(csgData.getTAData());
        cdTab.reloadCDTab();
        schedTab.reloadSchedTab();
        Collections.sort(csgData.getProjectData().getStudents());
        Collections.sort(csgData.getProjectData().getTeams());
        Collections.sort(csgData.getRecData().getRecitations());
        Collections.sort(csgData.getSchedData().getItems());
        
    }
    
}
