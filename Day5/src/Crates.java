import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Crates {
    static List<Stack<Character>> crates;

    public static void main(String[] args) throws Exception {
        Path basePath = Path.of("Day5");
        Path inputPath = basePath.resolve("input");
        Path crateConfigPath = basePath.resolve("crateConfig");

        System.out.println("Part 1");
        initCrates(crateConfigPath);
        performCommands(1, inputPath);
        System.out.print("The list of top crates is: ");
        crates.forEach(stack -> System.out.print(stack.pop()));

        System.out.println("\nPart 2");
        initCrates(crateConfigPath);
        performCommands(2, inputPath);
        System.out.print("The list of top crates is: ");
        crates.forEach(stack -> System.out.print(stack.pop()));
    }

    private static void performCommands(int part, Path inputPath) throws IOException {
        int[] orders = new int[3];
        String[] orderChars;
        for(String input : Files.readAllLines(inputPath)) {
            input = input
                    .replaceAll("move", "")
                    .replaceAll("from", ";")
                    .replaceAll("to", ";")
                    .replaceAll(" ", "");
            orderChars = input.split(";");
            for(int i = 0; i < orderChars.length; i++) {
                orders[i] = Integer.parseInt("" + orderChars[i]);
            }
            if(part == 1) {
                performCraneMovementPart1(orders);
            } else {
                performCraneMovementPart2(orders);
            }
        }
    }

    private static void performCraneMovementPart1(int[] orders) {
        char temp;
        for(int i = 0; i < orders[0]; i++) {
            temp = crates.get(orders[1] - 1).pop();
            crates.get(orders[2] - 1).push(temp);
        }
    }

    private static void performCraneMovementPart2(int[] orders) {
        List<Character> temp = new ArrayList<>();
        for(int i = 0; i < orders[0]; i++) {
            temp.add(crates.get(orders[1] - 1).pop());
        }
        Collections.reverse(temp);
        for(char c : temp) {
            crates.get(orders[2] - 1).push(c);
        }
    }

    private static void fillCrates(Path crateConfigPath) throws IOException {
        char[] chars;
        List<String> inputs = Files.readAllLines(crateConfigPath);
        List<String> copy = inputs.subList(0, inputs.size());
        Collections.reverse(copy);
        for(String input : copy) {
            input = input
                    .replaceAll("    ", ".")
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .replaceAll(" ", "");
            chars = input.toCharArray();
            for(int i = 0; i < chars.length; i++) {
                if(chars[i] != '.' && !Character.isDigit(chars[i]))
                    crates.get(i).push(chars[i]);
            }
        }
    }

    public static void initCrates(Path crateConfigPath) throws IOException {
        crates = new ArrayList<>();
        String cratesAmount = Files.readAllLines(crateConfigPath).get(Files.readAllLines(crateConfigPath).size() - 1);
        cratesAmount = cratesAmount.replaceAll(" ", "");
        for(char c : cratesAmount.toCharArray()) {
            crates.add(new Stack<>());
        }
        fillCrates(crateConfigPath);
    }
}