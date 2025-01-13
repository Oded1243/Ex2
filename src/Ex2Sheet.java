import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class Ex2Sheet implements Sheet {


    public SCell[][] table;


    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }
        eval();
    }
    public Ex2Sheet() {

        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String s = Ex2Utils.EMPTY_CELL;

        Cell c = get(x,y);
        if(c!=null) {s = c.toString();}

        /////////////////////
        return s;
    }

    @Override
    public Cell get(int x, int y) {

        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        CellEntry cell = new CellEntry(cords);
        int _x = cell.getX();
        int _y = cell.getY();
        return table[_x][_y];
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
    public void set(int x, int y, String s) {
        table[x][y].setData(s);
        eval();
    }
    @Override
    public void eval() {

        int[][] dd = depth();
        //Iterate until the biggest depth comes
        for (int q =0;q< findMaxInteger(dd)+1;q=q+1) {
            //Iterate through depth and calculate
            for (int x = 0; x < width(); x = x + 1) {
                for (int y = 0; y < height(); y = y + 1) {
                    if (dd[x][y]==q) {
                        CellEntry cell = new CellEntry(x, y);
                        table[x][y].setData(eval(x, y));
                    }
                }

            }
        }
        for (int x = 0; x < width(); x = x + 1) {
            for (int y = 0; y < height(); y = y + 1) {
                if (dd[x][y]==-1) {
                    CellEntry cell = new CellEntry(x, y);
                    table[x][y].setData(Ex2Utils.ERR_CYCLE);
                }
            }


        }
    }
    @Override
    public int[][] depth() {
        int[][] ans = Depth();
        return ans;
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0;

        /////////////////////
        return ans;
    }

    public int[][] Depth() {
        int width = this.width();
        int height = this.height();

        // Initialize depth matrix and helper arrays
        int[][] depthMatrix = new int[width][height];
        boolean[][] visited = new boolean[width][height];
        boolean[][] inStack = new boolean[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                depthMatrix[i][j] = -1;
            }
        }

        // Compute depth for each cell
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (depthMatrix[i][j] == -1) { // If depth is not computed
                    depthcheck(i, j, depthMatrix, visited, inStack);
                }
            }
        }

        return depthMatrix;
    }

    public int depthcheck(int x, int y, int[][] depthMatrix, boolean[][] visited, boolean[][] inStack) {
        //Check if the cell is valid in the table
        if (!isIn(x, y)) return 0; // Cell is out of bounds, depth = 0

        // If depth is already computed, return it
        if (depthMatrix[x][y] != -1) return depthMatrix[x][y];

        // Detect circular dependency
        if (inStack[x][y]) {
            // Circular dependency found, mark this cell's depth as -1
            depthMatrix[x][y] = -1;
            return -1;
        }

        // Mark the cell as visited and part of the recursion stack
        visited[x][y] = true;
        inStack[x][y] = true;

        Set<String> dependencies = parseDependencies(get(x, y).getData());

        int maxDependencyDepth = 0;

        // Recursive DFS on all dependencies of the current cell
        for (String dep : dependencies) {
            // Convert dependency (e.g., "A1") into coordinates (x, y)
            Cell dependentCell = get(dep);
            if (dependentCell == null) {
                // Skip if the dependent cell is invalid (e.g., out of bounds or empty)
                continue;
            }

            CellEntry cell = new CellEntry(dep);
            int depX = cell.getX(); //
            int depY = cell.getY(); //

            int dependentDepth = depthcheck(depX, depY, depthMatrix, visited, inStack);

            // If a circular dependency is encountered, propagate the failure (-1)
            if (dependentDepth == -1) {
                depthMatrix[x][y] = -1;
                inStack[x][y] = false;
                return -1;
            }

            // Update the maximum dependency depth
            maxDependencyDepth = Math.max(maxDependencyDepth, dependentDepth);
        }

        // The depth of the current cell is 1 + max of its dependencies
        depthMatrix[x][y] = maxDependencyDepth + 1;

        // Remove the cell from the recursion stack
        inStack[x][y] = false;
        for (x=0 ; x < x ; x = x + 1) {
            for (y=0;y<y;y=y+1) {
            }
        }
        return depthMatrix[x][y];
    }

    @Override
    public void load(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Clear the current spreadsheet
            for (int x = 0; x < width(); x++) {
                for (int y = 0; y < height(); y++) {
                    table[x][y] = new SCell(Ex2Utils.EMPTY_CELL);
                }
            }

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split the line into parts
                String[] parts = line.split(",", 3);
                if (parts.length < 3) {
                    // Ignore malformed lines
                    continue;
                }

                try {
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    String cellData = parts[2].trim();

                    if (isIn(x, y)) { // Ensure within bounds
                        table[x][y].setData(cellData);
                    }
                } catch (NumberFormatException e) {
                    // Ignore malformed lines with invalid numbers
                }
            }

            // Recalculate the table after loading new data
            eval();
        }
    }

    @Override
    public void save(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header line
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method\n");

            // Iterate over the table and write only non-empty cells
            for (int x = 0; x < width(); x++) {
                for (int y = 0; y < height(); y++) {
                    String cellData = table[x][y].getData();
                    if (!cellData.equals(Ex2Utils.EMPTY_CELL)) { // Only save non-empty cells
                        writer.write(x + "," + y + "," + cellData + "\n");
                    }
                }
            }
        }
    }


    @Override
    public String eval(int x, int y) {
        String ans = null;
        if(get(x,y)!=null) {
            try {
                ans = String.valueOf(computeForm(x,y));
            } catch (Exception e) {
                ans = Ex2Utils.ERR_FORM;
            }
        }
        return ans;
    }

    // Helper function to parse dependencies from a formula
    public Set<String> parseDependencies(String formula) {
        Set<String> dependencies = new HashSet<>();

        // Example parsing: Find all cell references like "A1", "B2", etc.
        // Customize this regex based on your formula syntax
        String regex = "[A-Za-z][0-9]+";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(formula);

        while (matcher.find()) {
            dependencies.add(matcher.group());
        }

        return dependencies;
    }


    public String computeForm(int x, int y) {
        if (!isIn(x, y)) return "ERROR: Out of bounds";
        // Check if the cell exists within the bounds of the spreadsheet

        // Retrieve the cell
        Cell cell = get(x, y);

        if (cell == null) return Ex2Utils.ERR_FORM;
        String data = cell.getData();
        if (data!=""){
        }
        if (table[x][y].formula != null) {
            data = table[x][y].formula;
        } else {
            data = cell.getData();}

        // Handle cases based on cell type
        if (Functions.IsNumber(data)) {
            // If the cell is a number, return it as a string
            return data;
        } else if (Functions.IsText(data)) {
            // If the cell is text, return it directly
            return data;
        } else if (Functions.IsForm(data)) {
            // If the cell is a formula:

            // Parse dependencies (e.g., "=A1 + B2" should extract A1 and B2)
            Set<String> dependencies = parseDependencies(data);

            // Resolve dependencies and replace them with their actual values
            String formulaToEvaluate = data.substring(1); // Remove the "=" at the start
            for (String dep : dependencies) {
                // Convert cell reference (e.g., "A1") to coordinates
                Cell depCell = get(dep);
                if (depCell == null) return "ERR: (" + dep + ")";
                CellEntry depEntry = new CellEntry(dep);
                int depX = depEntry.getX();
                int depY = depEntry.getY();

                // Recursively compute the value of the dependency
                String depValue = computeForm(depX, depY);

                // If the dependency is text or results in an error, propagate the error
                if (Functions.IsText(depValue) || depValue.startsWith("ERROR")) {
                    return "ERR:" + dep;
                }

                // Replace the dependency reference in the formula with its computed value
                formulaToEvaluate = formulaToEvaluate.replace(dep, depValue);
            }

            // Evaluate the formula using the calculate() method
            try {
                String result = Functions.Calculate((formulaToEvaluate));
                return result;
            } catch (Exception e) {
                return "ERROR: Calculation error in cell (" + x + "," + y + ")";
            }
        }

        // Unknown type or invalid content empty cell
        return Ex2Utils.EMPTY_CELL;
    }

    public static int findMaxInteger(int[][] array) {
        // Initialize max with the smallest possible integer
        int maxNum = Integer.MIN_VALUE;

        // Iterate over all rows
        for (int[] row : array) {
            // Iterate over all elements in the current row
            for (int value : row) {
                // Update max if the current value is greater
                if (value > maxNum) {
                    maxNum = value;
                }
            }
        }

        return maxNum;
    }
}

