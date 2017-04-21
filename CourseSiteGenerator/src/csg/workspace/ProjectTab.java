/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.ProjectData;
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
import properties_manager.PropertiesManager;

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
    
    Label tabHeader;
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
        ProjectData projectData = app.getCSGData().getProjectData();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        box = new VBox();
        teamsBox = new VBox();
        studentsBox = new VBox();
        sPane = new ScrollPane();
        
        tabHeader = new Label(props.getProperty(CSGProp.PROJECTS));
        box.getChildren().add(tabHeader);
        teamsHeaderBox = new HBox();
        teamsHeader = new Label(props.getProperty(CSGProp.TEAMS));
        removeTeamButton = new Button(props.getProperty(CSGProp.REMOVE_BUTTON));
        teamsHeaderBox.getChildren().addAll(teamsHeader, removeTeamButton);
        teamsBox.getChildren().add(teamsHeaderBox);
        
        teamTable = new TableView();
        teams = FXCollections.observableArrayList(
        new Team("Atomic Comics", "552211", "ffffff", "atomiccomic.com"),
                new Team("C4 Comics", "235511", "ffffff", "c4comic.com"));
        teamTable.setItems(projectData.getTeams());
        nameCol = new TableColumn(props.getProperty(CSGProp.NAME_COL));
        colorCol = new TableColumn(props.getProperty(CSGProp.COLOR_COL));
        textColorCol = new TableColumn(props.getProperty(CSGProp.TEXTCOLOR_COL));
        linkCol = new TableColumn(props.getProperty(CSGProp.LINK_COL));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        textColorCol.setCellValueFactory(new PropertyValueFactory<>("textColor"));
        linkCol.setCellValueFactory(new PropertyValueFactory<>("link"));
        teamTable.getColumns().addAll(nameCol, colorCol, textColorCol, linkCol);
        teamsBox.getChildren().add(teamTable);
        teamTable.setMaxHeight(200);
        teamTable.setMinWidth(500);
        teamTable.setMaxWidth(800);
        
        addTeamsBox = new GridPane();
        addTeamsBox.setHgap(10);
        addTeamsBox.setVgap(10);
        addTeamsBox.setPadding(new Insets(5,5,5,5));
        addTeamHeader = new Label(props.getProperty(CSGProp.ADDEDIT));
        addTeamsBox.add(addTeamHeader, 0, 0, 2, 2);
        addTeamsBox.add(new Label(props.getProperty(CSGProp.NAME)), 0, 3, 1, 1);
        nameField = new TextField();
        addTeamsBox.add(nameField, 1, 3, 10, 1);
        colorPicker = new ColorPicker();
        textColorPicker = new ColorPicker();
        addTeamsBox.add(new Label(props.getProperty(CSGProp.COLOR)), 0, 4, 1, 1);
        addTeamsBox.add(colorPicker, 1, 4, 5, 1);
        addTeamsBox.add(new Label(props.getProperty(CSGProp.TEXTCOLOR)), 10, 4, 1, 1);
        addTeamsBox.add(textColorPicker, 11, 4, 5, 1);
        addTeamsBox.add(new Label(props.getProperty(CSGProp.LINK)), 0, 5, 1, 1);
        linkField = new TextField();
        addTeamsBox.add(linkField, 1, 5, 20, 1);
        addTeamButton = new Button(props.getProperty(CSGProp.ADDEDIT_BUTTON));
        clearTeamButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON));
        addTeamsBox.add(addTeamButton, 0, 6, 1, 1);
        addTeamsBox.add(clearTeamButton, 1, 6, 5, 1);
        teamsBox.getChildren().add(addTeamsBox);
        
        studentsHeaderBox = new HBox();
        studentsHeader = new Label(props.getProperty(CSGProp.STUDENTS));
        removeStudentButton = new Button(props.getProperty(CSGProp.REMOVE_BUTTON));
        studentsHeaderBox.getChildren().addAll(studentsHeader, removeStudentButton);
        studentsBox.getChildren().add(studentsHeaderBox);
        
        studentTable = new TableView();
        students = FXCollections.observableArrayList(
        new Student("Joe", "Shmo", "Atomic Comics", "Lead Designer"),
                new Student("Jane", "Doe", "Atomic Comics", "Lead Programmer"),
                new Student("Bob", "Johnson", "C4 Comics", "Lead Designer"));
        studentTable.setItems(projectData.getStudents());
        firstNameCol = new TableColumn(props.getProperty(CSGProp.FIRSTNAME_COL));
        lastNameCol = new TableColumn(props.getProperty(CSGProp.LASTNAME_COL));
        teamCol = new TableColumn(props.getProperty(CSGProp.TEAM_COL));
        roleCol = new TableColumn(props.getProperty(CSGProp.ROLE_COL));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        teamCol.setCellValueFactory(new PropertyValueFactory<>("team"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        studentTable.getColumns().addAll(firstNameCol, lastNameCol, teamCol, roleCol);
        studentTable.setMaxHeight(200);
        studentTable.setMinWidth(500);
        studentTable.setMaxWidth(800);
        studentsBox.getChildren().add(studentTable);
        
        addStudentsBox = new GridPane();
        addStudentsBox.setHgap(10);
        addStudentsBox.setVgap(10);
        addStudentsBox.setPadding(new Insets(5,5,5,5));
        addStudentHeader = new Label(props.getProperty(CSGProp.ADDEDIT));
        addStudentsBox.add(addStudentHeader, 0, 0, 2, 2);
        addStudentsBox.add(new Label(props.getProperty(CSGProp.FIRSTNAME)), 0, 3, 1, 1);
        firstNameField = new TextField();
        addStudentsBox.add(firstNameField, 1, 3, 10, 1);
        addStudentsBox.add(new Label(props.getProperty(CSGProp.LASTNAME)), 0, 4, 1, 1);
        lastNameField = new TextField();
        addStudentsBox.add(lastNameField, 1, 4, 10, 1);
        addStudentsBox.add(new Label(props.getProperty(CSGProp.TEAM)), 0, 5, 1, 1);
        teamComboBox = new ComboBox();
        addStudentsBox.add(teamComboBox, 1, 5, 10, 1);
        addStudentsBox.add(new Label(props.getProperty(CSGProp.ROLE)), 0, 6, 1, 1);
        roleField = new TextField();
        addStudentsBox.add(roleField, 1, 6, 10, 1);
        addStudentButton = new Button(props.getProperty(CSGProp.ADDEDIT_BUTTON));
        clearStudentButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON));
        addStudentsBox.add(addStudentButton, 0, 7, 1, 1);
        addStudentsBox.add(clearStudentButton, 1, 7, 1, 1);
        studentsBox.getChildren().add(addStudentsBox);
        
     
        teamsBox.setMaxWidth(800);
        studentsBox.setMaxWidth(800);
        box.getChildren().addAll(teamsBox, studentsBox);
        sPane.setContent(box);
        box.prefWidthProperty().bind(sPane.widthProperty().multiply(0.98));
        this.setContent(sPane);
        this.setText(props.getProperty(CSGProp.PROJTAB_HEADER));
    }
    
    public ScrollPane getSPane(){
        return sPane;
    }
    public VBox getBox(){
        return box;
    }
    public Label getTabHeader(){
        return tabHeader;
    }
    public VBox getTeamsBox(){
        return teamsBox;
    }
    public VBox getStudentsBox(){
        return studentsBox;
    }
    public HBox getTeamsHeaderBox(){
        return teamsHeaderBox;
    }
    public HBox getStudentsHeaderBox(){
        return studentsHeaderBox;
    }
    public GridPane getAddTeamsBox(){
        return addTeamsBox;
    }
    public  GridPane getAddStudentsBox(){
        return addStudentsBox;
    }
    
   
    public TableView<Team> getTeamTable(){
        return teamTable;
    }
    public TableView<Student> getStudentTable(){
        return studentTable;
    }
    public TableColumn<Team, String> getNameCol(){
        return nameCol;
    }
    public TableColumn<Team, String> getColorCol(){
        return colorCol;
    }
    public TableColumn<Team, String> getTextColorCol(){
        return textColorCol;
    }
    public TableColumn<Team, String> getLinkCol(){
        return linkCol;
    }
    public TableColumn<Student, String> getFirstNameCol(){
        return firstNameCol;
    }
    public TableColumn<Student, String> getLastNameCol(){
        return lastNameCol;
    }
    public TableColumn<Student, String> getTeamCol(){
        return teamCol;
    }
    public TableColumn<Student, String> getRoleCol(){
        return roleCol;
    }
    
    public Label getTeamsHeader(){
        return teamsHeader;
    }
    public Label getAddTeamHeader(){
        return addTeamHeader;
    }
    public Label getStudentsHeader(){
        return studentsHeader;
    }
    public Label getAddStudentHeader(){
        return addStudentHeader;
    }
    
    public Button getRemoveTeamButton(){
        return removeTeamButton;
    }
    public Button getRemoveStudentButton(){
        return removeStudentButton;
    }
    public Button getAddTeamButton(){
        return addTeamButton;
    }
    public Button getAddStudentButton(){
        return addStudentButton;
    }
    public Button getClearTeamButton(){
        return clearTeamButton;
    }
    public Button getClearStudentButton(){
        return clearStudentButton;
    }
    
    public TextField getNameField(){
        return nameField;
    }
    public ColorPicker getColorPicker(){
        return colorPicker;
    }
    public ColorPicker getTextColorPicker(){
        return textColorPicker;
    }
    public TextField getLinkField(){
        return linkField;
    }
    
    public TextField getFirstNameField(){
        return firstNameField;
    }
    public TextField getLastNameField(){
        return lastNameField;
    }
    public ComboBox getTeamComboBox(){
        return teamComboBox;
    }
    public  TextField getRoleField(){
        return roleField;
    }
    
}
