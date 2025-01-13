public class SCell implements Cell {
    private String line;
    private int type;
    public int depth;
    public String formula;


    public String getFormula() {
        return formula;
    }
    public SCell(String s) {
        setData(s);
    }


    @Override
    public int getOrder() {
        return this.depth;
    }

    //@Override
    @Override
    public String toString() {
        return line;
    }

    @Override
    public void setData(String s) {
        line = s;
        // Automatically set type based on content (mock implementation)
        if (this.formula!= null)
            System.out.println(formula);
        //If the cell is a string - set type string
        if (Functions.IsNumber(s)) {
            type = Ex2Utils.NUMBER;
        } else {
            this.type = 1;
        }
        if (Functions.IsNumber(line)) {
            this.type = 2;
        }
        if (Functions.IsForm(line)) {
            this.formula = this.line; ///Critical line!
            this.type = 3;
        }
    }
    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int d) {
        this.depth = d;

    }
}