package org.bridge.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.bridge.core.filetree.TreeNode;
import org.bridge.core.services.MakeDiffFileTree;
import org.bridge.core.services.TextSetter;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.nio.file.Paths;

public class MainFrame {
    private JPanel rootPanel;
    private JTree fileTree;
    private JPanel mainPanel;
    private JScrollPane fileTreeScroll;
    private JTextPane codeTextPane1;
    private JTextPane codeTextPane2;
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
        loadMenuBar();
        createFileTree();
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
        MakeDiffFileTree.setDiffTree(fileTree, "G:\\workspace\\dev\\jdifferer\\A", "G:\\workspace\\dev\\jdifferer\\B");
        fileTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
                    TreeNode treeNode = (TreeNode) selectedNode.getUserObject();
                    try {
                        if (parentNode.getUserObject().equals("ADD")) {
                            TextSetter.readFileToPane(codeTextPane2, Paths.get(treeNode.getFilePath()));
                            codeTextPane1.setText(null);
                        } else {
                            TextSetter.readFileToPane(codeTextPane1, Paths.get(treeNode.getFilePath()));
                            codeTextPane2.setText(null);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new MainFrame().showGUI();
    }
}