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
public class Student {
    String firstName;
    String lastName;
    String team;
    String role;
    
    public Student(String firstName, String lastName, String team, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.role = role;
    }
    
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getTeam(){
        return team;
    }
    public String getRole(){
        return role;
    }
    
}
