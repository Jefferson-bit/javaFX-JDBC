package application;

import Model.services.DepartmentService;
import gui.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;

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
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStagem(event);
        Department obj = new Department();
        createDialogForm(obj, "DepartmentForm.fxml", parentStage);
    }
    @FXML
    private ObservableList<Department> obsList;

    public void setDepartmentService(DepartmentService service) {
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

    public void updateTablwView() {
        if (service == null) {
            throw new IllegalStateException("Serbvice was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);

    }

    public void createDialogForm(Department obj, String absoluteName, Stage parentStage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();
            
            DepartmentListController controller = loader.getController();
            controller.setDepartmentService(service);
            controller.updateTablwView();
            
            //instanciando um novo stage, para ser um palco na frente do outro
            Stage dialogStage = new Stage();
            //Colocando titulo na janela
            dialogStage.setTitle("Enter Department data");
            //criamos um novo stage, então temos que criar uma nova cena. O elemento raiz sera a variavel do Pane
            dialogStage.setScene(new Scene(pane));
            //usuario não poderá redimensionar a tela
            dialogStage.setResizable(false);
            //Colocando o stage pai da janela
            dialogStage.initOwner(parentStage);
            //metodo indica se a janela vai ser modal, ou terá outro comportamento. Ela ficara travada, impedindo acesso
            //a outra janela
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //Carregando tela do formulario
            dialogStage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(DepartmentListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
