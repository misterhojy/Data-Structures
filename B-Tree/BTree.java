package cse214HW3;
import java.util.*;

public class BTree<E extends Comparable<E>> implements AbstractBTree<E> {
    private final int MIN;
    private BTNode<E> root;

    public BTree(int minimumDegree){
        this.MIN = minimumDegree;
        this.root = new BTNode<>(minimumDegree, true);
    }

    @Override
    public NodeIndexPair<E> contains(E element) {
        return contains(element,root);
    }

    private NodeIndexPair<E> contains(E element, BTNode<E> node) {
        int i = 0;
        if (i < node.num && node.elements.get(i).compareTo(element) == 0){
            return new NodeIndexPair<>(node, i);
        }
        else
            while(i < node.num && node.elements.get(i).compareTo(element) < 0){
                i++;
                if (i < node.num && node.elements.get(i).compareTo(element) == 0){
                    return new NodeIndexPair<>(node, i);
                }
                if (node.isLeaf() && i == node.elements.size()){
                    return null;
                }
            }if (node.isLeaf()){
                return null;
            }
            else {
                return contains(element, node.children.get(i));
            }
    }

    private boolean isContained(E element){
        return contains(element, root) != null;
    }

    @Override
    public void add(E element) {
        if(isEmpty()){
            insert(element,root);
        } else if(!isContained(element)){
            insertNonFull(element, root);
        }
    }

    private void insert(E element, BTNode<E> node){
        node.elements.add(element);
        Collections.sort(node.elements);
        node.num++;
    }

    private void insertNonFull(E element, BTNode<E> node){
        if (node.isLeaf() && !node.isFull()) {
            insert(element, node);
        } else if (node.isFull() && node.parent == null) {
            splitAtRoot(node,getMedian(node.elements), element);
        } else if (node.isFull() && node.parent != null) {
            split(node, node.parent, getMedian(node.elements), element);
        } else if (!node.isLeaf() && !node.isFull()){
            insertNonFull(element, node.children.get(whichChild(element,node)));
        }
    }

    private void splitAtRoot(BTNode<E> node, int median, E element){
        BTNode<E> leftSplit = new BTNode<>(MIN, false);
        for(int i = 0;i < median; i++){
            leftSplit.elements.add(node.elements.get(i));
            leftSplit.num++;
        }
        BTNode<E> rightSplit = new BTNode<>(MIN, false);
        for(int i = median + 1;i < node.elements.size(); i++){
            rightSplit.elements.add(node.elements.get(i));
            rightSplit.num++;
        }
        BTNode<E> newnode = new BTNode<>(MIN, true);
        insertNonFull(node.elements.get(median), newnode);
        leftSplit.parent = newnode;
        rightSplit.parent = newnode;
        newnode.children.add(leftSplit);
        newnode.children.add(rightSplit);
        root = newnode;
        insertNonFull(element, newnode);
    }

    private void split(BTNode<E> child, BTNode<E> parent, int median, E element){
        BTNode<E> leftSplit = new BTNode<>(MIN, false);
        for(int i = 0;i < median; i++){
            leftSplit.elements.add(child.elements.get(i));
            leftSplit.num++;
        }
        BTNode<E> rightSplit = new BTNode<>(MIN, false);
        for(int i = median + 1;i < child.elements.size(); i++){
            rightSplit.elements.add(child.elements.get(i));
            rightSplit.num++;
        }
        parent.elements.add(child.elements.get(median));
        Collections.sort(parent.elements);
        parent.num++;
        int index = parent.children.indexOf(child);
        leftSplit.parent = parent;
        rightSplit.parent = parent;
        parent.children.set(index, leftSplit);
        parent.children.add(index + 1,rightSplit);
        insertNonFull(element, parent);
    }

    private int whichChild(E element, BTNode<E> node){
        int i = 0;
        do {
            if(element.compareTo(node.elements.get(i)) > 0)
                i++;
        } while (i != node.elements.size() && element.compareTo(node.elements.get(i)) > 0);
        return i;
    }

    private int getMedian(ArrayList<E> list){
        return ((list.size()-1)/2);
    }

    private boolean isEmpty(){
        return root.isEmpty();
    }

    @Override
    public String toString() {
        return root.toString();
    }
    @SafeVarargs
    private static <T extends Comparable<T>> void addAllInThisOrder(BTree<T> theTree, T... items) {
        for (T item : items)
            theTree.add(item);
    }

    public static void main(String[] args) {
        BTree<Integer> integerBTree = new BTree<>(2);
        addAllInThisOrder(integerBTree,10);
        integerBTree.add(15);
        integerBTree.add(20);
        integerBTree.add(25);
        integerBTree.add(30);
        integerBTree.add(35);
        integerBTree.add(40);
        integerBTree.add(45);
        integerBTree.add(50);
        integerBTree.add(55);
        System.out.println(integerBTree);
    }
}
