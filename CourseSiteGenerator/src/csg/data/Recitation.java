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
public class Recitation {
    String section;
    String instructor;
    String time;
    String location;
    String ta1;
    String ta2;
    
    public Recitation(){
    }
    
    public Recitation(String section, String instructor, String time, String location,
            String ta1, String ta2){
    this.section = section;
    this.instructor = instructor;
    this.time = time;
    this.location = location;
    this.ta1 = ta1;
    this.ta2 = ta2;
    }
    
    public String getSection(){
        return section;
    }
    public String getInstructor(){
        return instructor;
    }
    public String getTime(){
        return time;
    }
    public String getLocation(){
        return location;
    }
    public String getTa1(){
        return ta1;
    }
    public String getTa2(){
        return ta2;
    }
    
}
