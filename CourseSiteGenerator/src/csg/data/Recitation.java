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
    
}
