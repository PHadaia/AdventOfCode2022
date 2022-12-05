import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CalorieCounting {
    public static void main(String[] args) {

        List<Elf> elves;
        int index = -1;
        int topThreeCaloriesCount = 0;
        Path inputPath = Path.of("Day1").resolve("input");

        if (!Files.exists(inputPath)) {
            throw new RuntimeException("No input given");
        }

        try {
            elves = createElves(inputPath);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading input");
        }

        index = findElfWithMostCalories(elves);
        System.out.println("The highest amount of calories (" + elves.get(index).getTotalCalories() + ") " +
                "is being carried by elf " + (index + 1));

        topThreeCaloriesCount = findTopThreeCalories(elves);
        System.out.println("They total " + topThreeCaloriesCount + " Calories");
    }

    private static int findTopThreeCalories(List<Elf> elves) {
        int totalTopThreeCalories = 0;
        int index = -1;
        System.out.println("The top three elves are:");
        for(int i = 0; i < 3; i++) {
            index = findElfWithMostCalories(elves);
            System.out.println("Elf " + index);
            System.out.println("With " + elves.get(index).getTotalCalories() + " Calories");
            totalTopThreeCalories += elves.get(index).getTotalCalories();
            elves.remove(index);
        }
        return totalTopThreeCalories;
    }

    private static int findElfWithMostCalories(List<Elf> elves) {
        int maxCalories = -1;
        int index = -1;
        for(int i = 0; i < elves.size(); i++) {
            if(maxCalories < elves.get(i).getTotalCalories()) {
                maxCalories = elves.get(i).getTotalCalories();
                index = i;
            }
        }
        return index;
    }

    private static List<Elf> createElves(Path inputPath) throws IOException {
        List<Elf> elves = new ArrayList<>();
        Elf totalCalories = new Elf();
        elves.add(totalCalories);
        for (String inputLine : Files.readAllLines(inputPath)) {
            if(inputLine.isEmpty()) {
                totalCalories = new Elf();
                elves.add(totalCalories);
            } else {
                totalCalories.addCalories(Integer.parseInt(inputLine));
            }
        }
        return elves;
    }
}