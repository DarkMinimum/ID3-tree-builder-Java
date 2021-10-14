package com.example.demo;

import id3.TreeNode;
import id3.TreeBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController {

    private File url;
    private TreeBuilder builder;
    private List<List<String>> dataRaw;


    @FXML
    private AnchorPane anchor;

    @FXML
    protected void onDragAndDrop(DragEvent event) {


        Dragboard db = event.getDragboard();
        List<File> files = db.getFiles();

        if (files.isEmpty())
            return;

        int index = files.get(0).getPath().lastIndexOf('.');
        String extension = files.get(0).getPath().substring(index + 1);

        if (!extension.equals("csv"))
            return;

        url = files.get(0);
        builder = new TreeBuilder(url.getPath());
        dataRaw = builder.data.data;

        onTable();


    }

    @FXML
    protected void onOpenCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("D:\\DevEnv\\Workspace\\demo\\src\\main\\resources\\datasets"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dataset extension", "*.csv"));


        url = fileChooser.showOpenDialog(anchor.getScene().getWindow());
        if (url == null)
            return;

        builder = new TreeBuilder(url.getPath());
        dataRaw = builder.data.data;


        onTable();
    }

    @FXML
    protected void onHelloButtonClick() {

        if (builder == null)
            return;

        clear();
        TreeNode tree = builder.createTree();
        tree.drawTree(anchor, 400, 100);

    }

    @FXML
    protected void onTable() {

        if (dataRaw == null)
            return;

        clear();
        GridPane table = new GridPane();


        table.setGridLinesVisible(true);

        if (TreeBuilder.header != null)
            for (int i = 0; i < TreeBuilder.header.size(); i++) {
                table.add(new Label(TreeBuilder.header.get(i)), i, 0);
            }


        for (int i = 0; i < dataRaw.size(); i++) {
            for (int j = 0; j < dataRaw.get(i).size(); j++) {
                table.add(new Label(dataRaw.get(i).get(j)), j, i + 1);

            }
        }

        for (Node node : table.getChildren()) {
            table.setMargin(node, new Insets(5, 10, 5, 10));
        }

        table.setLayoutY(45);
        table.setLayoutX(5);

        //System.out.println(table.getRowCount());
        anchor.getChildren().add(table);

    }



    @FXML
    protected void onHelp() throws IOException {

        try {


            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("help.fxml"), HelloApplication.bundle);
            Scene scene = new Scene(fxmlLoader.load(), 630, 400);
            Stage stage = new Stage();

            stage.setTitle(HelloApplication.bundle.getString("window.settings"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);


            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void clear() {
        ObservableList<javafx.scene.Node> children = anchor.getChildren();
        List<javafx.scene.Node> listToRemove = new ArrayList<>();

        for (javafx.scene.Node node : children) {
            if (node instanceof HBox)
                continue;

            listToRemove.add(node);


        }

        children.removeAll(listToRemove);
    }


}