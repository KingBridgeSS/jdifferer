package org.bridge.filetree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String name;
    private List<TreeNode> children;
    private String path;

    public TreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }
}