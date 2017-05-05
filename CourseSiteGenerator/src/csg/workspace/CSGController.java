/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CDData;
import csg.data.ProjectData;
import csg.data.RecData;
import csg.data.Recitation;
import csg.data.SchedData;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.file.TimeSlot;
import csg.transactions.sched.AddItem_Transaction;
import csg.transactions.sched.RemoveItem_Transaction;
import csg.transactions.sched.UpdateItem_Transaction;
import csg.transactions.ta.AddTA_Transaction;
import csg.transactions.ta.ChangeEndHour_Transaction;
import csg.transactions.ta.ChangeStartHour_Transaction;
import csg.transactions.ta.RemoveTA_Transaction;
import csg.transactions.ta.ToggleTACell_Transaction;
import csg.transactions.ta.UpdateTA_Transaction;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import javafx.scene.control.ColorPicker;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.stage.FileChooser;
import javafx.scene.control.DatePicker;
import javafx.stage.DirectoryChooser;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGController {

    CSGApp app;

    public CSGController(CSGApp initApp) {
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
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TA_NAME_TITLE), props.getProperty(CSGProp.MISSING_TA_NAME_MESSAGE));
        }
        //Missing email
        if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TA_EMAIL_TITLE), props.getProperty(CSGProp.MISSING_TA_EMAIL_MESSAGE));
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(CSGProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        } else if (!data.isValidEmail(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_TITLE), props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_MESSAGE));

        } // EVERYTHING IS FINE, ADD A NEW TA
        else {
            TeachingAssistant ta = new TeachingAssistant(name, email);
            ArrayList<TimeSlot> TAHoursList = new ArrayList<>();
            jTPS_Transaction transaction = new AddTA_Transaction(app, ta, TAHoursList);
            jTPS jTPS = app.getCSGWorkspace().getJTPS();
            jTPS.addTransaction(transaction);
            // ADD THE NEW TA TO THE DATA
            data.addTA(name, email);

            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
        }
    }

    public void handleDeleteTA() {
        // GET THE TABLE
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();

        TeachingAssistant ta = (TeachingAssistant) selectedItem;
        if (ta == null) {
            return;
        }
        TAData taData = app.getCSGData().getTAData();
        ArrayList<TimeSlot> TAHoursList = TimeSlot.buildTATimeSlotList(taData, ta);
        jTPS_Transaction transaction = new RemoveTA_Transaction(app, ta, TAHoursList);
        jTPS jTPS = app.getCSGWorkspace().getJTPS();
        jTPS.addTransaction(transaction);
        taData.removeTA(ta);

    }

    /**
     * This function provides a response for when the user clicks on the office
     * hours grid to add or remove a TA to a time slot.
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
        TeachingAssistant ta = (TeachingAssistant) selectedItem;
        if (ta == null) {
            return;
        }
        String taName = ta.getName();
        TAData taData = app.getCSGData().getTAData();
        String cellKey = pane.getId();

        // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
        jTPS_Transaction transaction = new ToggleTACell_Transaction(app, cellKey, taName);
        jTPS jTPS = app.getCSGWorkspace().getJTPS();
        jTPS.addTransaction(transaction);
        taData.toggleTAOfficeHours(cellKey, taName);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());

    }

    public void handleHighlight(Pane pane) {
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #0026ff");

    }

    public void handleRCHigh(Pane pane) {
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #a8b5ff");
    }

    public void handleUnHighlight(Pane pane) {
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #000000");

    }

    public void handleRCUnHigh(Pane pane) {
        String cellKey = pane.getId();
        pane.setStyle("-fx-border-color: #000000");
    }

    public void handleSelectedTA() {
        // GET THE TABLE
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();

        // IS A TA SELECTED IN THE TABLE?
        if (!taTable.getSelectionModel().isEmpty()) {
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();

            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
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

    public void handleUpdateTA() {
        TATab taTab = app.getCSGWorkspace().getTATab();
        TableView taTable = taTab.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();

        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant) selectedItem;

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
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TA_NAME_TITLE), props.getProperty(CSGProp.MISSING_TA_NAME_MESSAGE));
        }
        //Missing email
        if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TA_EMAIL_TITLE), props.getProperty(CSGProp.MISSING_TA_EMAIL_MESSAGE));
        } else if (!data.isValidEmail(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_TITLE), props.getProperty(CSGProp.TA_EMAIL_NOT_VALID_MESSAGE));

        } // EVERYTHING IS FINE, UPDATE TA
        else {
            jTPS_Transaction transaction = new UpdateTA_Transaction(app, ta, name, email);
            jTPS jTPS = app.getCSGWorkspace().getJTPS();
            jTPS.addTransaction(transaction);
            data.updateTA(ta, name, email);
        }
    }

    public void handleClear() {
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

    public void handleChangeStartTime(String t1) {
        TATab taTab = app.getCSGWorkspace().getTATab();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TAData data = app.getCSGData().getTAData();
        int hour;

        String[] strings = t1.split(":");
        String hourString = strings[0];
        hour = Integer.parseInt(hourString);

        if ((strings[1].substring(2).equals("pm")) && hour != 12) {
            hour += 12;
        }

        if (t1.equals("12:00am")) {
            hour = 0;
        }

        ArrayList<TimeSlot> officeHoursList = TimeSlot.buildOfficeHoursList(data);
        if (hour > data.getEndHour()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.STARTHOUR_NOT_VALID_TITLE), props.getProperty(CSGProp.STARTHOUR_NOT_VALID_MESSAGE));
            taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
            taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
            return;
        }

        if (TimeSlot.toBeRemoved(officeHoursList, hour, data.getEndHour())) {
            AppYesNoCancelDialogSingleton dialog = AppYesNoCancelDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_TITLE), props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_MESSAGE));
            String selection = dialog.getSelection();
            if (!selection.equals(AppYesNoCancelDialogSingleton.YES)) {
                taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
                taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
                return;
            }
        }

        jTPS_Transaction transaction = new ChangeStartHour_Transaction(app, data.getStartHour(), hour, officeHoursList);
        jTPS jTPS = app.getCSGWorkspace().getJTPS();
        jTPS.addTransaction(transaction);
        data.changeStartHour(hour);

    }

    public void handleChangeEndTime(String t1) {
        TATab taTab = app.getCSGWorkspace().getTATab();
        TAData data = app.getCSGData().getTAData();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<TimeSlot> officeHoursList = TimeSlot.buildOfficeHoursList(data);

        String[] strings = t1.split(":");
        String hourString = strings[0];
        int hour = Integer.parseInt(hourString);

        if ((strings[1].substring(2).equals("pm")) && hour != 12) {
            hour += 12;
        }

        if (t1.equals("12:00am")) {
            hour = 0;
        }

        if (hour < data.getStartHour()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.ENDHOUR_NOT_VALID_TITLE), props.getProperty(CSGProp.ENDHOUR_NOT_VALID_MESSAGE));
            taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
            taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
            return;
        }

        if (TimeSlot.toBeRemoved(officeHoursList, data.getStartHour(), hour)) {
            AppYesNoCancelDialogSingleton dialog = AppYesNoCancelDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_TITLE), props.getProperty(CSGProp.OFFICE_HOURS_CONFLICT_MESSAGE));
            String selection = dialog.getSelection();
            if (!selection.equals(AppYesNoCancelDialogSingleton.YES)) {
                taTab.getStartTimeBox().setPromptText(taTab.buildCellText(data.getStartHour(), "00"));
                taTab.getEndTimeBox().setPromptText(taTab.buildCellText(data.getEndHour(), "00"));
                return;
            }
        }

        jTPS_Transaction transaction = new ChangeEndHour_Transaction(app, data.getEndHour(), hour, officeHoursList);
        jTPS jTPS = app.getCSGWorkspace().getJTPS();
        jTPS.addTransaction(transaction);
        data.changeEndHour(hour);

    }

    public void handleAddRec() {
        RecTab recTab = app.getCSGWorkspace().getRecTab();
        TextField sectionField = recTab.getSectionField();
        TextField instrField = recTab.getInstructorField();
        TextField timeField = recTab.getTimeField();
        TextField locationField = recTab.getLocationField();
        ComboBox ta1Box = recTab.getTa1Box();
        ComboBox ta2Box = recTab.getTa2Box();
        String section = sectionField.getText();
        String instr = instrField.getText();
        String time = timeField.getText();
        String location = locationField.getText();
        String ta1 = "";
        String ta2 = "";
        if (ta1Box.getValue() != null) {
            ta1 = ((TeachingAssistant) ta1Box.getValue()).getName();
        }
        if (ta2Box.getValue() != null) {
            ta2 = ((TeachingAssistant) ta2Box.getValue()).getName();
        }

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        RecData recData = app.getCSGData().getRecData();

        if (section == null || section.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_SECTION_TITLE), props.getProperty(CSGProp.MISSING_SECTION_MESSAGE));
        } else if (time == null || time.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TIME_TITLE), props.getProperty(CSGProp.MISSING_TIME_MESSAGE));
        } else if (location == null || location.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_LOCATION_TITLE), props.getProperty(CSGProp.MISSING_LOCATION_MESSAGE));
        } else {
            recData.addRec(section, instr, time, location, ta1, ta2);

            handleClearRec();
        }
    }

    public void handleUpdateRec() {
        RecTab recTab = app.getCSGWorkspace().getRecTab();
        Recitation rec = (Recitation) recTab.getRecTable().getSelectionModel().getSelectedItem();
        TextField sectionField = recTab.getSectionField();
        TextField instrField = recTab.getInstructorField();
        TextField timeField = recTab.getTimeField();
        TextField locationField = recTab.getLocationField();
        ComboBox ta1Box = recTab.getTa1Box();
        ComboBox ta2Box = recTab.getTa2Box();
        String section = sectionField.getText();
        String instr = instrField.getText();
        String time = timeField.getText();
        String location = locationField.getText();
        String ta1 = "";
        String ta2 = "";
        if (ta1Box.getValue() != null) {
            ta1 = ((TeachingAssistant) ta1Box.getValue()).getName();
        }
        if (ta2Box.getValue() != null) {
            ta2 = ((TeachingAssistant) ta2Box.getValue()).getName();
        }

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        RecData recData = app.getCSGData().getRecData();

        if (section == null || section.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_SECTION_TITLE), props.getProperty(CSGProp.MISSING_SECTION_MESSAGE));
        } else if (time == null || time.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TIME_TITLE), props.getProperty(CSGProp.MISSING_TIME_MESSAGE));
        } else if (location == null || location.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_LOCATION_TITLE), props.getProperty(CSGProp.MISSING_LOCATION_MESSAGE));
        } else {
            recData.updateRec(rec, section, instr, time, location, ta1, ta2);

            handleClearRec();
        }
    }

    public void handleSelectedRec() {
        RecTab recTab = app.getCSGWorkspace().getRecTab();
        TableView recTable = recTab.getRecTable();

        if (!recTable.getSelectionModel().isEmpty()) {
            Recitation rec = (Recitation) recTable.getSelectionModel().getSelectedItem();
            TextField sectionField = recTab.getSectionField();
            TextField instrField = recTab.getInstructorField();
            TextField timeField = recTab.getTimeField();
            TextField locationField = recTab.getLocationField();
            ComboBox ta1Box = recTab.getTa1Box();
            ComboBox ta2Box = recTab.getTa2Box();

            sectionField.setText(rec.getSection());
            instrField.setText(rec.getInstructor());
            timeField.setText(rec.getTime());
            locationField.setText(rec.getLocation());
            ta1Box.setValue(rec.getTa1());
            ta2Box.setValue(rec.getTa2());
        }
    }

    public void handleDeleteRec() {
        RecTab recTab = app.getCSGWorkspace().getRecTab();
        TableView recTable = recTab.getRecTable();
        RecData recData = app.getCSGData().getRecData();

        if (!recTable.getSelectionModel().isEmpty()) {
            Recitation rec = (Recitation) recTable.getSelectionModel().getSelectedItem();
            recData.deleteRec(rec);

        }
    }

    public void handleClearRec() {
        RecTab recTab = app.getCSGWorkspace().getRecTab();
        TableView recTable = recTab.getRecTable();

        TextField sectionField = recTab.getSectionField();
        TextField instrField = recTab.getInstructorField();
        TextField timeField = recTab.getTimeField();
        TextField locationField = recTab.getLocationField();
        ComboBox ta1Box = recTab.getTa1Box();
        ComboBox ta2Box = recTab.getTa2Box();

        sectionField.setText("");
        instrField.setText("");
        timeField.setText("");
        locationField.setText("");
        ta1Box.getSelectionModel().clearSelection();
        ta1Box.setValue(null);
        ta2Box.getSelectionModel().clearSelection();
        ta2Box.setValue(null);
        recTable.getSelectionModel().clearSelection();

        sectionField.requestFocus();
    }

    public void handleAddTeam() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();

        TextField nameField = projTab.getNameField();
        ColorPicker colorPick = projTab.getColorPicker();
        ColorPicker textColorPick = projTab.getTextColorPicker();
        TextField linkField = projTab.getLinkField();
        String name = nameField.getText();
        String link = linkField.getText();
        String color = colorPick.getValue().toString();
        String textColor = textColorPick.getValue().toString();

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ProjectData projData = app.getCSGData().getProjectData();

        if (name == null || name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_NAME_TITLE), props.getProperty(CSGProp.MISSING_NAME_MESSAGE));
        } else if (link == null || link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_LINK_TITLE), props.getProperty(CSGProp.MISSING_LINK_MESSAGE));
        } else if (color == null || color.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_COLOR_TITLE), props.getProperty(CSGProp.MISSING_COLOR_MESSAGE));
        } else if (textColor == null || textColor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TEXTCOLOR_TITLE), props.getProperty(CSGProp.MISSING_TEXTCOLOR_MESSAGE));
        } else {
            projData.addTeam(name, color, textColor, link);
            handleClearTeam();
        }
    }

    public void handleUpdateTeam() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();
        TableView teamTable = projTab.getTeamTable();
        Team t = (Team) teamTable.getSelectionModel().getSelectedItem();

        TextField nameField = projTab.getNameField();
        ColorPicker colorPick = projTab.getColorPicker();
        ColorPicker textColorPick = projTab.getTextColorPicker();
        TextField linkField = projTab.getLinkField();
        String name = nameField.getText();
        String link = linkField.getText();
        String color = colorPick.getValue().toString();
        String textColor = textColorPick.getValue().toString();

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ProjectData projData = app.getCSGData().getProjectData();

        if (name == null || name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_NAME_TITLE), props.getProperty(CSGProp.MISSING_NAME_MESSAGE));
        } else if (link == null || link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_LINK_TITLE), props.getProperty(CSGProp.MISSING_LINK_MESSAGE));
        } else if (color == null || color.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_COLOR_TITLE), props.getProperty(CSGProp.MISSING_COLOR_MESSAGE));
        } else if (textColor == null || textColor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TEXTCOLOR_TITLE), props.getProperty(CSGProp.MISSING_TEXTCOLOR_MESSAGE));
        } else {
            projData.updateTeam(t, name, color, textColor, link);
            handleClearTeam();
        }
    }

    public void handleClearTeam() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();

        TextField nameField = projTab.getNameField();
        ColorPicker colorPick = projTab.getColorPicker();
        ColorPicker textColorPick = projTab.getTextColorPicker();
        TextField linkField = projTab.getLinkField();
        nameField.setText("");
        linkField.setText("");
        colorPick.setValue(Color.WHITE);
        textColorPick.setValue(Color.WHITE);
        projTab.getTeamTable().getSelectionModel().clearSelection();
        nameField.requestFocus();

    }

    public void handleSelectedTeam() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();
        TableView teamTable = projTab.getTeamTable();

        TextField nameField = projTab.getNameField();
        ColorPicker colorPick = projTab.getColorPicker();
        ColorPicker textColorPick = projTab.getTextColorPicker();
        TextField linkField = projTab.getLinkField();

        if (!teamTable.getSelectionModel().isEmpty()) {
            Team t = (Team) teamTable.getSelectionModel().getSelectedItem();
            nameField.setText(t.getName());
            linkField.setText(t.getLink());
            colorPick.setValue(Color.valueOf(t.getColor()));
            textColorPick.setValue(Color.valueOf(t.getTextColor()));
        }
    }

    public void handleDeleteTeam() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();
        TableView teamTable = projTab.getTeamTable();
        ProjectData projData = app.getCSGData().getProjectData();

        if (!teamTable.getSelectionModel().isEmpty()) {
            Team t = (Team) teamTable.getSelectionModel().getSelectedItem();
            projData.removeTeam(t);
        }
    }

    public void handleAddStdeunt() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();

        TextField firstNameField = projTab.getFirstNameField();
        TextField lastNameField = projTab.getLastNameField();
        TextField roleField = projTab.getRoleField();
        ComboBox teamBox = projTab.getTeamComboBox();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String role = roleField.getText();
        String team = "";
        if (teamBox.getValue() != null) {
            team = ((Team) teamBox.getValue()).getName();
        }

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (firstName == null || firstName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_FIRSTNAME_TITLE), props.getProperty(CSGProp.MISSING_FIRSTNAME_MESSAGE));
        } else if (lastName == null || lastName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_LASTNAME_TITLE), props.getProperty(CSGProp.MISSING_LASTNAME_MESSAGE));
        } else if (team == null || team.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TEAM_TITLE), props.getProperty(CSGProp.MISSING_TEAM_MESSAGE));
        } else if (role == null || role.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_ROLE_TITLE), props.getProperty(CSGProp.MISSING_ROLE_MESSAGE));
        } else {
            projData.addStudent(firstName, lastName, team, role);
            handleClearStudent();
        }
    }

    public void handleUpdateStdeunt() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();
        ProjectData projData = app.getCSGData().getProjectData();
        Student s = (Student) projTab.getStudentTable().getSelectionModel().getSelectedItem();

        TextField firstNameField = projTab.getFirstNameField();
        TextField lastNameField = projTab.getLastNameField();
        TextField roleField = projTab.getRoleField();
        ComboBox teamBox = projTab.getTeamComboBox();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String role = roleField.getText();
        String team = "";
        if (teamBox.getValue() != null) {
            team = ((Team) teamBox.getValue()).getName();
        }

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (firstName == null || firstName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_FIRSTNAME_TITLE), props.getProperty(CSGProp.MISSING_FIRSTNAME_MESSAGE));
        } else if (lastName == null || lastName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_LASTNAME_TITLE), props.getProperty(CSGProp.MISSING_LASTNAME_MESSAGE));
        } else if (team == null || team.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TEAM_TITLE), props.getProperty(CSGProp.MISSING_TEAM_MESSAGE));
        } else if (role == null || role.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_ROLE_TITLE), props.getProperty(CSGProp.MISSING_ROLE_MESSAGE));
        } else {
            projData.updateStudent(s, firstName, lastName, team, role);
            handleClearStudent();
        }
    }

    public void handleClearStudent() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();

        TextField firstNameField = projTab.getFirstNameField();
        TextField lastNameField = projTab.getLastNameField();
        TextField roleField = projTab.getRoleField();
        ComboBox teamBox = projTab.getTeamComboBox();
        firstNameField.setText("");
        lastNameField.setText("");
        roleField.setText("");
        teamBox.getSelectionModel().clearSelection();
        teamBox.setValue(null);
        projTab.getTeamTable().getSelectionModel().clearSelection();
        firstNameField.requestFocus();

    }

    public void handleSelectedStudent() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();
        TableView studentTable = projTab.getStudentTable();

        TextField firstNameField = projTab.getFirstNameField();
        TextField lastNameField = projTab.getLastNameField();
        TextField roleField = projTab.getRoleField();
        ComboBox teamBox = projTab.getTeamComboBox();

        if (!studentTable.getSelectionModel().isEmpty()) {
            Student s = (Student) studentTable.getSelectionModel().getSelectedItem();
            firstNameField.setText(s.getFirstName());
            lastNameField.setText(s.getLastName());
            roleField.setText(s.getLastName());
            teamBox.setValue(s.getTeam());
        }

    }

    public void handleDeleteStudent() {
        ProjectTab projTab = app.getCSGWorkspace().getProjectTab();
        TableView studentTable = projTab.getStudentTable();
        ProjectData projData = app.getCSGData().getProjectData();

        if (!studentTable.getSelectionModel().isEmpty()) {
            Student s = (Student) studentTable.getSelectionModel().getSelectedItem();
            projData.removeStudent(s);
        }
    }

    public void handleChangeMon(LocalDate oldVal, LocalDate newVal) {
        if(newVal == null) return;
        SchedData schedData = app.getCSGData().getSchedData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        LocalDate endDate = LocalDate.parse("1/01/1900", formatter);
        if (schedData.getEndingFri() != null) {
            endDate = LocalDate.parse(schedData.getEndingFri(), formatter);
        }
       
        if (newVal.isAfter(endDate)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.STARTDATE_AFTER_TITLE), props.getProperty(CSGProp.STARTDATE_AFTER_MESSAGE));
            app.getCSGWorkspace().getSchedTab().getStartPicker().setValue(oldVal);
        } else if (!newVal.getDayOfWeek()
                .equals(DayOfWeek.MONDAY)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.STARTDATE_INVALID_TITLE), props.getProperty(CSGProp.STARTDATE_INVALID_MESSAGE));
            app.getCSGWorkspace().getSchedTab().getStartPicker().setValue(oldVal);
        } else {
            schedData.changeStartMon(newVal);
        }
    }

    public void handleChangeFri(LocalDate oldVal, LocalDate newVal) {
        if(newVal == null) return;
        SchedData schedData = app.getCSGData().getSchedData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        LocalDate startDate = LocalDate.parse("1/01/2099", formatter);
        if (schedData.getEndingFri() != null) {
            startDate = LocalDate.parse(schedData.getStartingMon(), formatter);
        }
        if (newVal.isBefore(startDate)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.ENDDATE_BEFORE_TITLE), props.getProperty(CSGProp.ENDDATE_BEFORE_MESSAGE));
            app.getCSGWorkspace().getSchedTab().getEndPicker().setValue(oldVal);
        } else if (!newVal.getDayOfWeek()
                .equals(DayOfWeek.FRIDAY)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.ENDDATE_INVALID_TITLE), props.getProperty(CSGProp.ENDDATE_INVALID_MESSAGE));
            app.getCSGWorkspace().getSchedTab().getEndPicker().setValue(oldVal);
        } else {
            schedData.changeEndFri(newVal);
        }
    }

    public void handleAddItem() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        SchedData schedData = app.getCSGData().getSchedData();
        SchedTab schedTab = app.getCSGWorkspace().getSchedTab();
        ComboBox typeBox = schedTab.getTypeBox();
        DatePicker datePicker = schedTab.getDatePicker();
        TextField timeField = schedTab.getTimeField();
        TextField titleField = schedTab.getTitleField();
        TextField topicField = schedTab.getTopicField();
        TextField linkField = schedTab.getLinkField();
        TextField criteriaField = schedTab.getCriteriaField();
        String type = (String) typeBox.getValue();
        
        String time = timeField.getText();
        String title = titleField.getText();
        String topic = topicField.getText();
        String link = linkField.getText();
        String criteria = criteriaField.getText();

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (title == null || title.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_TITLE_TITLE), props.getProperty(CSGProp.MISSING_TITLE_MESSAGE));
        } else if (datePicker.getValue() == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(CSGProp.MISSING_DATE_TITLE), props.getProperty(CSGProp.MISSING_DATE_MESSAGE));
        } else {
            String date = datePicker.getValue().format(formatter);
            ScheduleItem item = new ScheduleItem(type, date, title, topic, link, criteria, time);
            jTPS_Transaction transaction = new AddItem_Transaction(app, item);
            jTPS jTPS = app.getCSGWorkspace().getJTPS();
            jTPS.addTransaction(transaction);
            schedData.addItem(type, date, time, title, topic, link, criteria);
            handleClearSched();
        }

    }

    public void handleUpdateItem() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        SchedData schedData = app.getCSGData().getSchedData();
        SchedTab schedTab = app.getCSGWorkspace().getSchedTab();
        ComboBox typeBox = schedTab.getTypeBox();
        DatePicker datePicker = schedTab.getDatePicker();
        TextField timeField = schedTab.getTimeField();
        TextField titleField = schedTab.getTitleField();
        TextField topicField = schedTab.getTopicField();
        TextField linkField = schedTab.getLinkField();
        TextField criteriaField = schedTab.getCriteriaField();

        if (!schedTab.getItemsTable().getSelectionModel().isEmpty()) {
            ScheduleItem item = schedTab.getItemsTable().getSelectionModel().getSelectedItem();
            String type = (String) typeBox.getValue();
            String date = datePicker.getValue().format(formatter);
            String time = timeField.getText();
            String title = titleField.getText();
            String topic = topicField.getText();
            String link = linkField.getText();
            String criteria = criteriaField.getText();

            PropertiesManager props = PropertiesManager.getPropertiesManager();

            if (title == null || title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGProp.MISSING_TITLE_TITLE), props.getProperty(CSGProp.MISSING_TITLE_MESSAGE));
            } else if (date == null || date.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGProp.MISSING_DATE_TITLE), props.getProperty(CSGProp.MISSING_DATE_MESSAGE));
            } else {
                ScheduleItem newItem= new ScheduleItem(type, date, title, topic, link, criteria, time);
                jTPS_Transaction transaction = new UpdateItem_Transaction(app, item, newItem);
                jTPS jTPS = app.getCSGWorkspace().getJTPS();
                jTPS.addTransaction(transaction);
                schedData.updateItem(item, type, date, time, title, topic, link, criteria);
                handleClearSched();
            }
        }
    }

    public void handleSelectedItem() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        SchedTab schedTab = app.getCSGWorkspace().getSchedTab();
        ComboBox typeBox = schedTab.getTypeBox();
        DatePicker datePicker = schedTab.getDatePicker();
        TextField timeField = schedTab.getTimeField();
        TextField titleField = schedTab.getTitleField();
        TextField topicField = schedTab.getTopicField();
        TextField linkField = schedTab.getLinkField();
        TextField criteriaField = schedTab.getCriteriaField();

        if (!schedTab.getItemsTable().getSelectionModel().isEmpty()) {
            ScheduleItem item = schedTab.getItemsTable().getSelectionModel().getSelectedItem();
            typeBox.setValue(item.getType());
            datePicker.setValue(LocalDate.parse(item.getDate(), formatter));
            timeField.setText(item.getTime());
            titleField.setText(item.getTitle());
            topicField.setText(item.getTopic());
            linkField.setText(item.getLink());
            criteriaField.setText(item.getLink());
        }
    }

    public void handleDeleteItem() {
        SchedData schedData = app.getCSGData().getSchedData();
        SchedTab schedTab = app.getCSGWorkspace().getSchedTab();
        if (!schedTab.getItemsTable().getSelectionModel().isEmpty()) {
            ScheduleItem item = schedTab.getItemsTable().getSelectionModel().getSelectedItem();
            jTPS_Transaction transaction = new RemoveItem_Transaction(app, item);
            jTPS jTPS = app.getCSGWorkspace().getJTPS();
            jTPS.addTransaction(transaction);
            schedData.removeItem(item);
        }
    }

    public void handleClearSched() {
        SchedTab schedTab = app.getCSGWorkspace().getSchedTab();
        ComboBox typeBox = schedTab.getTypeBox();
        DatePicker datePicker = schedTab.getDatePicker();
        TextField timeField = schedTab.getTimeField();
        TextField titleField = schedTab.getTitleField();
        TextField topicField = schedTab.getTopicField();
        TextField linkField = schedTab.getLinkField();
        TextField criteriaField = schedTab.getCriteriaField();
        
        typeBox.getSelectionModel().clearSelection();
        typeBox.setValue(null);
    
        datePicker.setValue(null);
        timeField.setText("");
        titleField.setText("");
        topicField.setText("");
        linkField.setText("");
        criteriaField.setText("");
        typeBox.requestFocus();
        schedTab.getItemsTable().getSelectionModel().clearSelection();
    }
    
    public void handleChangeExportDir(){
        CDData cdData = app.getCSGData().getCDData();
        final DirectoryChooser directoryChooser =new DirectoryChooser();
        final File selectedDirectory = directoryChooser.showDialog(app.getGUI().getWindow());
        if (selectedDirectory != null) {
            String filePath = selectedDirectory.getAbsolutePath();
            cdData.changeExportDir(filePath);
        }
        app.getCSGWorkspace().getCDTab().reloadCDTab();
    }
    
    public void handleChangeTemplateDir(){
        CDData cdData = app.getCSGData().getCDData();
        final DirectoryChooser directoryChooser =new DirectoryChooser();
        final File selectedDirectory = directoryChooser.showDialog(app.getGUI().getWindow());
        if (selectedDirectory != null) {
            String filePath = selectedDirectory.getAbsolutePath();
            cdData.changeTemplateDir(filePath);
        }
        app.getCSGWorkspace().getCDTab().reloadCDTab();
    }
    
    public void handleChangeBannerImage(){
        CDData cdData = app.getCSGData().getCDData();
        String userDir = System.getProperty("user.dir");
        final FileChooser fileChooser =new FileChooser();
        final File selectedFile = fileChooser.showOpenDialog(app.getGUI().getWindow());
        String filePath = "";
        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
        }
        File source = new File(filePath);
        String imagePath = userDir + "/images";
        File dest = new File(imagePath);
        
        try {
            FileUtils.copyFileToDirectory(source, dest);
            cdData.changeBannerImage(filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        app.getCSGWorkspace().getCDTab().reloadCDTab();
        
    }
}
