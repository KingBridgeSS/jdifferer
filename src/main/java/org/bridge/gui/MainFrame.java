package org.bridge.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.bridge.core.filetree.TreeNode;
import org.bridge.core.services.JarProcessor;
import org.bridge.core.services.MakeDiffFileTree;
import org.bridge.core.services.TextSetter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

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
    private String dirA;
    private String dirB;

    public void showGUI() {
        frame = new JFrame("Jdifferer");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadOtherGUI();
        setDividerLocation();
        frame.setVisible(true);
    }

    void loadJars(File origin, File revised) {
        String[] dirInfo = JarProcessor.process(origin.getAbsolutePath(), revised.getAbsolutePath());
        dirA = dirInfo[0];
        dirB = dirInfo[1];
        createFileTree();
    }

    private void setDividerLocation() {
        mainSplitPane.setResizeWeight(0.15);
        codeSplitPane.setResizeWeight(0.5);
    }

    private void loadOtherGUI() {
        insertTextArea();
        loadMenuBar();
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
        openItem.addActionListener(e -> {
            JDialog dialog = new JarsChoosingDialog(frame, this);
            dialog.setVisible(true);
        });
        JMenuItem exitItem = new JMenuItem("exit");
        exitItem.addActionListener(e -> {
            System.exit(0);
        });
        projectMenu.add(openItem);
        projectMenu.add(exitItem);

        menuBar.add(projectMenu);

        frame.setJMenuBar(menuBar);
    }

    private void createFileTree() {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(mainPanel, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mainSplitPane = new JSplitPane();
        mainPanel.add(mainSplitPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        fileTreeScroll = new JScrollPane();
        mainSplitPane.setLeftComponent(fileTreeScroll);
        fileTree = new JTree();
        fileTree.setEnabled(true);
        fileTree.setRootVisible(true);
        fileTree.setShowsRootHandles(true);
        fileTreeScroll.setViewportView(fileTree);
        codeSplitPane = new JSplitPane();
        mainSplitPane.setRightComponent(codeSplitPane);
        codeScroll1 = new JScrollPane();
        codeSplitPane.setLeftComponent(codeScroll1);
        codeScroll2 = new JScrollPane();
        codeSplitPane.setRightComponent(codeScroll2);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}