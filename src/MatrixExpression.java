/**
 * Represents an immutable expression of matrix and scalar products.
 */

public interface MatrixExpression {

    // Data type definition (abbreviated MatExpr)
    //      MatExpr = Identity + Scalar(double) + Matrix(double[][]) + Product(MatExpr, MatExpr)

    /** @return a matrix expression consisting of just the scalar value */
    public static MatrixExpression make(double value) {
        return new Scalar(value);
    }

    /** @return a matrix expression consisting of just the matrix given */
    public static MatrixExpression make(double[][] array) {
        return new Matrix(array);
    }

    /**
     * requires: m1 and m2 are compatible for multiplication
     * @return m1×m2
     */
    public static MatrixExpression make(MatrixExpression m1, MatrixExpression m2) {
        return new Product(m1, m2);
    }

    /**
     *
     * @return true if the expression is the multiplicative identity
     */
    public boolean isIdentity();


    /** @return the product of all the scalars in this expression */
    public MatrixExpression scalars();

    /** @return the product of all the matrices in this expression.
     * times(scalars(), matrices()) is equivalent to this expression. */
    public MatrixExpression matrices();

    /**
     *
     * @return an expression with the same value, but which may be faster to compute
     */
    public MatrixExpression optimize();

    public String toString();
}
