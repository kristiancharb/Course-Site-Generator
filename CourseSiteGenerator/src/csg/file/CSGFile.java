/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import djf.components.AppFileComponent;
import csg.CSGApp;
import djf.components.AppDataComponent;
import java.io.IOException;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGFile implements AppFileComponent{
    
    CSGApp app;
    
    public CSGFile(CSGApp initApp){
        app = initApp;
    }
    
        @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {   
        
    }
    
      @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException { 
        
    }
    
      @Override
    public void importData(AppDataComponent data, String filePath) throws IOException { 
        
    }
    
      @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException { 
        
    }
    
    

    
    
    
    
}
