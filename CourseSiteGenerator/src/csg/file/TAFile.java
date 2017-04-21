/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;
import csg.CSGApp;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author kristiancharbonneau
 */
public class TAFile {
    CSGApp app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_EMAIL = "email";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    
    public TAFile(CSGApp app){
        this.app = app;
    }
    
    public JsonObject makeTAJson(TAData taData){
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = taData.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    
                    .add(JSON_EMAIL, ta.getEmail()).build();
            
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = taData.getOfficeHoursList();
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject TAJson = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + taData.getStartHour())
		.add(JSON_END_HOUR, "" + taData.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.build();
        
        return TAJson;
    }
    
    public void loadTAData(TAData taData, JsonObject json){
        	// LOAD THE START AND END HOURS
	taData.setStartHour(Integer.parseInt(json.getString(JSON_START_HOUR)));
        taData.setEndHour(Integer.parseInt(json.getString(JSON_END_HOUR)));
        

        

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            TeachingAssistant ta = new TeachingAssistant(name, email);
            taData.addTALoad(name, email);
        }

        // AND THEN ALL THE OFFICE HOURS
        ArrayList<TimeSlot> officeHoursList = new ArrayList<>();
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            officeHoursList.add(new TimeSlot(day, time, name));
        }
        taData.setOfficeHoursList(officeHoursList);
    }
    
    public void loadTAHours(TAData taData){
        ArrayList<TimeSlot> officeHoursList = taData.getOfficeHoursList();
        for(int i = 0; i < officeHoursList.size(); i++){
            taData.addOfficeHoursReservation(officeHoursList.get(i).getDay(),
                    officeHoursList.get(i).getTime(), officeHoursList.get(i).getName());
        }
    }
    
}
