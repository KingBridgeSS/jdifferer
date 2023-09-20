package org.bridge.gui;

import com.formdev.flatlaf.FlatLightLaf;
import org.bridge.core.filetree.TreeNode;
import org.bridge.core.services.MakeDiffFileTree;
import org.bridge.core.services.TextSetter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class MainFrame {
    private JPanel rootPanel;
    private JTree fileTree;
    private JPanel mainPanel;
    private JScrollPane fileTreeScroll;
    private RSyntaxTextArea textArea1;
    private RSyntaxTextArea textArea2;
    private JScrollPane codeScroll1;
    private JScrollPane codeScroll2;
    private JSplitPane codeSplitPane;
    private JSplitPane mainSplitPane;
    private JFrame frame;

    public void showGUI() {
        frame = new JFrame("Jdifferer");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadOtherGUI();
        setDividerLocation();
        frame.setVisible(true);
    }

    private void setDividerLocation() {
        mainSplitPane.setResizeWeight(0.15);
        codeSplitPane.setResizeWeight(0.5);
    }

    private void loadOtherGUI() {
        insertTextArea();
        loadMenuBar();
        createFileTree();
    }

    private void insertTextArea() {
        textArea1 = new RSyntaxTextArea();
        textArea2 = new RSyntaxTextArea();
        textArea1.setEditable(false);
        textArea1.setHighlightCurrentLine(false);
        textArea2.setEditable(false);
        textArea2.setHighlightCurrentLine(false);
        textArea1.setBackground(new Color(245, 245, 245));
        textArea2.setBackground(new Color(245, 245, 245));

        codeScroll1.setViewportView(textArea1);
        codeScroll2.setViewportView(textArea2);
    }

    private void loadMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Project
        JMenu projectMenu = new JMenu("Project");
        JMenuItem openItem = new JMenuItem("open");
        JMenuItem exitItem = new JMenuItem("exit");
        projectMenu.add(openItem);
        projectMenu.add(exitItem);

        menuBar.add(projectMenu);

        frame.setJMenuBar(menuBar);
    }

    private void createFileTree() {
        String dirA = "G:\\workspace\\dev\\jdifferer\\temp";
        String dirB = "G:\\workspace\\dev\\jdifferer\\temp\\temp2";
        MakeDiffFileTree.setDiffTree(fileTree, dirA, dirB);
        fileTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
                if (selectedNode != null && selectedNode.getUserObject() instanceof TreeNode) {
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
                    TreeNode treeNode = (TreeNode) selectedNode.getUserObject();
                    if (parentNode.getUserObject().equals("ADD")) {
                        TextSetter.readFileToTextArea(textArea2, treeNode);
                        textArea2.select(0, 0);
                        textArea1.setText(null);
                    } else if (parentNode.getUserObject().equals("DELETE")) {
                        TextSetter.readFileToTextArea(textArea1, treeNode);
                        textArea1.select(0, 0);
                        textArea2.setText(null);
                    } else {
                        TextSetter.setDiffTextArea(textArea1, textArea2, treeNode, dirA, dirB);
                        textArea1.select(0, 0);
                        textArea2.select(0, 0);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
//        FlatLightLaf.setup();
        new MainFrame().showGUI();
    }
}