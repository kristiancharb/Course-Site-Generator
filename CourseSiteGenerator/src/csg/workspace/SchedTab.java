/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.data.ScheduleItem;
import javafx.scene.control.Tab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import javafx.scene.control.DatePicker;
/**
 *
 * @author kristiancharbonneau
 */
public class SchedTab extends Tab{
    CSGApp app;
    CSGController controller;
    CSGWorkspace workspace;
    
    ScrollPane sPane;
    VBox box;
    GridPane boundariesBox;
    VBox itemsBox; 
    HBox itemsHeaderBox;
    GridPane addBox;
    ObservableList<ScheduleItem> items;
    TableView<ScheduleItem> itemsTable;
    TableColumn<ScheduleItem, String> typeCol;
    TableColumn<ScheduleItem, String> dateCol;
    TableColumn<ScheduleItem, String> titleCol;
    TableColumn<ScheduleItem, String> topicCol;
    
    Label boundariesHeader;
    Label itemsHeader;
    Label addHeader;
   
    
    DatePicker startPicker;
    DatePicker endPicker;
    TextField typeField;
    DatePicker datePicker;
    TextField timeField;
    TextField titleField;
    TextField topicField;
    TextField linkField;
    TextField criteriaField;
    
    Button removeButton;
    Button addButton;
    Button clearButton;
    
    public SchedTab(CSGApp app, CSGController controller, CSGWorkspace workspace){
        this.app = app;
        this.controller = controller;
        this.workspace = workspace;
        this.setText("Schedule");
        
        box = new VBox();
        boundariesHeader = new Label("Calender Boundaries");
        startPicker = new DatePicker();
        endPicker = new DatePicker();
        boundariesBox = new GridPane();
        boundariesBox.setHgap(10);
        boundariesBox.setVgap(10);
        boundariesBox.add(boundariesHeader, 0, 0, 2, 2);
        boundariesBox.add(new Label("Starting Monday: "), 0, 4, 1, 1);
        boundariesBox.add(new Label("Ending Friday: "), 10, 4, 1, 1);
        boundariesBox.add(startPicker, 1, 4, 5, 1);
        boundariesBox.add(endPicker, 11, 4, 5, 1);
        box.getChildren().add(boundariesBox);
        
        itemsHeaderBox = new HBox();
        removeButton = new Button("-");
        itemsHeader = new Label("Schedule Items");
        itemsHeaderBox.getChildren().addAll(itemsHeader, removeButton);
        box.getChildren().add(itemsHeaderBox);
        
        items = FXCollections.observableArrayList(
            new ScheduleItem("Holiday", "2/9/17", "Snow Day", ""),
                new ScheduleItem("Lecture", "2/14/17", "Lecture 3", "Event Programming"),
                new ScheduleItem("Holiday", "3/3/17", "Spring Break", ""));
        itemsTable = new TableView();
        itemsTable.setItems(items);
        typeCol = new TableColumn("Type");
        dateCol = new TableColumn("Date");
        titleCol = new TableColumn("Title");
        topicCol = new TableColumn("Topic");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        itemsTable.getColumns().addAll(typeCol, dateCol, titleCol, topicCol);
        itemsTable.setMaxHeight(200);
        
        box.getChildren().add(itemsTable);
        
        addBox = new GridPane();
        addBox.setHgap(10);
        addBox.setVgap(10);
        addBox.setPadding(new Insets(5,5,5,5));
        addHeader = new Label("Add/Edit");
        addBox.add(addHeader, 0, 0, 2, 2);
        typeField = new TextField();
        datePicker = new DatePicker();
        timeField = new TextField();
        titleField = new TextField();
        topicField = new TextField();
        linkField = new TextField();
        criteriaField = new TextField();
        addButton = new Button("Add/Edit");
        clearButton = new Button("Clear");
        addBox.add(new Label("Type: "), 0, 2, 1, 1);
        addBox.add(typeField, 1, 2, 5, 1);
        addBox.add(new Label("Date: "), 0, 3, 1, 1);
        addBox.add(datePicker, 1, 3, 5, 1);
        addBox.add(new Label("Time: "), 0, 4, 1, 1);
        addBox.add(timeField, 1, 4, 5, 1);
        addBox.add(new Label("Title: "), 0, 5, 1, 1);
        addBox.add(titleField, 1, 5, 5, 1);
        addBox.add(new Label("Topic: "), 0, 6, 1, 1);
        addBox.add(topicField, 1, 6, 5, 1);
        addBox.add(new Label("Link: "), 0, 7, 1, 1);
        addBox.add(linkField, 1, 7, 5, 1);
        addBox.add(new Label("Criteria: "), 0, 8, 1, 1);
        addBox.add(criteriaField, 1, 8, 5, 1);
        addBox.add(addButton, 0, 9, 1, 1);
        addBox.add(clearButton, 2, 9, 1, 1);
        box.getChildren().add(addBox);
        
        
        
        sPane = new ScrollPane();
        sPane.setContent(box);
        this.setContent(sPane);
        
    }
    
}
