package ClassesToChange;

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