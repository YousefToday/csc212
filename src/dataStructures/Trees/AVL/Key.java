package dataStructures.Trees.AVL;

public class Key {
    private final boolean isInt;
    private final int i;      // valid only if isInt == true
    private final String s;   // valid only if isInt == false

    public Key(int i) {
        this.isInt = true;
        this.i = i;
        this.s = null;
    }

    public Key(String s) {
        this.isInt = false;
        this.i = 0;
        this.s = s.trim();
    }



    public int compare(Key o) {
        if (this.isInt && o.isInt)
            return Integer.compare(this.i, o.i);

        if (!this.isInt && !o.isInt)
            return this.s.compareToIgnoreCase(o.s);

        throw new IllegalArgumentException("Can't compare int key with string key");
    }
    @Override
    public String toString() {
        return isInt? "Key[" + i +"]" : "Key[" + s +"]";
    }
}
