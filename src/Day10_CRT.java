import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;

public class Day10_CRT {
    public static void main(String[] args) throws IOException {
        System.out.println(new Day10_CRT().displayCRT());
    }

    private String displayCRT() throws IOException {
        Path operationsFile = Path.of("resources/operations.txt");
        Scanner scanner = new Scanner(operationsFile);
        Integer valueToAdd = null;
        int x = 1;
        int totalStrength = 0;
        int cycle = 0;
        StringBuilder crt = new StringBuilder();

        while (scanner.hasNextLine()) {
            if (cycle%40 == 0) {
                crt.append("\n");
            }

            if (Math.abs(x-cycle%40) < 2) {
                crt.append("#");
            } else {
                crt.append(" ");
            }

            if (valueToAdd == null) {
                String operation = scanner.nextLine();
                if (!operation.equals("noop")) {
                    //assume it's addx
                    valueToAdd = Integer.parseInt(operation.substring(operation.lastIndexOf(" ")+1));
                }
            } else {
                x += valueToAdd;
                valueToAdd = null;
            }


            cycle++;
        }

        return crt.toString();
    }

    private int getSignalStrength() throws IOException {
        Path operationsFile = Path.of("resources/operations.txt");
        Scanner scanner = new Scanner(operationsFile);
        Integer valueToAdd = null;
        int x = 1;
        int totalStrength = 0;
        Set<Integer> valuesWeCareAbout = Set.of(20, 60, 100, 140, 180, 220);

        for (int cycle = 1; cycle <= 220; cycle++) {
            if (valuesWeCareAbout.contains(cycle)) {
                totalStrength += (cycle)*x;
            }

            if (valueToAdd == null) {
                if (!scanner.hasNextLine()) {
                    scanner = new Scanner(operationsFile);
                }
                String operation = scanner.nextLine();
                if (!operation.equals("noop")) {
                    //assume it's addx
                    valueToAdd = Integer.parseInt(operation.substring(operation.lastIndexOf(" ")+1));
                }
            } else {
                x += valueToAdd;
                valueToAdd = null;
            }
        }

        return totalStrength;
    }
}
