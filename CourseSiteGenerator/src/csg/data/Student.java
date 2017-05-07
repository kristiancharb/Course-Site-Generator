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
public class Student <E extends Comparable<E>> implements Comparable<E>{
    String firstName;
    String lastName;
    String team;
    String role;
    
    public Student(){}
    
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
    public boolean equals(Student s){
        if(s.getFirstName().equals(firstName) && s.getLastName().equals(lastName) && s.getRole().equals(role) && s.getTeam().equals(team)){
            return true;
        }else{
            return false;
        }
    }
    
    public void setTeam(String team){
        this.team = team;
    }
    
    @Override
    public int compareTo(E otherStudent){
        return team.compareTo(((Student)otherStudent).getTeam());
    }
    
}
