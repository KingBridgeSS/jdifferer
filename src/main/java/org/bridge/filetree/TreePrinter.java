package org.bridge.filetree;

import java.util.List;

public class TreePrinter {

    public static void printFileTree(TreeNode node, String indent) {
        System.out.println(indent + node.getName());
        List<TreeNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            TreeNode child = children.get(i);
            if (i == children.size() - 1) {
                printFileTree(child, indent + "   ");
            } else {
                printFileTree(child, indent + "│  ");
            }
        }
    }

    public static void printDifference(List<TreeDifference> differences) {
        for (TreeDifference diff : differences) {
            TreeNode node1 = diff.getNode1();
            TreeNode node2 = diff.getNode2();
            if (node1 == null) {
                System.out.println("++++++++++");
                printFileTree(node2, "");
                System.out.println("****************************************************");
            } else {
                System.out.println("----------");
                printFileTree(node1, "");
                System.out.println("****************************************************");
            }
        }
    }

}
