package org.bridge.core.services;

import org.bridge.core.filetree.*;
import sun.reflect.generics.tree.Tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class MakeDiffFileTree {
    public static void setDiffTree(JTree tree, String dirA, String dirB) {
        TreeNode fileTreeA = FileTreeBuilder.buildFileTree(dirA);
        TreeNode fileTreeB = FileTreeBuilder.buildFileTree(dirB);
        DeltaCommons dc = TreeComparator.compareTrees(fileTreeA, fileTreeB);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Diff Tree");
        // update
        // add
        DefaultMutableTreeNode addNode = new DefaultMutableTreeNode("ADD");
        // delete
        DefaultMutableTreeNode deleteNode = new DefaultMutableTreeNode("DELETE");
        for (TreeDifference td : dc.getDifferences()) {
            if (td.getNode1() == null) {
                List<TreeNode> nodeList = TreeUtils.getFileNodeList(td.getNode2());
                nodeList.forEach(node -> addNode.add(new DefaultMutableTreeNode(node)));
            } else {
                List<TreeNode> nodeList = TreeUtils.getFileNodeList(td.getNode1());
                nodeList.forEach(node -> deleteNode.add(new DefaultMutableTreeNode(node)));
            }
        }
        root.add(addNode);
        root.add(deleteNode);
        tree.setModel(new DefaultTreeModel(root));
    }
}
