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
public class ScheduleItem <E extends Comparable<E>> implements Comparable<E>{
    String type;
    String date;
    String title;
    String topic;
    String link;
    String time;
    String criteria;
    
    public ScheduleItem(){
    }
    
    public ScheduleItem(String type, String date, String title, String topic){
        this.type = type;
        this.date = date;
        this.title = title;
        this.topic = topic;
        this.link = "";
        this.criteria = "";
        this.time = "";
    }
    
    public ScheduleItem(String type, String date, String title, String topic, String link, String criteria, String time){
        this.type = type;
        this.date = date;
        this.title = title;
        this.topic = topic;
        this.link = link;
        this.criteria = criteria;
        this.time = time;
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
    public String getLink(){
        return link;
    }
    public String getCriteria(){
        return criteria;
    }
    public String getTime(){
        return time;
    }
    
    public boolean equals(ScheduleItem item){
        if(item.getType().equals(this.getType()) &&
                item.getTitle().equals(this.getTitle()) &&
                item.getTopic().equals(this.getTopic()) &&
                item.getDate().equals(this.getDate()) &&
                item.getLink().equals(this.getLink()) &&
                item.getCriteria().equals(this.getCriteria())){
            return true;
        }else{ 
            return false;
        }
    }
    
    @Override
    public int compareTo(E otherItem){
        return type.compareTo(((ScheduleItem)otherItem).getType());
    }
    
}
