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
public class ScheduleItem {
    String type;
    String date;
    String title;
    String topic;
    
    public ScheduleItem(String type, String date, String title, String topic){
        this.type = type;
        this.date = date;
        this.title = title;
        this.topic = topic;
    }
    
    public String getType(){
        return type;
    }
    public String getDate(){
        return date;
    }
    public String getTitle(){
        return title;
    }
    public String getTopic(){
        return topic;
    }
    
}
