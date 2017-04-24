/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;
import java.awt.Color;

/**
 *
 * @author kristiancharbonneau
 */
public class Team {
    String name;
    String color;
    String textColor;
    String link;
    
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
        Color col = Color.decode(color);
        return Integer.toString(col.getRed());
    }
    public String getBlue(){
        Color col = Color.decode(color);
        return Integer.toString(col.getBlue());
    }
    public String getGreen(){
        Color col = Color.decode(color);
        return Integer.toString(col.getGreen());
    }
}
