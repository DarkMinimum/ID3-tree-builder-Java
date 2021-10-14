package com.example.demo.graphics;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

import java.util.List;

public class Ln {

    private Label label;
    private Line line;
    private String text;

    public Ln(String text) {

        label = new Label();
        line = new Line();
        this.text = text;
        label.setText(text);


    }

    public void setStart(double x, double y) {
        line.setStartX(x);
        line.setStartY(y+15);
    }
    public void setEnd(double x, double y) {
        line.setEndX(x);
        line.setEndY(y+15);

        double xL = (line.getEndX() + line.getStartX()) / 2 - text.length() * 2;
        double yL = (line.getEndY() + line.getStartY()) / 2;

        label.setLayoutX(xL);
        label.setLayoutY(yL);
    }

    public List<Node> get() {
        return List.of(line, label);
    }
}
