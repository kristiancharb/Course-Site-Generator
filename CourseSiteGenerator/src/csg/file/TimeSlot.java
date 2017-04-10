/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import csg.data.TAData;
import csg.data.TeachingAssistant;

/**
 * This class stores info for a single TA-Day/Time mapping
 * for our office hours grid. It's useful to provide a list
 * of these objects for file I/O.
 * 
 * @author Richard McKenna
 */
public class TimeSlot {
    private String day;
    private String time;
    private String name;
    
    public TimeSlot(String initDay, String initTime, String initName) {
        day = initDay;
        time = initTime;
        name = initName;
    }
    
    // ACCESSORS AND MUTATORS
    
    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getName() { return name; }
    public String getKey() {
        return day + " " + time + " " + name;
    }    
    
    public void setDay(String initDay) {
        day = initDay;
    }
    public void setTime(String initTime) {
        time = initTime;
    }
    public void setName(String initName) {
        name = initName;
    }
    
    /**
     * This function builds a list of all the office hours stored in 
     * the grid excluding empty time slots. This helps us do our file
     * input since it reflects how office hours are stored in the 
     * JSON file.
     */
    public static ArrayList<TimeSlot> buildOfficeHoursList(TAData data) {
        // BUILD AND RETURN THIS LIST
        ArrayList<TimeSlot> officeHoursList = new ArrayList();
        ArrayList<String> gridHeaders = data.getGridHeaders();
        HashMap<String, StringProperty> officeHours = data.getOfficeHours();
        for (int row = 1; row < data.getNumRows(); row++) {
            for (int col = 2; col < 7; col++) {
                // WE ONLY WANT THE DATA, NOTE THE HEADERS
                String cellKey = data.getCellKey(col, row);
                StringProperty timeSlotProp = officeHours.get(cellKey);
                String timeSlotText = timeSlotProp.getValue();
                String[] taNames = timeSlotText.split("\n");
                String day = gridHeaders.get(col);
                String timeCellKey = data.getCellKey(0, row);
                String time = officeHours.get(timeCellKey).getValue().replace(":", "_");
                for (int i = 0; i < taNames.length; i++) {
                    String taName = taNames[i];
                    if (taName.length() > 0) {
                        // ADD A TIME SLOT FOR EACH TA FOR EACH GRID CELL
                        TimeSlot ts = new TimeSlot(day, time, taName);
                        officeHoursList.add(ts);
                    }
                }
            }
        }
        return officeHoursList;
    }
    
    public static ArrayList<TimeSlot> buildTATimeSlotList(TAData data, TeachingAssistant ta){
        // BUILD AND RETURN THIS LIST
        ArrayList<TimeSlot> taOfficeHoursList = new ArrayList();
        ArrayList<String> gridHeaders = data.getGridHeaders();
        HashMap<String, StringProperty> officeHours = data.getOfficeHours();
        for (int row = 1; row < data.getNumRows(); row++) {
            for (int col = 2; col < 7; col++) {
                // WE ONLY WANT THE DATA, NOTE THE HEADERS
                String cellKey = data.getCellKey(col, row);
                StringProperty timeSlotProp = officeHours.get(cellKey);
                String timeSlotText = timeSlotProp.getValue();
                String[] taNames = timeSlotText.split("\n");
                String day = gridHeaders.get(col);
                String timeCellKey = data.getCellKey(0, row);
                String time = officeHours.get(timeCellKey).getValue().replace(":", "_");
                for (int i = 0; i < taNames.length; i++) {
                    String taName = taNames[i];
                    if (taName.equals(ta.getName())) {
                        // ADD A TIME SLOT FOR EACH TA FOR EACH GRID CELL
                        TimeSlot ts = new TimeSlot(day, time, taName);
                        taOfficeHoursList.add(ts);
                    }
                }
            }
        }
        return taOfficeHoursList;
    }
    
    public static void removeTAOutsideTimes(ArrayList<TimeSlot> officeHoursList, TAData data){
        int startHour = data.getStartHour();
        int endHour = data.getEndHour();
        int n = officeHoursList.size();
        ArrayList<TimeSlot> toRemove = new ArrayList<>();
        
        for(int i = 0; i < n; i++){
            String timeString = officeHoursList.get(i).getTime();
            int hour = Integer.parseInt(timeString.substring(0, timeString.indexOf("_")));
            if (timeString.contains("pm") && !timeString.contains("12"))
                hour += 12;
            if (timeString.contains("am") && hour == 12)
                hour = 0;
            if(hour < startHour) toRemove.add(officeHoursList.get(i));
            if(hour >= endHour) toRemove.add(officeHoursList.get(i));
        }
        
        for(TimeSlot t: toRemove){
            officeHoursList.remove(t);
        }
        
        
    }
    
    public static boolean toBeRemoved(ArrayList<TimeSlot> officeHoursList, int startHour, int endHour){
        boolean bool = false;
        int n = officeHoursList.size();
        
        for(int i = 0; i < n; i++){
            String timeString = officeHoursList.get(i).getTime();
            int hour = Integer.parseInt(timeString.substring(0, timeString.indexOf("_")));
            if (timeString.contains("pm") && !timeString.contains("12"))
                hour += 12;
            if (timeString.contains("am") && hour == 12)
                hour = 0;
            if(hour < startHour){ 
                bool = true;
                return bool;
            }
            if(hour >= endHour){
                bool = true;
                return bool;
            }
            
        }
        return bool;
    }
}
