package org.bridge.core.filetree;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {

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


    private static void getFilePathHelper(TreeNode node, List<String> pathList) {
        for (TreeNode child : node.getChildren()) {
            if (child.isFile()) {// if child is a file, stop recursing and add to list
                pathList.add(child.getPath());
                continue;
            }
            getFilePathHelper(child, pathList);
        }
    }

    public static List<TreeNode> getFileNodeList(TreeNode root) {
        List<TreeNode> nodeList = new ArrayList<>();
        if(root.isFile()){
            nodeList.add(root);
        }
        getFileNodeListHelper(root, nodeList);
        return nodeList;
    }

    private static void getFileNodeListHelper(TreeNode node, List<TreeNode> nodeList) {
        for (TreeNode child : node.getChildren()) {
            if (child.isFile()) {
                nodeList.add(child);
                continue;
            }
            getFileNodeListHelper(child, nodeList);
        }
    }
}
