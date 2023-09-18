package org.bridge.core.services;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextSetter {
    public static void readFileToPane(JTextPane pane, Path filePath) throws IOException {
        String text= new String(Files.readAllBytes(filePath));
        pane.setText(text);
    }
}
