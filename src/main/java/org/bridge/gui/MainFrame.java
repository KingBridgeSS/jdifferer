package org.bridge.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.bridge.core.services.MakeDiffFileTree;
import javax.swing.*;

public class MainFrame {
    private JPanel rootPanel;
    private JTree fileTree;
    private JTextPane code1TextPane;
    private JTextPane code2TextPane;
    private JPanel mainPanel;
    private JScrollPane scroll1;
    private JScrollPane scroll2;
    private JScrollPane fileTreeScroll;
    private JPanel codePanel;
    private JFrame frame;

    public void showGUI() {
        frame = new JFrame("Jdifferer");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadOtherGUI();
        frame.pack();
        frame.setVisible(true);
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
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new MainFrame().showGUI();
    }

}
