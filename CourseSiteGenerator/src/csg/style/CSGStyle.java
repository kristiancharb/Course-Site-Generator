/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import djf.components.AppStyleComponent;
import djf.AppTemplate;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import csg.workspace.CSGWorkspace;
import csg.workspace.TATab;
import csg.data.TeachingAssistant;
import csg.data.SiteTemplate;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.Team;
import csg.data.Recitation;
import csg.CSGApp;
import csg.workspace.CDTab;
import csg.workspace.RecTab;
import csg.workspace.SchedTab;
import csg.workspace.ProjectTab;
import java.util.HashMap;
import javafx.scene.Node;

/**
 *
 * @author kristiancharbonneau
 */
public class CSGStyle extends AppStyleComponent {
    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    public static String CLASS_TAB_PANE = "tab_pane";

    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";

    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";
    public static String CLASS_SUBHEADER_PANE = "subheader_pane";
    public static String CLASS_SUBHEADER_LABEL = "subheader_label";
    public static String CLASS_SCROLL_PANE = "scroll_pane";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_BUTTON = "add_button";
    public static String CLASS_CLEAR_BUTTON = "clear_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;

    public CSGStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        initCSGWorkspaceStyle();
        initTAWorkspaceStyle();
        initCDWorkspaceStyle();
        initRecWorkspaceStyle();
        initSchedWorkspaceStyle();
        initProjectWorkspaceStyle();
    }
    
    private void initCSGWorkspaceStyle(){
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        csgWorkspace.getWorkspace().getStyleClass().add(CLASS_HEADER_PANE);
        csgWorkspace.getTabs().getStyleClass().add(CLASS_TAB_PANE);
    }

    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        TATab taTab = ((CSGWorkspace) app.getWorkspaceComponent()).getTATab();
        taTab.getTAsHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        taTab.getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        taTab.getSPane().getStyleClass().add(CLASS_SCROLL_PANE);

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = taTab.getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        taTab.getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        taTab.getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        taTab.getAddButton().getStyleClass().add(CLASS_ADD_BUTTON);
        taTab.getClearButton().getStyleClass().add(CLASS_CLEAR_BUTTON);

        // RIGHT SIDE - THE HEADER
        taTab.getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        taTab.getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
    }
    
    private void initCDWorkspaceStyle(){
        CDTab cdTab = ((CSGWorkspace) app.getWorkspaceComponent()).getCDTab();
        cdTab.getSPane().getStyleClass().add(CLASS_SCROLL_PANE);
        cdTab.getPane().getStyleClass().add(CLASS_SCROLL_PANE);
        cdTab.getCourseInfoHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        cdTab.getPageStyleHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        cdTab.getSiteTemplateHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        cdTab.getCourseInfoBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        cdTab.getPageStyleBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        cdTab.getSiteTemplateBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        cdTab.getSelectTemplateButton().getStyleClass().add(CLASS_ADD_BUTTON);
        cdTab.getChangeExportButton().getStyleClass().add(CLASS_ADD_BUTTON);
        cdTab.getChangeBannerButton().getStyleClass().add(CLASS_ADD_BUTTON);
        cdTab.getLeftFootButton().getStyleClass().add(CLASS_ADD_BUTTON);
        cdTab.getRightFootButton().getStyleClass().add(CLASS_ADD_BUTTON);
        TableView<SiteTemplate> siteTemplateTable = cdTab.getSiteTemplateTable();
        siteTemplateTable.getStyleClass().add(CLASS_TA_TABLE);
        for(TableColumn tableColumn : siteTemplateTable.getColumns()){
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
    }
    
    private void initRecWorkspaceStyle(){
        RecTab recTab = ((CSGWorkspace) app.getWorkspaceComponent()).getRecTab();
        recTab.getSPane().getStyleClass().add(CLASS_SCROLL_PANE);
        recTab.getBox().getStyleClass().add(CLASS_PLAIN_PANE);
        recTab.getHeader().getStyleClass().add(CLASS_PLAIN_PANE);
        recTab.getAddBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        
        recTab.getHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        recTab.getAddHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        
        recTab.getAddButton().getStyleClass().add(CLASS_ADD_BUTTON);
        recTab.getRemoveButton().getStyleClass().add(CLASS_ADD_BUTTON);
        recTab.getClearButton().getStyleClass().add(CLASS_ADD_BUTTON);
        
        TableView<Recitation> recTable = recTab.getRecTable();
        recTable.getStyleClass().add(CLASS_TA_TABLE);
        for(TableColumn tableColumn : recTable.getColumns()){
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
    }
    
    public void initSchedWorkspaceStyle(){
        SchedTab schedTab = ((CSGWorkspace)app.getWorkspaceComponent()).getSchedTab();
        schedTab.getSPane().getStyleClass().add(CLASS_SCROLL_PANE);
        schedTab.getBox().getStyleClass().add(CLASS_PLAIN_PANE);
        schedTab.getBoundariesBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        schedTab.getItemsHeaderBox().getStyleClass().add(CLASS_PLAIN_PANE);
        schedTab.getItemsBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        schedTab.getAddBox().getStyleClass().add(CLASS_PLAIN_PANE);
        
        schedTab.getTabHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        schedTab.getItemsHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        schedTab.getAddHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);  
        schedTab.getBoundariesHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        
        schedTab.getAddButton().getStyleClass().add(CLASS_ADD_BUTTON);
        schedTab.getClearButton().getStyleClass().add(CLASS_ADD_BUTTON);
        schedTab.getRemoveButton().getStyleClass().add(CLASS_ADD_BUTTON);
        
        TableView<ScheduleItem> itemsTable = schedTab.getItemsTable();
        itemsTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : itemsTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
    }
    
    public void initProjectWorkspaceStyle(){
        ProjectTab projectTab = ((CSGWorkspace)app.getWorkspaceComponent()).getProjectTab();
        projectTab.getSPane().getStyleClass().add(CLASS_SCROLL_PANE);
        projectTab.getBox().getStyleClass().add(CLASS_PLAIN_PANE);
        projectTab.getTeamsBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        projectTab.getStudentsBox().getStyleClass().add(CLASS_SUBHEADER_PANE);
        projectTab.getAddStudentsBox().getStyleClass().add(CLASS_PLAIN_PANE);
        projectTab.getAddTeamsBox().getStyleClass().add(CLASS_PLAIN_PANE);
        projectTab.getStudentsHeaderBox().getStyleClass().add(CLASS_PLAIN_PANE);
        projectTab.getTeamsHeaderBox().getStyleClass().add(CLASS_PLAIN_PANE);
        
        projectTab.getAddStudentHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        projectTab.getAddTeamHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        projectTab.getStudentsHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        projectTab.getTeamsHeader().getStyleClass().add(CLASS_SUBHEADER_LABEL);
        projectTab.getTabHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        
        projectTab.getAddTeamButton().getStyleClass().add(CLASS_ADD_BUTTON);
        projectTab.getAddStudentButton().getStyleClass().add(CLASS_ADD_BUTTON);
        projectTab.getRemoveTeamButton().getStyleClass().add(CLASS_ADD_BUTTON);
        projectTab.getRemoveStudentButton().getStyleClass().add(CLASS_ADD_BUTTON);
        projectTab.getClearTeamButton().getStyleClass().add(CLASS_ADD_BUTTON);
        projectTab.getClearStudentButton().getStyleClass().add(CLASS_ADD_BUTTON);
        
        TableView<Student> studentTable = projectTab.getStudentTable();
        studentTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : studentTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        TableView<Team> teamTable = projectTab.getTeamTable();
        studentTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : teamTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
    }
    
    

    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        TATab taTab = ((CSGWorkspace) app.getWorkspaceComponent()).getTATab();
        taTab.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(taTab.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(taTab.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(taTab.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(taTab.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(taTab.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(taTab.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(taTab.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(taTab.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }

    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node) nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }

}
