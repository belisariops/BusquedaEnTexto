package SuffixArray;

/**
 * Created by belisariops on 6/5/17.
 */
public class Tripla {
    private int start;
    private long character;
    private long arrayIndex;
    private int lexicographicalNaming;
    private boolean isAlpha;

    public Tripla(int start, int lexNaming, boolean isAlpha) {
        this.start = start;
        lexicographicalNaming = lexNaming;
        this.isAlpha = isAlpha;
    }

    public void setCharacter(long character) {
        this.character = character;
    }

    public int getStart() {
        return this.start;
    }

    public long getCharacter() {
        return this.character;
    }

    public int getLexNaming() {
        return this.lexicographicalNaming;
    }

    public void setLexNaming(int lexNaming) {
        this.lexicographicalNaming = lexNaming;
    }

}
