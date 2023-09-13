package org.bridge.filetree;

import java.io.File;
import java.util.List;


public class FileTreeBuilder {

    public static TreeNode buildFileTree(String directoryPath) {
        File rootDirectory = new File(directoryPath);
        if (!rootDirectory.exists() || !rootDirectory.isDirectory()) {
            return null;
        }

        return buildFileTreeHelper(rootDirectory, "");
    }

    private static TreeNode buildFileTreeHelper(File directory, String path) {
        TreeNode node = new TreeNode(directory.getName());
        node.setPath(path + "/" + directory.getName());

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    TreeNode childNode = buildFileTreeHelper(file, node.getPath());
                    if (childNode != null) {
                        node.addChild(childNode);
                    }
                } else {
                    node.addChild(new TreeNode(file.getName()));
                }
            }
        }

        return node;
    }

    public static void main(String[] args) {
        String A = "G:\\workspace\\dev\\jdifferer\\A";
        String B = "G:\\workspace\\dev\\jdifferer\\B";
        TreeNode t1 = buildFileTree(A);
        TreeNode t2 = buildFileTree(B);
        DeltaCommons dc = TreeComparator.compareTrees(t1, t2);
        TreePrinter.printDifference(dc.getDifferences());
        TreePrinter.printFileTree(dc.getIntersectionTree(), "");
    }
}
