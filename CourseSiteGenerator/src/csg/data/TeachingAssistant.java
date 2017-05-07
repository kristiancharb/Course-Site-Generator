/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private BooleanProperty grad;
    private final StringProperty name;
    private final StringProperty email; 

    /**
     * Constructor initializes the TA name and email
     */
    
    public TeachingAssistant(boolean b, String initName, String initEmail) {
        this.grad = new SimpleBooleanProperty(b);
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        
    }
    public TeachingAssistant(String initName, String initEmail) {
        this.grad = new SimpleBooleanProperty(true);
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        
    }
    public TeachingAssistant(String initName) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty("");
        
    }


    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }
    
    public BooleanProperty getGrad(){
        return grad;
    }

    public void setName(String initName) {
        name.set(initName);
    }
    
      public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }
    
    
    

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}