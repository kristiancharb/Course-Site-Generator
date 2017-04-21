/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.CDData;
import csg.data.SiteTemplate;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGFileTest {
    
    public CSGFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadData method, of class CSGFile.
     */
    /*
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");
        AppDataComponent data = null;
        String filePath = "";
        CSGFile instance = null;
        instance.loadData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveData method, of class CSGFile.
     */
    /*
    @Test
    public void testSaveData() throws Exception {
        System.out.println("saveData");
        AppDataComponent data = null;
        String filePath = "";
        CSGFile instance = null;
        instance.saveData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
    @Test
    public void testLoadCDData() throws Exception {
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        CDData cdData = csgData.getCDData();
        CDFile cdFile = csgFile.getCDFile();
        JsonArray jsonArray = csgFile.loadJSONFile(System.getProperty("user.dir") + "/work/SiteSaveTest");
        cdFile.loadCDData(cdData, jsonArray.getJsonObject(1));
        assertTrue(cdData.getSubject().equals("CSE"));
        assertTrue(cdData.getNumber().equals("219"));
        assertTrue(cdData.getYear().equals("2017"));
        assertTrue(cdData.getTitle().equals("Comp Sci III"));
        assertTrue(cdData.getInstructorHome().equals("mckenna.com"));
        assertTrue(cdData.getInstructorName().equals("McKenna"));
        assertTrue(cdData.getExportDirPath().equals("CSE219\\Summer2017\\public"));
        assertTrue(cdData.getSiteTemplateDir().equals("\\templates\\CSE219"));
        assertTrue(cdData.getBannerImagePath().equals("yale.png"));
        assertTrue(cdData.getLeftFootPath().equals("yale.png"));
        assertTrue(cdData.getRightFootPath().equals("yale.png"));
        assertTrue(cdData.getStyleSheet().equals("seawolf.css"));
        
        ArrayList<SiteTemplate> templates = new ArrayList<>();
        templates.add(new SiteTemplate("Home", "index.html", "HomeBuilder.js"));
        templates.add(new SiteTemplate("Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        templates.add(new SiteTemplate("Schedule", "schedule.html", "ScheduleBuilder.js"));
        templates.add(new SiteTemplate("HWs", "hws.html", "HWsBuilder"));
        templates.add(new SiteTemplate("Projects", "projects.html", "ProjectBuilder.js"));
        ObservableList<SiteTemplate> loadTemp = cdData.getSiteTemplates();
        for(int i = 0; i < templates.size(); i++){
            assertTrue(loadTemp.get(i).getUse().getValue().equals(templates.get(i).getUse().getValue()));
            assertTrue(loadTemp.get(i).getFileName().equals(templates.get(i).getFileName()));
            assertTrue(loadTemp.get(i).getNavbarTitle().equals(templates.get(i).getNavbarTitle()));
            assertTrue(loadTemp.get(i).getScript().equals(templates.get(i).getScript()));
        } 
    }
    
    @Test
    public void testLoadTAData() throws Exception{
        CSGApp csgApp = new CSGApp();
        boolean b = csgApp.loadProperties("app_properties.xml");
        CSGFile csgFile = new CSGFile(csgApp);
        CSGData csgData = new CSGData(csgApp);
        TAData taData = csgData.getTAData();
        TAFile taFile = csgFile.getTAFile();
        JsonArray jsonArray = csgFile.loadJSONFile(System.getProperty("user.dir") + "/work/SiteSaveTest");
        taFile.loadTAData(taData, jsonArray.getJsonObject(0));
        
        ObservableList<TeachingAssistant> loadTas = taData.getTeachingAssistants();
        ObservableList<TeachingAssistant> tas = FXCollections.observableArrayList();
        tas.addAll(
                new TeachingAssistant("John Doe", "jdoe@gmail.com"),
                new TeachingAssistant("Jane Doe", "jane@gmail.com"),
                new TeachingAssistant("Bob", "bob@yahoo.com"));
        Collections.sort(tas);
        
        for(int i = 0; i < tas.size(); i++){
            assertTrue(loadTas.get(i).getGrad().getValue().equals(tas.get(i).getGrad().getValue()));
            assertTrue(loadTas.get(i).getName().equals(tas.get(i).getName()));
            assertTrue(loadTas.get(i).getEmail().equals(tas.get(i).getEmail()));
        }
        
        
        
    }


    
}
