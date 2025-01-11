package ClassesToChange;

import ClassesNotToChange.Ex2Utils;
import Interfaces.Cell;

import java.util.Stack;

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;

    /**
     * Constructor that initializes the cell with data.
     * @param s the initial data of the cell
     */
    public SCell(String s) {
        setData(s);
    }

    /**
     * Computes the natural order of this cell.
     * If the cell is of type TEXT or NUMBER, returns 0.
     * Otherwise, computes 1 + the maximum order of dependent cells (mock implementation here).
     * @return an integer representing the number of rounds needed to compute this cell
     */
    @Override
    public int getOrder() {
        if (type == Ex2Utils.TEXT || type == Ex2Utils.NUMBER) {
            return 0;
        }
        // Mock computation for dependent cells (as no dependencies are implemented)
        // Replace this with actual dependency logic.
        return order;
    }

    /**
     * Returns the string representation of this cell.
     * @return the input text (string) of the cell
     */
    @Override
    public String toString() {

        return getData();
    }

    /**
     * Sets the data of the cell.
     * @param s the new data string to set
     */
    @Override
    public void setData(String s) {
        line = s;
        // Automatically set type based on content (mock implementation)
        if (isNumber(s)) {
            type = Ex2Utils.NUMBER;
        } else if (s.startsWith("=")) {
            type = Ex2Utils.FORM;
        } else {
            type = Ex2Utils.TEXT;
        }
    }

    /**
     * Gets the data of the cell.
     * @return the data string
     */
    @Override
    public String getData() {

        return line;
    }

    /**
     * Gets the type of the cell.
     * @return the type of the cell as defined in Ex2Utils
     */
    @Override
    public int getType() {

        return type;
    }

    /**
     * Sets the type of the cell.
     * @param t the type to set
     */
    @Override
    public void setType(int t) {
        type = t;
    }

    /**
     * Sets the order of the cell.
     * @param t the order to set
     */
    @Override
    public void setOrder(int t) {
        order = t;
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double ComputeForm(String expression) {
        expression = expression.replaceAll("\\s+", "");   // Remove spaces and validate
        if (!Main.isForm(expression)) {
            throw new IllegalArgumentException("Invalid expression");
        }

        String postfix = infixToPostfix(expression);   // Convert infix to postfix (Reverse Polish Notation)

        return evaluatePostfix(postfix);   // Evaluate the postfix expression
    }

    private static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                // Append numbers directly to postfix
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(' ').append(stack.pop());
                }
                stack.pop(); // Remove '('
            } else if (isOperator(c)) {
                postfix.append(' '); // Separate tokens
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    postfix.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            postfix.append(' ').append(stack.pop());
        }

        return postfix.toString();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.matches("[0-9.]+")) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        stack.push(a / b);
                        break;
                }
            }
        }

        return stack.pop();
    }

    public static boolean isText(String s) {

        return s.matches(".*[a-zA-Z{}].*");
    }

    public static boolean isForm(String s) {
        // Regular expressions
        String cellPattern = "^([A-Ia-i][0-9]|[A-Ia-i]1[0-6])$";
        if (s.matches(cellPattern)) {
            CellEntry XY = new CellEntry(s);
            SCell value = new SCell()
        }
        // Match =A1, etc.
        String mathPattern = "^\\(*\\d+(\\.\\d+)?(\\s*[+\\-*/]\\s*\\(*\\d+(\\.\\d+)?\\)*)*\\s*$"; // Match math expressions

        // Check if it matches either a cell reference pattern or a mathematical expression
        return s.matches(mathPattern);
    }
}
