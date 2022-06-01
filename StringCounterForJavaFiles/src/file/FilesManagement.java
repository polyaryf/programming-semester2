package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FilesManagement {

    private List<String> files = new LinkedList<>();
    private String dir;

    public FilesManagement(String dir) {
        this.files = files;
        this.dir = dir;
    }


    public List<String> allJavaFiles(File dir) throws FileNotFoundException {
        try {
            if (dir.isDirectory()) {
                for (File item : dir.listFiles()) {
                    if (item.isDirectory()) {
                        allJavaFiles(new File(item.getAbsolutePath()));

                    } else {
                        if (item.getName().endsWith(".java")) {
                            this.files.add(item.getAbsolutePath());
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return files;
    }

    public List<String> getFiles() {
        return files;
    }
}




