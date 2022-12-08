import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day06_TuningTrouble {
    public static void main(String[] args) throws IOException {
        System.out.println(new Day06_TuningTrouble().firstMarker(new Scanner(Path.of("resources/signal.txt"))));
    }

    private int firstMarker(Scanner scanner) {
        String signal = scanner.nextLine();

        char[] compare = new char[14];
        for (int i = 0; i < compare.length; i++) {
            compare[i] = signal.charAt(i);
        }
        for (int i = compare.length-1; i < signal.length(); i++) {
            compare[i%14] = signal.charAt(i);

            if (allCharactersUnique(compare)) {
                return i+1;
            }
        }

        return -1;
    }

    private boolean allCharactersUnique(char[] compare) {
        Set<Character> charSet = new HashSet<>();

        for (char character : compare) {
            charSet.add(character);
        }

        return charSet.size() == compare.length;
    }
}
