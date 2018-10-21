/**
 * Represents an inmutable expression of matrix and scalar products.
 */

public interface MatrixExpression {

    // data type definition (abbreviated MatExpr)
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
}
