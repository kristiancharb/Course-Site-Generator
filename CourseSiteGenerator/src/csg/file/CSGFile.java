/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import djf.components.AppFileComponent;
import csg.CSGApp;
import csg.data.CSGData;
import javax.json.JsonObject;
import djf.components.AppDataComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGFile implements AppFileComponent {

    CSGApp app;
    CDFile cdFile;
    TAFile taFile;
    RecFile recFile;
    SchedFile schedFile;
    ProjectFile projectFile;

    public CSGFile(CSGApp initApp) {
        app = initApp;
        cdFile = new CDFile(initApp);
        taFile = new TAFile(initApp);
        recFile = new RecFile(initApp);
        schedFile = new SchedFile(initApp);
        projectFile = new ProjectFile(initApp);

    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        CSGData csgData = (CSGData) data;
        JsonArray jsonArray = loadJSONFile(filePath);
        taFile.loadTAData(csgData.getTAData(), jsonArray.getJsonObject(0));
        cdFile.loadCDData(csgData.getCDData(), jsonArray.getJsonObject(1));
        recFile.loadRecData(csgData.getRecData(), jsonArray.getJsonObject(2));
        schedFile.loadSchedData(csgData.getSchedData(), jsonArray.getJsonObject(3));
        projectFile.loadProjectData(csgData.getProjectData(), jsonArray.getJsonObject(4));
        csgData.getTAData().initHours(String.valueOf(csgData.getTAData().getStartHour()), 
                String.valueOf(csgData.getTAData().getEndHour()));
        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
        
        taFile.loadTAHours(csgData.getTAData());
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        CSGData csgData = (CSGData) data;

        JsonObject taJson = taFile.makeTAJson(csgData.getTAData());
        JsonObject cdJson = cdFile.makeCDJson(csgData.getCDData());
        JsonObject recJson = recFile.makeRecJson(csgData.getRecData());
        JsonObject schedJson = schedFile.makeSchedJson(csgData.getSchedData());
        JsonObject projectJson = projectFile.makeProjectJson(csgData.getProjectData());

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(taJson);
        jsonArrayBuilder.add(cdJson);
        jsonArrayBuilder.add(recJson);
        jsonArrayBuilder.add(schedJson);
        jsonArrayBuilder.add(projectJson);
        JsonArray jsonArray = jsonArrayBuilder.build();
        jsonWriter.writeArray(jsonArray);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeArray(jsonArray);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();

    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {

    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        CSGData csgData = (CSGData)data;
        filePath = csgData.getCDData().getExportDirPath();
        String userDir = System.getProperty("user.dir");
        String path = csgData.getCDData().getSiteTemplateDir();
        File publicHTML = new File(path);
        
        File selectedFile = new File(filePath);
        copyDirectory(publicHTML, selectedFile);
        String cdFilePath = filePath + "/js/CDData.json";
        String taFilePath = filePath + "/js/OfficeHoursGridData.json";
        String recFilePath = filePath + "/js/RecitationsData.json";
        String schedFilePath = filePath + "/js/ScheduleData.json";
        String teamsAndStudentsFilePath = filePath + "/js/TeamsAndStudents.json";
        String projFilePath = filePath + "/js/ProjectsData.json";
        String cssFileDest = filePath + "/css";
        String imageFileDest = filePath + "/images";
        String cssFileSource = userDir + "/work/css";
        String imageFileSource = userDir + "/images";
        try {
            taFile.exportTAData(csgData, taFilePath);
            recFile.exportRecData(csgData, recFilePath);
            schedFile.exportSchedData(csgData, schedFilePath);
            projectFile.exportTeamsAndStudents(csgData, teamsAndStudentsFilePath);
            projectFile.exportProjects(csgData, projFilePath);
            cdFile.exportCDData(csgData, cdFilePath);
            copyDirectory(new File(cssFileSource), new File(cssFileDest));
            copyDirectory(new File(imageFileSource), new File(imageFileDest));
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    
    public static void copyDirectory(File source, File dest) throws IOException{
        FileUtils.copyDirectory(source, dest);
    }

    public JsonArray loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonArray json = jsonReader.readArray();
        jsonReader.close();
        is.close();
        return json;
    }
    
    public CDFile getCDFile(){
        return cdFile;
    }
    public TAFile getTAFile(){
        return taFile;
    }
    public RecFile getRecFile(){
        return recFile;
    }
    public SchedFile getSchedFile(){
        return schedFile;
    }
    public ProjectFile getProjectFile(){
        return projectFile;
    }

}
