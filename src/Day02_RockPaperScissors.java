import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Day02_RockPaperScissors {
    public static void main(String[] args) throws IOException {
        System.out.println(new Day02_RockPaperScissors().calculateStrategyScore(new Scanner(Path.of("resources/rockPaperScissorsStrategyGuide.txt"))));
    }

    private long calculateStrategyScore(Scanner scanner) {
        long total = 0;
        while (scanner.hasNextLine()) {
            total += getScoreForGame(scanner.nextLine());
        }
        return total;
    }

    private long getScoreForGame(String game) {
        //Curveball - X means LOSE, Y means DRAW, Z means WIN
        String[] gameDetails = game.split(" ");
        if (game.length() != 3) {
            throw new RuntimeException("Line improperly formatted: " + game);
        }
        char them = game.charAt(0);
        char me = game.charAt(2);

        RockPaperScissors iShouldPlay = null;

        long total = 0;

        if (me == 'Z') {
            total += 6;
            iShouldPlay = RockPaperScissors.decode(them).win();
        } else if (me == 'Y') {
            total += 3;
            iShouldPlay = RockPaperScissors.decode(them);
        } else {
            iShouldPlay = RockPaperScissors.decode(them).lose();
        }

        total += iShouldPlay.points;

        return total;
    }

    private enum RockPaperScissors {
        ROCK(1), PAPER(2), SCISSORS(3);

        public final int points;

        RockPaperScissors(int points) {
            this.points = points;
        }

        static RockPaperScissors decode(char decode) {
            switch (decode) {
                case 'A':
                    return ROCK;
                case 'B':
                    return PAPER;
                default:
                    return SCISSORS;
            }
        }

        RockPaperScissors win() {
            switch (this) {
                case ROCK:
                    return PAPER;
                case PAPER:
                    return SCISSORS;
                case SCISSORS:
                    return ROCK;
            }
            return null;
        }

        RockPaperScissors lose() {
            switch (this) {
                case ROCK:
                    return SCISSORS;
                case PAPER:
                    return ROCK;
                case SCISSORS:
                    return PAPER;
            }
            return null;
        }
    }
}
