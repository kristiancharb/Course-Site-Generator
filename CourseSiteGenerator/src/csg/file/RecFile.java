/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;
import csg.CSGApp;
import csg.data.RecData;
import csg.data.Recitation;
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
public class RecFile {
    CSGApp app;
    
    public RecFile(CSGApp app){
        this.app = app;
        
    }
    
    public JsonObject makeRecJson(RecData recData){
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recitations = recData.getRecitations();
	for (Recitation r : recitations) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add("section", r.getSection())
		    .add("instructor", r.getInstructor())
		    .add("time", r.getTime())
                    .add("location", r.getLocation())
                    .add("ta1", r.getTa1())
                    .add("ta2", r.getTa2())
                    .build();
	    recitationArrayBuilder.add(tsJson);
	}
	JsonArray recitationArray = recitationArrayBuilder.build();
                
        JsonObject recJson = Json.createObjectBuilder()
                .add("recitations", recitationArray).build();
        return recJson;
    }
    
    public void loadRecData(RecData recData, JsonObject json){
        JsonArray jsonArray = json.getJsonArray("recitations");
        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject jsonObj = jsonArray.getJsonObject(i);
            String section = jsonObj.getString("section");
            String instructor = jsonObj.getString("instructor");
            String time = jsonObj.getString("time");
            String location = jsonObj.getString("location");
            String ta1 = jsonObj.getString("ta1");
            String ta2 = jsonObj.getString("ta2");
            Recitation rec = new Recitation(section, instructor, time, location, ta1, ta2);
            recData.getRecitations().add(rec);
        }
    }
}
