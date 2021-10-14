package id3;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TreeBuilder {

    public static double dataEntropy;
    public static Attribute[] attributes;
    public TreeNode root;
    public Data data;
    private static List<List<String>> rawData;
    public static List<String> header;
    public static List<String> listOfUsedAttributes;




    public static class Values {
        public String condition;
        public int p;
        public int n;
        public double entropy;
    }

    public static class Attribute {

        public String name;
        public List<Values> values;
        public double I;
        public double gain;
    }

    public TreeBuilder(final String path) {

        try {
            listOfUsedAttributes = new ArrayList<String>();
            List<List<String>> rawContainer = new DataReader().read(path);
            this.header = rawContainer.get(0);
            data = new Data(rawContainer.subList(1, rawContainer.size()));
            rawData = data.data;

            dataEntropy = entropy(data.getPositive(YNQ.Yes), data.getNegative(YNQ.No));
            root = getAttributeData(data);

        } catch (Exception exception) {
            System.out.println(exception);
        }


    }

    private static double log2(double N) {
        return (Math.log(N) / Math.log(2));
    }

    public static double entropy(double p, double n) {
        if (p == n)
            return 1;
        else if (p == 0.0 || n == 0.0)
            return 0;
        else
            return -p / (p + n) * log2(p / (p + n)) - n / (p + n) * log2(n / (p + n));
    }

    public static List<List<String>> getSubList(final String field) {
        List<List<String>> result = new ArrayList<List<String>>();
        int lines = rawData.size();
        int attributeCount = rawData.get(0).size();

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < attributeCount; j++) {
                if (rawData.get(i).get(j).equals(field))
                    result.add(rawData.get(i));
            }
        }

        return result;
    }

    public static TreeNode getAttributeData(Data data) {

        //System.out.println(dataEntropy);
        attributes = new Attribute[data.data.get(0).size()];

        //values for attributes
        for (int i = 0; i < data.data.get(0).size(); i++) {

            Attribute attribute = new Attribute();
            attribute.name = header.get(i);
            attributes[i] = attribute;

        }

        for (int j = 0; j < data.data.get(0).size(); j++) {

            Set<String> conditions = new LinkedHashSet<String>();

            for (int i = 0; i < data.data.size(); i++) {
                if (conditions.contains(data.data.get(i).get(j)) || listOfUsedAttributes.contains(data.data.get(i).get(j))) {
                    continue;
                } else conditions.add(data.data.get(i).get(j));
            }

            attributes[j].values = new ArrayList<Values>();

            for (String condition : conditions) {
                Values value = new Values();

                value.condition = condition;
                value.p = data.getPositive(condition);
                value.n = data.getNegative(condition);
                value.entropy = entropy(value.p, value.n);
                attributes[j].values.add(value);
            }

            double I = 0;
            for (Values value : attributes[j].values) {
                I += (value.n + value.p) / (double) (data.getPositive(YNQ.Yes) + data.getNegative(YNQ.No)) * value.entropy;
            }

            attributes[j].I = I;
            attributes[j].gain = dataEntropy - I;


        }

        Attribute result = attributes[0];

        //ToDo: Gosh is it important ?
        //because gain of the +- max
        for (int i = 0; i < data.data.get(0).size() - 1; i++) {
            if (result.gain <= attributes[i].gain)
                result = attributes[i];

        }

        listOfUsedAttributes.add(result.name);
        return new TreeNode(result);
    }

    public void printAttr() {
        for (int i = 0; i < data.data.get(0).size() - 1; i++) {

            if (listOfUsedAttributes.contains(attributes[i].name))
                continue;

            System.out.println(attributes[i].name);

            for (int j = 0; j < attributes[i].values.size(); j++) {
                System.out.println("     " + attributes[i].values.get(j).condition);
                System.out.println("        p= " + attributes[i].values.get(j).p);
                System.out.println("        n= " + attributes[i].values.get(j).n);
                System.out.println("        ent= " + attributes[i].values.get(j).entropy);

            }

            System.out.println("    I: " + attributes[i].I);
            System.out.println("    Gain: " + attributes[i].gain);
        }
    }

    public TreeNode createTree() {
        root.calculateTree();
        root.clearNodes(root, 0);

        return root;

    }

}
