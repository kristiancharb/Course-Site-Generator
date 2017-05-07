/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CDData;
import csg.data.SiteTemplate;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import properties_manager.PropertiesManager;
import javafx.scene.control.TableColumn.CellDataFeatures;

/**
 *
 * @author kristiancharbonneau
 */
public class CDTab extends Tab{
    CSGApp app;
    CSGController controller;
    CSGWorkspace workspace;
    
    VBox pane;
    ScrollPane sPane;
    GridPane courseInfoBox;
    VBox siteTemplateBox;
    GridPane pageStyleBox;
    Label courseInfoHeader;
    Label siteTemplateHeader;
    Label pageStyleHeader;
    Label siteTemplateDir;
    Label exportDir;
    
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
    ImageView bannerImageView;
    Button changeBannerButton;
    Image leftFootImage;
    ImageView leftFootImageView;
    Button changeLeftFootButton;
    Image rightFootImage;
    ImageView rightFootImageView;
    Button changeRightFootButton;
    
    ComboBox styleSheetBox;
    
    
    
    
    
    
    
    public CDTab(CSGApp app, CSGController controller, CSGWorkspace workspace){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        this.app = app;
        this.controller = controller;
        this.workspace = workspace;
        CDData cdData = app.getCSGData().getCDData();
        this.setText(props.getProperty(CSGProp.CDTAB_HEADER));

        
        pane = new VBox();
        courseInfoBox = new GridPane();
        courseInfoBox.setHgap(10);
        courseInfoBox.setVgap(10);
        courseInfoBox.setPadding(new Insets(5,5,5,5));
        courseInfoHeader = new Label(props.getProperty(CSGProp.COURSEINFO_HEADER));
        courseInfoBox.add(courseInfoHeader, 0, 0, 2, 2);
        subjectBox = new ComboBox();
        subjectBox.setItems(FXCollections.observableArrayList("CSE", "MAT", "AMS", "ECE", "EST", "MEC", "PHY", "AST", "ISE", "BME"));
        subjectBox.setValue(cdData.getSubject());
        numberBox = new ComboBox();
        numberBox.setItems(FXCollections.observableArrayList("101", "114", "214", "215", "219", "220", "260", "300", "303", "320"));
        numberBox.setValue(cdData.getNumber());
        semesterBox = new ComboBox();
        semesterBox.setItems(FXCollections.observableArrayList("Fall", "Winter", "Spring", "Summer"));
        semesterBox.setValue(cdData.getSemester());
        yearBox = new ComboBox();
        yearBox.setItems(FXCollections.observableArrayList("2015", "2016", "2017", "2018", "2019", "2020"));
        yearBox.setValue(cdData.getYear());
        courseInfoBox.add(new Label(props.getProperty(CSGProp.SUBJECT)), 0, 2, 1, 1);
        courseInfoBox.add(subjectBox, 1, 2, 1, 1);
        courseInfoBox.add(new Label(props.getProperty(CSGProp.NUMBER)), 9, 2, 1, 1);
        courseInfoBox.add(numberBox, 10, 2, 1, 1);
        courseInfoBox.add(new Label(props.getProperty(CSGProp.SEMESTER)), 0, 3, 1, 1);
        courseInfoBox.add(semesterBox, 1, 3, 1, 1);
        courseInfoBox.add(new Label(props.getProperty(CSGProp.YEAR)), 9, 3, 1, 1);
        courseInfoBox.add(yearBox, 10, 3, 1, 1);
        titleField = new TextField();
        titleField.setText(cdData.getTitle());
        instrNameField = new TextField();
        instrNameField.setText(cdData.getInstructorName());
        instrHomeField = new TextField();
        instrHomeField.setText(cdData.getInstructorHome());
        courseInfoBox.add(new Label(props.getProperty(CSGProp.TITLE)), 0, 4, 1, 1);
        courseInfoBox.add(titleField, 1, 4, 10, 1);
        courseInfoBox.add(new Label(props.getProperty(CSGProp.INSTRNAME)), 0, 5, 1, 1);
        courseInfoBox.add(instrNameField, 1, 5, 10, 1);
        courseInfoBox.add(new Label(props.getProperty(CSGProp.INSTRHOME)), 0, 6, 1, 1);
        courseInfoBox.add(instrHomeField, 1, 6, 10, 1);
        courseInfoBox.add(new Label(props.getProperty(CSGProp.CHANGEEXPORTDIR)), 0, 7, 1, 1);
        exportDir = new Label(cdData.getExportDirPath());
        courseInfoBox.add(exportDir, 1, 7, 9, 1);
        changeExportButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON));
        courseInfoBox.add(changeExportButton, 10, 7, 1, 1);
        
        siteTemplateBox = new VBox();
        siteTemplateHeader = new Label(props.getProperty(CSGProp.SITETEMP_HEADER));
        siteTemplateBox.getChildren().add(siteTemplateHeader);
        siteTemplateBox.getChildren().add(new Label(props.getProperty(CSGProp.SITETEMP_NOTE)));
        siteTemplateDir = new Label(cdData.getSiteTemplateDir());
        siteTemplateBox.getChildren().add(siteTemplateDir);
        selectTemplateButton = new Button(props.getProperty(CSGProp.SELECTTEMPLATEDIR));
        siteTemplateBox.getChildren().add(selectTemplateButton);
        siteTemplateBox.getChildren().add(new Label(props.getProperty(CSGProp.SITEPAGES)));
        

        siteTemplateTable = new TableView();
        siteTemplateTable.setItems(cdData.getSiteTemplates());
        useColumn = new TableColumn(props.getProperty(CSGProp.USE_COL));
        useColumn.setCellValueFactory((CellDataFeatures<SiteTemplate, Boolean> param) -> param.getValue().getUse());
        useColumn.setCellFactory(CheckBoxTableCell.forTableColumn(useColumn));
        titleColumn = new TableColumn(props.getProperty(CSGProp.NAVTITLE_COL));
        titleColumn.setCellValueFactory(new PropertyValueFactory<SiteTemplate, String>("navbarTitle"));
        fileNameColumn = new TableColumn(props.getProperty(CSGProp.FILENAME_COL));
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<SiteTemplate, String>("fileName"));
        scriptColumn = new TableColumn(props.getProperty(CSGProp.SCRIPT_COL));
        scriptColumn.setCellValueFactory(new PropertyValueFactory<SiteTemplate, String>("script"));
        siteTemplateTable.getColumns().addAll(useColumn, titleColumn, fileNameColumn, scriptColumn);
        siteTemplateTable.setMaxWidth(500);
        siteTemplateTable.setMaxHeight(200);
        siteTemplateTable.setEditable(true);
        siteTemplateBox.getChildren().add(siteTemplateTable);
        
        pageStyleBox = new GridPane();
        pageStyleBox.setHgap(10);
        pageStyleBox.setVgap(10);
        pageStyleBox.setPadding(new Insets(5,5,5,5));
        pageStyleHeader = new Label(props.getProperty(CSGProp.PAGESTYLE_HEADER));
        pageStyleBox.add(pageStyleHeader, 0, 0, 2, 2);
        pageStyleBox.add(new Label(props.getProperty(CSGProp.BANNER)), 0, 2, 1, 1);
        
        String imagePath;
        imagePath = FILE_PROTOCOL + PATH_IMAGES + cdData.getBannerImagePath();
        bannerImage = new Image(imagePath, 300, 100 , true, true);
        bannerImageView = new ImageView(bannerImage);
        pageStyleBox.add(bannerImageView, 1, 2, 5, 1);
        changeBannerButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON));
        pageStyleBox.add(changeBannerButton, 6, 2, 1, 1);
        
        
        pageStyleBox.add(new Label(props.getProperty(CSGProp.LEFTFOOT)), 0, 3, 1, 1);
        imagePath = FILE_PROTOCOL + PATH_IMAGES + cdData.getLeftFootPath();
        leftFootImage = new Image(imagePath, 300, 100, true, true);
        leftFootImageView = new ImageView(leftFootImage);
        pageStyleBox.add(leftFootImageView, 1, 3, 5, 1);
        changeLeftFootButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON));
        pageStyleBox.add(changeLeftFootButton, 6, 3, 1, 1);
        
        pageStyleBox.add(new Label(props.getProperty(CSGProp.RIGHTFOOT)), 0, 4, 1, 1);
        imagePath = FILE_PROTOCOL + PATH_IMAGES + cdData.getRightFootPath();
        rightFootImage = new Image(imagePath, 300, 100, true, true);
        rightFootImageView = new ImageView(rightFootImage);
        pageStyleBox.add(rightFootImageView, 1, 4, 5, 1);
        changeRightFootButton = new Button(props.getProperty(CSGProp.CHANGE_BUTTON));
        pageStyleBox.add(changeRightFootButton, 6, 4, 1, 1);
        pageStyleBox.add(new Label(props.getProperty(CSGProp.STYLESHEET)), 0, 5, 1, 1);
        styleSheetBox = new ComboBox();
        styleSheetBox.setValue(cdData.getStyleSheet());
        pageStyleBox.add(styleSheetBox, 1, 5, 1, 1);
        pageStyleBox.add(new Label(props.getProperty(CSGProp.STYLESHEET_NOTE)), 0, 6, 10, 1);
        
        courseInfoBox.setMaxWidth(800);
        siteTemplateBox.setMaxWidth(800);
        pageStyleBox.setMaxWidth(800);
        
        pane.getChildren().add(courseInfoBox);
        pane.getChildren().add(siteTemplateBox);
        pane.getChildren().add(pageStyleBox);
        sPane = new ScrollPane();
        sPane.setContent(pane);
        pane.prefWidthProperty().bind(sPane.widthProperty().multiply(0.98));

        this.setContent(sPane);
        
        styleSheetBox.setItems(cdData.getStylesheets());
        
        changeExportButton.setOnAction(e -> {
            controller.handleChangeExportDir();
        });
        selectTemplateButton.setOnAction(e -> {
            controller.handleChangeTemplateDir();
        });
        changeBannerButton.setOnAction(e -> {
            controller.handleChangeBannerImage();
        });
        changeLeftFootButton.setOnAction(e -> {
            controller.handleChangeLeftFootImage(); 
        });
        changeRightFootButton.setOnAction(e -> {
            controller.handleChangeRightFootImage();
        });
        subjectBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeSubject((String)newValue);
        });
        semesterBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeSemester((String)newValue);
        });
        numberBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeNumber((String)newValue);
        });
        yearBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeYear((String)newValue);
        });
    
        titleField.textProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeTitle((String)newValue);
        });
        instrNameField.textProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeInstrName((String)newValue);
        });
        instrHomeField.textProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeInstrHome((String)newValue);
        });
        styleSheetBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            controller.handleChangeStylesheet((String)newValue);
        });
    }
    
    public GridPane getCourseInfoBox(){
        return courseInfoBox;
    }
    public VBox getPane(){
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
     public Button getChangeBannerButton(){
        return changeBannerButton;
    }
    public Button getLeftFootButton(){
        return changeLeftFootButton;
    }
    public Button getRightFootButton(){
        return changeRightFootButton;
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
    
    public void reloadCDTab(){
        CDData cdData = app.getCSGData().getCDData();
        subjectBox.setValue(cdData.getSubject());
        numberBox.setValue(cdData.getNumber());
        semesterBox.setValue(cdData.getSemester());
        yearBox.setValue(cdData.getYear());
        titleField.setText(cdData.getTitle());
        instrNameField.setText(cdData.getInstructorName());
        instrHomeField.setText(cdData.getInstructorHome());
        siteTemplateDir.setText(cdData.getSiteTemplateDir());
        exportDir.setText(cdData.getExportDirPath());
        styleSheetBox.setValue(cdData.getStyleSheet());
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + cdData.getBannerImagePath();
        bannerImage = new Image(imagePath, 110, 20, true, true);
        bannerImageView.setImage(bannerImage);
        imagePath = FILE_PROTOCOL + PATH_IMAGES + cdData.getLeftFootPath();
        leftFootImage = new Image(imagePath, 110, 20, true, true);
        leftFootImageView.setImage(leftFootImage);
        imagePath = FILE_PROTOCOL + PATH_IMAGES + cdData.getRightFootPath();
        rightFootImage = new Image(imagePath, 110, 20, true, true);
        rightFootImageView.setImage(rightFootImage);
        
        
    }
    
}
