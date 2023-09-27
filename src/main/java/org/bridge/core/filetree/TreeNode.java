package org.bridge.core.filetree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String name;
    private List<TreeNode> children;
    private String path;
    private Boolean isFile;
    private String filePath;

    public TreeNode(String name, String path,String filePath, Boolean isFile) {
        this.name = name;
        this.children = new ArrayList<>();
        this.path = path;
        this.isFile = isFile;
        this.filePath=filePath;
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

    public String getFilePath() {
        return filePath;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return name;
    }
}