package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("KnapperMedValg.fxml"));
        primaryStage.setTitle("Faktura");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        makeDB();
        launch(args);
    }



    private static Connection makeDB(){

        File database = new File("oblig3Database.db");

        if(!database.exists()) {
            String url = "jdbc:sqlite:oblig3Database.db";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
                Statement statement = conn.createStatement();

                statement.execute("CREATE TABLE IF NOT EXISTS address (\n" +
                        "       address_id INTEGER PRIMARY KEY,\n" +
                        "       street_number TEXT,\n" +
                        "       street_name TEXT,\n" +
                        "       postal_code TEXT,\n" +
                        "       postal_town TEXT,\n" +
                        "       UNIQUE(street_number, street_name, postal_code, postal_town)\n" +
                        ");");

                statement.execute("CREATE TABLE IF NOT EXISTS customer (\n" +
                        "       customer_id INTEGER PRIMARY KEY,\n" +
                        "       customer_name TEXT,\n" +
                        "       address INTEGER,\n" +
                        "       phone_number TEXT,\n" +
                        "       billing_account TEXT,\n" +
                        "       UNIQUE(billing_account),\n" +
                        "       UNIQUE(phone_number),\n" +
                        "       FOREIGN KEY(address) REFERENCES address(address_id)\n" +
                        ");");

                statement.execute("CREATE TABLE IF NOT EXISTS category (\n" +
                        "       category_id INTEGER PRIMARY KEY,\n" +
                        "       category_name TEXT,\n" +
                        "       UNIQUE(category_name)\n" +
                        ");");

                statement.execute("CREATE TABLE IF NOT EXISTS product (\n" +
                        "       product_id INTEGER PRIMARY KEY,\n" +
                        "       product_name TEXT,\n" +
                        "       description TEXT,\n" +
                        "       price REAL,\n" +
                        "       category INTEGER,\n" +
                        "       FOREIGN KEY(category) REFERENCES category(category_id)\n" +
                        ");");


                statement.execute("CREATE TABLE IF NOT EXISTS invoice (\n" +
                        "       invoice_id INTEGER PRIMARY KEY,\n" +
                        "       customer INTEGER,\n" +
                        "       dato TEXT,\n" +
                        "       FOREIGN KEY(customer) REFERENCES customer(customer_id)\n" +
                        ");");

                statement.execute("CREATE TABLE IF NOT EXISTS invoice_items (\n" +
                        "       invoice INTEGER,\n" +
                        "       product INTEGER,\n" +
                        "       FOREIGN KEY (invoice) REFERENCES invoice(invoice_id),\n" +
                        "       FOREIGN KEY (product) REFERENCES product(product_id)\n" +
                        ");");

                statement.execute("INSERT OR IGNORE INTO address (\n" +
                        "       address_id,\n" +
                        "       street_number,\n" +
                        "       street_name,\n" +
                        "       postal_code,\n" +
                        "       postal_town\n" +
                        ") VALUES (\n" +
                        "     1,\n" +
                        "     \"6\",\n" +
                        "     \"Fosswinckelsgate\",\n" +
                        "     \"5007\",\n" +
                        "     \"Bergen\"\n" +
                        ");");

                statement.execute("INSERT OR IGNORE INTO category (\n" +
                        "       category_id,\n" +
                        "       category_name\n" +
                        ") VALUES (\n" +
                        "       1,\n" +
                        "       \"books\"\n" +
                        ");");


                statement.execute("INSERT OR IGNORE INTO product (\n" +
                        "       product_id,\n" +
                        "       product_name,\n" +
                        "       description,\n" +
                        "       price,\n" +
                        "       category\n" +
                        ") VALUES (\n" +
                        "  1,\n" +
                        "  \"Structure and interpretation of computer programs\",\n" +
                        "  \"Book about programming\",\n" +
                        "  499.00,\n" +
                        "  1\n" +
                        ");");


                statement.execute("INSERT OR IGNORE INTO invoice (\n" +
                        "       invoice_id,\n" +
                        "       customer,\n" +
                        "       dato\n" +
                        ") VALUES (\n" +
                        "  1, 1, \"04.04.2018\"\n" +
                        ");");


                statement.execute("INSERT OR IGNORE INTO invoice_items (\n" +
                        "   invoice,\n" +
                        "   product\n" +
                        ") VALUES (\n" +
                        "  1, 1\n" +
                        ");");

                statement.close();
                conn.close();


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return conn;
        }
        return null;
    }




}


