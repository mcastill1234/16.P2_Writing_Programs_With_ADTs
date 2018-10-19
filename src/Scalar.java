class Scalar implements MatrixExpression {
    private final double value;

    /** Identity for all matrix computations. */
    public static final MatrixExpression I = new Identity();

    public Scalar(double value) {
        this.value = value;
    }

    @Override
    public boolean isIdentity() {
        return value == 1;
    }

    @Override
    public MatrixExpression scalars() {
        return this;
    }

    @Override
    public MatrixExpression matrices() {
        return I;
    }

    @Override
    public MatrixExpression optimize() {
        return this;
    }
}
