import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day09_RopeBridge {

    public static void main(String[] args) throws IOException {
        System.out.println(new Day09_RopeBridge().followTheTail(new Scanner(Path.of("resources/bridgeInstructions.txt"))));
    }

    private int followTheTail(Scanner scanner) {
        Set<RopeNode> tailVisitedNodes = new HashSet<>();

        //Curevall 10 nodes. Not just 2;
        RopeNode[] nodes = new RopeNode[10];

        for (int i = 0; i < 10; i++) {
            nodes[i] = new RopeNode(0, 0);
        }

        tailVisitedNodes.add(new RopeNode(nodes[9]));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char direction = line.charAt(0);
            int amount = Integer.parseInt(line.substring(2));

            while (amount > 0) {
                moveHeadNode(nodes[0], direction);
                for (int i = 1; i < nodes.length; i++) {
                    moveTailNode(nodes[i-1], nodes[i]);
                }
                tailVisitedNodes.add(new RopeNode(nodes[9]));

                amount--;
            }
        }

        return tailVisitedNodes.size();
    }

    private void moveHeadNode(RopeNode headNode, char direction) {
        switch(direction) {
            case 'L':
                headNode.x--;
                break;
            case 'R':
                headNode.x++;
                break;
            case 'U':
                headNode.y++;
                break;
            case 'D':
                headNode.y--;
                break;
        }
    }

    private void moveTailNode(RopeNode headNode, RopeNode tailNode) {
        if (!headNodeAndTailNodeAreCloseEnough(headNode, tailNode)) {
            if (headNode.x == tailNode.x) {
                moveTailNodeVertically(headNode, tailNode);
            } else if (headNode.y == tailNode.y) {
                moveTailNodeHorizontally(headNode, tailNode);
            } else { //Off by a diagonal. Do both.
                moveTailNodeHorizontally(headNode, tailNode);
                moveTailNodeVertically(headNode, tailNode);
            }
        }
    }

    private static void moveTailNodeVertically(RopeNode headNode, RopeNode tailNode) {
        if (tailNode.y > headNode.y) {
            tailNode.y--;
        } else {
            tailNode.y++;
        }
    }

    private static void moveTailNodeHorizontally(RopeNode headNode, RopeNode tailNode) {
        if (tailNode.x > headNode.x) {
            tailNode.x--;
        } else {
            tailNode.x++;
        }
    }

    private boolean headNodeAndTailNodeAreCloseEnough(RopeNode headNode, RopeNode tailNode) {
        return Math.abs(headNode.x - tailNode.x) <= 1 && Math.abs(headNode.y - tailNode.y) <= 1;
    }

    public class RopeNode {
        int x;
        int y;

        public RopeNode(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public RopeNode(RopeNode nodeToCopy) {
            this.x = nodeToCopy.x;
            this.y = nodeToCopy.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RopeNode ropeNode = (RopeNode) o;
            return x == ropeNode.x && y == ropeNode.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
