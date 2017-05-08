/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import csg.CSGProp;
import csg.workspace.TATab;
import csg.file.TimeSlot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import properties_manager.PropertiesManager;

/**
 *
 * @author kristiancharbonneau
 */
public class TAData {

    CSGApp app;
    CSGData data;
    
            // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
        // DATA IN THE ROWS OF THE TABLE VIEW
        ObservableList<TeachingAssistant> teachingAssistants;

        // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
        // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
        // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
        // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
        HashMap<String, StringProperty> officeHours;
        ArrayList<TimeSlot> officeHoursList;

        // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
        // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
        // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
        // INITIALIZE OUR OFFICE HOURS GRID
        ArrayList<String> gridHeaders;

        // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
        // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
        // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
        // NO MEANS FOR CHANGING THESE VALUES
        int startHour;
        int endHour;
        // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
        public static final int MIN_START_HOUR = 0;
        public static final int MAX_END_HOUR = 23;

    public TAData(CSGApp app, CSGData data) {
        this.app = app;
        this.data = data;
        
        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();
        

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;

        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        officeHoursList = new ArrayList<TimeSlot>();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }
    
    public void resetTAData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
    }

    // ACCESSOR METHODS
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setStartHour(int hour) {
        startHour = hour;
    }

    public void setEndHour(int hour) {
        endHour = hour;
    }

    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }
    public ArrayList<TimeSlot> getOfficeHoursList(){
        return officeHoursList;
    }

    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public String getCol(String cellKey) {
        String[] key = cellKey.split("_");
        return key[0];
    }

    public String getRow(String cellKey) {
        String[] key = cellKey.split("_");
        return key[1];
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }

    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;
        if (time.contains("pm") && !time.contains("12")) {
            milHour += 12;
        }
        row += (milHour - startHour) * 2;
        if (time.contains("_30")) {
            row += 1;
        }
        return getCellKey(col, row);
    }

    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }

    /**
     * This method is for giving this data manager the string property for a
     * given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }

    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
            int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }
    
    public void setOfficeHoursList(ArrayList<TimeSlot> list){
        officeHoursList = list;
    }
    
    public void setTAsList(ObservableList<TeachingAssistant> tas){
        teachingAssistants = tas;
    }

    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;

        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();

        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        TATab taTab = app.getCSGWorkspace().getTATab();
        taTab.getStartTimeBox().setPromptText(taTab.buildCellText(getStartHour(), "00"));
        taTab.getEndTimeBox().setPromptText(taTab.buildCellText(getEndHour(), "00"));
        taTab.reloadOfficeHoursGrid(this);
    }

    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
    }

    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
        }
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public void addTA(String initName, String initEmail) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail);

        // ADD THE TA
        if ((!containsTA(initName, initEmail)) && isValidEmail(initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
        
        
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void addTA(boolean grad, String initName, String initEmail) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(grad, initName, initEmail);

        // ADD THE TA
        if ((!containsTA(initName, initEmail)) && isValidEmail(initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);

        
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void addTALoad(String initName, String initEmail) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail);

        // ADD THE TA
        if ((!containsTA(initName, initEmail)) && isValidEmail(initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        
        Collections.sort(teachingAssistants);
    }

    public void removeTA(TeachingAssistant ta) {
        if (ta == null) {
            return;
        }
        ObservableList teachingAssistants = getTeachingAssistants();
        String taName = ta.getName();

        HashMap<String, StringProperty> officeHours = getOfficeHours();
        for (int i = 2; i < 7; i++) {
            for (int j = 1; j < (getEndHour() * 2 - getStartHour() * 2) + 1; j++) {
                StringProperty cellProp = getCellTextProperty(i, j);
                String cellText = cellProp.getValue();
                String cellKey = getCellKey(i, j);
                if (cellText.contains(taName)) {
                    toggleTAOfficeHours(cellKey, taName);
                }

            }
        }
        RecData recData = app.getCSGData().getRecData();
        recData.removeTAFromRecs(taName);
        teachingAssistants.remove(ta);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void updateTA(TeachingAssistant ta, boolean grad, String newName, String newEmail) {
        //Remove old TA from data and from grid while adding new one
        RecData recData = app.getCSGData().getRecData();
        recData.updateTAinRecs(ta.getName(), newName);
        removeTAByName(ta.getName());
        addTA(grad, newName, newEmail);
        TATab taTab = app.getCSGWorkspace().getTATab();
        
        

        HashMap<String, StringProperty> officeHours = getOfficeHours();
        for (int i = 2; i < 7; i++) {
            for (int j = 1; j < (getEndHour() * 2 - getStartHour() * 2) + 1; j++) {
                StringProperty cellProp = officeHours.get(taTab.buildCellKey(i, j));
                String cellText = cellProp.getValue();
                if (cellText.contains(ta.getName())) {
                    toggleTAOfficeHours(taTab.buildCellKey(i, j), ta.getName());
                    toggleTAOfficeHours(taTab.buildCellKey(i, j), newName);
                }
            }
        }
        // CLEAR THE TEXT FIELDS
        taTab.getNameTextField().setText("");
        taTab.getEmailTextField().setText("");

        // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
        taTab.getNameTextField().requestFocus();
        Button addButton = taTab.getAddButton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        addButton.setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT));
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void removeTATransaction(TeachingAssistant ta) {
        ObservableList teachingAssistants = getTeachingAssistants();
        String taName = ta.getName();

        HashMap<String, StringProperty> officeHours = getOfficeHours();
        for (int i = 2; i < 7; i++) {
            for (int j = 1; j < (getEndHour() * 2 - getStartHour() * 2) + 1; j++) {
                StringProperty cellProp = getCellTextProperty(i, j);
                String cellText = cellProp.getValue();
                String cellKey = getCellKey(i, j);
                if (cellText.contains(taName)) {
                    toggleTAOfficeHours(cellKey, taName);
                }

            }
        }
        RecData recData = app.getCSGData().getRecData();
        recData.removeTAFromRecs(taName);
        removeTAByName(taName);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void removeTAByName(String name) {
        ArrayList<TeachingAssistant> toRemove = new ArrayList();
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(name)) {
                toRemove.add(ta);
            }
        }
        teachingAssistants.removeAll(toRemove);
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }

    public void addOfficeHoursReservation(String day, String time, String taName) {
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName);
    }

    /**
     * This function toggles the taName in the cell represented by cellKey.
     * Toggle means if it's there it removes it, if it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
        } else {
            if (cellText.isEmpty()) {
                cellProp.setValue(cellText + taName);
            } else {
                cellProp.setValue(cellText + "\n" + taName);
            }
        }
        
        officeHoursList = TimeSlot.buildOfficeHoursList(this);

    }

    /**
     * This method removes taName from the office grid cell represented by
     * cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        if (cellText.charAt(0) == '\n') {
            cellText = cellText.replaceFirst("/n", "");
        }
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        } // IT MUST BE ANOTHER TA IN THE CELL
        else {
            cellText = cellText.replaceAll("\n" + taName, "");
            cellText = cellText.replaceAll(taName + "\n", "");
            cellText = cellText.replaceAll(taName, "");
            cellProp.setValue(cellText);
        }
    }
    
    public void changeStartHour(int hour){
        ArrayList<TimeSlot> officeHoursList = TimeSlot.buildOfficeHoursList(this);
        setStartHour(hour);

        TATab taTab = app.getCSGWorkspace().getTATab();
        
        GridPane officeHoursGridPane = taTab.getOfficeHoursGridPane();
        officeHoursGridPane.getChildren().clear();
        taTab.reloadOfficeHoursGrid(this);

        TimeSlot.removeTAOutsideTimes(officeHoursList, this);
        for (int i = 0; i < officeHoursList.size(); i++) {
            String name = officeHoursList.get(i).getName();
            String day = officeHoursList.get(i).getDay();
            String time = officeHoursList.get(i).getTime();

            addOfficeHoursReservation(day, time, name);
        }
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
    }
    
    public void changeEndHour(int hour){
        ArrayList<TimeSlot> officeHoursList = TimeSlot.buildOfficeHoursList(this);

        setEndHour(hour);

        TATab taTab = app.getCSGWorkspace().getTATab();
       
        GridPane officeHoursGridPane = taTab.getOfficeHoursGridPane();
        officeHoursGridPane.getChildren().clear();
        taTab.reloadOfficeHoursGrid(this);

        TimeSlot.removeTAOutsideTimes(officeHoursList, this);
        for (int i = 0; i < officeHoursList.size(); i++) {
            String name = officeHoursList.get(i).getName();
            String day = officeHoursList.get(i).getDay();
            String time = officeHoursList.get(i).getTime();

            addOfficeHoursReservation(day, time, name);
        }
        app.getGUI().getAppFileController().markAsEdited(app.getGUI());
        
    }

}