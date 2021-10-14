package id3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {

    public List<List<String>> read(final String path) throws FileNotFoundException {

        List<List<String>> result = new ArrayList<List<String>>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(path));
            String allData = reader.lines().collect(Collectors.joining("\n"));
            String[] lines = allData.split("\n");
            int i = 0;

            for (String values : lines) {
                result.add(i, new ArrayList<String>());

                for (String value : values.split(",")) {
                    result.get(i).add(value);
                }

                i++;
            }
            reader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        DataReader reader = new DataReader();
        final String path = "D:\\DevEnv\\Workspace\\laba_3_machine_learning\\src\\main\\resources\\dataset.csv";
        List<List<String>> dataSet = reader.read(path);
    }
}
