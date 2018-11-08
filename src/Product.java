class Product implements MatrixExpression {

    /** Identity for all matrix computations. */
    public static final MatrixExpression I = new Identity();

    private final MatrixExpression m1;
    private final MatrixExpression m2;
    // RI: m1's column count == m2's row count, or m1 or m2 is scalar
    public Product(MatrixExpression m1, MatrixExpression m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    public MatrixExpression times(MatrixExpression m1, MatrixExpression m2) {
        if (m1.isIdentity()) return m2;
        else if (m2.isIdentity()) return m1;
        return new Product(m1, m2);
    }

    @Override
    public boolean isIdentity() {
        return m1.isIdentity() && m2.isIdentity();
    }

    @Override
    public MatrixExpression scalars() {
        return times(m1.scalars(), m2.scalars());
    }

    @Override
    public MatrixExpression matrices() {
        return times(m1.matrices(), m2.matrices());
    }

    @Override
    public MatrixExpression optimize() {
        return times(scalars(), matrices());
    }

    @Override
    public String toString() {
        return m1.toString() + " * " + m2.toString();
    }
}
