import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class RucksackReorganization {
    public static void main(String[] args) throws Exception {
        Path inputPath = Path.of("Day3").resolve("input");
        int prioritiesSum = organizeRucksacks(inputPath);
        System.out.println("The total sum of priorities is " + prioritiesSum);
        System.out.println("Part2");
        prioritiesSum = organizeRucksacksPart2(inputPath);
        System.out.println("The total sum of all the badges is " + prioritiesSum);
    }

    public static int organizeRucksacks(Path inputPath) throws IOException {
        int totalPriorities = 0;
        for(String input : Files.readAllLines(inputPath)) {
            totalPriorities += calculatePriorities(input.substring(0, input.length() / 2), input.substring(input.length() / 2));
        }
        return totalPriorities;
    }

    public static int organizeRucksacksPart2(Path inputPath) throws IOException {
        int badgePriorities = 0;
        List<String> lines = Files.readAllLines(inputPath);
        String[] groups = new String[3];
        for(int i = 0; i < lines.size(); i++) {
            groups[i % 3] = lines.get(i);
            if(i % 3 == 2) {
                badgePriorities += calculateBadgePriorities(groups);
            }
        }
        return badgePriorities;
    }

    private static int calculateBadgePriorities(String[] groups) {
        List<Integer> duplicateChars = groups[0].chars()
                .filter(c -> (groups[1].indexOf(c) >= 0))
                .boxed()
                .toList();
        for(int item : duplicateChars) {
            if(groups[2].indexOf(item) >= 0) {
                return calculateCharPriority(item);
            }
        }
        return 0;
    }

    public static int calculatePriorities(String firstHalf, String secondHalf) {
        OptionalInt duplicateChar = firstHalf.chars()
                .filter(c -> (secondHalf.indexOf(c) >= 0))
                .findFirst();
        if(duplicateChar.isPresent()) {
            return calculateCharPriority(duplicateChar.getAsInt());
        }
        return 0;
    }

    private static int calculateCharPriority(int duplicateChar) {
        return (duplicateChar < 97) ? duplicateChar - 38 : duplicateChar - 96;
    }
}