package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;

public class NyKundeController {

    @FXML
    private TextField kundeId;
    @FXML
    private TextField kundeNavn;
    @FXML
    private TextField kundeAdresse;
    @FXML
    private TextField kundeTelefon;
    @FXML
    private TextField billingKonto;



    /**
     * Change scene to KnapperMedValg.fxml
     */
    public void changeScenButton(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("KnapperMedValg.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void addKunde(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO customer(customer_id, customer_name, address, phone_number, billing_account) VALUES ('" +
                    kundeId.getText() + "', '" +  kundeNavn.getText() + "' , '" + kundeAdresse.getText() + "', '" + kundeTelefon.getText()  + "', '" + billingKonto.getText() + "');");
            statement.close();
            conn.close();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




}
