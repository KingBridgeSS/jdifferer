package org.bridge.core.filetree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String name;
    private List<TreeNode> children;
    private String path;
    private Boolean isFile;

    public TreeNode(String name, String path, Boolean isFile) {
        this.name = name;
        this.children = new ArrayList<>();
        this.path = path;
        this.isFile = isFile;
    }

    public String getName() {
        return name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public String getPath() {
        return path;
    }
    public Boolean isFile() {
        return isFile;
    }
    public void addChild(TreeNode child) {
        children.add(child);
    }
}