public class MaxABC<V, K> {
    protected V text;
    protected K count;

    public void setText(V text) {
        this.text = text;
    }

    public void setCount(K count) {
        this.count = count;
    }

    public V getText() {
        return text;
    }

    public K getCount() {
        return count;
    }
}