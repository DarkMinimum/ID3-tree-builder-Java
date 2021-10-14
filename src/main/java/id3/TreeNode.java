package id3;

import com.example.demo.SettingsController;
import com.example.demo.graphics.Elp;
import com.example.demo.graphics.Ln;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    public String label;
    public String connection;
    public List<TreeNode> outputs;
    public boolean settings = false;

    public static Color root = Color.BURLYWOOD;
    public static Color yes = Color.LIGHTGREEN;
    public static Color no = Color.ORANGERED;
    public static Color ordinary = Color.CADETBLUE;

    public TreeNode() {

    }

    public TreeNode(TreeBuilder.Attribute attribute) {
        this.label = attribute.name;
        outputs = new ArrayList<TreeNode>();

        for (int i = 0; i < attribute.values.size(); ++i) {

            TreeNode treeNode = new TreeNode();
            treeNode.connection = attribute.values.get(i).condition;

            if (attribute.values.get(i).entropy == 0.0) {
                if (attribute.values.get(i).p > attribute.values.get(i).n) {
                    treeNode.label = "+";
                } else {
                    treeNode.label = "-";
                }

            } else {
                treeNode.label = "?";
            }

            outputs.add(treeNode);


        }

    }

    public void drawTree(final AnchorPane anchor, double x, double y) {

        Elp root = new Elp(label);
        root.setPos(x, y);


        anchor.getChildren().addAll(root.get());

        if(!settings) {
            if (connection == null) {
                root.setColor(this.root);

            } else if (outputs == null) {
                if (label.equals(YNQ.Yes)) {
                    root.setColor(this.yes);
                } else {
                    root.setColor(this.no);
                }
                return;
            } else {
                root.setColor(ordinary);
            }
        }
        else {
            if (connection == null) {
                root.setColor(SettingsController.colorContainer.root);

            } else if (outputs == null) {
                if (label.equals(YNQ.Yes)) {
                    root.setColor(SettingsController.colorContainer.yes);
                } else {
                    root.setColor(SettingsController.colorContainer.no);
                }
                return;
            } else {
                root.setColor(SettingsController.colorContainer.ordinary);
            }
        }

        //Children array
        double L = 175;
        double angle = 180.0 / ((double) outputs.size() + 1);
        double currentAngle = angle;


        for (TreeNode treeNode : outputs) {

            Ln line = new Ln(treeNode.connection);
            line.setStart(root.getPos().get(0), root.getPos().get(1));
            double xN = Math.cos(Math.toRadians(currentAngle)) * L;
            double yN = Math.sin(Math.toRadians(currentAngle)) * L;
            line.setEnd(root.getPos().get(0) - xN, root.getPos().get(1) + yN);

            anchor.getChildren().addAll(line.get());
            currentAngle += angle;

            treeNode.drawTree(anchor, root.getPos().get(0) - xN, root.getPos().get(1) + yN);


        }
    }

    public void clearNodes(TreeNode root, int i) {

        if (outputs != null) {

            if (this.label.equals(YNQ.Qu) && outputs.size() == 1) {
                outputs.get(0).connection = this.connection;
                root.outputs.set(i, outputs.get(0));
            }

            for (int j = 0; j < outputs.size(); j++) {
                outputs.get(j).clearNodes(this, j);
            }
        }

    }

    public void calculateTree() {

        if (label.equals(YNQ.Qu)) {

            List<List<String>> subList = TreeBuilder.getSubList(connection);
            Data data = new Data(subList);
            TreeBuilder.dataEntropy = TreeBuilder.entropy(data.getPositive(YNQ.Yes), data.getNegative(YNQ.No));
            outputs = new ArrayList<TreeNode>();
            outputs.add(TreeBuilder.getAttributeData(data));

        }

        if (outputs != null) {

            for (int j = 0; j < outputs.size(); j++) {
                outputs.get(j).calculateTree();
            }
        }


    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(label).append(":").append("\n");

        if (outputs != null)
            for (int i = 0; i < outputs.size(); i++) {
                sb.append("\t-").append(outputs.get(i).connection).append(":\n\t\t\t");
                sb.append(outputs.get(i).label).append("\n");
                sb.append(outputs.get(i).toString());
            }

        return sb.toString();
    }
}
