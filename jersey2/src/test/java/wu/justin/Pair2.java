package wu.justin;

public class Pair2<L, R> {

    private final L left;
    private final R right;

    public Pair2() {
        this.left = null;
        this.right = null;
    }

    public Pair2(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public final L getL() {
        return this.left;
    }

    public final R getR() {
        return this.right;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.left).append(", ").append(this.right);
        sb.append("]");
        return sb.toString();
    }

}

