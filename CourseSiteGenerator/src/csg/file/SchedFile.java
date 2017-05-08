/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CSGApp;
import csg.data.SchedData;
import csg.data.CSGData;
import csg.data.ScheduleItem;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author kristiancharbonneau
 */
public class SchedFile {

    CSGApp app;

    public SchedFile(CSGApp app) {
        this.app = app;
    }

    public JsonObject makeSchedJson(SchedData schedData) {
        JsonArrayBuilder itemsArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItem> items = schedData.getItems();
        for (ScheduleItem s : items) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add("type", s.getType())
                    .add("date", s.getDate())
                    .add("title", s.getTitle())
                    .add("topic", s.getTopic())
                    .add("link", s.getLink())
                    .add("time", s.getTime())
                    .add("criteria", s.getCriteria())
                    .build();
            itemsArrayBuilder.add(tsJson);
        }
        JsonArray itemsArray = itemsArrayBuilder.build();

        JsonObject schedJson = Json.createObjectBuilder()
                .add("startingMonday", "" + schedData.getStartingMon())
                .add("endingFriday", "" + schedData.getEndingFri())
                .add("items", itemsArray).build();
        return schedJson;
    }
    
    public void loadSchedData(SchedData schedData, JsonObject json){
        
        JsonArray jsonArray = json.getJsonArray("items");
        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject jsonObj = jsonArray.getJsonObject(i);
            String type = jsonObj.getString("type");
            String date = jsonObj.getString("date");
            String title = jsonObj.getString("title");
            String topic = jsonObj.getString("topic");
            String link = jsonObj.getString("link");
            String time = jsonObj.getString("time");
            String criteria = jsonObj.getString("criteria");
            
            ScheduleItem item = new ScheduleItem(type, date, title, topic, link, criteria, time);
            schedData.getItems().add(item);
        }
        
        schedData.setStartingMon(json.getString("startingMonday"));
        schedData.setEndingFri(json.getString("endingFriday"));
    }
    
    public void exportSchedData(CSGData csgData, String filePath) throws Exception{
        SchedData schedData = csgData.getSchedData();
        
        JsonArrayBuilder holidaysArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder lecturesArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder referencesArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder recitationsArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItem> items = schedData.getItems();
        
        for (ScheduleItem item: items) {
            JsonObject json = Json.createObjectBuilder().build();
            if (item.getType().equalsIgnoreCase("Holiday")) {
                String date = item.getDate();
                String[] dates = date.split("/");
                if(dates[1].charAt(0) == '0') dates[1] = dates[1].substring(1);
                json = Json.createObjectBuilder()
                        .add("month", dates[0])
                        .add("day", dates[1])
                        .add("title", item.getTitle())
                        .add("link", item.getLink()).build();
                holidaysArrayBuilder.add(json);
            }
            if(item.getType().equalsIgnoreCase("Lecture")){
                String date = item.getDate();
                String[] dates = date.split("/");
                if(dates[1].charAt(0) == '0') dates[1] = dates[1].substring(1);
                json = Json.createObjectBuilder()
                        .add("month", dates[0])
                        .add("day", dates[1])
                        .add("title", item.getTitle())
                        .add("topic", item.getTopic())
                        .add("link", item.getLink()).build();
                lecturesArrayBuilder.add(json);
            }
            if(item.getType().equalsIgnoreCase("Reference")){
                String date = item.getDate();
                String[] dates = date.split("/");
                if(dates[1].charAt(0) == '0') dates[1] = dates[1].substring(1);
                json = Json.createObjectBuilder()
                        .add("month", dates[0])
                        .add("day", dates[1])
                        .add("title", item.getTitle())
                        .add("topic", item.getTopic())
                        .add("link", item.getLink()).build();
                referencesArrayBuilder.add(json);
            }
            if(item.getType().equalsIgnoreCase("Recitation")){
                String date = item.getDate();
                String[] dates = date.split("/");
                if(dates[1].charAt(0) == '0') dates[1] = dates[1].substring(1);
                json = Json.createObjectBuilder()
                        .add("month", dates[0])
                        .add("day", dates[1])
                        .add("title", item.getTitle())
                        .add("topic", item.getTopic())
                        .add("link", item.getLink()).build();
                recitationsArrayBuilder.add(json);
            }
            if(item.getType().equalsIgnoreCase("Homework")){
                String date = item.getDate();
                String[] dates = date.split("/");
                if(dates[1].charAt(0) == '0') dates[1] = dates[1].substring(1);
                json = Json.createObjectBuilder()
                        .add("month", dates[0])
                        .add("day", dates[1])
                        .add("title", item.getTitle())
                        .add("topic", item.getTopic())
                        .add("link", item.getLink())
                        .add("time", item.getTime())
                        .add("criteria", item.getCriteria()).build();
                hwsArrayBuilder.add(json);
            }    
        }
        JsonArray holidaysArray = holidaysArrayBuilder.build();
        JsonArray lecturesArray = lecturesArrayBuilder.build();
        JsonArray referencesArray = referencesArrayBuilder.build();
        JsonArray recitationsArray = recitationsArrayBuilder.build();
        JsonArray hwsArray = hwsArrayBuilder.build();
        
        String startDate = "" + schedData.getStartingMon();
        String endDate = "" + schedData.getEndingFri();
        String[] startDateArr = startDate.split("/");
        String[] endDateArr = endDate.split("/");
        JsonObject schedJson = Json.createObjectBuilder()
                .add("startingMondayMonth", startDateArr[0])
                .add("startingMondayDay", startDateArr[1])
                .add("endingFridayMonth", endDateArr[0])
                .add("endingFridayDay", endDateArr[1])
                .add("holidays", holidaysArray)
                .add("lectures", lecturesArray)
                .add("references", referencesArray)
                .add("recitations", recitationsArray)
                .add("hws", hwsArray)
                .build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(schedJson);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(schedJson);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }

}
