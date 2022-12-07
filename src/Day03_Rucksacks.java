import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day03_Rucksacks {
    public static void main(String[] args) throws IOException {
        System.out.println(new Day03_Rucksacks().calculateCommonItemPriority(new Scanner(Path.of("resources/rucksacks.txt"))));
    }

    private long calculateCommonItemPriority(Scanner scanner) {
        long total = 0;
        while (scanner.hasNextLine()) {
            total += calculatePriorityOfCommonItem(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
        }
        return total;
    }

    private long calculatePriorityOfCommonItem(String rucksack1, String rucksack2, String rucksack3) {
        //Curveball: now find common part of a group of three rucksacks
        Set<Character> thingsInFirstRucksack = new HashSet<>();

        for (int i = 0; i < rucksack1.length(); i++) {
            thingsInFirstRucksack.add(rucksack1.charAt(i));
        }

        Set<Character> thingsInBothFirstAndSecondRucksack = new HashSet<>();
        for (int j = 0; j < rucksack2.length(); j++) {
            if (thingsInFirstRucksack.contains(rucksack2.charAt(j))) {
                thingsInBothFirstAndSecondRucksack.add(rucksack2.charAt(j));
            }
        }

        char charInCommon = '-';

        if (thingsInBothFirstAndSecondRucksack.size() == 1) {
            charInCommon = thingsInBothFirstAndSecondRucksack.iterator().next();
        } else {
            for (int k = 0; k < rucksack3.length(); k++) {
                if (thingsInBothFirstAndSecondRucksack.contains(rucksack3.charAt(k))) {
                    charInCommon = rucksack3.charAt(k);
                    break;
                }
            }
        }
        if (charInCommon == '-') {
            throw new RuntimeException(String.format("No characters in common across the three rucksacks: %s %s %s", rucksack1, rucksack2, rucksack3));
        }

        if (charInCommon >= 'a' && charInCommon <= 'z') {
            return charInCommon-'a'+1;
        } else if (charInCommon >= 'A' && charInCommon <= 'Z') {
            return charInCommon-'A'+27;
        } else {
            throw new RuntimeException("Unexpected character in bagging area: " + charInCommon);
        }
    }
}
