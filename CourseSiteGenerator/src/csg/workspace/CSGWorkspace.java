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
import properties_manager.PropertiesManager;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
        
        tabs = new TabPane();
        cdTab = new CDTab(initApp, controller, this); 
        taTab = new TATab(initApp, controller, this);
        recTab = new RecTab(initApp, controller, this);
        schedTab = new SchedTab(initApp, controller, this);
        projectTab = new ProjectTab(initApp, controller, this);
        tabs.getTabs().addAll(cdTab, taTab, recTab, schedTab, projectTab);
        workspace = new BorderPane();
        ((BorderPane) workspace).setCenter(tabs);
        
        jTPS = new jTPS();   
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
    
    
    @Override
    public BorderPane getWorkspace(){
        return (BorderPane)workspace;
    }
    
    @Override
    public void resetWorkspace(){
        taTab.resetTATab(); 
        
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent){ 
        CSGData csgData = (CSGData)dataComponent;
        TAData taData = csgData.getTAData();
        taTab.reloadTATab(taData);
        
        
    }
    
}
