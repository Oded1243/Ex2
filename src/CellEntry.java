
import java.util.ArrayList;
public class CellEntry implements Index2D {
    private String[][] grid = new String[Ex2Utils.WIDTH][Ex2Utils.HEIGHT];
    private int _x ;
    private int _y ;
    private String stringindex;

    public CellEntry(int x, int y) {
        for (int i=0; i< Ex2Utils.WIDTH;i ++) {
            for (int j = 0; j < Ex2Utils.HEIGHT; j++) {
                char letter = (char) (i + 65);
                this.grid[i][j] = String.valueOf((letter)) + j;
            }
        }
        this._x = x;
        this._y = y;
        stringindex = this.toString();

    }

    public CellEntry(String CellName) {
        CellName = CellName.toUpperCase();
        this.stringindex = CellName;
        if (isValid()) {
            for (int i = 0; i < Ex2Utils.WIDTH; i++) {
                for (int j = 0; j < Ex2Utils.HEIGHT; j++) {
                    char letter = (char) (i + 65);
                    this.grid[i][j] = String.valueOf((letter)) + j;
                }
            }
            ArrayList<Integer> result = toCords();

            this._x = result.get(0);
            this._y = result.get(1);
        }else
            throw new IllegalArgumentException("Illigal coords");
    }

    @Override
    public boolean isValid() {
        return stringindex.matches("^[A-Za-z][0-9]{1,2}$");
    }

    @Override
    public String toString() {

        if (_x >= 0 && _x < Ex2Utils.WIDTH && _y >= 0 && _y < Ex2Utils.HEIGHT) {
            return grid[_x][_y];
        } else {
            throw new IllegalStateException("CellEntry coordinates are out of bounds: " + _x + ", " + _y);
        }
    }

    public ArrayList<Integer> toCords() {
        ArrayList<Integer> result = new ArrayList<>();
        int x = (int)stringindex.charAt(0) - 65;
        int y = Integer.parseInt(stringindex.substring(1));
        result.add(x);
        result.add(y);
        return result;
    }

    @Override
    public int getX() {return this._x;}

    @Override
    public int getY() {return this._y;}
}

