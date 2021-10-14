package com.example.demo.graphics;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.List;
import java.util.Vector;

public class Elp {

    private Label label = new Label();
    private Ellipse ellipse = new Ellipse();

    public void setColor(Color color) {
        ellipse.setFill(color);
        ellipse.setStroke(Color.BLACK);
    }


    public Elp(String label_) {

        //has to be rewritten
        this.label.setText(label_);

        ellipse.setFill(Color.BURLYWOOD);
        ellipse.setRadiusX(35);
        ellipse.setRadiusY(15);




    }

    public void setPos(double x, double y) {
        label.setLayoutX(x - label.getText().length() * 3);
        label.setLayoutY(y - 10);
        ellipse.setCenterX(x);
        ellipse.setCenterY(y);

    }

    public Vector<Double> getPos() {
        Vector<Double> result = new Vector<>();
        result.add(ellipse.getCenterX());
        result.add(ellipse.getCenterY());

        return result;
    }

    public List<Node> get() {
        return List.of(ellipse, label);
    }

}
