package client.controllers;

import java.io.File;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import client.Personne;
import client.PersonneInterface;

public class MainsceneController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAllowed;

    @FXML
    private TableColumn<?, ?> colRdv;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colIdentifiant;

    @FXML
    private TableColumn<?, ?> colNom;

    @FXML
    private TableColumn<?, ?> colPrenom;

    @FXML
    private TableView<Personne> tfTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRdv.setCellValueFactory(new PropertyValueFactory<>("rdv"));
        colIdentifiant.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        colAllowed.setCellValueFactory(new PropertyValueFactory<>("isAllowed"));

        try {   
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            PersonneInterface stub = (PersonneInterface) registry.lookup("admin");
            ObservableList<Personne> list = FXCollections.observableArrayList(stub.getListDesPersonnes());
            tfTable.getItems().clear();
            tfTable.setItems(list);

            tfTable.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override 
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        Personne pr = tfTable.getSelectionModel().getSelectedItem();
                        String id =  pr.getIdentifiant(); 
                        Boolean hasCovid = pr.getHasCovid();

                        Stage primarystage = (Stage) tfTable.getScene().getWindow();
                        try {
                            String url;
                            if(hasCovid){
                                AjouterPersonneWithCovidController.PersonneId = id; 
                                url = "/client/AjouterPersonneWithCovidscene.fxml";
                            }else{
                                PersonneDetailController.PersonneId = id; 
                                url = "/client/PersonneDetailscene.fxml";
                            }
                            //FXMLLoader loader = new FXMLLoader(url);
                            //Parent root = loader.load();
                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(url)));
                            Scene scene = new Scene(root);
                            primarystage.setScene(scene);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Client exception" + e.toString());
            alert.show();
        }   
    }

    @FXML
    void btnAjouterClicked(ActionEvent event) {
        Stage primarystage = (Stage) tfTable.getScene().getWindow();

        try {
            //URL url = new File("src/resources/fxml/AjouterPersonnescene.fxml").toURI().toURL();
            //Parent root;
            //root = FXMLLoader.load(url);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/client/AjouterPersonnescene.fxml")));
            Scene scene = new Scene(root);
            primarystage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}