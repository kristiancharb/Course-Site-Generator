/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import djf.components.AppDataComponent;
import csg.CSGApp;


/**
 *
 * @author kristiancharbonneau
 */
public class CSGData implements AppDataComponent{
    CSGApp app;
    CDData cdData;
    TAData taData;
    RecData recData;
    SchedData schedData;
    ProjectData projectData;
    
    
    public CSGData(CSGApp initApp){
        app = initApp;
        taData = new TAData(initApp, this);
        cdData = new CDData(initApp, this);
        recData = new RecData(initApp, this);
        schedData = new SchedData(initApp, this);
        projectData = new ProjectData(initApp, this);
            
    }
    
    
    
    @Override
    public void resetData(){
        taData.resetTAData();
        cdData.resetCDData();
        schedData.resetSchedData();
        recData.getRecitations().clear();
        projectData.getStudents().clear();
        projectData.getTeams().clear();
    }
    
    public TAData getTAData(){
        return taData;
    }
    
    public CDData getCDData(){
        return cdData;
    }
    
    public RecData getRecData(){
        return recData;
    }
    
    public SchedData getSchedData(){
        return schedData;
    }
    
    public ProjectData getProjectData(){
        return projectData;
    }
    
}
