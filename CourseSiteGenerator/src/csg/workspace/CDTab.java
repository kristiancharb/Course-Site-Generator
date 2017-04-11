/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.data.SiteTemplate;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import properties_manager.PropertiesManager;

/**
 *
 * @author kristiancharbonneau
 */
public class CDTab extends Tab{
    CSGApp app;
    CSGController controller;
    CSGWorkspace workspace;
    
    BorderPane pane;
    ScrollPane sPane;
    GridPane courseInfoBox;
    VBox siteTemplateBox;
    GridPane pageStyleBox;
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
    
    ObservableList<SiteTemplate> siteTemplates;
    TableView<SiteTemplate> siteTemplateTable;
    TableColumn<SiteTemplate, Boolean> useColumn;
    TableColumn<SiteTemplate, String> titleColumn;
    TableColumn<SiteTemplate, String> fileNameColumn;
    TableColumn<SiteTemplate, String> scriptColumn;
    
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

        
        pane = new BorderPane();
        courseInfoBox = new GridPane();
        courseInfoBox.setHgap(10);
        courseInfoBox.setVgap(10);
        courseInfoBox.setPadding(new Insets(5,5,5,5));
        courseInfoHeader = new Label("Course Information");
        courseInfoBox.add(courseInfoHeader, 0, 0, 2, 2);
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
        
        siteTemplates = FXCollections.observableArrayList(
                new SiteTemplate("Home", "index.html", "HomeBuilder.js"),
                new SiteTemplate("Syllabus", "syllabus.html", "SyllabusBuilder.js"),
                new SiteTemplate("Schedule", "schedule.html", "ScheduleBuilder.js"),
                new SiteTemplate("HWs", "hws.html", "HWsBuilder"),
                new SiteTemplate("Projects", "projects.html", "ProjectBuilder.js")
        );
        siteTemplateTable = new TableView();
        siteTemplateTable.setItems(siteTemplates);
        useColumn = new TableColumn("Use");
        useColumn.setCellValueFactory(new PropertyValueFactory<SiteTemplate, Boolean>("use"));
        titleColumn = new TableColumn("Navbar Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<SiteTemplate, String>("navbarTitle"));
        fileNameColumn = new TableColumn("File Name");
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<SiteTemplate, String>("fileName"));
        scriptColumn = new TableColumn("Script");
        scriptColumn.setCellValueFactory(new PropertyValueFactory<SiteTemplate, String>("script"));
        siteTemplateTable.getColumns().addAll(useColumn, titleColumn, fileNameColumn, scriptColumn);
        siteTemplateTable.setMaxWidth(500);
        siteTemplateTable.setMaxHeight(200);
        siteTemplateBox.getChildren().add(siteTemplateTable);
        
        pageStyleBox = new GridPane();
        pageStyleBox.setHgap(10);
        pageStyleBox.setVgap(10);
        pageStyleBox.setPadding(new Insets(5,5,5,5));
        pageStyleHeader = new Label("Page Style");
        pageStyleBox.add(pageStyleHeader, 0, 0, 2, 2);
        pageStyleBox.add(new Label("Banner School Image"), 0, 2, 1, 1);
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + "yale.png";
        pageStyleBox.add(new ImageView(new Image(imagePath, 110, 20, true, true)), 1, 2, 5, 1);
        changeBannerButton = new Button("Change");
        pageStyleBox.add(changeBannerButton, 6, 2, 1, 1);
        pageStyleBox.add(new Label("Left Footer Image"), 0, 3, 1, 1);
        pageStyleBox.add(new ImageView(new Image(imagePath, 110, 20, true, true)), 1, 3, 5, 1);
        changeLeftFootButton = new Button("Change");
        pageStyleBox.add(changeLeftFootButton, 6, 3, 1, 1);
        pageStyleBox.add(new Label("Right Footer Image"), 0, 4, 1, 1);
        pageStyleBox.add(new ImageView(new Image(imagePath, 110, 20, true, true)), 1, 4, 5, 1);
        changeRightFootButton = new Button("Change");
        pageStyleBox.add(changeRightFootButton, 6, 4, 1, 1);
        pageStyleBox.add(new Label("Stylesheet"), 0, 5, 1, 1);
        styleSheetBox = new ComboBox();
        pageStyleBox.add(styleSheetBox, 1, 5, 1, 1);
        pageStyleBox.add(new Label("NOTE: New Stylesheets maybe be placed in work/css to be selectable."), 0, 6, 10, 1);
        
        pane.setTop(courseInfoBox);
        pane.setCenter(siteTemplateBox);
        pane.setBottom(pageStyleBox);
        sPane = new ScrollPane();
        sPane.setContent(pane);
        pane.prefWidthProperty().bind(sPane.widthProperty().multiply(0.98));
        //siteTemplateBox.prefWidthProperty().bind(sPane.widthProperty().multiply(0.9));
        //pageStyleBox.prefWidthProperty().bind(sPane.widthProperty().multiply(0.9));
        //courseInfoBox.prefWidthProperty().bind(sPane.widthProperty().multiply(0.9));
        this.setContent(sPane);
    }
    
    public GridPane getCourseInfoBox(){
        return courseInfoBox;
    }
    public BorderPane getPane(){
        return pane;
    }
    public ScrollPane getSPane(){
        return sPane;
    }
    public VBox getSiteTemplateBox(){
        return siteTemplateBox;
    }
    public GridPane getPageStyleBox(){
        return pageStyleBox;
    }
    public Label getCourseInfoHeader(){
        return courseInfoHeader;
    }
    public Label getSiteTemplateHeader(){
       return siteTemplateHeader;
    }
    public Label getPageStyleHeader(){
        return pageStyleHeader;
    }
    
    public ComboBox getSubjectBox(){
        return subjectBox;
    }
    public ComboBox getSemesterBox(){
        return semesterBox;
    }
    public ComboBox getNumberBox(){
        return numberBox;
    }
    public ComboBox getYearBox(){
        return yearBox;
    }
    
    public TextField getTitleField(){
        return titleField;
    }
    
   public TextField getInstrNameField(){
       return instrNameField;
   }
    public TextField getInstrHomeField(){
       return instrHomeField;
   }
    
    public Button getChangeExportButton(){
        return changeExportButton;
    }
    
    public Button getSelectTemplateButton(){
        return selectTemplateButton;
    }

    public TableView<SiteTemplate> getSiteTemplateTable(){
        return siteTemplateTable;
    }
    public TableColumn<SiteTemplate, Boolean> getUseColumn(){
        return useColumn;
    }
    public TableColumn<SiteTemplate, String> getTitleColumn(){
        return titleColumn;
    }
    public TableColumn<SiteTemplate, String> getFileNameColumn(){
        return fileNameColumn;
    }
    public TableColumn<SiteTemplate, String> getScriptColumn(){
        return scriptColumn;
    }
    
}
