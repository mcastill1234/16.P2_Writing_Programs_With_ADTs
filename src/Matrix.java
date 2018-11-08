class Matrix implements MatrixExpression {

    private final double[][] array;

    /** Identity for all matrix computations. */
    public static final MatrixExpression I = new Identity();

    // RI: array.length > 0, and all array[i] are equal nonzero length
    public Matrix(double[][] array) {
        this.array = array; // note: danger!
    }

    @Override
    public boolean isIdentity() {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                double expected = (row == col) ? 1 : 0;
                if (array[row][col] != expected) { return false; }
            }
        }
        return true;
    }

    @Override
    public MatrixExpression scalars() {
        return I;
    }

    @Override
    public MatrixExpression matrices() {
        return this;
    }

    @Override
    public MatrixExpression optimize() {
        return this;
    }

    @Override
    public String toString() {
        String MatrixString = "[ ";

        for (int row = 0; row < array.length; row++) {
            MatrixString += "[";
            for (int col = 0; col < array[row].length; col++) {
                if (col == array[row].length-1) {
                    MatrixString += String.valueOf(array[row][col]);
                } else {
                    MatrixString += String.valueOf(array[row][col]) + " ";
                }

            }
            MatrixString += "]";
        }
        MatrixString += " ]";
        return MatrixString;
    }
}
