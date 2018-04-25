package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Controller {


    @FXML
    private Text forfall;
    @FXML
    private Text customerName;
    @FXML
    private Text address;
    @FXML
    private Text phoneNumber;
    @FXML
    private Text billingAccount;
    @FXML
    private Text gateNummer;
    @FXML
    private Text postKode;
    @FXML
    private Text byNavn;
    @FXML
    private Text sum;
    @FXML
    private ListView produkterKjopt;
    @FXML
    public TextField invoiceID2;

    //AddresseID som jeg trenger for å få tak gatenevn, husnummer osv.
    private String addressID;
    // Se myCustomer
    private String customerID;



    ObservableList<String> produkter;
    ObservableList<String> produkter2;

    /**
     * Oppretter faktura med data fra DB
     */
    @FXML
    public void opprettFakura(){


        Integer invoiceID = Integer.parseInt(invoiceID2.getText());


        double sumDouble = 0.0;

        String url = "jdbc:sqlite:oblig3Database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();


            String invoiceSql = "SELECT * FROM invoice WHERE invoice_id = " + invoiceID;
            statement.execute(invoiceSql);
            ResultSet resultInvoice = statement.getResultSet();
                forfall.setText(resultInvoice.getString("dato"));
                customerID = resultInvoice.getString("customer");
                resultInvoice.close();


            String customerSql = "SELECT * FROM customer WHERE customer_id =" + customerID;
            statement.execute(customerSql);
            ResultSet resultCustomer = statement.getResultSet();
                customerName.setText(resultCustomer.getString("customer_name"));
                phoneNumber.setText(resultCustomer.getString("phone_number"));
                billingAccount.setText(resultCustomer.getString("billing_account"));
                addressID = resultCustomer.getString("address");
            resultCustomer.close();


            String myCustomerInvoiceItems = "SELECT * FROM invoice_items WHERE invoice = " + invoiceID;
            statement.execute(myCustomerInvoiceItems);
            ResultSet resultInvoiceItem = statement.getResultSet();
            produkter = FXCollections.observableArrayList();
            while(resultInvoiceItem.next()){
                produkter.add(resultInvoiceItem.getString("product"));
            }
//                produkterKjopt.setItems(produkter);
            resultInvoiceItem.close();



            String myCustomerAddress = "SELECT * FROM address WHERE address_id = " + addressID;
            statement.execute(myCustomerAddress);
            ResultSet resultAddress = statement.getResultSet();
                address.setText(resultAddress.getString("street_name"));
                gateNummer.setText(resultAddress.getString("street_number"));
                postKode.setText(resultAddress.getString("postal_code"));
                byNavn.setText(resultAddress.getString("postal_town"));
            resultAddress.close();



            //Fix. Funker ikke som den skal... I think
            String myCustomerProduktListe = "SELECT * FROM product";
            statement.execute(myCustomerProduktListe);
            ResultSet resultProduktListe = statement.getResultSet();
            produkter2 = FXCollections.observableArrayList();
            for(String s : produkter) {
                produkter2.add(resultProduktListe.getString("product_name"));
            }
            produkterKjopt.setItems(produkter2);

            resultProduktListe.close();


            //dfaq im I doing?
            for(String s : produkter) {
                String myCustomerProductItem = "SELECT * FROM product WHERE product_id = " + s;
                statement.execute(myCustomerProductItem);
                ResultSet resultProduct = statement.getResultSet();
                sumDouble += resultProduct.getFloat("price");
                resultProduct.close();
            }
            sum.setText(Double.toString(sumDouble));

            statement.close();
            conn.close();


        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

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
    public ObservableList getInvoiceId() throws SQLException {
        String url = "jdbc:sqlite:oblig3Database.db";
        ObservableList<String> optionList = FXCollections.observableArrayList();

        Connection con = DriverManager.getConnection(url);
        Statement st = con.createStatement();
        String sql = ("SELECT invoice_id FROM invoice");
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()) {
            optionList.add(rs.getString("invoice_id"));
        }

        con.close();
        return optionList;
    }




}


