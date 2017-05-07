/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.SchedData;
import csg.data.ScheduleItem;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
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
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import properties_manager.PropertiesManager;
import java.time.format.DateTimeFormatter;
import javafx.scene.input.KeyCode;

/**
 *
 * @author kristiancharbonneau
 */
public class SchedTab extends Tab {

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

    Label tabHeader;
    Label boundariesHeader;
    Label itemsHeader;
    Label addHeader;

    DatePicker startPicker;
    DatePicker endPicker;
    ComboBox typeBox;
    DatePicker datePicker;
    TextField timeField;
    TextField titleField;
    TextField topicField;
    TextField linkField;
    TextField criteriaField;

    Button removeButton;
    Button addButton;
    Button clearButton;
    
    ChangeMondayListener changeMonListen;
    ChangeFridayListener changeFriListen;

    public SchedTab(CSGApp app, CSGController controller, CSGWorkspace workspace) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        this.app = app;
        this.controller = controller;
        this.workspace = workspace;
        this.setText(props.getProperty(CSGProp.SCHEDTAB_HEADER));
        SchedData schedData = app.getCSGData().getSchedData();

        box = new VBox();
        tabHeader = new Label(props.getProperty(CSGProp.SCHEDULE));
        box.getChildren().add(tabHeader);
        boundariesHeader = new Label(props.getProperty(CSGProp.BOUNDARIES));
        startPicker = new DatePicker();

        startPicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });

        endPicker = new DatePicker();
        
        endPicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        boundariesBox = new GridPane();
        boundariesBox.setHgap(10);
        boundariesBox.setVgap(10);
        boundariesBox.add(boundariesHeader, 0, 0, 2, 2);
        boundariesBox.add(new Label(props.getProperty(CSGProp.STARTINGMON)), 0, 4, 1, 1);
        boundariesBox.add(new Label(props.getProperty(CSGProp.ENDINGFRI)), 10, 4, 1, 1);
        boundariesBox.add(startPicker, 1, 4, 5, 1);
        boundariesBox.add(endPicker, 11, 4, 5, 1);
        box.getChildren().add(boundariesBox);

        itemsBox = new VBox();
        itemsHeaderBox = new HBox();
        removeButton = new Button(props.getProperty(CSGProp.REMOVE_BUTTON));
        itemsHeader = new Label(props.getProperty(CSGProp.SCHEDULEITEMS));
        itemsHeaderBox.getChildren().addAll(itemsHeader, removeButton);
        itemsBox.getChildren().add(itemsHeaderBox);

        items = FXCollections.observableArrayList(
                new ScheduleItem("Holiday", "2/9/17", "Snow Day", ""),
                new ScheduleItem("Lecture", "2/14/17", "Lecture 3", "Event Programming"),
                new ScheduleItem("Holiday", "3/3/17", "Spring Break", ""));
        itemsTable = new TableView();
        itemsTable.setItems(schedData.getItems());
        typeCol = new TableColumn(props.getProperty(CSGProp.TYPE_COL));
        dateCol = new TableColumn(props.getProperty(CSGProp.DATE_COL));
        titleCol = new TableColumn(props.getProperty(CSGProp.TITLE_COL));
        topicCol = new TableColumn(props.getProperty(CSGProp.TOPIC_COL));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        itemsTable.getColumns().addAll(typeCol, dateCol, titleCol, topicCol);
        itemsTable.setMaxHeight(200);

        itemsBox.getChildren().add(itemsTable);

        addBox = new GridPane();
        addBox.setHgap(10);
        addBox.setVgap(10);
        addBox.setPadding(new Insets(5, 5, 5, 5));
        addHeader = new Label(props.getProperty(CSGProp.ADDEDIT));
        addBox.add(addHeader, 0, 0, 2, 2);
        typeBox = new ComboBox();
        datePicker = new DatePicker();
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        timeField = new TextField();
        titleField = new TextField();
        topicField = new TextField();
        linkField = new TextField();
        criteriaField = new TextField();
        addButton = new Button(props.getProperty(CSGProp.ADDEDIT_BUTTON));
        clearButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON));
        addBox.add(new Label(props.getProperty(CSGProp.TYPE)), 0, 2, 1, 1);
        addBox.add(typeBox, 1, 2, 5, 1);
        addBox.add(new Label(props.getProperty(CSGProp.DATE)), 0, 3, 1, 1);
        addBox.add(datePicker, 1, 3, 5, 1);
        addBox.add(new Label(props.getProperty(CSGProp.TIME)), 0, 4, 1, 1);
        addBox.add(timeField, 1, 4, 5, 1);
        addBox.add(new Label(props.getProperty(CSGProp.TITLE)), 0, 5, 1, 1);
        addBox.add(titleField, 1, 5, 5, 1);
        addBox.add(new Label(props.getProperty(CSGProp.TOPIC)), 0, 6, 1, 1);
        addBox.add(topicField, 1, 6, 5, 1);
        addBox.add(new Label(props.getProperty(CSGProp.LINK)), 0, 7, 1, 1);
        addBox.add(linkField, 1, 7, 5, 1);
        addBox.add(new Label(props.getProperty(CSGProp.CRITERIA)), 0, 8, 1, 1);
        addBox.add(criteriaField, 1, 8, 5, 1);
        addBox.add(addButton, 0, 9, 1, 1);
        addBox.add(clearButton, 2, 9, 1, 1);
        itemsBox.getChildren().add(addBox);
        itemsBox.setMaxWidth(800);
        boundariesBox.setMaxWidth(800);
        box.getChildren().add(itemsBox);

        sPane = new ScrollPane();
        sPane.setContent(box);
        box.prefWidthProperty().bind(sPane.widthProperty().multiply(0.98));

        this.setContent(sPane);
        ObservableList types = FXCollections.observableArrayList();
        types.addAll("Holiday", "Lecture", "Reference", "Recitation", "Homework");
        typeBox.setItems(types);
        
        /*
        typeBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue == null){
                datePicker.setDisable(false);
                timeField.setDisable(false);
                titleField.setDisable(false);
                topicField.setDisable(false);
                linkField.setDisable(false);
                criteriaField.setDisable(false);
            }else if(newValue.equals("Holiday")){
                datePicker.setDisable(false);
                timeField.setDisable(true);
                titleField.setDisable(false);
                topicField.setDisable(true);
                linkField.setDisable(false);
                criteriaField.setDisable(true);  
            }else if(newValue.equals("Lecture")){
                datePicker.setDisable(false);
                timeField.setDisable(true);
                titleField.setDisable(false);
                topicField.setDisable(false);
                linkField.setDisable(false);
                criteriaField.setDisable(true); 
            }else if(newValue.equals("Reference")){
                datePicker.setDisable(false);
                timeField.setDisable(true);
                titleField.setDisable(false);
                topicField.setDisable(false);
                linkField.setDisable(false);
                criteriaField.setDisable(true); 
            }else if(newValue.equals("Recitation")){
                datePicker.setDisable(false);
                timeField.setDisable(true);
                titleField.setDisable(false);
                topicField.setDisable(false);
                linkField.setDisable(true);
                criteriaField.setDisable(true); 
            }else{
                datePicker.setDisable(false);
                timeField.setDisable(false);
                titleField.setDisable(false);
                topicField.setDisable(false);
                linkField.setDisable(false);
                criteriaField.setDisable(false);
            }
        }); */
        changeMonListen = new ChangeMondayListener(controller);
        changeFriListen = new ChangeFridayListener(controller);
        startPicker.valueProperty().addListener(changeMonListen);
        endPicker.valueProperty().addListener(changeFriListen);

        addButton.setOnAction(e -> {
            if(itemsTable.getSelectionModel().isEmpty())
                controller.handleAddItem();
            else 
                controller.handleUpdateItem();
        });
        itemsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.handleSelectedItem();
        });
        itemsTable.setOnMouseClicked(e -> {
            controller.handleSelectedItem();
        });
        sPane.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE)) {
                controller.handleDeleteItem();
            }
        });
        removeButton.setOnAction(e -> {
            controller.handleDeleteItem();
        });
        clearButton.setOnAction(e -> {
            controller.handleClearSched();
        });

    }

    public ScrollPane getSPane() {
        return sPane;
    }

    public Label getTabHeader() {
        return tabHeader;
    }

    public VBox getBox() {
        return box;
    }

    public GridPane getBoundariesBox() {
        return boundariesBox;
    }

    public VBox getItemsBox() {
        return itemsBox;
    }

    public HBox getItemsHeaderBox() {
        return itemsHeaderBox;
    }

    public GridPane getAddBox() {
        return addBox;
    }

    public TableView<ScheduleItem> getItemsTable() {
        return itemsTable;
    }

    public TableColumn<ScheduleItem, String> getTypeCol() {
        return typeCol;
    }

    public TableColumn<ScheduleItem, String> getDateCol() {
        return dateCol;
    }

    public TableColumn<ScheduleItem, String> getTitleCol() {
        return titleCol;
    }

    public TableColumn<ScheduleItem, String> getTopicCol() {
        return topicCol;
    }

    public Label getBoundariesHeader() {
        return boundariesHeader;
    }

    public Label getItemsHeader() {
        return itemsHeader;
    }

    public Label getAddHeader() {
        return addHeader;
    }

    public DatePicker getStartPicker() {
        return startPicker;
    }

    public DatePicker getEndPicker() {
        return endPicker;
    }

    public ComboBox getTypeBox() {
        return typeBox;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public TextField getTimeField() {
        return timeField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getTopicField() {
        return topicField;
    }

    public TextField getLinkField() {
        return linkField;
    }

    public TextField getCriteriaField() {
        return criteriaField;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getClearButton() {
        return clearButton;
    }
    public ChangeMondayListener getMonListener(){
        return changeMonListen;
    }
    public ChangeFridayListener getFriListener(){
        return changeFriListen;
    }

    public void reloadSchedTab() {
        SchedData schedData = app.getCSGData().getSchedData();
        String startDateString = schedData.getStartingMon();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        startPicker.valueProperty().removeListener(changeMonListen);
        endPicker.valueProperty().removeListener(changeFriListen);
        if(startDateString != null){
            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            startPicker.setValue(startDate);
        }else{
            startPicker.getEditor().clear();
            startPicker.setValue(null);
        }
        
        String endDateString = schedData.getEndingFri();
        if(endDateString != null){
            LocalDate endDate = LocalDate.parse(endDateString, formatter);
            endPicker.setValue(endDate);
        }
        else{
            endPicker.getEditor().clear();
            endPicker.setValue(null);
        }
        startPicker.valueProperty().addListener(changeMonListen);
        endPicker.valueProperty().addListener(changeFriListen);
    }

}
