package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class NyAdresseController {
    @FXML
    private TextField adresseId;
    @FXML
    private TextField husNummer;
    @FXML
    private TextField gateNavn;
    @FXML
    private TextField postKode;
    @FXML
    private TextField by;





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
    public void addAddress(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO address( address_id,street_number, street_name, postal_code, postal_town) VALUES ('" +
                    adresseId.getText() + "', '" +  husNummer.getText() + "' , '" + gateNavn.getText() + "', '" + postKode.getText()  + "', '" + by.getText() + "');");
            statement.close();
            conn.close();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
