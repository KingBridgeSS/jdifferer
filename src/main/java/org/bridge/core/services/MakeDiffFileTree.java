package org.bridge.core.services;

import org.bridge.core.differ.DiffFileNodeInfo;
import org.bridge.core.filetree.*;
import sun.reflect.generics.tree.Tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MakeDiffFileTree {
    public static void setDiffTree(JTree tree, String dirA, String dirB) {
        TreeNode fileTreeA = FileTreeBuilder.buildFileTree(dirA);
        TreeNode fileTreeB = FileTreeBuilder.buildFileTree(dirB);
        DeltaCommons dc = TreeComparator.compareTrees(fileTreeA, fileTreeB);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Diff Tree");
        // update
        DefaultMutableTreeNode diffNode = new DefaultMutableTreeNode("UPDATE");
        List<TreeNode> commonsNodeList = TreeUtils.getFileNodeList(dc.getIntersectionTree());
        List<TreeNode> diffNodeList = new ArrayList<>();
        for (TreeNode node : commonsNodeList) {
            DiffFileNodeInfo infoA = new DiffFileNodeInfo(node.getPath(), dirA);
            DiffFileNodeInfo infoB = new DiffFileNodeInfo(node.getPath(), dirB);
            if (!infoA.fileContentsEquals(infoB)) {
                diffNodeList.add(node);
            }
        }
        diffNodeList.forEach(node -> diffNode.add(new DefaultMutableTreeNode(node)));
        // add and delete
        DefaultMutableTreeNode addNode = new DefaultMutableTreeNode("ADD");
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
        root.add(diffNode);
        root.add(addNode);
        root.add(deleteNode);
        tree.setModel(new DefaultTreeModel(root));
    }
}
