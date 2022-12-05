import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissors {
    static Map<Character, Integer> inputValues = new HashMap<>();
    public static void main(String[] args) throws Exception {
        Path inputPath = Path.of("Day2").resolve("input");
        initializeInputs();
        int totalScore = playRockPaperScissors(0, inputPath);
        System.out.println("Total score of " + totalScore);

        System.out.println("Part 2");
        totalScore = playRockPaperScissors(1, inputPath);
        System.out.println("Total score of " + totalScore);
    }

    public static int playRockPaperScissors(int part, Path inputPath) throws IOException {
        int scoreSum = 0;
        for(String input : Files.readAllLines(inputPath)) {
            scoreSum += 1 + ((part == 0)
                    ? calculateScore(input)
                    : calculateScorePartTwo(input)
            );
        }
        return scoreSum;
    }

    public static int calculateScore(String input) {
        int opponent = getInputValue(input.charAt(0));
        int self = getInputValue(input.charAt(2));
        if((self + 1) % 3 == opponent)
            return self;
        if(self == opponent) {
            return self + 3;
        }
        return self + 6;
    }

    public static int calculateScorePartTwo(String input) {
        int opponent = getInputValue(input.charAt(0));
        int self = getInputValue(input.charAt(2));
        if(self == 2)
            return 6 + ((opponent + 1) % 3);
        if(self == 1) {
            return opponent + 3;
        }
        return Math.floorMod(opponent - 1, 3);
    }
    public static int getInputValue(char input) {
        return inputValues.get(input);
    }

    public static void initializeInputs() {
        inputValues.put('A', 0);
        inputValues.put('B', 1);
        inputValues.put('C', 2);
        inputValues.put('X', 0);
        inputValues.put('Y', 1);
        inputValues.put('Z', 2);
    }
}