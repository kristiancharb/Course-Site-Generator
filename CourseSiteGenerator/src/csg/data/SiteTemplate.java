/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author kristiancharbonneau
 */
public class SiteTemplate {
    BooleanProperty use;
    String navbarTitle;
    String fileName;
    String script;
    
    public SiteTemplate(String navbarTitle, String fileName, String script){
        this.use = new SimpleBooleanProperty(true);
        this.navbarTitle = navbarTitle;
        this.fileName = fileName;
        this.script = script;
    }
    
    public String getNavbarTitle(){
        return navbarTitle;
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public String getScript(){
        return script;
    }
    public BooleanProperty getUse(){
        return use;
    }
   
}
