    package org.bridge.gui;

    import com.formdev.flatlaf.FlatDarculaLaf;

    import javax.swing.*;
    import javax.swing.tree.DefaultMutableTreeNode;
    import javax.swing.tree.DefaultTreeModel;

    public class MainFrame {
        private JPanel rootPanel;
        private JTree fileTree;
        private JTextPane code1TextPane;
        private JTextPane code2TextPane;
        private JPanel mainPanel;
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
        private void createFileTree() {
            // 创建根节点
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

            // 创建子节点
            DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("Child 1");
            DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Child 2");

            // 将子节点添加到根节点
            root.add(child1);
            root.add(child2);

            // 创建树模型并将其设置到JTree
            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            fileTree.setModel(treeModel);
        }

        public static void main(String[] args) {
            FlatDarculaLaf.setup();
            new MainFrame().showGUI();
        }

    }
