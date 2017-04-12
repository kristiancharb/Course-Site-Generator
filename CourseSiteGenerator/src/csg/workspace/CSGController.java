/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.file.TimeSlot;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGController {
    CSGApp app;
    
    public CSGController(CSGApp initApp){
        app = initApp;
    }
    
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TATab taTab = app.getCSGWorkspace().getTATab();
        TextField nameTextField = taTab.getNameTextField();
        TextField emailTextField = taTab.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = app.getCSGData().getTAData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()){
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(CSGProp.MISSING_TA_NAME_TITLE), props.getProperty(CSGProp.MISSING_TA_NAME_MESSAGE));            
        }
        //Missing email
        if (email.isEmpty()){
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(CSGProp.MISSING_TA_EMAIL_TITLE), props.getProperty(CSGProp.MISSING_TA_EMAIL_MESSAGE));            
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        else if (!data.isValidEmail(email)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_TITLE), props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_MESSAGE));
            
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            TeachingAssistant ta = new TeachingAssistant(name, email);
            ArrayList<TimeSlot> TAHoursList = new ArrayList<>();
            //jTPS_Transaction transaction = new AddTA_Transaction(app, ta, TAHoursList);
            //jTPS jTPS = workspace.getJTPS();
            //jTPS.addTransaction(transaction);
            // ADD THE NEW TA TO THE DATA
            data.addTA(name, email);
            
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
        }
    }
    
    public void handleDeleteTA(){
        // GET THE TABLE
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        if(ta == null) return;
        TAData taData = app.getCSGData().getTAData();
        ArrayList<TimeSlot> TAHoursList = TimeSlot.buildTATimeSlotList(taData, ta);
        //jTPS_Transaction transaction = new RemoveTA_Transaction(app, ta, TAHoursList);
        //jTPS jTPS = workspace.getJTPS();
        //jTPS.addTransaction(transaction);
        taData.removeTA(ta);
        
    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        
        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        if(ta == null) return;
        String taName = ta.getName();
        TAData taData = app.getCSGData().getTAData();
        String cellKey = pane.getId();
        
        // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
        //jTPS_Transaction transaction = new ToggleTACell_Transaction(app, cellKey, taName);
        //jTPS jTPS = workspace.getJTPS();
        //jTPS.addTransaction(transaction);
        taData.toggleTAOfficeHours(cellKey, taName);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
     
    }
    
    public void handleHighlight(Pane pane){
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #0026ff");
        
    }
    
    public void handleRCHigh(Pane pane){
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #a8b5ff");
    }
    
    public void handleUnHighlight(Pane pane){
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #000000");
        
    }
    
    public void handleRCUnHigh(Pane pane){
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #000000");
    }
    
    public void handleSelectedTA(){
        // GET THE TABLE
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        if(!taTable.getSelectionModel().isEmpty()){
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            String taEmail = ta.getEmail();
            
            TextField nameTextField = taTab.getNameTextField();
            TextField emailTextField = taTab.getEmailTextField();
            nameTextField.setText(taName);
            emailTextField.setText(taEmail);
            
            Button addButton = taTab.getAddButton();
            addButton.setText(props.getProperty(CSGProp.UPDATE_BUTTON_TEXT));
            
        }
        
    }
    
    public void handleUpdateTA(){
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        
        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TextField nameTextField = taTab.getNameTextField();
        TextField emailTextField = taTab.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = app.getCSGData().getTAData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()){
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(CSGProp.MISSING_TA_NAME_TITLE), props.getProperty(CSGProp.MISSING_TA_NAME_MESSAGE));            
        }
        //Missing email
        if (email.isEmpty()){
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(CSGProp.MISSING_TA_EMAIL_TITLE), props.getProperty(CSGProp.MISSING_TA_EMAIL_MESSAGE));            
        }
   
        else if (!data.isValidEmail(email)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_TITLE), props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_MESSAGE));
            
        }
        // EVERYTHING IS FINE, UPDATE TA
        else {
            //jTPS_Transaction transaction = new UpdateTA_Transaction(app, ta, name, email);
            //jTPS jTPS = workspace.getJTPS();
            //jTPS.addTransaction(transaction);
            data.updateTA(ta, name, email);
        }
    }
    
    public void handleClear(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();
        
        TextField nameTextField = taTab.getNameTextField();
        TextField emailTextField = taTab.getEmailTextField();
        
        nameTextField.setText("");
        emailTextField.setText("");
        nameTextField.requestFocus();
        
        Button addButton = taTab.getAddButton();
        addButton.setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT));
        
        taTable.getSelectionModel().clearSelection();
        
        
    }
    
    public void handleChangeStartTime(String t1){
        TATab taTab = app.getCSGWorkspace().getTATab();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TAData data = app.getCSGData().getTAData();
        int hour;
        
        String[] strings = t1.split(":");
        String hourString = strings[0];
        hour = Integer.parseInt(hourString);
        
        if((strings[1].substring(2).equals("pm")) && hour != 12) hour+=12;
        
        
        if(t1.equals("12:00am")) 
            hour = 0;
        
        ArrayList<TimeSlot> officeHoursList = TimeSlot.buildOfficeHoursList(data);
        if(hour > data.getEndHour()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.STARTHOUR_NOT_VALID_TITLE), props.getProperty(CSGProp.STARTHOUR_NOT_VALID_MESSAGE));
            taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
            taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
            return;
        }
        
        if(TimeSlot.toBeRemoved(officeHoursList, hour, data.getEndHour())){
            AppYesNoCancelDialogSingleton dialog = AppYesNoCancelDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_TITLE), props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_MESSAGE));
            String selection = dialog.getSelection();
            if(!selection.equals(AppYesNoCancelDialogSingleton.YES)){
                taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
                taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
                return;
            }
        }
        
        //jTPS_Transaction transaction = new ChangeStartHour_Transaction(app, data.getStartHour(), hour, officeHoursList);
        //jTPS jTPS = workspace.getJTPS();
        //jTPS.addTransaction(transaction);
        data.changeStartHour(hour);
        
    }
    
    public void handleChangeEndTime(String t1){
        TATab taTab = app.getCSGWorkspace().getTATab();
        TAData data = app.getCSGData().getTAData();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<TimeSlot> officeHoursList = TimeSlot.buildOfficeHoursList(data);
        
        String[] strings = t1.split(":");
        String hourString = strings[0];
        int hour = Integer.parseInt(hourString);
        
        if((strings[1].substring(2).equals("pm")) && hour != 12) hour+=12;
        
        if(t1.equals("12:00am")) 
            hour = 0;
        
        if(hour < data.getStartHour()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.ENDHOUR_NOT_VALID_TITLE), props.getProperty(CSGProp.ENDHOUR_NOT_VALID_MESSAGE));
            taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
            taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
            return;
        }
        
        if(TimeSlot.toBeRemoved(officeHoursList, data.getStartHour(), hour)){
            AppYesNoCancelDialogSingleton dialog = AppYesNoCancelDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_TITLE), props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_MESSAGE));
            String selection = dialog.getSelection();
            if(!selection.equals(AppYesNoCancelDialogSingleton.YES)){
                taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
                taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
                return;
            }
        }
       
        //jTPS_Transaction transaction = new ChangeEndHour_Transaction(app, data.getEndHour(), hour, officeHoursList);
        //jTPS jTPS = workspace.getJTPS();
        //jTPS.addTransaction(transaction);
        data.changeEndHour(hour);
        
    }
}
