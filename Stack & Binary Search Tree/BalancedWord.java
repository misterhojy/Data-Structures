package cse214HW2;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BalancedWord {

    private static class Stack<E> implements cse214HW2.Stack<E> {

        private final LinkedList<E> thelist;

        public Stack(){
            this.thelist = new LinkedList<>();
        }

        @Override
        public void push(E element) {
            this.thelist.add(0, element);
        }

        public boolean isEmpty(){
            return thelist.isEmpty();
        }

        @Override
        public E pop() {
            if(thelist.isEmpty()){
                throw new NoSuchElementException();
            }
            return thelist.remove(0);
        }

        @Override
        public E peek() {
            if (thelist.isEmpty()) {
                throw new NoSuchElementException();
            }
            return thelist.get(0);
        }
    }
    private final String word;

    public BalancedWord(String word) {
        if (isBalanced(word))
            this.word = word;
        else
            throw new IllegalArgumentException(String.format("%s is not a balanced word.", word));
    }
    private static boolean isBalanced(String word) {
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < word.length(); i++){

            String token = word.substring(i,i+1);
            if (token.equals("(") || token.equals("[") || token.equals("{")) {
                stack.push(token);
            }
            else if (token.equals(")") || token.equals("]") || token.equals("}")) {
                if (stack.isEmpty()) {
                    return false;
                }
                else{
                    String prev = stack.pop();
                    boolean match = false;
                    switch (token) {
                        case ")":
                            if (prev.equals("("))
                                match = true;
                            break;
                        case "]":
                            if (prev.equals("["))
                                match = true;
                            break;
                        case "}":
                            if (prev.equals("{"))
                                match = true;
                            break;
                    }
                    if (!match){
                        return false;
                    }
                }
            }
            if ((i == word.length()-1) && !(stack.isEmpty())) {
                return false;
            }
        }
        return true;
    }

    public String getWord() {
        return word;
    }
    public static void main (String [] args){
        BalancedWord word = new BalancedWord("");
        System.out.println(word.getWord());


    }
}
