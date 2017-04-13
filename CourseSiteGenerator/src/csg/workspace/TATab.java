/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.data.TeachingAssistant;
import csg.CSGProp;
import csg.data.TAData;
import csg.data.CSGData;
import csg.style.CSGStyle;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author kristiancharbonneau
 */
public class TATab extends Tab {

    CSGApp app;
    CSGController controller;
    CSGWorkspace csgWorkspace;

    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    Button removeButton;
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    TableColumn<TeachingAssistant, Boolean> gradColumn;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    Button addButton;
    Button clearButton;
    TextField emailTextField;
    ComboBox startTimeBox;
    ComboBox endTimeBox;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;

    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;
    
    SplitPane sPane;

    public TATab(CSGApp app, CSGController controller, CSGWorkspace csgWorkspace) {
        this.app = app;
        this.controller = controller;
        this.csgWorkspace = csgWorkspace;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        this.setText(props.getProperty(CSGProp.TATAB_HEADER));
        
   
        
                // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(CSGProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        removeButton = new Button(props.getProperty(CSGProp.REMOVE_BUTTON));
        tasHeaderBox.getChildren().add(tasHeaderLabel);
        tasHeaderBox.getChildren().add(removeButton);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData taData = ((CSGData)app.getDataComponent()).getTAData();
        ObservableList<TeachingAssistant> tableData = taData.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(CSGProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGProp.EMAIL_COLUMN_TEXT.toString());
        String gradColumnText = props.getProperty(CSGProp.UNDERGRAD);
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        gradColumn = new TableColumn(gradColumnText);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        gradColumn.setCellValueFactory((CellDataFeatures<TeachingAssistant, Boolean> param) -> param.getValue().getGrad());
        gradColumn.setCellFactory(CheckBoxTableCell.forTableColumn(gradColumn));
        taTable.getColumns().add(gradColumn);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        taTable.setEditable(true);
        
        
        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(CSGProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(CSGProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(CSGProp.ADD_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField = new TextField();
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        clearButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_TEXT));
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.3));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.3));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);
       

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(CSGProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();
        
        //Combo Boxes
        startTimeBox = new ComboBox();
        endTimeBox = new ComboBox();
        VBox farRightPane = new VBox();
        farRightPane.getChildren().add(new Label(props.getProperty(CSGProp.SET_START_TIME_LABEL)));
        farRightPane.getChildren().add(startTimeBox);
        farRightPane.getChildren().add(new Label(props.getProperty(CSGProp.SET_END_TIME_LABEL)));
        farRightPane.getChildren().add(endTimeBox);
        
        int hour = 1;
        startTimeBox.getItems().add("12:00am");
        endTimeBox.getItems().add("12:00am");
        while(hour < 24){
            if(hour%12 == hour){
                startTimeBox.getItems().add(hour + ":00am");
                endTimeBox.getItems().add(hour + ":00am");
            }else if(hour == 12){
                startTimeBox.getItems().add("12:00pm");
                endTimeBox.getItems().add("12:00pm");
            }else{ 
                startTimeBox.getItems().add((hour%12) + ":00pm");
                endTimeBox.getItems().add((hour%12) + ":00pm");
            }
            hour++;
        }
        
            
        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        sPane = new SplitPane(leftPane, new ScrollPane(rightPane), farRightPane);
        sPane.setDividerPositions(0.35f, 0.9f, 0.35f);
        
       
        
        this.setContent(sPane);
        taTable.prefHeightProperty().bind(sPane.heightProperty().multiply(1.9));
        
        
        
                // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            if(taTable.getSelectionModel().isEmpty())
                controller.handleAddTA();
            else
                controller.handleUpdateTA();
        });
        removeButton.setOnAction(e -> {
            controller.handleDeleteTA();
        });
        taTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.handleSelectedTA();
        });
        clearButton.setOnAction(e -> {
            controller.handleClear();
        });
        taTable.setOnMouseClicked(e -> {
            controller.handleSelectedTA();
        });
        sPane.setOnKeyPressed(e -> {           
            if((e.getCode() == KeyCode.Z) && (e.isControlDown())){
                //jTPS.undoTransaction();
                
            }
            if((e.getCode() == KeyCode.Y) && (e.isControlDown())){
                //jTPS.doTransaction();
                
            }
            if(e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE))
                controller.handleDeleteTA();
        });
        startTimeBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                controller.handleChangeStartTime(t1);
                startTimeBox.setPromptText(buildCellText(taData.getStartHour(), "00"));
            }    
        });
        endTimeBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                controller.handleChangeEndTime(t1);
                endTimeBox.setPromptText(buildCellText(taData.getEndHour(), "00"));
            }    
        }); 
        
        
    }
    
    
    public CSGController getController(){
        return controller;
    }
    
    public Button getRemoveButton(){
        return removeButton;
    }
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }
    
    public SplitPane getSPane(){
        return sPane;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }
    
    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }
    
    public ComboBox getStartTimeBox(){
        return startTimeBox;
    }
    
    public ComboBox getEndTimeBox(){
        return endTimeBox;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }
    
    public Label getTimeCellLabel(String cellKey) {
        return officeHoursGridTimeCellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
        public void resetTATab() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
        //jTPS = new jTPS();
        controller.handleClear();
        
    }
    
    
    public void reloadTATab(TAData taData) {
        reloadOfficeHoursGrid(taData);
        
        
        
    }

    public void reloadOfficeHoursGrid(TAData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();
        startTimeBox.setPromptText(buildCellText(dataComponent.getStartHour(), "00"));
        endTimeBox.setPromptText(buildCellText(dataComponent.getEndHour(), "00"));

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            if(i == 0){
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
                dataComponent.getCellTextProperty(col, row).set("12:00am");
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
                dataComponent.getCellTextProperty(col, row+1).set("12:30am");
            }else{
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
                dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
                dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));
            }

            // END TIME COLUMN
            col++;
            int endHour = i;
            if(i == 0){
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
                dataComponent.getCellTextProperty(col, row).set("12:30am");
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
                dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
                col++;
            }else{
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
                dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
                addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
                dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
                col++;
            }

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
        }
        
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseEntered(e -> {
                int pRow = Integer.parseInt(dataComponent.getRow(p.getId()));
                int pCol = Integer.parseInt(dataComponent.getCol(p.getId()));
                for (Pane pa : officeHoursGridTACellPanes.values()) {
                    int paRow = Integer.parseInt(dataComponent.getRow(pa.getId()));
                    int paCol = Integer.parseInt(dataComponent.getCol(pa.getId()));
                    if(((pCol == paCol)&&(paRow <= pRow)) || 
                           ((pRow == paRow)&&(paCol <= pCol)) )
                        controller.handleRCHigh(pa);
                }
                controller.handleHighlight((Pane) e.getSource());
            });
        }
        
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseExited(e -> {
                int pRow = Integer.parseInt(dataComponent.getRow(p.getId()));
                int pCol = Integer.parseInt(dataComponent.getCol(p.getId()));
                for (Pane pa : officeHoursGridTACellPanes.values()) {
                    int paRow = Integer.parseInt(dataComponent.getRow(pa.getId()));
                    int paCol = Integer.parseInt(dataComponent.getCol(pa.getId()));
                    if(((pCol == paCol)&&(paRow <= pRow)) || 
                           ((pRow == paRow)&&(paCol <= pCol)) )
                        controller.handleRCUnHigh(pa);
                }
                controller.handleUnHighlight((Pane) e.getSource());
            });
        }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        CSGStyle csgStyle = (CSGStyle)app.getStyleComponent();
        csgStyle.initOfficeHoursGridStyle();
        
        
        
        
    }
    
    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }

   
}



