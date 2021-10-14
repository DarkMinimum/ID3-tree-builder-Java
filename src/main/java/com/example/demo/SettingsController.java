package com.example.demo;

import id3.TreeNode;
import id3.YNQ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SettingsController {

    public static class colorContainer {
        public static Color root = Color.BURLYWOOD;
        public static Color yes = Color.LIGHTGREEN;
        public static Color no = Color.ORANGERED;
        public static Color ordinary = Color.CADETBLUE;
    }

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private AnchorPane canvas;

    private TreeNode tree;

    @FXML
    private Button closeButton;

    @FXML
    private ColorPicker root;
    @FXML
    private ColorPicker ordinary;
    @FXML
    private ColorPicker first;
    @FXML
    private ColorPicker second;

    @FXML
    private TextField firstClass;
    @FXML
    private TextField secondClass;

    private void getFromCode() {
        root.setValue(TreeNode.root);
        ordinary.setValue(TreeNode.ordinary);
        first.setValue(TreeNode.yes);
        second.setValue(TreeNode.no);

        firstClass.setText(YNQ.Yes);
        secondClass.setText(YNQ.No);
    }
    private void setToCode() {
        TreeNode.root = root.getValue();
        TreeNode.ordinary = ordinary.getValue();
        TreeNode.yes = first.getValue();
        TreeNode.no = second.getValue();

        YNQ.Yes = firstClass.getText();
        YNQ.No = secondClass.getText();

        HelloApplication.lang = comboBox.getValue();
    }
    private void changeColorOfExample() {
        colorContainer.root = root.getValue();
        colorContainer.ordinary = ordinary.getValue();
        colorContainer.yes = first.getValue();
        colorContainer.no = second.getValue();
    }
    private void initTree() {
        tree = new TreeNode();
        tree.label = "Root";
        tree.outputs = new ArrayList<>();
        tree.settings = true;

        TreeNode stick1 = new TreeNode();
        stick1.label = YNQ.Yes;
        stick1.connection = "Likes";
        stick1.settings = true;

        TreeNode stick2 = new TreeNode();
        stick2.label = "Loves";
        stick2.connection = "Don't care";
        stick2.settings = true;

        TreeNode stick2_1 = new TreeNode();
        stick2_1.label = YNQ.Yes;
        stick2_1.connection = "Yes";
        stick2_1.settings = true;

        TreeNode stick2_2 = new TreeNode();
        stick2_2.label = YNQ.No;
        stick2_2.connection = "No";
        stick2_2.settings = true;

        stick2.outputs = new ArrayList<>();
        stick2.outputs.addAll(List.of(stick2_1, stick2_2));


        TreeNode stick3 = new TreeNode();
        stick3.label = YNQ.No;
        stick3.connection = "Dislikes";
        stick3.settings = true;

        tree.outputs.addAll(List.of(stick3, stick2, stick1));


        tree.drawTree(canvas, 160, 0);
        canvas.setScaleX(0.75);
        canvas.setScaleY(0.75);
    }



    @FXML
    public void initialize() {
        getFromCode();
        initTree();
        initLangBox();
    }

    private void initLangBox() {

        comboBox.setItems(FXCollections.observableArrayList(List.of("ru_RU", "en_EN")));
        comboBox.setValue(HelloApplication.lang);
    }


    @FXML
    protected void onSettingsClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void onSave() {
        setToCode();
        onSettingsClose();
    }

    @FXML
    protected void changeColor() {
        changeColorOfExample();
        tree.drawTree(canvas, 160, 0);
    }


}