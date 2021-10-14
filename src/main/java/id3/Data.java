package id3;

import java.util.List;

public class Data {

    public List<List<String>> data;
    public int attributeCount;
    public int lines;

    public Data(List<List<String>> data) {
        this.data = data;

        lines = data.size();
        attributeCount = data.get(0).size();
    }

    public static void print(final List<List<String>> data) {

        for (List<String> values : data) {

            for (String value : values) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }


    private int getColumnByConditionValue(String value) {

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {

                if (data.get(i).get(j).equals(value))
                    return j;


            }

        }

        return Integer.MAX_VALUE;

    }

    //has to be remade
    public int getPositive(String value) {

        int result = 0;
        int indexCondition = getColumnByConditionValue(value);

        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).get(indexCondition).equals(value) && data.get(i).get(attributeCount - 1).equals(YNQ.Yes))
                result++;

        }

        return result;
    }

    //has to be remade
    public int getNegative(String value) {
        int result = 0;
        int indexCondition = getColumnByConditionValue(value);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get(indexCondition).equals(value) && data.get(i).get(attributeCount - 1).equals(YNQ.No))
                result++;

        }

        return result;
    }
}
