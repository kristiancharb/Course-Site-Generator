/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;
import csg.CSGApp;
import csg.data.CDData;
import csg.data.CSGData;
import csg.data.SiteTemplate;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
public class CDFile {
    CSGApp app;
    
    public CDFile(CSGApp app){
        this.app = app;
    }
    
    public JsonObject makeCDJson(CDData cdData){
        JsonArrayBuilder siteTemplateArrayBuilder = Json.createArrayBuilder();
	ObservableList<SiteTemplate> siteTemplates = cdData.getSiteTemplates();
	for (SiteTemplate s : siteTemplates) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add("use", s.getUse().getValue())
		    .add("navbarTitle", s.getNavbarTitle())
		    .add("fileName", s.getFileName())
                    .add("script", s.getScript()).build();
	    siteTemplateArrayBuilder.add(tsJson);
	}
	JsonArray siteTemplatesArray = siteTemplateArrayBuilder.build();
        
        JsonObject cdJson = Json.createObjectBuilder()
                .add("subject", "" + cdData.getSubject())
                .add("semester", "" + cdData.getSemester())
                .add("number", "" + cdData.getNumber())
                .add("year", "" + cdData.getYear())
                .add("title", "" + cdData.getTitle())
                .add("instructorName", "" + cdData.getInstructorName())
                .add("instructorHome", "" + cdData.getInstructorHome())
                .add("exportDirPath", "" + cdData.getExportDirPath())
                .add("siteTemplateDir", "" + cdData.getSiteTemplateDir())
                .add("siteTemplates", siteTemplatesArray)
                .add("bannerImagePath", "" + cdData.getBannerImagePath())
                .add("leftFootPath", "" + cdData.getLeftFootPath())
                .add("rightFootPath", "" + cdData.getRightFootPath())
                .add("stylesheet", "" + cdData.getStyleSheet())
                .build();
        return cdJson;
    }
    
    public void loadCDData(CDData cdData, JsonObject json){
        
        cdData.setSubject(json.getString("subject"));
        cdData.setSemester(json.getString("semester"));
        cdData.setNumber(json.getString("number"));
        cdData.setYear(json.getString("year"));
        cdData.setTitle(json.getString("title"));
        cdData.setInstructorName(json.getString("instructorName"));
        cdData.setInstructorHome(json.getString("instructorHome"));
        cdData.setExportDirPath(json.getString("exportDirPath"));
        cdData.setSiteTemplateDir(json.getString("siteTemplateDir"));
        cdData.setBannerImagePath(json.getString("bannerImagePath"));
        cdData.setLeftFootPath(json.getString("leftFootPath"));
        cdData.setRightFootPath(json.getString("rightFootPath"));
        cdData.setStyleSheet(json.getString("stylesheet"));
        
        JsonArray jsonArray = json.getJsonArray("siteTemplates");
        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject jsonObj = jsonArray.getJsonObject(i);
            boolean use = jsonObj.getBoolean("use");
            String navbarTitle = jsonObj.getString("navbarTitle");
            String fileName = jsonObj.getString("fileName");
            String script = jsonObj.getString("script");
            SiteTemplate st = new SiteTemplate(use, navbarTitle, fileName, script);
            cdData.getSiteTemplates().add(st);
        }
    }
    
    public void exportCDData(CSGData csgData, String filePath) throws Exception{
        CDData cdData = csgData.getCDData();
        JsonArrayBuilder siteTemplateArrayBuilder = Json.createArrayBuilder();
	ObservableList<SiteTemplate> siteTemplates = cdData.getSiteTemplates();
	for (SiteTemplate s : siteTemplates) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add("use", s.getUse().getValue())
                    .add("id", s.getNavbarTitle().toLowerCase()+"_link")
		    .add("navbarTitle", s.getNavbarTitle())
		    .add("fileName", s.getFileName())
                    .add("script", s.getScript()).build();
	    siteTemplateArrayBuilder.add(tsJson);
	}
	JsonArray siteTemplatesArray = siteTemplateArrayBuilder.build();
        
        JsonObject cdJson = Json.createObjectBuilder()
                .add("subject", "" + cdData.getSubject())
                .add("semester", "" + cdData.getSemester())
                .add("number", "" + cdData.getNumber())
                .add("year", "" + cdData.getYear())
                .add("title", "" + cdData.getTitle())
                .add("instructorName", "" + cdData.getInstructorName())
                .add("instructorHome", "" + cdData.getInstructorHome())
                .add("exportDirPath", "" + cdData.getExportDirPath())
                .add("siteTemplateDir", "" + cdData.getSiteTemplateDir())
                .add("site_templates", siteTemplatesArray)
                .add("bannerImagePath", "" + cdData.getBannerImagePath())
                .add("leftFootPath", "" + cdData.getLeftFootPath())
                .add("rightFootPath", "" + cdData.getRightFootPath())
                .add("stylesheet", "" + cdData.getStyleSheet())
                .build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(cdJson);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(cdJson);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
}
