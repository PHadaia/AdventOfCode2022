import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Trees {
    static List<List<Tree>> trees = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Path inputPath = Path.of("Day8").resolve("input");
        recordTrees(inputPath);
        System.out.println("" + countTrees() + " trees are visible from the edges");
        System.out.println("Part 2");
        System.out.println("The best scenic score is " + getHighestScenicScore());
    }

    private static int getHighestScenicScore() {
        int highestScenicScore = -1;

        for(int i = 0; i < trees.size(); i++) {
            for(int j = 0; j < trees.get(i).size(); j++) {
                Tree tree = trees.get(i).get(j);
                tree.setScenicScore(calculateScenicScore(tree, i, j));
                if(tree.scenicScore > highestScenicScore) {
                    highestScenicScore = tree.scenicScore;
                }
            }
        }

        return highestScenicScore;
    }

    private static int calculateScenicScore(Tree tree, int yPosition, int xPosition) {
        int[] tempScoreMultiplier = new int[4];

        //Left Edge
        for(int i = xPosition - 1; i >= 0; i--) {
            tempScoreMultiplier[0]++;
            if(trees.get(yPosition).get(i).height >= tree.height) {
                break;
            }
        }

        //Right Edge
        for(int i = xPosition + 1; i < trees.get(0).size(); i++) {
            tempScoreMultiplier[1]++;
            if(trees.get(yPosition).get(i).height >= tree.height) {
                break;
            }
        }

        //Top Edge
        for(int i = yPosition - 1; i >= 0; i--) {
            tempScoreMultiplier[2]++;
            if(trees.get(i).get(xPosition).height >= tree.height) {
                break;
            }
        }

        //Bottom Edge
        for(int i = yPosition + 1; i < trees.size(); i++) {
            tempScoreMultiplier[3]++;
            if(trees.get(i).get(xPosition).height >= tree.height) {
                break;
            }
        }


        return Arrays.stream(tempScoreMultiplier).reduce(1, (a, b) -> a * b);
    }

    private static void recordTrees(Path inputPath) throws IOException {
        for(String input : Files.readAllLines(inputPath)) {
            List<Tree> treeRow = new ArrayList<>();
            input.chars().map(Character::getNumericValue).forEach(c -> treeRow.add(new Tree(c)));
            trees.add(treeRow);
        }
    }

    private static int countTrees() {
        int totalVisibleTrees = 0;
        int heightOfHighestTree;

        //Row forwards
        for(List<Tree> row : trees) {
            heightOfHighestTree = -1;
            for(Tree tree : row) {
                if(heightOfHighestTree < tree.height) {
                    heightOfHighestTree = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        totalVisibleTrees++;
                    }
                }
            }
        }

        //Row backwards
        for(List<Tree> row : trees) {
            heightOfHighestTree = -1;
            List<Tree> tempRow = new ArrayList<>(row);
            Collections.reverse(tempRow);
            for(Tree tree : tempRow) {
                if(heightOfHighestTree < tree.height) {
                    heightOfHighestTree = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        totalVisibleTrees++;
                    }
                }
            }
        }

        //Column top-down
        for(int i = 0; i < trees.get(0).size(); i++) {
            heightOfHighestTree = -1;
            for(int j = 0; j < trees.size(); j++) {
                Tree tree = trees.get(j).get(i);
                if(heightOfHighestTree < tree.height) {
                    heightOfHighestTree = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        totalVisibleTrees++;
                    }
                }
            }
        }


        //Column bottom-up
        for(int i = 0; i < trees.get(0).size(); i++) {
            heightOfHighestTree = -1;
            for(int j = trees.get(0).size() - 1; j >= 0; j--) {
                Tree tree = trees.get(j).get(i);
                if(heightOfHighestTree < tree.height) {
                    heightOfHighestTree = tree.height;
                    if(!tree.visible) {
                        tree.visible = true;
                        totalVisibleTrees++;
                    }
                }
            }
        }

        return totalVisibleTrees;
    }
}