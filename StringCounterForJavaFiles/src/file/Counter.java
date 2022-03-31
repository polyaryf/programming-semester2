package file;

import java.io.*;
import java.util.List;

public class Counter {

    String DIR = "/Users/polinom/Repositories";
    FilesManagement management = new FilesManagement(DIR);


    public int countStrings(List<String> files) throws IOException {
        int count = 0;
        for (String filePath : files) {
            count += countStringsInFile(new File(filePath));
        }
        return count;
    }


    public int countStringsInFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        int count = 0;
        while (line != null) {
            if (line.length() != 0) {
                count++;
                line = reader.readLine();
            } else {
                line = reader.readLine();
            }
        }
        return count;
    }


    public void printCount() {
        try {
            System.out.println(countStrings(management.allJavaFiles(new File(DIR))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
