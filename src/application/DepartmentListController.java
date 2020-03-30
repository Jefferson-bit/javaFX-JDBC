
package application;

import Model.entities.Department;
import Model.services.DepartmentService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class DepartmentListController implements Initializable {
     
    @FXML
    private DepartmentService service;
    
    @FXML
    private TableView<Department> tableViewDepartment;
    
    @FXML
    private TableColumn<Department, Integer> tableColumnId;
    
    @FXML
    private TableColumn<Department, String> tableColumnName;
    
    @FXML
    private Button btNew;
    
    @FXML
    public void onBtNewAction(){
        System.out.println("OnBtNewAction");
    }
    @FXML
    private ObservableList<Department> obsList;
    
    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializaNode();
    }    
    
    
    private void initializaNode() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }
    public void updateTablwView(){
        if(service == null){
            throw new IllegalStateException("Serbvice was null");
        }        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);

    }
}
