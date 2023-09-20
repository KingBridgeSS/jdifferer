package org.bridge.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class JarsChoosingDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dirTextField1;
    private JButton browseButton1;
    private JButton browseButton2;
    private JTextField dirTextField2;
    private JPanel topPanel;
    private java.util.List<File> filesMessage;
    private Component parent;
    private MainFrame main;

    public JarsChoosingDialog(Component parent, MainFrame main) {
        super((Frame) parent);
        this.parent = parent;
        this.main = main;
        init();
    }

    private void init() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        filesMessage = new ArrayList<>(Arrays.asList(null, null));

        setLocationAndSize();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        addListener(browseButton1, dirTextField1, true);
        addListener(browseButton2, dirTextField2, false);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void setLocationAndSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double widthRatio = 0.5;
        double heightRatio = 0.25;
        int dialogWidth = (int) (screenSize.width * widthRatio);
        int dialogHeight = (int) (screenSize.height * heightRatio);
        setSize(dialogWidth, dialogHeight);
        setLocationRelativeTo(parent);
    }

    private void addListener(JButton button, JTextField textField, Boolean origin) {
        button.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                textField.setText(selectedFile.getAbsolutePath());
                filesMessage.set(origin ? 0 : 1, selectedFile);
            }
        });
    }

    private void onOK() {
        if (filesMessage.get(0) == null || filesMessage.get(1) == null ||
                !Files.isRegularFile(filesMessage.get(0).toPath()) ||
                !Files.isRegularFile(filesMessage.get(1).toPath())
        ) {
            alert("File Invalid.");

        } else {
            main.loadJars(filesMessage.get(0), filesMessage.get(1));
            dispose();
        }


    }
    private void alert(String msg){
        JDialog alertDialog = new JDialog(this, "alert", true);
        JLabel messageLabel = new JLabel(msg);

        JPanel panel = new JPanel();
        panel.add(messageLabel);
        alertDialog.add(panel);
        alertDialog.setLocationRelativeTo(this);
        alertDialog.pack();
        alertDialog.setVisible(true);
    }
    private void onCancel() {
        dispose();
    }

    public List<File> getFilesMessage() {
        return filesMessage;
    }
}
