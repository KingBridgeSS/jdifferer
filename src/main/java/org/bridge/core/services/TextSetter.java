package org.bridge.core.services;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import org.bridge.core.filetree.TreeNode;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TextSetter {
    public final static Color CHANGE_COLOR = new Color(251, 232, 161);
    public final static Color DELETE_COLOR = new Color(255, 197, 197);
    public final static Color INSERT_COLOR = new Color(184, 234, 184);

    public static void readFileToTextArea(RSyntaxTextArea pane, TreeNode node) {
        Path filePath = Paths.get(node.getFilePath());
        StringBuilder sb = new StringBuilder();
        sb.append(node.getPath() + "\n");
        try {
            sb.append(new String(Files.readAllBytes(filePath)));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (filePath.toString().endsWith(".java")) {
            pane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        } else {
            pane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        }
        pane.setText(sb.toString());
    }

    public static void setDiffTextArea(RSyntaxTextArea pane1, RSyntaxTextArea pane2, TreeNode node, String dirA, String dirB) {
        try {
            Path path1 = Paths.get(dirA, node.getPath());
            Path path2 = Paths.get(dirB, node.getPath());
            pane1.setText(node.getPath() + "\n" + new String(Files.readAllBytes(path1)));
            pane2.setText(node.getPath() + "\n" + new String(Files.readAllBytes(path2)));
            pane1.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
            pane2.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

            Patch<String> patch = getPatchFromFile(path1, path2);
            for (AbstractDelta<String> delta : patch.getDeltas()) {
                switch (delta.getType()) {
                    case CHANGE: {
                        applyDeltaToPane(delta, pane1, pane2, CHANGE_COLOR, CHANGE_COLOR);
                    }
                    case DELETE: {
                        applyDeltaToPane(delta, pane1, pane2, DELETE_COLOR, null);
                    }
                    case INSERT: {
                        applyDeltaToPane(delta, pane1, pane2, null, INSERT_COLOR);
                    }
                    default: {
                        break;
                    }
                }
            }
        } catch (IOException | BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Patch<String> getPatchFromFile(Path path1, Path path2) throws IOException {
        List<String> original = Files.readAllLines(path1);
        List<String> revised = Files.readAllLines(path2);
        Patch<String> patch = DiffUtils.diff(original, revised);
        return patch;
    }

    private static void applyDeltaToPane(AbstractDelta<String> delta, RSyntaxTextArea pane1, RSyntaxTextArea pane2, Color color1, Color color2) throws BadLocationException {
        int sourcePos = delta.getSource().getPosition();
        int sourceLen = delta.getSource().getLines().size();
        int targetPos = delta.getTarget().getPosition();
        int targetLen = delta.getTarget().getLines().size();

        for (int i = sourcePos; i < sourcePos + sourceLen; i++) {
            pane1.addLineHighlight(i + 1, color1);
        }
        for (int i = targetPos; i < targetPos + targetLen; i++) {
            pane2.addLineHighlight(i + 1, color2);
        }
    }


}
