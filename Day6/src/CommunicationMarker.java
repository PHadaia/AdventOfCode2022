import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CommunicationMarker {
    public static void main(String[] args) throws IOException {
        Path inputPath = Path.of("Day6").resolve("input");
        int firstMarker;
        System.out.println("Part 1");
        firstMarker = findMarker(1, inputPath);
        System.out.println("The first package marker was found after character " + firstMarker);

        System.out.println("Part 2");
        firstMarker = findMarker(2, inputPath);
        System.out.println("The first message marker was found after character " + firstMarker);
    }

    public static int findMarker(int part, Path inputPath) throws IOException {
        char[] inputChars;
        int partValue = (part == 1)
                ? 4
                : 14;
        List<Character> chars = Arrays.asList(new Character[partValue]);
        for(String input : Files.readAllLines(inputPath)) {
            inputChars = input.toCharArray();
            for(int i = 0; i < inputChars.length; i++) {
                chars.set(i % partValue, inputChars[i]);
                if(i > partValue - 1) {
                    if(checkOccurences(chars, partValue)) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
    }

    private static boolean checkOccurences(List<Character> chars, int keys) {
        Map<Character, Long> counts = chars.stream().collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return new HashSet<>(counts.values()).size() == 1 && counts.keySet().size() == keys;
    }
}