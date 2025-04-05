package generics.recursion;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Recursion {

    public List<String> getParts(Path path) {
        List<String> parts = new ArrayList<>();
        Path current = path;

        while (current != null) {
            Path fileName = current.getFileName();
            if (fileName != null) {
                parts.add(0, fileName.toString());
            }
            current = current.getParent();
        }

        return parts;
    }

    public List<String> getParts2(Path path) {
        printPathElementsRecursively(path);
        return new ArrayList<>();
    }

    private void printPathElementsRecursively(Path path) {
        if (path == null) {
            return;
        }

        printPathElementsRecursively(path.getParent());

        Path fileName = path.getFileName();
        if (fileName != null) {
            System.out.println(fileName);
        }
    }

    public List<String> getParts3(Path path, List<String> parts) {
        if (path == null) {
            return parts;
        }
        getParts3(path.getParent(), parts);
        Path fileName = path.getFileName();
        if (fileName != null) {
            parts.add(fileName.toString());
        }
        return parts;
    }
    public List<String> getParts4(Path path) {
        if (path == null) {
            return new ArrayList<>();
        }
        List<String> parts = getParts4(path.getParent());
        Path fileName = path.getFileName();
        if (fileName != null) {
            parts.add(fileName.toString());
        }
        return parts;
    }
}