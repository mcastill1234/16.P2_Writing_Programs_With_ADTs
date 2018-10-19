class Identity implements MatrixExpression {
    public Identity() {
    }

    @Override
    public boolean isIdentity() {
        return true;
    }

    @Override
    public MatrixExpression scalars() {
        return this;
    }

    @Override
    public MatrixExpression matrices() {
        return this;
    }

    @Override
    public MatrixExpression optimize() {
        return this;
    }
}
