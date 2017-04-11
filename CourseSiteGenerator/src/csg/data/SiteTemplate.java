/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

/**
 *
 * @author kristiancharbonneau
 */
public class SiteTemplate {
    boolean use;
    String navbarTitle;
    String fileName;
    String script;
    
    public SiteTemplate(String navbarTitle, String fileName, String script){
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
    
}
