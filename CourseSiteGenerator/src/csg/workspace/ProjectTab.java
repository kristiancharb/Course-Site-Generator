/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author kristiancharbonneau
 */
public class ProjectTab extends Tab{
    CSGApp app;
    
    public ProjectTab(CSGApp initApp){
        app = initApp;
        this.setText("Projects");
    }
    
}
