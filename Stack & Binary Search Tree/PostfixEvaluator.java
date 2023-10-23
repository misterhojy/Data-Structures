package cse214HW2;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PostfixEvaluator implements Evaluator{

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
    public double evaluate(String expressionString) {
        Stack<Double> stack = new Stack<>();
        int index = 0;

        while(index < expressionString.length()){
            String token = "";
            while(!(expressionString.charAt(index) == ' ')){
                token += expressionString.charAt(index);
                index++;
            }
            index ++;
            if (Operator.isOperator(token.charAt(0))){
                double operand1 = stack.pop();
                double operand2 = stack.pop();
                double result = 0;
                Operator operator = Operator.of(token);
                if(operator == Operator.ADDITION){
                    result = operand1 + operand2;
                }
                else if(operator == Operator.SUBTRACTION){
                    result = operand2 - operand1;           //if wrong flip
                }
                else if(operator == Operator.MULTIPLICATION){
                    result = operand1 * operand2;
                }
                else if(operator == Operator.DIVISION){
                    result = operand2 / operand1;
                }
                stack.push(result);
            }
            else {
                stack.push(Double.parseDouble(token));
            }
        }
        return Double.parseDouble(String.format("%.2f", stack.pop()));
    }
}
