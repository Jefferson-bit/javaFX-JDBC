package application;

import Dados.DbIntegrityException;
import Model.services.DepartmentService;
import Model.services.SellerService;
import gui.Alerts;
import gui.DataChangeListener;
import gui.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import model.entities.Seller;

public class SellerListController implements Initializable, DataChangeListener {

    private SellerService service;

    @FXML
    private TableView<Seller> tableViewSeller;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, String> tableColumnEmail;

    @FXML
    private TableColumn<Seller, Date> tableColumnBirthDate;

    @FXML
    private TableColumn<Seller, Double> tableColumnBaseSalary;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnRemove;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStagem(event);
        Seller obj = new Seller();
        createDialogForm(obj, "SellerForm.fxml", parentStage);
    }
    @FXML
    private ObservableList<Seller> obsList;

    public void setSellerService(SellerService service) {
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
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        Utils.formatTableColumnDate(tableColumnBirthDate, "dd//MM/yyyy");     
        tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));       
        Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
        
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        //programação defensiva, caso o programador esquece de inserir dados no service
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    public void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();
            //injetando todas as dependencias 
            SellerFormController controller = loader.getController();

            controller.setSeller(obj);
            controller.setServices(new SellerService(), new DepartmentService());
            controller.loadAssociatedObject();
            controller.subsCribleDateChangeListener(this);
            controller.updateFormData();
           
            //instanciando um novo stage, para ser um palco na frente do outro
            Stage dialogStage = new Stage();
            //Colocando titulo na janela
            dialogStage.setTitle("Enter Seller data");
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
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(obj, "SellerForm.fxml", Utils.currentStagem(event)));

            }
        });
    }

    private void initRemoveButtons() {
        tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnRemove.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
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

    private void removeEntity(Seller obj) {
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
            } catch (DbIntegrityException e) {
                Alerts.alertShow("Error removing object ", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        }

    }

}
