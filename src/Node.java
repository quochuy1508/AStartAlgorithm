public class Node implements Comparable<Node> {
    Node parent;
    int x;
    int y;
    double g;
    double h;

    public Node(Node parent, int x, int y, double g, double h) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
    }

    @Override
    public int compareTo(Node node) {
        if ((this.g + this.h) > (node.g + node.h)) {
            return 1;
        } else if ((this.g + this.h) < (node.g + node.h)) {
            return -1;
        }
        return 0;
    }
}
