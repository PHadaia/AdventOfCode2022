import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CleanUpRanges {
    public static void main(String[] args) throws Exception {
        Path inputPath = Path.of("Day4").resolve("input");
        int totalOcclusions;

        System.out.println("Part 1");
        totalOcclusions = findAllOccludingPairs(1, inputPath);
        System.out.println("There is a total of " + totalOcclusions + " occlusions");

        System.out.println("Part 2");
        totalOcclusions = findAllOccludingPairs(2, inputPath);
        System.out.println("There is a total of " + totalOcclusions + " partially occluded segments");
    }

    public static int findAllOccludingPairs(int part, Path inputPath) throws IOException {
        int totalOcclusions = 0;
        int[] firstElfRanges = new int[2];
        int[] secondElfRanges = new int[2];
        String[] inputPerElf;
        for(String input : Files.readAllLines(inputPath)) {
            inputPerElf = input.split(",");
            firstElfRanges[0] = Integer.parseInt(inputPerElf[0].split("-")[0]);
            firstElfRanges[1] = Integer.parseInt(inputPerElf[0].split("-")[1]);
            secondElfRanges[0] = Integer.parseInt(inputPerElf[1].split("-")[0]);
            secondElfRanges[1] = Integer.parseInt(inputPerElf[1].split("-")[1]);
            totalOcclusions += (part == 1)
                    ? checkOcclusion(firstElfRanges, secondElfRanges)
                    : checkPartialOcclusions(firstElfRanges, secondElfRanges);
        }
        return totalOcclusions;
    }

    public static int checkOcclusion(int[] firstElf, int[] secondElf) {
        if(firstElf[0] >= secondElf[0] && firstElf[1] <= secondElf[1]) {
            return 1;
        }
        if(secondElf[0] >= firstElf[0] && secondElf[1] <= firstElf[1]) {
            return 1;
        }
        return 0;
    }

    private static int checkPartialOcclusions(int[] firstElf, int[] secondElf) {
        if(firstElf[0] > secondElf[1] || firstElf[1] < secondElf[0]) {
            return 0;
        }
        if(secondElf[0] > firstElf[1] || secondElf[1] < firstElf[0]) {
            return 0;
        }
        return 1;
    }
}