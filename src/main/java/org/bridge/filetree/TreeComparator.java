package org.bridge.filetree;

import java.util.ArrayList;
import java.util.List;


public class TreeComparator {
    public static List<TreeDifference> compareTrees(TreeNode tree1, TreeNode tree2) {
        List<TreeDifference> differences = new ArrayList<>();
        compareNodes(tree1, tree2, differences);
        return differences;
    }

    public static void printDifference(List<TreeDifference> differences) {
        for (TreeDifference diff : differences) {
            TreeNode node1 = diff.getNode1();
            TreeNode node2 = diff.getNode2();
            if (node1 == null) {
                System.out.println("++++++++++");
                FileTreeBuilder.printFileTree(node2, "");
                System.out.println("****************************************************");
            } else {
                System.out.println("----------");
                FileTreeBuilder.printFileTree(node1, "");
                System.out.println("****************************************************");
            }
        }
    }

    private static void compareNodes(TreeNode node1, TreeNode node2, List<TreeDifference> differences) {
        if (node1 == null && node2 == null) {
            return;
        }
        if (node1 == null || node2 == null) {// current node only in A or only in B
            differences.add(new TreeDifference(node1, node2));
            return;
        }
        for (TreeNode childA : node1.getChildren()) {
            boolean foundMatch = false;
            for (TreeNode childB : node2.getChildren()) {
                if (childA.getName().equals(childB.getName())) {
                    foundMatch = true;
                    if ((!childA.getChildren().isEmpty()) && (!childB.getChildren().isEmpty())) { // both are same directory
                        compareNodes(childA, childB, differences);
                    }
                    break;
                }
            }
            if (!foundMatch) {// childA only in A
                differences.add(new TreeDifference(childA, null));
            }
        }
        for (TreeNode childB : node2.getChildren()) {
            boolean foundMatch = false;
            for (TreeNode childA : node1.getChildren()) {
                if (childA.getName().equals(childB.getName())) {
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {// childB only in B
                differences.add(new TreeDifference(null, childB));
            }
        }
    }
}