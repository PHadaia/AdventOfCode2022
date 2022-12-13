public class Tree {
    boolean visible = false;
    int height;
    int scenicScore = 0;

    Tree(int height) {
        this.height = height;
    }

    public void setScenicScore(int scenicScore) {
        this.scenicScore = scenicScore;
    }
}
