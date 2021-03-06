/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Model.services.DepartmentService;
import Model.services.SellerService;
import gui.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ViewsController implements Initializable {

    @FXML
    private Button botao;

    @FXML
    public void actionBotao() {

    }

    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        loadView("SellerList.fxml", (SellerListController controller) -> {
            controller.setSellerService(new SellerService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        loadView("DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("About.fxml", x -> {
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //synchronized serve para o metodo não ser interrompido, caso ocorra alguma interrupção ou um comportamento inesperado
    //assim ele não sera interrompido pelo multiThread
    private synchronized <T> void loadView(String absoluteName, Consumer<T> InitializingAction) {
        try {
            //pegando a Tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();

            //
            Scene mainScene = Main.getMainScene();

            //pegando a raiz que é o scrollpane e o content, da views.fxml, 
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            //pegando os filhos do VBOX, pegando o primeiro filho da Vbox
            Node mainMenu = mainVBox.getChildren().get(0);

            //limpand todos os filhos do mainVBOX
            mainVBox.getChildren().clear();

            //adicionando o mainMenu
            mainVBox.getChildren().add(mainMenu);

            //adicionando uma coleção filhos do newVbox
            mainVBox.getChildren().addAll(newVbox.getChildren());
            T controller = loader.getController();
            InitializingAction.accept(controller);

        } catch (IOException ex) {
            Alerts.alertShow("IO Exception", "Error Load view", ex.getMessage(), Alert.AlertType.ERROR);
        }

    }

}
