package cse214HW2;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ToPostfixConverter implements Converter {

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

    @Override
    public String convert(ArithmeticExpression expression) {
        Stack<String> stack = new Stack<>();
        String result = "";
        String first = getToken(expression.getExpression(), 0);

        if (isOperand(first)) {
            result += first.trim() + " ";
        }
        else {
            stack.push(first);
        }

        int index = first.length() - 1;
        String token;
        while(index < expression.getExpression().length() - 1) {
            token = nextToken(expression.getExpression(), index);
            if(token.charAt(0) == Operator.LEFT_PARENTHESIS.getSymbol()){
                stack.push(token);
            }
            else if (token.charAt(0) == Operator.RIGHT_PARENTHESIS.getSymbol()){
                while(stack.peek().charAt(0) != Operator.LEFT_PARENTHESIS.getSymbol()) {
                    result += stack.pop().trim() + " ";
                }
                stack.pop();
            }
            else if (isOperand(token)){
                result += token.trim() + " ";
            }
            else if (Operator.isOperator(token)) {
                if (stack.isEmpty() || stack.peek().charAt(0) == Operator.LEFT_PARENTHESIS.getSymbol()){
                    stack.push(token);
                }
                else if (Operator.of(token.charAt(0)).getRank() < Operator.of(stack.peek()).getRank()){
                    stack.push(token);
                }
                else if (Operator.of(token.charAt(0)).getRank() == Operator.of(stack.peek()).getRank()){
                    result += stack.pop().trim() + " ";
                    stack.push(token);
                }
                else if (Operator.of(token.charAt(0)).getRank() > Operator.of(stack.peek()).getRank()){
                    while (!(stack.isEmpty()) && stack.peek().charAt(0) != Operator.LEFT_PARENTHESIS.getSymbol() && Operator.of(token.charAt(0)).getRank() >= Operator.of(stack.peek()).getRank()){
                        result += stack.pop().trim() + " ";
                    }
                    stack.push(token);
                }
            }
            index += token.length();
        }
        while(!(stack.isEmpty())){
            result += stack.pop().trim() + " ";
        }
        return result;
    }

    @Override
    public String nextToken(String s, int start) {
        String currentToken = getToken(s, start);
        if (start + currentToken.length() >= s.length()) {
            throw new NoSuchElementException("NO TOKEN AFTER");
        }
        return getToken(s, start + currentToken.length());
    }

    public String getToken(String s, int start) {
        TokenBuilder token = new TokenBuilder();
        if (s.charAt(start) == ' ') {
            return " ";
        }
        if (!isOperand(s.substring(start))) {
            token.append(s.charAt(start));
            if (start < s.length() - 1 && s.charAt(start + 1) == ' ') {
                token.append(' ');
            }
            return token.build();
        }
        while(start < s.length() && isOperand(s.substring(start))) {
            token.append(s.charAt(start));
            start++;
        }
        return token.build();
    }

    @Override
    public boolean isOperand(String s) {
        return !(Operator.isOperator(s) || s.charAt(0) == Operator.LEFT_PARENTHESIS.getSymbol() ||
                s.charAt(0) == Operator.RIGHT_PARENTHESIS.getSymbol());

    }
}
