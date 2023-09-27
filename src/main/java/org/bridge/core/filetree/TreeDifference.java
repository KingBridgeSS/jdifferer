package org.bridge.core.filetree;

public class TreeDifference {

    private TreeNode node1;
    private TreeNode node2;

    public TreeDifference(TreeNode node1, TreeNode node2) {
        if ((node1 == null) == (node2 == null)) {
            throw new IllegalArgumentException("node1 and node2 should have and only have one null value.");
        }
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

