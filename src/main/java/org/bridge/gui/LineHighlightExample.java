package org.bridge.gui;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class LineHighlightExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Line Highlight Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            RSyntaxTextArea textArea = getrSyntaxTextArea();
            // Add line highlight
            try {
                textArea.addLineHighlight(5, Color.YELLOW);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }

            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.getContentPane().add(scrollPane);

            frame.setVisible(true);
        });
    }

    private static RSyntaxTextArea getrSyntaxTextArea() {
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
        textArea.setText("// Your First Program\n" +
                "\n" +
                "class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\"); \n" +
                "    }\n" +
                "}");
        return textArea;
    }
}