package org.bridge.core.services;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class DiffTest {
    public static void main(String[] args) throws Exception {
        final String ORIGINAL = "G:\\workspace\\dev\\jdifferer\\temp\\text1.txt";
        final String RIVISED = "G:\\workspace\\dev\\jdifferer\\temp\\temp2\\text1.txt";

        //build simple lists of the lines of the two testfiles
        List<String> original = Files.readAllLines(new File(ORIGINAL).toPath());
        List<String> revised = Files.readAllLines(new File(RIVISED).toPath());

        //compute the patch: this is the diffutils part
        Patch<String> patch = DiffUtils.diff(original, revised);

        //simple output the computed patch to console
        for (AbstractDelta<String> delta : patch.getDeltas()) {
            System.out.println(delta);
        }
    }
}
