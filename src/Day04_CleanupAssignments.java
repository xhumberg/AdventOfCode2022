import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Day04_CleanupAssignments {
    public static void main(String[] args) throws IOException {
        System.out.println(new Day04_CleanupAssignments().calculateTotalOverlappingCleanups(new Scanner(Path.of("resources/campCleanup.txt"))));
    }

    private int calculateTotalOverlappingCleanups(Scanner scanner) {
        int total = 0;

        while (scanner.hasNextLine()) {
            if (oneAssignmentOverlapsTheOther(scanner.nextLine())) {
                total++;
            }
        }

        return total;
    }

    //This was the initial problem to solve. Instead of rewriting it, I took the framework of String processing for the next method
    private boolean oneAssignmentFullyContainsTheOther(String assignmentString) {
        String[] assignments = assignmentString.split(",");

        String[] assignment1 = assignments[0].split("-");
        String[] assignment2 = assignments[1].split("-");

        int assignment1Start = Integer.parseInt(assignment1[0]);
        int assignment1End = Integer.parseInt(assignment1[1]);
        int assignment2Start = Integer.parseInt(assignment2[0]);
        int assignment2End = Integer.parseInt(assignment2[1]);

        return (assignment1Start >= assignment2Start && assignment1End <= assignment2End) || (assignment2Start >= assignment1Start && assignment2End <= assignment1End);
    }
    
    private boolean oneAssignmentOverlapsTheOther(String assignmentString) {
        String[] assignments = assignmentString.split(",");

        String[] assignment1 = assignments[0].split("-");
        String[] assignment2 = assignments[1].split("-");

        int assignment1Start = Integer.parseInt(assignment1[0]);
        int assignment1End = Integer.parseInt(assignment1[1]);
        int assignment2Start = Integer.parseInt(assignment2[0]);
        int assignment2End = Integer.parseInt(assignment2[1]);

        boolean assignment1StartOverlaps = assignment1Start >= assignment2Start && assignment1Start <= assignment2End;
        boolean assignment1EndOverlaps = assignment1End >= assignment2Start && assignment1End <= assignment2End;

        boolean assignment2StartOverlaps = assignment2Start >= assignment1Start && assignment2Start <= assignment1End;
        boolean assignment2EndOverlaps = assignment2End >= assignment1Start && assignment2End <= assignment1End;

        return assignment1EndOverlaps || assignment1StartOverlaps || assignment2EndOverlaps || assignment2StartOverlaps;
    }


}
