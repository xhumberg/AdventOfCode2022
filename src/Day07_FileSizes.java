import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day07_FileSizes {
    public static void main(String[] args) throws IOException {
        System.out.println(new Day07_FileSizes().findDirectoryToDelete(new Scanner(Path.of("resources/fileSizes.txt"))));
    }

    private int findDirectoryToDelete(Scanner scanner) {
        ElfDirectory topLevel = createFileSystemFromScanner(scanner);

        calculateSizeForSubDirectoriesAndUpdateThisDirectory(topLevel);

        int needToDelete = (30_000_000 + topLevel.directorySize) - 70_000_000;

        List<ElfDirectory> allDirectoriesThatCouldBeDeleted = new ArrayList<>();
        addAllDirectoriesAtLeastSize(needToDelete, topLevel, allDirectoriesThatCouldBeDeleted);

        int currentBestDirectorySize = allDirectoriesThatCouldBeDeleted.get(0).directorySize;

        for (ElfDirectory directory : allDirectoriesThatCouldBeDeleted) {
            if (directory.directorySize < currentBestDirectorySize) {
                currentBestDirectorySize = directory.directorySize;
            }
        }

        return currentBestDirectorySize;
    }

    private ElfDirectory createFileSystemFromScanner(Scanner scanner) {
        ElfDirectory topDirectory = new ElfDirectory();
        ElfDirectory currentDirectory = topDirectory;
        currentDirectory.name = "/";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("$ cd")) {
                String directoryNameToFind = line.substring(5);
                if (directoryNameToFind.equals("/")) {
                    //ignore
                } else if (directoryNameToFind.equals("..")) {
                    currentDirectory = currentDirectory.parentDirectory;
                } else {
                    currentDirectory = findSubdirectoryWithName(currentDirectory, directoryNameToFind);
                }
            } else if (line.startsWith("dir")) {
                ElfDirectory newDirectory = new ElfDirectory();
                newDirectory.parentDirectory = currentDirectory;
                newDirectory.name = line.substring(4);
                if (currentDirectory.subDirectories == null) {
                    currentDirectory.subDirectories = new ArrayList<>();
                }
                currentDirectory.subDirectories.add(newDirectory);
            } else if (Character.isDigit(line.charAt(0))) {
                ElfFile newFile = new ElfFile();
                String[] fileDetails = line.split(" ");
                newFile.size = Integer.parseInt(fileDetails[0]);
                newFile.name = fileDetails[1];
                if (currentDirectory.files == null) {
                    currentDirectory.files = new ArrayList<>();
                }
                currentDirectory.files.add(newFile);
            }
        }
        return topDirectory;
    }

    private static ElfDirectory findSubdirectoryWithName(ElfDirectory currentDirectory, String directoryNameToFind) {
        for (ElfDirectory directory : currentDirectory.subDirectories) {
            if (directory.name.equals(directoryNameToFind)) {
                return directory;
            }
        }
        throw new RuntimeException("Cannot find subdirectory with name: " + directoryNameToFind);
    }

    private void calculateSizeForSubDirectoriesAndUpdateThisDirectory(ElfDirectory directory) {
        int total = 0;
        if (directory.subDirectories != null) {
            for (ElfDirectory childDirectory : directory.subDirectories) {
                calculateSizeForSubDirectoriesAndUpdateThisDirectory(childDirectory);
                total+=childDirectory.directorySize;
            }
        }
        if (directory.files != null) {
            for (ElfFile file : directory.files) {
                total+=file.size;
            }
        }

        directory.directorySize = total;
    }

    //This was for part 1. Curveball could not reuse any of this code aside from the general idea
    private int addUpAllSubDirectoriesLessThanSpecifiedSize(int size, ElfDirectory directory) {
        int total = 0;
        if (directory.subDirectories != null) {
            for (ElfDirectory childDirectory : directory.subDirectories) {
                total+=addUpAllSubDirectoriesLessThanSpecifiedSize(size, childDirectory);
            }
        }
        if (directory.directorySize < size) {
            total += directory.directorySize;
        }

        return total;
    }

    private void addAllDirectoriesAtLeastSize(int atLeastThisLarge, ElfDirectory directory, List<ElfDirectory> allDirectoriesThatCouldBeDeleted) {
        if (directory.directorySize >= atLeastThisLarge) {
            allDirectoriesThatCouldBeDeleted.add(directory);
        }

        if (directory.subDirectories != null) {
            for (ElfDirectory childDirectory : directory.subDirectories) {
                addAllDirectoriesAtLeastSize(atLeastThisLarge, childDirectory, allDirectoriesThatCouldBeDeleted);
            }
        }
    }

    private class ElfDirectory {
        String name;
        List<ElfDirectory> subDirectories;
        ElfDirectory parentDirectory;
        List<ElfFile> files;

        int directorySize = -1;

        @Override
        public String toString() {
            return "D: " + name;
        }
    }

    private class ElfFile {
        String name;
        int size;

        @Override
        public String toString() {
            return name;
        }
    }
}
