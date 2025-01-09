import java.util.*;
import java.util.regex.*;


public class Main {
    public static boolean isNumber (String s) {
        boolean ans = true;
        try {
            double d = Double.parseDouble(s);
        } catch (Exception e) {
            ans = false;
        }
        return ans;
    }

    public static boolean isText(String s) {

        return s.matches(".*[a-zA-Z{}].*");
    }

    public static boolean isForm(String s) {

        String formulaPattern = "\\s*\\(*\\d+(\\.\\d+)?([+\\-*/]\\(*\\d+(\\.\\d+)?\\)*)*\\s*";

        return s.matches(formulaPattern);
    }

    public List<CellEntry> extractDependencies(String formula) {
        // This pattern matches cell references like A1, B2, C3, etc.
        Pattern pattern = Pattern.compile("[A-Za-z]+[0-9]+");
        Matcher matcher = pattern.matcher(formula);
        List<CellEntry> dependencies = new ArrayList<>();

        // Find all matches in the formula
        while (matcher.find()) {
            String cellRef = matcher.group();
            // Create a new CellEntry for each reference and add it to the list
            dependencies.add(new CellEntry(cellRef));
        }

        return dependencies;
    }

    public static Index2D parseCellIndex(String cords) {
        System.out.println("Input cords: " + cords);  // Debug

        if (cords == null || cords.isEmpty() || !cords.matches("[A-Za-z]+\\d+")) {
            throw new IllegalArgumentException("Invalid cell coordinates format: " + cords);
        }

        int column = 0;
        int row = -1;
        int i = 0;

        // Parse the column (letters)
        while (i < cords.length() && Character.isLetter(cords.charAt(i))) {
            column = column * 26 + (Character.toUpperCase(cords.charAt(i)) - 'A' + 1);
            i++;
        }
        column--; // Convert to zero-based index

        // Parse the row (numbers)
        StringBuilder rowBuilder = new StringBuilder();
        while (i < cords.length() && Character.isDigit(cords.charAt(i))) {
            rowBuilder.append(cords.charAt(i));
            i++;
        }

        if (rowBuilder.length() > 0) {
            row = Integer.parseInt(rowBuilder.toString()) - 1; // Convert to zero-based index
        }

        // Debug prints
        System.out.println("Parsed row: " + row + ", column: " + column);

        if (row < 0 || column < 0 || i < cords.length()) {
            throw new IllegalArgumentException("Invalid cell coordinates: row=" + row + ", column=" + column);
        }

        return new Index2D() {

            @Override
            public String toString() {
                return "";
            }

            @Override
            public boolean isValid() {
                return false;
            }

            @Override
            public int getX() {
                return 0;
            }

            @Override
            public int getY() {
                return 0;
            }
        };
    }

}