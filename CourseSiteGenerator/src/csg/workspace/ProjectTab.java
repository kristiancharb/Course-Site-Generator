/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.data.Team;
import csg.data.Student;
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
import javafx.scene.control.ColorPicker;

/**
 *
 * @author kristiancharbonneau
 */
public class ProjectTab extends Tab{
    CSGApp app;
    CSGController controller;
    CSGWorkspace workspace;
    
    ScrollPane sPane;
    VBox box;
    VBox teamsBox;
    VBox studentsBox;
    HBox teamsHeaderBox;
    HBox studentsHeaderBox;
    GridPane addTeamsBox;
    GridPane addStudentsBox;
    
    ObservableList<Team> teams;
    ObservableList<Student> students;
    TableView<Team> teamTable;
    TableView<Student> studentTable;
    TableColumn<Team, String> nameCol;
    TableColumn<Team, String> colorCol;
    TableColumn<Team, String> textColorCol;
    TableColumn<Team, String> linkCol;
    TableColumn<Student, String> firstNameCol;
    TableColumn<Student, String> lastNameCol;
    TableColumn<Student, String> teamCol;
    TableColumn<Student, String> roleCol;
    
    Label teamsHeader;
    Label addTeamHeader;
    Label studentsHeader;
    Label addStudentHeader;
    
    Button removeTeamButton;
    Button removeStudentButton;
    Button addTeamButton;
    Button addStudentButton;
    Button clearTeamButton;
    Button clearStudentButton;
    
    TextField nameField;
    ColorPicker colorPicker;
    ColorPicker textColorPicker;
    TextField linkField;
    
    TextField firstNameField;
    TextField lastNameField;
    ComboBox teamComboBox;
    TextField roleField;
    
   
    
    public ProjectTab(CSGApp app, CSGController controller, CSGWorkspace workspace){
        this.app = app;
        this.controller = controller;
        this.workspace = workspace;
        
        box = new VBox();
        teamsBox = new VBox();
        studentsBox = new VBox();
        sPane = new ScrollPane();
        
        teamsHeaderBox = new HBox();
        teamsHeader = new Label("Teams");
        removeTeamButton = new Button("-");
        teamsHeaderBox.getChildren().addAll(teamsHeader, removeTeamButton);
        teamsBox.getChildren().add(teamsHeaderBox);
        
        teamTable = new TableView();
        teams = FXCollections.observableArrayList(
        new Team("Atomic Comics", "552211", "ffffff", "atomiccomic.com"),
                new Team("C4 Comics", "235511", "ffffff", "c4comic.com"));
        teamTable.setItems(teams);
        nameCol = new TableColumn("Name");
        colorCol = new TableColumn("Color");
        textColorCol = new TableColumn("Text Color");
        linkCol = new TableColumn("Link");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        textColorCol.setCellValueFactory(new PropertyValueFactory<>("textColor"));
        linkCol.setCellValueFactory(new PropertyValueFactory<>("link"));
        teamTable.getColumns().addAll(nameCol, colorCol, textColorCol, linkCol);
        teamsBox.getChildren().add(teamTable);
        teamTable.setMaxHeight(200);
        teamTable.setMinWidth(500);
        
        addTeamsBox = new GridPane();
        addTeamsBox.setHgap(10);
        addTeamsBox.setVgap(10);
        addTeamsBox.setPadding(new Insets(5,5,5,5));
        addTeamHeader = new Label("Add/Edit");
        addTeamsBox.add(addTeamHeader, 0, 0, 2, 2);
        addTeamsBox.add(new Label("Name: "), 0, 3, 1, 1);
        nameField = new TextField();
        addTeamsBox.add(nameField, 1, 3, 10, 1);
        colorPicker = new ColorPicker();
        textColorPicker = new ColorPicker();
        addTeamsBox.add(new Label("Color: "), 0, 4, 1, 1);
        addTeamsBox.add(colorPicker, 1, 4, 5, 1);
        addTeamsBox.add(new Label("Text Color: "), 10, 4, 1, 1);
        addTeamsBox.add(textColorPicker, 11, 4, 5, 1);
        addTeamsBox.add(new Label("Link: "), 0, 5, 1, 1);
        linkField = new TextField();
        addTeamsBox.add(linkField, 1, 5, 20, 1);
        addTeamButton = new Button("Add/Update");
        clearTeamButton = new Button("Clear");
        addTeamsBox.add(addTeamButton, 0, 6, 1, 1);
        addTeamsBox.add(clearTeamButton, 1, 6, 5, 1);
        teamsBox.getChildren().add(addTeamsBox);
        
        studentsHeaderBox = new HBox();
        studentsHeader = new Label("Students");
        removeStudentButton = new Button("-");
        studentsHeaderBox.getChildren().addAll(teamsHeader, removeTeamButton);
        studentsBox.getChildren().add(teamsHeaderBox);
        
        studentTable = new TableView();
        students = FXCollections.observableArrayList(
        new Student("Joe", "Shmo", "Atomic Comics", "Lead Designer"),
                new Student("Jane", "Doe", "Atomic Comics", "Lead Programmer"),
                new Student("Bob", "Johnson", "C4 Comics", "Lead Designer"));
        studentTable.setItems(students);
        firstNameCol = new TableColumn("First Name");
        lastNameCol = new TableColumn("Last Name");
        teamCol = new TableColumn("Team");
        roleCol = new TableColumn("Role");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        teamCol.setCellValueFactory(new PropertyValueFactory<>("team"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        studentTable.getColumns().addAll(firstNameCol, lastNameCol, teamCol, roleCol);
        studentTable.setMaxHeight(200);
        studentTable.setMinWidth(500);
        studentsBox.getChildren().add(studentTable);
        
        addStudentsBox = new GridPane();
        addStudentsBox.setHgap(10);
        addStudentsBox.setVgap(10);
        addStudentsBox.setPadding(new Insets(5,5,5,5));
        addStudentHeader = new Label("Add/Edit");
        addStudentsBox.add(addStudentHeader, 0, 0, 2, 2);
        addStudentsBox.add(new Label("First Name: "), 0, 3, 1, 1);
        firstNameField = new TextField();
        addStudentsBox.add(firstNameField, 1, 3, 10, 1);
        addStudentsBox.add(new Label("Last Name: "), 0, 4, 1, 1);
        lastNameField = new TextField();
        addStudentsBox.add(lastNameField, 1, 4, 10, 1);
        addStudentsBox.add(new Label("Team: "), 0, 5, 1, 1);
        teamComboBox = new ComboBox();
        addStudentsBox.add(teamComboBox, 1, 5, 10, 1);
        addStudentsBox.add(new Label("Role: "), 0, 6, 1, 1);
        roleField = new TextField();
        addStudentsBox.add(roleField, 1, 6, 10, 1);
        addStudentButton = new Button("Add/Update");
        clearStudentButton = new Button("Clear");
        addStudentsBox.add(addStudentButton, 0, 7, 1, 1);
        addStudentsBox.add(clearStudentButton, 1, 7, 1, 1);
        studentsBox.getChildren().add(addStudentsBox);
        
        
        
        
        
        
        box.getChildren().addAll(teamsBox, studentsBox);
        sPane.setContent(box);
        this.setContent(sPane);
        this.setText("Projects");
    }
    
}
