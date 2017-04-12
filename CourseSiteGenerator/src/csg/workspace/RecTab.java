/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.data.Recitation;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

/**
 *
 * @author kristiancharbonneau
 */
public class RecTab extends Tab{
    CSGApp app;
    CSGController controller;
    CSGWorkspace workspace;
    
    ScrollPane sPane;
    VBox box;
    HBox header;
    Label headerLabel;
    GridPane addBox;
    
    ObservableList recitations;
    TableView<Recitation> recTable;
    TableColumn<Recitation, String> sectionCol;
    TableColumn<Recitation, String> instructorCol;
    TableColumn<Recitation, String> timeCol;
    TableColumn<Recitation, String> locationCol;
    TableColumn<Recitation, String> ta1Col;
    TableColumn<Recitation, String> ta2Col;
    
    Button removeButton;
    
    Label addHeader;
    TextField sectionField;
    TextField instructorField;
    TextField timeField;
    TextField locationField;
    ComboBox ta1Box;
    ComboBox ta2Box;
    Button addButton;
    Button clearButton; 
    
    public RecTab(CSGApp app, CSGController controller, CSGWorkspace workspace){
        this.app = app;
        this.controller = controller;
        this.workspace = workspace;
        this.setText("Recitations");
        
        sPane = new ScrollPane();
        box = new VBox();
        header = new HBox();
        headerLabel = new Label("Recitations");
        removeButton = new Button("-");
        header.getChildren().addAll(headerLabel, removeButton);
        box.getChildren().add(header);
        
        recitations = FXCollections.observableArrayList(
                new Recitation("R02", "McKenna", "Wed 3:30pm - 4:23pm", "Old CS 2114",
                "Jane Doe", "Joe Shmo"),
                new Recitation("R05", "Banerjee", "Tues 5:30pm-6:23pm", "Old CS 2114",
                "", "")
        );
        recTable = new TableView();
        recTable.setItems(recitations);
        sectionCol = new TableColumn("Section");
        sectionCol.setCellValueFactory(new PropertyValueFactory<>("section"));
        instructorCol = new TableColumn("Instructor");
        instructorCol.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        timeCol = new TableColumn("Day/Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        locationCol = new TableColumn("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        ta1Col = new TableColumn("TA");
        ta1Col.setCellValueFactory(new PropertyValueFactory<>("ta1"));
        ta2Col = new TableColumn("TA");
        ta2Col.setCellValueFactory(new PropertyValueFactory<>("ta2"));
        recTable.getColumns().addAll(sectionCol, instructorCol, timeCol, locationCol, ta1Col, ta2Col);
        recTable.setMinWidth(600);
        recTable.setMaxWidth(800);
        recTable.setMaxHeight(200);
        box.getChildren().add(recTable);
        
        addBox = new GridPane();
        addBox.setHgap(10);
        addBox.setVgap(10);
        addBox.setPadding(new Insets(5,5,5,5));
        addHeader = new Label("Add/Edit");
        addBox.add(addHeader, 0, 0, 2, 2);
        addBox.add(new Label("Section"), 0, 3, 1, 1);
        sectionField = new TextField();
        addBox.add(sectionField, 1, 3, 15, 1);
        addBox.add(new Label("Instructor"), 0, 4, 1, 1);
        instructorField = new TextField();
        addBox.add(instructorField, 1, 4, 15, 1);
        addBox.add(new Label("Day/Time"), 0, 5, 1, 1);
        timeField = new TextField();
        addBox.add(timeField, 1, 5, 15, 1);
        ta1Box = new ComboBox();
        ta2Box = new ComboBox();
        addBox.add(new Label("Supervising TA"), 0, 6, 1, 1);
        addBox.add(new Label("Supervising TA"), 0, 7, 1, 1);
        addBox.add(ta1Box, 1, 6, 15, 1);
        addBox.add(ta2Box, 1, 7, 15, 1);
        addButton = new Button("Add/Edit");
        clearButton = new Button("Clear");
        addBox.add(addButton, 0, 8, 1, 1);
        addBox.add(clearButton, 1, 8, 1, 1);
        
        addBox.setMaxWidth(800);
        header.setMaxWidth(800);
        
        box.getChildren().add(addBox);
        box.prefWidthProperty().bind(sPane.widthProperty().multiply(0.98));
        box.prefHeightProperty().bind(sPane.heightProperty().multiply(0.98));
        
        sPane.setContent(box);
        this.setContent(sPane);
        
    }
    
    public ScrollPane getSPane(){
        return sPane;
    }
    public VBox getBox(){
        return box;
    }
    public HBox getHeader(){
        return header;
    }
    public Label getHeaderLabel(){
        return headerLabel;
    }
    public GridPane getAddBox(){
        return addBox;
    }
    
    public TableView<Recitation> getRecTable(){
        return recTable;
    }
    public TableColumn<Recitation, String> getSectionCol(){
        return sectionCol;
    }
    public TableColumn<Recitation, String> getInstructorCol(){
        return instructorCol;
    }
    public TableColumn<Recitation, String> getTimeCol(){
        return timeCol;
    }
    public TableColumn<Recitation, String> getLocationCol(){
        return locationCol;
    }
    public TableColumn<Recitation, String> getTa1Col(){
        return ta1Col;
    }
    public TableColumn<Recitation, String> getTa2Col(){
        return ta2Col;
    }
    
    public Button getRemoveButton(){
        return removeButton;
    }
    
    public Label getAddHeader(){
        return addHeader;
    }
    public TextField getSectionField(){
        return sectionField;
    }
    public TextField getInstructorField(){
        return instructorField;
    }
    public TextField getTimeField(){
        return timeField;
    }
    public TextField getLocationField(){
        return locationField;
    }
    public ComboBox ta1Box(){
        return ta1Box;
    }
    public ComboBox ta2Box(){
        return ta2Box;
    }
    public Button getAddButton(){
        return addButton;
    }
    public Button getClearButton(){
        return clearButton;
    } 
    
}
