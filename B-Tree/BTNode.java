package cse214HW3;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BTNode<E> {
        ArrayList<E> elements;
        ArrayList<BTNode<E>> children;
        BTNode<E> parent;
        private final int MIN;
        int num;
        boolean leaf;
        BTNode(int min, boolean leaf) {
            this.MIN = min;
            this.num = 0;
            this.leaf = leaf;
            this.parent = null;
            this.elements = new ArrayList<E>(2 * this.MIN - 1);
            this.children = new ArrayList<BTNode<E>>(2 * this.MIN);
        }


    /**
     * @return <code>true</code> if and only if this node is a leaf node.
     */
    public boolean isLeaf(){
        return (children.size() == 0);
    }
    /**
     * @return <code>true</code> if and only if this node is a full node, i.e., it has the maximum
     * possible number of elements permitted by the minimum degree of the B-tree.
     */
    public boolean isFull(){
        return (elements.size() == (2 * MIN - 1));
    }

    public boolean isEmpty() { return (elements.size() == 0); }

    @Override
    public String toString() {
        return toString(0);
    }
    // based on what toString() does, think about what ‘elements’ and ‘children’ can be
    private String toString(int depth) {
        StringBuilder builder = new StringBuilder();
        String blankPrefix = new String(new char[depth]).replace("\0", "\t");
        List<String> printedElements = new LinkedList<>();
        for (E e : elements) printedElements.add(e.toString());
        String eString = String.join(" :: ", printedElements);
        builder.append(blankPrefix).append(eString).append("\n");
        children.forEach(c -> builder.append(c.toString(depth+1)));
        return builder.toString();
    }
}
