import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day08_Treetops {
    public static void main(String[] args) throws IOException {
        //Calculated number of trees visible from the outside for part 1.
//        System.out.println(new Day08_Treetops().howManyVisibleTrees(new Scanner(Path.of("resources/treetops.txt"))));
        
        //Curveball: find trees visible from a given tree
        System.out.println(new Day08_Treetops().findMostScenicTree(new Scanner(Path.of("resources/treetops.txt"))));
    }

    private int findMostScenicTree(Scanner scanner) {
        Tree[][] treeMatrix = buildTreeMatrix(scanner);
        int bestScenicScore = 0;
        for (int y = 0; y < treeMatrix.length; y++) {
            for (int x = 0; x < treeMatrix[0].length; x++) {
                int scenicScore = findTreeScenicScore(treeMatrix, x, y);
                if (scenicScore > bestScenicScore) {
                    bestScenicScore = scenicScore;
                }
            }
        }
        return bestScenicScore;
    }

    private int findTreeScenicScore(Tree[][] treeMatrix, int x, int y) {
        if (x == 0 || y == 0 || x == treeMatrix[0].length-1 || y == treeMatrix.length-1) {
            return 0;
        }
        
        Tree currentTree = treeMatrix[y][x];
        
        //Up
        int upScore = 0;
        for (int goingUp = y-1; goingUp >= 0; goingUp--) {
            Tree upTree = treeMatrix[goingUp][x];
            upScore++;
            if (upTree.height >= currentTree.height) {
                 break;
            }
        }
        
        //Right
        int rightScore = 0;
        for (int goingRight = x+1; goingRight < treeMatrix[0].length; goingRight++) {
            Tree rightTree = treeMatrix[y][goingRight];
            rightScore++;
            if (rightTree.height >= currentTree.height) {
                break;
            }
        }
        
        //Down
        int downScore = 0;
        for (int goingDown = y+1; goingDown < treeMatrix.length; goingDown++) {
            Tree DownTree = treeMatrix[goingDown][x];
            downScore++;
            if (DownTree.height >= currentTree.height) {
                break;
            }
        }
        
        //Left
        int leftScore = 0;
        for (int goingLeft = x-1; goingLeft >= 0; goingLeft--) {
            Tree LeftTree = treeMatrix[y][goingLeft];
            leftScore++;
            if (LeftTree.height >= currentTree.height) {
                break;
            }
        }

        return upScore * rightScore * downScore * leftScore;
    }

    private int howManyVisibleTrees(Scanner scanner) {
        Tree[][] treeMatrix = buildTreeMatrix(scanner);
        findVisibleTrees(treeMatrix);
        int total = 0;
        for (Tree[] treeRow : treeMatrix) {
            for (Tree tree : treeRow) {
                if (tree.canBeSeen) {
                    total++;
                }
            }
        }
        return total;
    }

    private Tree[][] buildTreeMatrix(Scanner scanner) {
        List<Tree[]> matrix = new LinkedList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            Tree[] treeRow = new Tree[line.length()];
            for (int i = 0; i < line.length(); i++) {
                treeRow[i] = new Tree(line.charAt(i));
            }
            matrix.add(treeRow);
        }

        return matrix.toArray(new Tree[matrix.size()][]);
    }

    private class Tree {
        int height;
        boolean canBeSeen = false;

        public Tree(char heightCharacter) {
            height = heightCharacter - '0';
        }

        public void setCanBeSeen() {
            canBeSeen = true;
        }

        @Override
        public String toString() {
            return String.valueOf(height);
        }
    }

    private void findVisibleTrees(Tree[][] trees) {
        //From the left
        for (int y = 0; y < trees.length; y++) {
            int currentTallestTreeInThisDirection = -1;
            for (int x = 0; x < trees[0].length; x++) {
                Tree currentTree = trees[y][x];
                if (currentTree.height > currentTallestTreeInThisDirection) {
                    currentTree.setCanBeSeen();
                    currentTallestTreeInThisDirection = currentTree.height;
                }
            }
        }
        //From the right
        for (int y = 0; y < trees.length; y++) {
            int currentTallestTreeInThisDirection = -1;
            for (int x = trees[0].length - 1; x >= 0; x--) {
                Tree currentTree = trees[y][x];
                if (currentTree.height > currentTallestTreeInThisDirection) {
                    currentTree.setCanBeSeen();
                    currentTallestTreeInThisDirection = currentTree.height;
                }
            }
        }

        //From the top
        for (int x = 0; x < trees[0].length; x++) {
            int currentTallestTreeInThisDirection = -1;
            for (int y = 0; y < trees.length; y++) {
                Tree currentTree = trees[y][x];
                if (currentTree.height > currentTallestTreeInThisDirection) {
                    currentTree.setCanBeSeen();
                    currentTallestTreeInThisDirection = currentTree.height;
                }
            }
        }

        //From the bottom
        for (int x = 0; x < trees[0].length; x++) {
            int currentTallestTreeInThisDirection = -1;
            for (int y = trees.length - 1; y >= 0; y--) {
                Tree currentTree = trees[y][x];
                if (currentTree.height > currentTallestTreeInThisDirection) {
                    currentTree.setCanBeSeen();
                    currentTallestTreeInThisDirection = currentTree.height;
                }
            }
        }
    }
}
