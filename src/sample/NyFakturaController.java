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

public class NyFakturaController {

    @FXML
    private TextField fakturaId;
    @FXML
    private TextField kunde;
    @FXML
    private TextField dato;



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
    public void addFaktura(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO invoice(invoice_id, customer, dato) VALUES ('" +
                    fakturaId.getText() + "', '" +  kunde.getText() + "' , '" + dato.getText() + "');");
            statement.close();
            conn.close();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
