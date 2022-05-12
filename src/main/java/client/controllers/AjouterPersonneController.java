package client.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Objects;

import client.PersonneInterface;

public class AjouterPersonneController {

    @FXML
    private TextField tfMaladies;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenom;

    @FXML
    private DatePicker tfDate;

    @FXML
    private DatePicker tfRdv;
    
    @FXML
    private CheckBox cbCovid;
    
    @FXML
    void btnOkClicked(ActionEvent event) {
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String maladies = tfMaladies.getText();
        Boolean hasCovid = cbCovid.isSelected();

        LocalDate date = tfDate.getValue();
        LocalDate rdv = tfRdv.getValue();

        if( nom.isBlank() || prenom.isBlank() || date == null || rdv == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("First Name, Last Name, Date Of Birth, Rdv Of Vaccination are all required");
            alert.show();
            return;
        }

        try {   
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            PersonneInterface stub = (PersonneInterface) registry.lookup("admin");
            stub.ajouterPersonne(nom,prenom,date,rdv,hasCovid,maladies);
            Stage primarystage = (Stage) tfMaladies.getScene().getWindow();


            //URL url = new File("src/resources/fxml/Mainscene.fxml").toURI().toURL();
            //Parent root;
            //root = FXMLLoader.load(url);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/client/Mainscene.fxml")));
            Scene scene = new Scene(root);
            primarystage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Client exception" + e.toString());
            alert.show();
        }
        
    }

    @FXML
    void btnGoBackClicked(ActionEvent event) {
        Stage primarystage = (Stage) tfMaladies.getScene().getWindow();

        try {
            //URL url = new File("src/resources/fxml/Mainscene.fxml").toURI().toURL();
            //Parent root;
            //root = FXMLLoader.load(url);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/client/Mainscene.fxml")));
            Scene scene = new Scene(root);
            primarystage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
