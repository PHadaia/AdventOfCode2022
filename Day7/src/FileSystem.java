import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystem {
    static Directory root = new Directory("/", null);
    public static void main(String[] args) throws IOException {
        Path inputPath = Path.of("Day7").resolve("input");
        generateFileSystem(inputPath);

        int freeSpace = 70000000 - root.calculateSize(1, 0);
        System.out.println("Total size of all deletable directories is " + Directory.totalDeletableSize);
        Directory.totalDeletableSize = 0;
        root.calculateSize(2, freeSpace);
        System.out.println("There's a free space of " + freeSpace + " on the system");
        System.out.println("The smallest deletable directory is " + Directory.smallestDirectoryToDelete.name + " with a size of " + Directory.totalDeletableSize);
    }

    private static void generateFileSystem(Path inputPath) throws IOException {
        Directory currentDirectory = root;
        Directory tempDirectory = null;
        for(String input : Files.readAllLines(inputPath)) {
            if(input.contains("$ cd")) {
                if(input.contains("..")) {
                    currentDirectory = (currentDirectory.parentDirectory == null) ? root : currentDirectory.parentDirectory;
                } else if(input.contains("/")) {
                    tempDirectory = root;
                } else {
                    tempDirectory = new Directory(input.replaceAll("\\$ cd ", ""), currentDirectory);
                    currentDirectory.subDirectories.add(tempDirectory);
                    currentDirectory = tempDirectory;
                }
            }
            if(input.charAt(0) != '$') {
                if(!input.contains("dir")) {
                    String[] tempData = input.split(" ");
                    tempDirectory = new Directory(tempData[1], currentDirectory);
                    tempDirectory.size = Integer.parseInt(tempData[0]);
                    currentDirectory.addDirectory(tempDirectory);
                }
            }
        }
    }
}