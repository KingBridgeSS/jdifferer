package org.bridge.gui;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class MainFrame {
    private JPanel rootPanel;
    private JTree tree1;
    private JTextPane code1TextPane;
    private JTextPane code2TextPane;
    private JPanel mainPanel;
    private JFrame frame;

    public void showGUI() {
        FlatDarculaLaf.setup();
        frame = new JFrame("Jdifferer");
        frame.setContentPane(new MainFrame().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        loadOtherGUI();
        frame.setVisible(true);
    }

    private void loadOtherGUI() {
        loadMenuBar();
    }
    private void loadMenuBar(){
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

    public static void main(String[] args) {
        new MainFrame().showGUI();
    }
}
