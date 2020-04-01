package application;

import Dados.DbIntegrityException;
import Model.services.DepartmentService;
import gui.Alerts;
import gui.DataChangeListener;
import gui.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable, DataChangeListener {

    private DepartmentService service;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;

    @FXML
    private TableColumn<Department, Department> tableColumnRemove;

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
        //colocando nomes nas colunas
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        //programação defensiva, caso o programador esquece de inserir dados no service
        if (service == null) {
            throw new IllegalStateException("Serbvice was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    public void createDialogForm(Department obj, String absoluteName, Stage parentStage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();
            //injetando todas as dependencias 
            DepartmentFormController controller = loader.getController();

            controller.setDepartment(obj);
            controller.setDepartmentService(new DepartmentService());
            controller.subsCribleDateChangeListener(this);
            controller.updateFormData();

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
               ex.printStackTrace();
            Alerts.alertShow("IO exception error", null, ex.getMessage(), Alert.AlertType.ERROR);
        }

    }
    //esse metodo aqui, vai ser responsável por atualizar minha lista
    //temos que inserir a dependencia

    @Override
    public void onDataChenged() {
        updateTableView();
    }

    private void initEditButtons() {

        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(obj, "DepartmentForm.fxml", Utils.currentStagem(event)));

            }
        });
    }

    private void initRemoveButtons() {
        tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnRemove.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }

        });
    }

    private void removeEntity(Department obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to deleted?");
        
        //usando o result,get devido ao optional, ele é um objeto que carrega o outro objeto dentro dele, podendo
        //esse objeto está dentro dele ou não. Então fazemos um teste
        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            } 
            catch (DbIntegrityException e) {
                Alerts.alertShow("Error removing object ", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        }

    }

}
