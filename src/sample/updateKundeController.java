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

public class updateKundeController {

    @FXML
    private TextField kundeId;
    @FXML
    private TextField kundeNavn;
    @FXML
    private TextField adresse;
    @FXML
    private TextField telefonNummer;
    @FXML
    private TextField billingKonto;


    @FXML
    public void updateKundeNavn(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute("UPDATE customer SET customer_name = '" +
                    kundeNavn.getText() + "' "+ "WHERE customer_id = " + "'" + kundeId.getText() + "';");
            statement.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void updateKundeAdresse(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute("UPDATE customer SET address = '" +
                    adresse.getText() + "' "+ "WHERE customer_id = " + "'" + kundeId.getText() + "';");
            statement.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void updateKundeTelefon(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute("UPDATE customer SET phone_number = '" +
                    telefonNummer.getText() + "' "+ "WHERE customer_id = " + "'" + kundeId.getText() + "';");
            statement.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void updateKundeBillingKonto(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute("UPDATE customer SET billing_account = '" +
                    billingKonto.getText() + "' "+ "WHERE customer_id = " + "'" + kundeId.getText() + "';");
            statement.close();
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    private ObservableList<ObservableList> data;
    @FXML
    private TableView tableview;

    //CONNECTION DATABASE
    public void buildData(){
        String url = "jdbc:sqlite:oblig3Database.db";
        Connection c ;
        data = FXCollections.observableArrayList();
        try{
            c = DriverManager.getConnection(url);
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from customer";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);

            }


            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();

        }
    }




    public void changeScenButton(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("KnapperMedValg.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
