package cse214HW1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HotPotato<E>{

    public static DoublyLinkedList<Integer> playWithDoublyLinkedList(int numberOfPlayers, int lengthOfPass) {
        DoublyLinkedList<Integer> game = new DoublyLinkedList<>();
        DoublyLinkedList<Integer> output = new DoublyLinkedList<>();
        for (int i = 0; i < numberOfPlayers; i++){
            game.add(i+1);
        }
        TwoWayListIterator<Integer> iterator = game.iterator();
        int playerwithpotato;
        while(!(game.isEmpty())){
            try {
                playerwithpotato = iterator.next();
            } catch(NoSuchElementException ex) {
                iterator = game.iterator();
                playerwithpotato = iterator.next();
            }
            for(int i = 0; i < lengthOfPass; i++){
                try {
                    playerwithpotato = iterator.next();
                } catch(NoSuchElementException ex) {
                    iterator = game.iterator();
                    playerwithpotato = iterator.next();
                }
            }
            output.add(playerwithpotato);
            game.remove(Integer.valueOf(playerwithpotato));
        }


        return output; // TODO
    }
    public static LinkedList<Integer> playWithLinkedList(int numberOfPlayers, int lengthOfPass) {
        LinkedList<Integer> game = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();
        for (int i = 0; i < numberOfPlayers; i++){
            game.add(i+1);
        }
        Iterator<Integer> iterator = game.iterator();
        int playerwithpotato;
        while(!(game.isEmpty())){
            try {
                playerwithpotato = iterator.next();
            } catch(NoSuchElementException ex) {
                iterator = game.iterator();
                playerwithpotato = iterator.next();
            }
            for(int i = 0; i < lengthOfPass; i++){
                try {
                    playerwithpotato = iterator.next();
                } catch(NoSuchElementException ex) {
                    iterator = game.iterator();
                    playerwithpotato = iterator.next();
                }
            }
            output.add(playerwithpotato);
            iterator.remove();
        }
        return output;
    }
    public static void main(String... args) {
// in both methods, the list is the order in which the players are eliminated
// the last player (i.e., the last element in the returned list) is the winner
        System.out.println(playWithDoublyLinkedList(5, 0)); // expected output: [1, 2, 3, 4, 5]
        System.out.println(playWithDoublyLinkedList(5, 1));
        System.out.println(playWithDoublyLinkedList(5, 2));
        System.out.println(playWithDoublyLinkedList(5, 3));
        System.out.println(playWithDoublyLinkedList(5, 4));
        System.out.println(playWithDoublyLinkedList(5, 5));
        System.out.println();
        System.out.println();
        System.out.println(playWithLinkedList(5, 0));
        System.out.println(playWithLinkedList(5, 1)); // expected output: [2, 4, 1, 5, 3]
        System.out.println(playWithLinkedList(5, 2));
        System.out.println(playWithLinkedList(5, 3));
        System.out.println(playWithLinkedList(5, 4));
        System.out.println(playWithLinkedList(5, 5));
    }
}
