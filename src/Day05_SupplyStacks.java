import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Day05_SupplyStacks {
    public static void main(String[] args) throws IOException {
        System.out.println(new Day05_SupplyStacks().findTopBoxes(new Scanner(Path.of("resources/boxes.txt"))));
    }

    private String findTopBoxes(Scanner scanner) {

        List<Stack<Character>> stacks = parseStacks(scanner);
        operateOnStacksAccordingToScanner(stacks, scanner);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stacks.size(); i++) {
            sb.append(stacks.get(i).peek());
        }
        return sb.toString();
    }

    private List<Stack<Character>> parseStacks(Scanner scanner) {
        List<List<Character>> reversedStacks = createReversedStacks(scanner);
        return createStacksFromReversedStacks(reversedStacks);
    }

    private static List<List<Character>> createReversedStacks(Scanner scanner) {
        List<List<Character>> reversedStacks = new ArrayList<List<Character>>();
        String line = scanner.nextLine();
        do {
            parseLineIntoReversedStacks(reversedStacks, line);
        } while (!(line = scanner.nextLine()).isEmpty());
        return reversedStacks;
    }

    private static void parseLineIntoReversedStacks(List<List<Character>> reversedStacks, String line) {
        for (int i = 1; i < line.length(); i+=4) {
            char currentCharacter = line.charAt(i);
            if (currentCharacter != ' ' && !Character.isDigit(currentCharacter)) {
                ensureReversedStacksHasAListForIndex(reversedStacks, i/4);
                List<Character> currentReversedStack = reversedStacks.get(i/4);
                currentReversedStack.add(currentCharacter);
            }
        }
    }

    private static void ensureReversedStacksHasAListForIndex(List<List<Character>> reversedStacks, int index) {
        while (reversedStacks.size() <= index) {
            reversedStacks.add(new ArrayList<>());
        }
    }

    private static List<Stack<Character>> createStacksFromReversedStacks(List<List<Character>> reversedStacks) {
        List<Stack<Character>> stacks = new ArrayList<>();
        for (List<Character> reversedStack : reversedStacks) {
            Stack<Character> stack = new Stack<>();
            for (int i = reversedStack.size()-1; i >= 0; i--) {
                stack.add(reversedStack.get(i));
            }
            stacks.add(stack);
        }
        return stacks;
    }

    private void operateOnStacksAccordingToScanner(List<Stack<Character>> stacks, Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] instruction = line.split(" ");
            int quantity = Integer.parseInt(instruction[1]);
            int from = Integer.parseInt(instruction[3]) - 1;
            int to = Integer.parseInt(instruction[5]) - 1;

            char[] subStack = new char[quantity];
            for (int repeat = 0; repeat < quantity; repeat++) {
                //Curveball: things stay in the same order when we move multiple
//                stacks.get(to).push(stacks.get(from).pop());    (old functionality was basic stack operations)
                Character currentTopOfStack = stacks.get(from).pop();
                //Substack must be reversed so looping through and pushing works.
                //NOTE: I would totally do away with Stacks at this point in a real codebase. Stack is not correct anymore.
                subStack[quantity - repeat - 1] = currentTopOfStack;
            }

            for (Character stackChar : subStack) {
                stacks.get(to).push(stackChar);
            }
        }
    }
}
