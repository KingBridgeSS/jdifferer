package org.bridge.filetree;

public class TreeDifference {

    private TreeNode node1;
    private TreeNode node2;

    public TreeDifference(TreeNode node1, TreeNode node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public TreeNode getNode1() {
        return node1;
    }

    public TreeNode getNode2() {
        return node2;
    }
}

