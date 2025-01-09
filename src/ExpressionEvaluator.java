import java.util.Stack;

public class ExpressionEvaluator {
    public static double evaluate(String expression) {
        expression = expression.replaceAll("\\s+", "");   // Remove spaces and validate
        if (!isForm(expression)) {
            throw new IllegalArgumentException("Invalid expression");
        }

        String postfix = infixToPostfix(expression);   // Convert infix to postfix (Reverse Polish Notation)

        return evaluatePostfix(postfix);   // Evaluate the postfix expression
    }

    public static boolean isForm(String s) {

        String formulaPattern = "\\s*\\(*\\d+(\\.\\d+)?([+\\-*/]\\(*\\d+(\\.\\d+)?\\)*)*\\s*";

        return s.matches(formulaPattern);
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

}
