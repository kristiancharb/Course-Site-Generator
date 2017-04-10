/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

import java.util.Locale;
import djf.AppTemplate;
import csg.workspace.CSGWorkspace;
import csg.file.CSGFile;
import csg.style.CSGStyle;
import csg.data.CSGData;

import static javafx.application.Application.launch;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGApp extends AppTemplate {

    /**
     * This hook method must initialize all four components in the proper order
     * ensuring proper dependencies are respected, meaning all proper objects
     * are already constructed when they are needed for use, since some may need
     * others for initialization.
     */
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL FOUR COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, SO BE CAREFUL OF THE ORDER
        
        
        dataComponent = new CSGData(this);
        workspaceComponent = new CSGWorkspace(this);
        fileComponent = new CSGFile(this);
        styleComponent = new CSGStyle(this);

    }
    
    public CSGWorkspace getCSGWorkspace(){
        return (CSGWorkspace)workspaceComponent;
    }
    
    public CSGData getCSGData(){
        return (CSGData)dataComponent;
    }

    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method
     * inherited from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
}
