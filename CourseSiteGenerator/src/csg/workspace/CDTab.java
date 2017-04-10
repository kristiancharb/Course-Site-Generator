/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;

/**
 *
 * @author kristiancharbonneau
 */
public class CDTab extends Tab{
    CSGApp app;
    CSGController controller;
    CSGWorkspace workspace;
    
    GridPane courseInfoBox;
    VBox siteTemplateBox;
    GridPane pageStyle;
    Label courseInfoHeader;
    Label siteTemplateHeader;
    Label pageStyleHeader;
    
    ComboBox subjectBox;
    ComboBox semesterBox;
    ComboBox numberBox;
    ComboBox yearBox;
    
    TextField titleField;
    TextField instrNameField;
    TextField instrHomeField;
    
    Button changeExportButton;
    
    Button selectTemplateButton;
    
    TableView<String> siteTemplateTable;
    TableColumn<String, Boolean> useColumn;
    TableColumn<String, String> titleColumn;
    TableColumn<String, String> fileNameColumn;
    TableColumn<String, String> scriptColumn;
    
    Image bannerImage;
    Button changeBannerButton;
    Image leftFootImage;
    Button changeLeftFootButton;
    Image rightFootImage;
    Button changeRightFootButton;
    
    ComboBox styleSheetBox;
    
    
    
    
    
    public CDTab(CSGApp app, CSGController controller, CSGWorkspace workspace){
        this.app = app;
        this.controller = controller;
        this.workspace = workspace;
        this.setText("Course Details");
        
        BorderPane pane = new BorderPane();
        courseInfoBox = new GridPane();
        courseInfoBox.setHgap(10);
        courseInfoBox.setVgap(10);
        courseInfoBox.setPadding(new Insets(5,5,5,5));
        courseInfoBox.add(new Label("Course Information"), 0, 0, 2, 2);
        subjectBox = new ComboBox();
        numberBox = new ComboBox();
        semesterBox = new ComboBox();
        yearBox = new ComboBox();
        courseInfoBox.add(new Label("Subject: "), 0, 2, 1, 1);
        courseInfoBox.add(subjectBox, 1, 2, 1, 1);
        courseInfoBox.add(new Label("Number: "), 9, 2, 1, 1);
        courseInfoBox.add(numberBox, 10, 2, 1, 1);
        courseInfoBox.add(new Label("Semester: "), 0, 3, 1, 1);
        courseInfoBox.add(semesterBox, 1, 3, 1, 1);
        courseInfoBox.add(new Label("Year: "), 9, 3, 1, 1);
        courseInfoBox.add(yearBox, 10, 3, 1, 1);
        titleField = new TextField();
        titleField.setPromptText("Computer Science III");
        instrNameField = new TextField();
        instrNameField.setPromptText("Richard McKenna");
        instrHomeField = new TextField();
        instrNameField.setPromptText("http://www.cs.stonybrook.edu");
        courseInfoBox.add(new Label("Title: "), 0, 4, 1, 1);
        courseInfoBox.add(titleField, 1, 4, 10, 1);
        courseInfoBox.add(new Label("Instructor Name: "), 0, 5, 1, 1);
        courseInfoBox.add(instrNameField, 1, 5, 10, 1);
        courseInfoBox.add(new Label("Instructor Home: "), 0, 6, 1, 1);
        courseInfoBox.add(instrHomeField, 1, 6, 10, 1);
        courseInfoBox.add(new Label("Change Export Dir: "), 0, 7, 1, 1);
        courseInfoBox.add(new Label("..\\..\\Courses\\CSE219\\Summer2017\\public"), 1, 7, 9, 1);
        changeExportButton = new Button("Change");
        courseInfoBox.add(changeExportButton, 10, 7, 1, 1);
        
        siteTemplateBox = new VBox(8);
        siteTemplateHeader = new Label("Site Template");
        siteTemplateBox.getChildren().add(siteTemplateHeader);
        siteTemplateBox.getChildren().add(new Label("The selected directory should contain the full site template, "
                + "including the HTML files"));
        siteTemplateBox.getChildren().add(new Label(".\\templates\\CSE219"));
        selectTemplateButton = new Button("Select Template Directory");
        siteTemplateBox.getChildren().add(selectTemplateButton);
        siteTemplateBox.getChildren().add(new Label("SitePages: "));
        
        siteTemplateTable = new TableView();
        useColumn = new TableColumn("Use");
        titleColumn = new TableColumn("Navbar Title");
        fileNameColumn = new TableColumn("File Name");
        scriptColumn = new TableColumn("Script");
        
        
        
        
        
        pane.setTop(courseInfoBox);
        pane.setCenter(siteTemplateBox);
        this.setContent(pane);
    }
    
}
