package com.lzb.mixed;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 带括号计算器<br/>
 * (3 + 4) * 5 - 6
 * 前缀表达式(波兰表达式):-*+3456，数字入栈再弹出符号计算
 * 中缀表达式:我们常用的顺序
 * 后缀表达式(逆波兰表达式):34+5*6-//3+4=7,7*5=35,35-6=29
 * 中缀表达式转后缀表达式?
 * Created on : 2020-12-26 09:57
 * @author lizebin
 */
public class Calculator {


    /**
     * 操作符
     */
    private enum Operation {
        ADD('+', 1),
        SUBSTRACE('-', 1),
        MUTITI('*', 2),
        DIVIDE('/', 2),
        //"("不作为运算符
        LEFT_BRACKET('(', 3),
        //")"不作为运算符
        RIGHT_BRACKET(')', 3);

        /**
         * 操作符
         */
        private char c;
        /**
         * 优先级
         */
        private int priority;

        Operation(char c, int priority) {
            this.c = c;
            this.priority = priority;
        }

        public char getC() {
            return c;
        }

        public void setC(char c) {
            this.c = c;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public static Optional<Operation> of(char c) {
            return Stream.of(Operation.values()).filter(item -> c == item.getC()).findAny();
        }

        public int calculate(int i, int j) {
            if (this.c == ADD.c) {
                return i + j;
            }
            if (this.c == SUBSTRACE.c) {
                return i - j;
            }
            if (this.c == MUTITI.c) {
                return i * j;
            }
            if (this.c == DIVIDE.c) {
                return i / j;
            }
            return 0;
        }
    }

    /**
     * 输入：1 + 2 + 3 * 4 - 5
     * @param operationString
     * @return
     */
    public static int calculate(String operationString) {

        //数字栈
        MyStack<Integer> numberStack = new MyStack<>(10000);
        //操作符栈
        MyStack<Operation> operationStack = new MyStack<>(10000);

        char[] array = operationString.toCharArray();

        StringBuilder numberString = new StringBuilder();
        for (int i = 0, length = array.length; i < length; i++) {
            char c = array[i];

            if (Character.isDigit(c)) {
                numberString.append(Objects.toString(c));
                if (i == (length - 1)) {
                    numberStack.push(Integer.valueOf(numberString.toString()));
                } else {
                    //后一位非数字，入栈
                    if (!Character.isDigit(array[i + 1])) {
                        numberStack.push(Integer.valueOf(numberString.toString()));
                        numberString = new StringBuilder();
                    }
                }
            }

            Operation.of(c).ifPresent(operation -> {
                //判断操作符优先级，先计算
                if (operationStack.isEmpty()) {
                    operationStack.push(operation);
                    return;
                }
                if (operation.c == Operation.RIGHT_BRACKET.c) {
                    Operation leftBracket = operationStack.pop();
                    while (leftBracket.c != Operation.LEFT_BRACKET.c) {
                        int num2 = numberStack.pop(), num1 = numberStack.pop();
                        numberStack.push(leftBracket.calculate(num1, num2));
                        leftBracket = operationStack.pop();
                    }
                    return;
                }
                if (operation.c == Operation.LEFT_BRACKET.c) {
                    operationStack.push(operation);
                    return;
                }
                while (!operationStack.isEmpty()) {
                    Operation top = operationStack.peek();
                    if ((top.priority < operation.priority) || top.c == Operation.LEFT_BRACKET.c || top.c == Operation.RIGHT_BRACKET.c) {
                        break;
                    }
                    //先计算低优先级的结果
                    top = operationStack.pop();
                    int num2 = numberStack.pop(), num1 = numberStack.pop();
                    numberStack.push(top.calculate(num1, num2));
                }
                operationStack.push(operation);
            });

            //最后一个元素
            if (i == (length - 1)) {
                while (!operationStack.isEmpty()) {
                    Operation operation = operationStack.pop();
                    int num2 = numberStack.pop(), num1 = numberStack.pop();
                    numberStack.push(operation.calculate(num1, num2));
                }
            }
        }
        return numberStack.pop();
    }

    public static void main(String[] args) {
        //String inputString = "(8-2)*3+2";
        String inputString = "((20 * 2) / 2 * 1 * 1 - 1) * 1 * 1 / 1 + (2 + 3) * 24 + 25 + 8 - 2 * 3 + 2 + 4 / (2 + 2) * 3";
        System.out.println(Calculator.calculate(inputString));
    }
}
