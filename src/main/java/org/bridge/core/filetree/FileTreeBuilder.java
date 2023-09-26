package org.bridge.core.filetree;

import java.io.File;
import java.util.Arrays;


public class FileTreeBuilder {

    public static TreeNode buildFileTree(String directoryPath) {
        File rootDirectory = new File(directoryPath);
        if (!rootDirectory.exists() || !rootDirectory.isDirectory()) {
            return null;
        }

        return buildFileTreeHelper(rootDirectory, "",true);
    }

    private static TreeNode buildFileTreeHelper(File directory, String path, Boolean root) {
        String appendPath = root ? path + "/" : path + "/" + directory.getName();
        TreeNode node = new TreeNode(directory.getName(), appendPath, directory.getPath(), false);

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    TreeNode childNode = buildFileTreeHelper(file, node.getPath(),false);
                    if (childNode != null) {
                        node.addChild(childNode);
                    }
                } else {
                    node.addChild(new TreeNode(file.getName(), appendPath + "/" + file.getName(), file.getPath(), true));
                }
            }
        }

        return node;
    }

}
