/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CSGApp;
import csg.data.SchedData;
import csg.data.ScheduleItem;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

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
            ScheduleItem item = new ScheduleItem(type, date, title, topic);
            schedData.getItems().add(item);
        }
        
        schedData.setStartingMon(json.getString("startingMonday"));
        schedData.setEndingFri(json.getString("endingFriday"));
    }

}
