import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Day01_Snacks {
    long fattestGnome = 0;
    long secondFattestGnome = 0;
    long thirdFattestGnome = 0;

    public static void main(String[] args) throws IOException {
        System.out.println(new Day01_Snacks().findFattestGnome(new Scanner(Path.of("resources/gnomeCalories.txt"))));
    }

    public long findFattestGnome(Scanner calorieListScanner) {
        //Curveball. Top 3 fattest gnomes!
        long currentGnome = 0;
        while (calorieListScanner.hasNextLine()) {
            String currentLine = calorieListScanner.nextLine();
            if (currentLine.isEmpty()) {
                updateFattestGnomes(currentGnome);
                currentGnome = 0;
            } else {
                currentGnome += Integer.parseInt(currentLine);
            }
        }
        updateFattestGnomes(currentGnome);

        return fattestGnome + secondFattestGnome + thirdFattestGnome;
    }

    private void updateFattestGnomes(long currentGnome) {
        if (fattestGnome < currentGnome) {
            thirdFattestGnome = secondFattestGnome;
            secondFattestGnome = fattestGnome;
            fattestGnome = currentGnome;
        } else if (secondFattestGnome < currentGnome) {
            thirdFattestGnome = secondFattestGnome;
            secondFattestGnome = currentGnome;
        } else if (thirdFattestGnome < currentGnome) {
            thirdFattestGnome = currentGnome;
        }
    }
}
