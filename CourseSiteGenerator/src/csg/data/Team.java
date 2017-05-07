/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.scene.paint.Color;

/**
 *
 * @author kristiancharbonneau
 */
public class Team <E extends Comparable<E>> implements Comparable<E>{
    String name;
    String color;
    String textColor;
    String link;
    
    public Team(){}
    
    public Team(String name, String color, String textColor, String link){
        this.name = name;
        this.color = color;
        this.textColor = textColor;
        this.link = link;
    }
    
    public String getName(){
        return name;
    }
    public String getColor(){
        return color;
    }
    public String getTextColor(){
        return textColor;
    }
    public String getLink(){
        return link;
    }
    public String getRed(){
        Color col = Color.valueOf(color);
        return Integer.toString((int)(col.getRed()*255));
    }
    public String getBlue(){
        Color col = Color.valueOf(color);
        return Integer.toString((int)(col.getBlue()*255));
    }
    public String getGreen(){
        Color col = Color.valueOf(color);
        return Integer.toString((int)(col.getGreen()*255));
    }
    @Override
    public String toString(){
        return name;
    }

    public boolean equals(Team t){
        if(t.getName().equals(name) && t.getColor().equals(color) 
                && t.getTextColor().equals(textColor) && t.getLink().equals(link)){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public int compareTo(E otherTeam){
        return name.compareTo(((Team)otherTeam).getName());
    }
}
