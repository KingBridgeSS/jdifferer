package org.bridge.core.filetree;

import java.util.ArrayList;
import java.util.List;


public class TreeComparator {
    public static DeltaCommons compareTrees(TreeNode tree1, TreeNode tree2) {
        List<TreeDifference> differences = new ArrayList<>();
        TreeNode intersectionTree = new TreeNode("", "", null, false);
        compareNodes(tree1, tree2, differences, intersectionTree);
        return new DeltaCommons(differences, intersectionTree);
    }

    private static void compareNodes(TreeNode node1, TreeNode node2, List<TreeDifference> differences, TreeNode intersectionTree) {
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
                if (childA.getName().equals(childB.getName()) &&
                        (childA.isFile() == childB.isFile()) // one is a file and the other is a directory
                ) {
                    foundMatch = true;
                    if ((!childA.isFile()) && (!childB.isFile())) { // both are same directories
                        TreeNode commonNode = new TreeNode(childA.getName(), childA.getPath(), childA.getFilePath(), false);
                        intersectionTree.addChild(commonNode);
                        compareNodes(childA, childB, differences, commonNode);
                    } else { // both are files
                        TreeNode commonNode = new TreeNode(childA.getName(), childA.getPath(), childA.getFilePath(), true);
                        intersectionTree.addChild(commonNode);
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
                if (childA.getName().equals(childB.getName()) && (childA.isFile() == childB.isFile())) {
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