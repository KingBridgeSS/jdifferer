package org.bridge.core.decompile;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JarProcessor {
    public static void unzipAndDecompile(String jarPath, String destination, Decompiler decompiler) throws IOException {
        unzipJarToDirectory(jarPath, destination);
        decompileAllClassFile(destination, decompiler);
    }

    private static void unzipJarToDirectory(String jarPath, String destination) throws ZipException {
        new net.lingala.zip4j.ZipFile(jarPath).extractAll(destination);
    }

    private static void decompileAllClassFile(String directory, Decompiler decompiler) throws IOException {
        List<Path> pathList = listFiles(Paths.get(directory));
        for (Path path : pathList) {
            File f = path.toFile();
            if (f.isFile() && f.getName().endsWith(".class")) {
                try {
                    String source = decompiler.decompile(f.getPath());
                    String javaPath = f.getPath().replace(".class", ".java");
                    Files.write(Paths.get(javaPath), source.getBytes());
                    Files.delete(Paths.get(f.getPath()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static List<Path> listFiles(Path path) throws IOException {

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;
    }
}
