/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CSGApp;
import csg.data.RecData;
import csg.data.CSGData;
import csg.data.Recitation;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class RecFile {

    CSGApp app;

    public RecFile(CSGApp app) {
        this.app = app;

    }

    public JsonObject makeRecJson(RecData recData) {
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

    public void loadRecData(RecData recData, JsonObject json) {
        JsonArray jsonArray = json.getJsonArray("recitations");
        for (int i = 0; i < jsonArray.size(); i++) {
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

    public void exportRecData(CSGData csgData, String filePath) throws Exception{
        RecData recData = csgData.getRecData();

        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recs = recData.getRecitations();
        for (Recitation r : recs) {
            JsonObject recJson = Json.createObjectBuilder().build();
            recJson = Json.createObjectBuilder()
                    .add("section", "<strong>" + r.getSection() + "</strong> (" + r.getInstructor() + ")")
                    .add("day_time", r.getTime())
                    .add("location", r.getLocation())
                    .add("ta_1", r.getTa1())
                    .add("ta_2", r.getTa2())
                    .build();
            recArrayBuilder.add(recJson);

        }
        JsonArray recsArray = recArrayBuilder.build();
        
        JsonObject recitationJson = Json.createObjectBuilder()
                .add("recitations", recsArray).build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(recitationJson);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(recitationJson);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
}
