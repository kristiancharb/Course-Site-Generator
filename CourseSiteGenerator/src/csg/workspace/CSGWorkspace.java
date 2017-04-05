/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import csg.CSGApp;
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
        
        TabPane tabs = new TabPane();
        CDTab cdTab = new CDTab(initApp); 
        TATab taTab = new TATab(initApp, controller);
        RecTab recTab = new RecTab(initApp);
        SchedTab schedTab = new SchedTab(initApp);
        ProjectTab projectTab = new ProjectTab(initApp);
        tabs.getTabs().addAll(cdTab, taTab, recTab, schedTab, projectTab);
        workspace = new BorderPane();
        ((BorderPane) workspace).setCenter(tabs);
        
        jTPS = new jTPS();   
    }
    
    public void resetWorkspace(){
        
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent){   
    }
    
}
