package ClassesToChange;

import ClassesNotToChange.Ex2Utils;
import Interfaces.Cell;
import Interfaces.Sheet;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    private Map<String, String> evalCache = new HashMap<>();

    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < width() && y < height();
    }

    @Override
    public int width() {
        return table.length;
    }

    @Override
    public int height() {
        return table[0].length;
    }

    @Override
    public void set(int x, int y, String c) {
        if (isIn(x, y)) {
            table[x][y] = new SCell(c);
        }
    }

    @Override
    public Cell get(int x, int y) {
        if (isIn(x, y)) {
            return table[x][y];
        }
        return null;
    }

    @Override
    public Cell get(String entry) {
        // Regex with capture groups to separate column and row
        Matcher matcher = Pattern.compile("([A-Za-z]+)([0-9]+)").matcher(entry);

        if (matcher.matches()) {
            // Extract column (letters) and row (numbers)
            String column = matcher.group(1);
            String row = matcher.group(2);

            // Convert column to a zero-based index (e.g., A=0, B=1)
            int x = 0;
            for (char c : column.toUpperCase().toCharArray()) {
                x = x * 26 + (c - 'A' + 1);
            }
            x--;

            int y = Integer.parseInt(row);

            // Return the cell at (x, y)
            return get(x, y);
        }

        // Return null if entry doesn't match the expected format
        return null;
    }


    @Override
    public String value(int x, int y) {
        Cell cell = get(x, y);
        if (cell == null) return Ex2Utils.EMPTY_CELL;
        return eval(x, y);
    }

    @Override
    public String eval(int x, int y) {
        Cell cell = get(x, y);
        if (cell == null) return Ex2Utils.EMPTY_CELL;

        if (cell.getType() == Ex2Utils.NUMBER || cell.getType() == Ex2Utils.TEXT) {
            return cell.getData(); // מחזיר ערך מספרי או טקסטואלי ישיר
        } else if (cell.getType() == Ex2Utils.FORM) {
            try {
                double result = SCell.ComputeForm(cell.getData().substring(1)); // מחשב את הנוסחה
                return String.valueOf(result);
            } catch (Exception e) {
                cell.setType(Ex2Utils.ERR_FORM_FORMAT);
                return Ex2Utils.ERR_FORM;
            }
        } else if (cell.getType() == Ex2Utils.ERR_CYCLE_FORM) {
            return Ex2Utils.ERR_CYCLE; // טיפול במעגלים
        }
        return Ex2Utils.EMPTY_CELL;
    }


    @Override
    public void eval() {
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                eval(x, y);
            }
        }
    }

    @Override
    public int[][] depth() {
        int[][] depths = new int[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                depths[x][y] = computeDepth(x, y, new HashSet<>());
            }
        }
        return depths;
    }

    private int computeDepth(int x, int y, Set<String> visited) {
        if (!isIn(x, y)) return -1;
        String key = x + "," + y;
        if (visited.contains(key)) return -1; // Cycle detected
        visited.add(key);

        Cell cell = get(x, y);
        if (cell == null || cell.getType() == Ex2Utils.TEXT || cell.getType() == Ex2Utils.NUMBER) {
            return 0;
        } else if (cell.getType() == Ex2Utils.FORM) {
            String formula = cell.getData().substring(1);
            int maxDepth = 0;
            for (String ref : extractReferences(formula)) {
                Cell refCell = get(ref);
                if (refCell instanceof CellEntry) {
                    int refX = ((CellEntry) refCell).getX();
                    int refY = ((CellEntry) refCell).getY();
                    maxDepth = Math.max(maxDepth, computeDepth(refX, refY, new HashSet<>(visited)));
                }
            }
            return 1 + maxDepth;
        }
        return 0;
    }


    private List<String> extractReferences(String formula) {
        List<String> refs = new ArrayList<>();
        Matcher matcher = Pattern.compile("[A-Z]+\\d+").matcher(formula);
        while (matcher.find()) {
            refs.add(matcher.group());
        }
        return refs;
    }

    @Override
    public void save(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment\n");
            for (int x = 0; x < width(); x++) {
                for (int y = 0; y < height(); y++) {
                    Cell cell = get(x, y);
                    if (cell != null && !cell.getData().equals(Ex2Utils.EMPTY_CELL)) {
                        writer.write(x + "," + y + "," + cell.getData() + "\n");
                    }
                }
            }
        }
    }

    @Override
    public void load(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); // Skip header line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length >= 3) {
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    String data = parts[2];
                    set(x, y, data);
                }
            }
        }
    }
}

