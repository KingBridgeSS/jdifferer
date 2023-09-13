package org.bridge.filetree;

import java.util.List;

public class DeltaCommons {
    private List<TreeDifference> differences;
    private TreeNode intersectionTree;

    public DeltaCommons(List<TreeDifference> differences, TreeNode intersectionTree) {
        this.differences = differences;
        this.intersectionTree = intersectionTree;
    }

    public List<TreeDifference> getDifferences() {
        return differences;
    }

    public TreeNode getIntersectionTree() {
        return intersectionTree;
    }
}
