package cse214HW2;

import static cse214HW2.PrintTest.printTree;

public class DynamicIntegerSet implements DynamicSet {
    private Node root;

    @Override
    public int size() {
        if (isEmpty()){
            return 0;
        } else {
            return getSize(root());
        }
    }

    private int getSize(Node node){
        int leftsize = 0;
        int rightsize = 0;
        if (node.left != null){
            leftsize = getSize(node.left);
        }
        if (node.right != null){
            rightsize = getSize(node.right);
        }
        return 1 + leftsize + rightsize;
    }

    @Override
    public boolean contains(Integer x) {
        return contains(root, x);
    }

    private boolean contains(Node node, Integer x){
        if (node == null) {
            return false;
        }
        else if (node.data.equals(x)) {
            return true;
        }
        else if (x < node.data) {
            return contains(node.left, x);
        } else {
            return contains(node.right, x);
        }
    }

    @Override
    public boolean add(Integer x) {
        if(contains(x)){
            return false;
        } else {
            if (isEmpty()){
                root = new Node(x);
            }else {
                add(root(),x);
            }
            return true;
        }
    }

    private boolean isEmpty(){
        return root() == null;
    }

    private Node add(Node node, Integer x){

        if (node == null){
            node = new Node(x);
            return node;
        }
        if (x < node.data){
            node.left = add(node.left, x);
        }else {
            node.right = add(node.right, x);
        }
        return node;
    }

    @Override
    public boolean remove(Integer x) {
        if (contains(x)){
            remove(root(),x);
            return true;
        }
        else {
            return false;
        }
    }

    private Node remove(Node node, Integer x){
        if (node == null){
            return null;
        }
        if (x < node.data){
            node.left = remove(node.left, x);
        }
        else if (x > node.data){
            node.right = remove(node.right, x);
        }
        else {
            if (node.right == null && node.left == null){
                //has neither left and right
                node = null;
                return node;
            }
            else if (node.right != null && node.left != null){
                //has both left and right
                Node successor = getSuccessor(node);
                node.data = successor.data; //its most left child of its right child is copied, now there is 2
                node.right = remove(node.right, successor.data);
                return node;
            }
            else {
                if (node.right == null){
                    //has a left
                    Node temp = node.left;
                    node.left = null;
                    node.data = temp.data;
                    return node;
                }
                else {
                    //has a right
                    Node temp = node.right;
                    node.right = null;
                    node.data = temp.data;
                    return node;
                }
            }
        }
        return node;
    }

    private Node getSuccessor(Node node){
        //successor of a node is the most left of its right child
        if (node == null){
            return null;
        }
        else {
            Node temp = node.right;
            while (temp.left != null){
                temp = temp.left;
            }
            return temp;
        }
    }

    public Node root() {
        return this.root;
    }

    public static class Node implements PrintableNode {
        Integer data;
        Node left, right;
        Node(int x) { this(x, null, null); }
        Node(int x, Node left, Node right) {
            this.data = x;
            this.left = left;
            this.right = right;
        }
        @Override
        public String getValueAsString() {
            return data.toString();
        }
        @Override
        public PrintableNode getLeft() {
            return left;
        }
        @Override
        public PrintableNode getRight() {
            return right;
        }
    }

    public static void main(String [] args){
        DynamicIntegerSet set1 = new DynamicIntegerSet();

        set1.add(4);
        set1.add(2);
        set1.add(8);
        set1.add(5);
        set1.add(10);
        set1.add(6);
        set1.add(9);
        printTree(set1.root);
        set1.size();

        set1.remove(10);
        set1.remove(5);
        set1.remove(4);
        printTree(set1.root);

    }
}
