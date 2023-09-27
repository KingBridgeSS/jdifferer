package org.bridge.core.differ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class DiffFileNodeInfo {
    private String path;
    private String filePath;
    private String directory;
    public DiffFileNodeInfo(String path, String directory) {
        this.path = path;
        this.directory = directory;
        this.filePath = String.valueOf(Paths.get(directory, path));
    }

    public String getFilePath() {
        return filePath;
    }

    public Boolean fileContentsEquals(DiffFileNodeInfo info) {
        try {
            byte[] myFileContent = Files.readAllBytes(Paths.get(filePath));
            byte[] anotherFileContent = Files.readAllBytes(Paths.get(info.getFilePath()));
            return Arrays.equals(myFileContent, anotherFileContent);
        } catch (IOException e) {
            return false;
        }
    }
}
