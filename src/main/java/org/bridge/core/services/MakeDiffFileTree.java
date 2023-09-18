package org.bridge.core.services;

import org.bridge.core.filetree.*;

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
                List<String> fileNameList = TreeUtils.getFilePathList(td.getNode2());
                fileNameList.forEach(name -> addNode.add(new DefaultMutableTreeNode(name)));
            } else {
                List<String> fileNameList = TreeUtils.getFilePathList(td.getNode1());
                fileNameList.forEach(name -> deleteNode.add(new DefaultMutableTreeNode(name)));
            }
        }
        root.add(addNode);
        root.add(deleteNode);
        tree.setModel(new DefaultTreeModel(root));
    }
}
