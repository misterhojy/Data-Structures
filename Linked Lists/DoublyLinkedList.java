package cse214HW1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements ListAbstractType<E> {

    //start with list being null
    private Node<E> head;
    private Node<E> tail;
    Node<E> lastReturned;
    @Override
    public boolean add(E element) {
        addElementAfterNode(tail, element);
        return true;
    }

    @Override
    public boolean remove(E element) {
        lastReturned = null;
        //having iterator, if equal then change node links and leave loop
        if (isEmpty()) {
            return false;
        }
        else {
            Node<E> pointer = head;
            while (pointer != null) {
                if(pointer.element == element) {
                    if (pointer == head && head == tail) {
                        head = null;
                        tail = null;
                        return true;
                    }
                    if(pointer == head){
                        pointer.next.previous = null;
                        head = pointer.next;
                        return true;
                    }
                    else if(pointer == tail){
                        pointer.previous.next = null;
                        tail = pointer.previous;
                        return true;
                    }
                    else {
                        pointer.previous.next = pointer.next;
                        pointer.next.previous = pointer.previous;
                        return true;
                    }
                }
                else {
                    pointer = pointer.next;
                }
            }
            return false;
        }
    }

    @Override
    public int size() {
        //have count how much time the iterator iterates
        int count = 0;
        if (isEmpty()) {
            return 0;
        }
        else {
            Node<E> pointer = head;
            while (pointer != null) {
                count++;
                pointer = pointer.next;
            }
            return count;
        }
    }

    @Override
    public boolean isEmpty() {
        if(head == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        Node<E> pointer = head;
        while (pointer != null) {
            if(pointer.element == element) {
                return true;
            }
            pointer = pointer.next;
        }
        return false;
    }

    @Override
    public E get(int index) {
        Node<E> pointer = head;
        int j = 0;
        while(j != index) {
            pointer = pointer.next;
            j++;
        }
        return pointer.element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> pointer = head;
        Node<E> newnode = new Node<>(element);
        E prevposition;
        if(index == 0) {
            prevposition = get(index);
            pointer.next.previous = newnode;
            newnode.next = pointer.next;
            newnode.previous = null;
            head = newnode;
        }
        else if(index == size()){
            prevposition = tail.element;
            pointer = tail;
            pointer.previous.next = newnode;
            newnode.previous = pointer.previous;
            tail = newnode;
        }
        else {
            prevposition = get(index);
            pointer = head;
            int j = 0;
            while(j != index) {
                pointer = pointer.next;
                j++;
            }
            pointer.previous.next = newnode;
            pointer.next.previous = newnode;
            newnode.previous = pointer.previous;
            newnode.next = pointer.next;
        }
        return prevposition;
    }

    @Override
    public void add(int index, E element) {
        lastReturned = null;
        Node<E> pointer = head;
        int j = 0;
        if (index == 0) {
            Node<E> newNode = addElementAfterNode(null, element);
        }
        else if (index == size()) {
            Node<E> newNode = addElementAfterNode(tail, element);
        }
        else {
            while (j != index) {
                pointer = pointer.next;
                j++;
            }
            addElementAfterNode(pointer.previous, element);
        }
    }

    @Override
    public void remove(int index) {
        lastReturned = null;
        Node<E> pointer;
        if(index == 0) {
            pointer = head;
            if (pointer == head && head == tail) {
                head = null;
                tail = null;
            }
            else {
                pointer.next.previous = null;
                head = pointer.next;
            }
        }
        else if(index == size() - 1) {
            pointer = tail;
            pointer.previous.next = null;
            tail = pointer.previous;
        }
        else {
            pointer = head;
            int j = 0;
            while (j != index) {
                pointer = pointer.next;
                j++;
            }
            pointer.previous.next = pointer.next;
            pointer.next.previous = pointer.previous;
        }
    }

    private void print(){
        //test method to just print and see size
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println("\n" + size() + "\n");
    }

    private Node<E> addElementAfterNode(Node<E> node, E element){
        lastReturned = null;
        Node<E> newnode = new Node<>(element);
        if (isEmpty()) {
            head = newnode;
            tail = newnode;
            head.previous = null;
            tail.next = null;
        }
        else if(node == tail){
            node.next = newnode;
            newnode.previous = node;
            tail = newnode;
        }
        else if (node == null) {
            newnode.next = head;
            head.previous = newnode;
            head = newnode;
        }
        else {
            newnode.previous = node;
            node.next.previous = newnode;
            newnode.next = node.next;
            node.next = newnode;
        }
        return newnode;
    }

    @Override
    public TwoWayListIterator<E> iterator() {

        return new DoublyLinkedListIterator();
    }

    public String toString() {
        Iterator<E> it = this.iterator();
        if (!it.hasNext())
            return "[]";
        StringBuilder builder = new StringBuilder("[");
        while (it.hasNext()) {
            E e = it.next();
            builder.append(e.toString());
            if (!it.hasNext())
                return builder.append("]").toString();
            builder.append(", ");
        }
        // code execution should never reach this line
        return null;
    }


    private class DoublyLinkedListIterator implements TwoWayListIterator<E> {

        Node<E> current;

        public DoublyLinkedListIterator() {
            this.current = new Node<>();
            current.next = head;
        }

        @Override
        public boolean hasPrevious() {
            if(current.previous != null){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        public E previous() {
            if(hasPrevious()) {
                E element = current.previous.element;
                lastReturned = current.previous;
                Node<E> next = current.previous;
                current.previous = current.previous.previous;
                current.next = next;
                return element;
            }
            else{
                throw new NoSuchElementException();
            }
        }

        @Override
        public void add(E element) {
            current.previous = addElementAfterNode(current.previous,element);
        }

        @Override
        public void set(E element) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.element = element;
        }

        @Override
        public boolean hasNext() {
            if(current.next != null){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        public E next() {
            if(hasNext()) {
                E element = current.next.element;
                lastReturned = current.next;
                Node<E> prev = current.next;
                current.next = current.next.next;
                current.previous = prev;
                return element;
            }
            else{
                throw new NoSuchElementException();
            }
        }

    }

    //non-static has to be accessed as inner class throughout the outer class
    private static class Node<E> {

        Node<E> previous;
        Node<E> next;
        E element;

        Node() {}
        Node(E element) {
            this.element = element;
        }

    }
    public static void main (String [] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(2);
        list.add(4);
        list.add(6);
        list.add(8);
        System.out.println(list.add(10));
        list.print();

        list.add(0, 1);
        list.add(6,11);
        list.add(2,3);
        list.print();

        list.add(4,5);
        list.add(6,7);
        list.add(8,9);
        list.print();

        list.remove(0);
        list.remove(9);
        list.remove(1);
        list.print();

        System.out.println(list.remove(Integer.valueOf(5)));
        System.out.println(list.remove(Integer.valueOf(7)));
        System.out.println(list.remove(Integer.valueOf(9)));
        System.out.println(list.remove(Integer.valueOf(10)));
        System.out.println(list.remove(Integer.valueOf(10000)));
        list.print();

        System.out.println("Does the list contain 3: " + list.contains(Integer.valueOf(3)));
        System.out.println("Does the list contain 6: " + list.contains(Integer.valueOf(6)));

        System.out.println("\n" + "Replace " + list.set(2,Integer.valueOf(60)) + " with 60");
        list.print();

        System.out.println("\n" + "Replace " + list.set(0,Integer.valueOf(20)) + " with 20");
        list.print();


        // Test next and prev and add and hasNext and hasPrev

        // For set
        // Test the following
        // 1) Never calling next or prev and calling set
        // 2) calling next then set
        // 3) calling prev then set
        // 4) calling next/prev then add/remove then set
        DoublyLinkedList<Double> list2 = new DoublyLinkedList<>();

        list2.add(1.0);
        list2.add(2.0);
        list2.add(3.0);
        list2.add(4.0);

        TwoWayListIterator<Double> iterator = list2.iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
//        System.out.println(iterator.next());
        System.out.println();

        System.out.println(iterator.previous());
        System.out.println(iterator.previous());
        System.out.println(iterator.previous());
        System.out.println(iterator.previous());
//        System.out.println(iterator.previous());

        System.out.println();
        iterator.add(0.0);
        list2.print();
        System.out.println(iterator.next());
        System.out.println(iterator.previous());
        System.out.println(iterator.previous());
        iterator.add(-1.0);
        list2.print();
        System.out.println(iterator.previous());

        System.out.println("###########");
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        iterator.add(0.5);
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        iterator.add(2.5);
        System.out.println(iterator.next());
        iterator.add(3.5);
        System.out.println(iterator.next());
        iterator.add(5.0);
        list2.print();

        System.out.println();
        System.out.println();


        DoublyLinkedList<Integer> list3 = new DoublyLinkedList<>();
        list3.add(1);
        list3.add(2);
        list3.add(3);
        list3.add(4);
        list3.add(5);
        TwoWayListIterator<Integer> iterator2 = list3.iterator();
        list3.print();

        iterator2.next();
        iterator2.set(100);
        list3.print();

        iterator2.next();
        iterator2.next();
        iterator2.previous();
        iterator2.add(250);
        list3.print();

        list3.remove(2);
        list3.remove(Integer.valueOf(5));
        list3.print();

        System.out.println(iterator2.next());
        iterator2.set(300);
        list3.print();

        System.out.println(list3);
        System.out.println();
        System.out.println();


        DoublyLinkedList<Integer> list4 = new DoublyLinkedList<>();

        list4.add(1);
        list4.add(2);
        list4.add(3);
        list4.add(4);

        TwoWayListIterator<Integer> iterator3 = list4.iterator();
        System.out.println(list4);
        System.out.println(iterator3.next());
        System.out.println(iterator3.next());
        iterator3.add(25);
        System.out.println(list4);
        System.out.println(iterator3.next());
        list4.remove(0);
        list4.remove(3);
        list4.remove(0);
        list4.remove(0);
        System.out.println(list4);
        list4.remove(0);
        System.out.println(list4);




    }

}
