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
    TAData taData;
    
    public CSGData(CSGApp initApp){
        app = initApp;
        taData = new TAData(initApp, this);
    }
    
    @Override
    public void resetData(){
        
    }
    
    public TAData getTAData(){
        return taData;
    }
}
