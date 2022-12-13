import java.util.LinkedList;
import java.util.List;

public class Directory {
    static int totalDeletableSize = 0;
    static Directory smallestDirectoryToDelete = null;
    String name;
    int size = 0;
    Directory parentDirectory;
    List<Directory> subDirectories = new LinkedList<>();

    public Directory(String name, Directory parentDirectory) {
        this.name = name;
        this.parentDirectory = parentDirectory;
    }

    public void addDirectory(Directory directory) {
        subDirectories.add(directory);
    }

    public int calculateSize(int part, int freeSpace) {
        int totalSize = 0;
        String classification = (subDirectories.isEmpty()) ? "File" : "Directory";
        for(Directory directory : subDirectories) {
            totalSize += directory.calculateSize(part, (part == 1) ? 0 : freeSpace);
        }

        totalSize += size;
        System.out.println(classification + " " + name + " of size " + totalSize);

        if(classification.equals("Directory")) {
            if(part == 1) {
                if(totalSize < 100000) {
                    totalDeletableSize += totalSize;
                }
            } else {
                if(freeSpace + totalSize >= 30000000) {
                    if(smallestDirectoryToDelete == null || totalDeletableSize > totalSize) {
                        totalDeletableSize = totalSize;
                        smallestDirectoryToDelete = this;
                    }
                }
            }
        }
        return totalSize;
    }
}
