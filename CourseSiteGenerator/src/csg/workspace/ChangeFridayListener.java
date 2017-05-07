/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author kristiancharbonneau
 */
public class ChangeFridayListener implements ChangeListener{
    CSGController controller;
    
    public ChangeFridayListener(CSGController controller){
        this.controller = controller;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        LocalDate oldDate = (LocalDate)oldValue;
        LocalDate newDate = (LocalDate)newValue;
        controller.handleChangeFri(oldDate, newDate);
        
    }
}