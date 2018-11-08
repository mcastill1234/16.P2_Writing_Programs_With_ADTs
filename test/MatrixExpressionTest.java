import org.junit.Test;
import static org.junit.Assert.*;


public class MatrixExpressionTest {

    // Testing strategy:
    // Test for isIdentity method:
    //      Scalar = 1, 0, >2, <0
    //      Matrix = identity and not identity
    //      Product = scalar product, matrix product
    // Tests for toString method
    //      Test 0, 1, 2 scalars in expression
    //      Test 0, 1, 2 matrices in expression
    //      Test scalars and matrices in expression
    // Test for scalars method:
    //      Test 0, 1, 2, >2 scalars in expression
    //      Test on scalars and matrix expression
    // Tests for Optimize method
    //      Number of scalars in expression: 0, 1, 2, >2
    //      Position of scalar in expression tree: Immediate left, immediate right, left of left, left of right
    //      right of left, right of right

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // Tests for isIdentity method
    // Covers Scalar 1, 0, >2, <0
    @Test public void testScalarIdentity() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(1);
        MatrixExpression testMatExpr2 = MatrixExpression.make(0);
        MatrixExpression testMatExpr3 = MatrixExpression.make(3);
        MatrixExpression testMatExpr4 = MatrixExpression.make(-4);
        assertTrue(testMatExpr1.isIdentity());
        assertFalse(testMatExpr2.isIdentity());
        assertFalse(testMatExpr3.isIdentity());
        assertFalse(testMatExpr4.isIdentity());
    }

    // Covers Matrix identity and not identity
    @Test public void testMatrixIdentity() {
        double[][] testArray1 = { {1 ,0}, {0, 1}};
        double[][] testArray2 = { {5 ,3}, {4, -1}};
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr2 = MatrixExpression.make(testArray2);
        assertTrue(testMatExpr1.isIdentity());
        assertFalse(testMatExpr2.isIdentity());
    }

    // Covers Product of Identities and Product of not Identities
    @Test public void testProductIdentity() {
        MatrixExpression testScalarExpr1 = MatrixExpression.make(1);
        MatrixExpression testScalarExpr2 = MatrixExpression.make(2);
        double[][] testArray1 = { {1 ,0}, {0, 1}};
        double[][] testArray2 = { {5 ,3}, {4, -1}};
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr2 = MatrixExpression.make(testArray2);
        MatrixExpression testProduct1 = MatrixExpression.make(testScalarExpr1, testScalarExpr1);
        MatrixExpression testProduct2 = MatrixExpression.make(testScalarExpr1, testScalarExpr2);
        MatrixExpression testProduct3 = MatrixExpression.make(testScalarExpr1, testMatExpr1);
        MatrixExpression testProduct4 = MatrixExpression.make(testScalarExpr1, testMatExpr2);
        MatrixExpression testProduct5 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        assertTrue(testProduct1.isIdentity());
        assertFalse(testProduct2.isIdentity());
        assertTrue(testProduct3.isIdentity());
        assertFalse(testProduct4.isIdentity());
        assertFalse(testProduct5.isIdentity());
    }

    // Tests for toString method
    // Covers toString with one scalar in expression
    @Test public void testToStringScalar() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(-5);
        assertEquals("-5.0", testMatExpr1.toString());
    }

    // Covers toString with one matrix in expression
    @Test public void testToStringMatrix() {
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        String Expected = "[ [5.0 3.0][4.0 -1.0] ]";
        assertEquals(Expected, testMatExpr1.toString());
    }

    // Covers toString with several scalars in expression
    @Test public void testToStringProductScalars() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(-5);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-34);
        MatrixExpression testMatExpr3 = MatrixExpression.make(54.45);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        MatrixExpression testProductExpr2 = MatrixExpression.make(testProductExpr1, testMatExpr3);
        String Expected = "-5.0 * -34.0 * 54.45";
        assertEquals(Expected, testProductExpr2.toString());
    }

    // Covers toString with two matrices in expression
    @Test public void testToStringProductMatrices() {
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        double[][] testArray2 = { {1 ,0}, {0, 1} };
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr2 = MatrixExpression.make(testArray2);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        String Expected = "[ [5.0 3.0][4.0 -1.0] ] * [ [1.0 0.0][0.0 1.0] ]";
        assertEquals(Expected, testProductExpr1.toString());
    }

    // Covers toString with scalar and matrix in expression
    @Test public void testToStringProductScalarMatrix() {
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-5);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr2, testMatExpr1);
        String Expected = "-5.0 * [ [5.0 3.0][4.0 -1.0] ]";
        assertEquals(Expected, testProductExpr1.toString());
    }

    // Tests for scalars method
    // Covers one scalar in expression
    @Test public void testScalarsOneScalar() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(12.45);
        MatrixExpression testScalar1 = testMatExpr1.scalars();
        String Expected = "12.45";
        assertEquals(Expected, testScalar1.toString());
    }

    // Covers two scalars in expression
    @Test public void testScalarsTwoScalars() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(12.45);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-12.46);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        MatrixExpression testScalar1 = testProductExpr1.scalars();
        String Expected = "12.45 * -12.46";
        assertEquals(Expected, testScalar1.toString());
    }

    // Covers expression with scalar and matrix
    @Test public void testScalarsMatrixExpression() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(0.05);
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr2 = MatrixExpression.make(testArray1);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        MatrixExpression testScalar1 = testProductExpr1.scalars();
        String Expected = "0.05";
        assertEquals(Expected, testScalar1.toString());
    }

    // Covers expression with two scalars and two matrices
    @Test public void testScalarsBigExpression() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(0.05);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-105.05);
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        double[][] testArray2 = { {1 ,0}, {0, 1} };
        MatrixExpression testMatExpr3 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr4 = MatrixExpression.make(testArray2);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        MatrixExpression testProductExpr2 = MatrixExpression.make(testProductExpr1, testMatExpr3);
        MatrixExpression testProductExpr3 = MatrixExpression.make(testProductExpr2, testMatExpr4);
        MatrixExpression testScalar1 = testProductExpr3.scalars();
        String Expected = "0.05 * -105.05";
        assertEquals(Expected, testScalar1.toString());
}

    // Tests for Matrices method
    // Covers one matrix in expression
    @Test public void testMatricesOneMatrix() {
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatrix1 = testMatExpr1.matrices();
        String Expected = "[ [5.0 3.0][4.0 -1.0] ]";
        assertEquals(Expected, testMatrix1.toString());
    }

    // Covers two matrices in expression
    @Test public void testMatricesTwoMatrices() {
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        double[][] testArray2 = { {1.5 ,-5}, {3.1, 5} };
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr2 = MatrixExpression.make(testArray2);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        MatrixExpression testMatrix1 = testProductExpr1.matrices();
        String Expected = "[ [5.0 3.0][4.0 -1.0] ] * [ [1.5 -5.0][3.1 5.0] ]";
        assertEquals(Expected, testMatrix1.toString());
    }

    // Covers scalar and matrix in expression
    @Test public void testMatricesScalarMatrix() {
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-5);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr2, testMatExpr1);
        MatrixExpression testMatrix1 = testProductExpr1.matrices();
        String Expected = "[ [5.0 3.0][4.0 -1.0] ]";
        assertEquals(Expected, testMatrix1.toString());
    }

    // Covers expression with two scalars and two matrices
    @Test public void testMatricesBigExpression() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(0.05);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-105.05);
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        double[][] testArray2 = { {1.5 ,-5}, {3.1, 5} };
        MatrixExpression testMatExpr3 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr4 = MatrixExpression.make(testArray2);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        MatrixExpression testProductExpr2 = MatrixExpression.make(testProductExpr1, testMatExpr3);
        MatrixExpression testProductExpr3 = MatrixExpression.make(testProductExpr2, testMatExpr4);
        MatrixExpression testMatrix1 = testProductExpr3.matrices();
        String Expected = "[ [5.0 3.0][4.0 -1.0] ] * [ [1.5 -5.0][3.1 5.0] ]";
        assertEquals(Expected, testMatrix1.toString());
    }

    // Tests for optimize method
    // Covers no scalars in expression: X -> X
    @Test public void testOptimizeOneMatrix() {
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr1 = MatrixExpression.make(testArray1);
        MatrixExpression testMatrix1 = testMatExpr1.optimize();
        String Expected = "[ [5.0 3.0][4.0 -1.0] ]";
        assertEquals(Expected, testMatrix1.toString());
    }

    // Covers 1 scalar, immediate left: aX -> aX
    @Test public void testOptimizeScalarMatrix() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(10.9);
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr2 = MatrixExpression.make(testArray1);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr2);
        MatrixExpression testOptimize1 = testProductExpr1.optimize();
        String Expected = "10.9 * [ [5.0 3.0][4.0 -1.0] ]";
        assertEquals(Expected, testOptimize1.toString());
    }

    // Covers 2 scalars, immediate left, right of right: a(Xb) -> (ab)X
    @Test public void testOptimize2Scalars1Matrix() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(0.05);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-105.05);
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr3 = MatrixExpression.make(testArray1);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr3, testMatExpr2);
        MatrixExpression testProductExpr2 = MatrixExpression.make(testMatExpr1, testProductExpr1);
        MatrixExpression testOptimize1 = testProductExpr2.optimize();
        String Expected = "0.05 * -105.05 * [ [5.0 3.0][4.0 -1.0] ]";
        System.out.println("Original Expression: " + testProductExpr2.toString());
        System.out.println("Optimized Expression: " + testOptimize1.toString());
        assertEquals(Expected, testOptimize1.toString());
    }

    // Covers 2 scalars, immediate right, left-of-left: (aX)b -> (ab)X
    @Test public void testOptimize2Scalars1Matrix2() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(0.05);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-105.05);
        double[][] testArray1 = { {5 ,3}, {4, -1} };
        MatrixExpression testMatExpr3 = MatrixExpression.make(testArray1);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr1, testMatExpr3);
        MatrixExpression testProductExpr2 = MatrixExpression.make(testProductExpr1, testMatExpr2);
        MatrixExpression testOptimize1 = testProductExpr2.optimize();
        String Expected = "0.05 * -105.05 * [ [5.0 3.0][4.0 -1.0] ]";
        System.out.println("Original Expression: " + testProductExpr2.toString());
        System.out.println("Optimized Expression: " + testOptimize1.toString());
        assertEquals(Expected, testOptimize1.toString());
    }

    // Convers 2 scalars left-of-right, right-of-left: (Xa)(bY) -> (((ab)X)Y)
    @Test public void testOptimizeBigExpression() {
        MatrixExpression testMatExpr1 = MatrixExpression.make(0.05);
        MatrixExpression testMatExpr2 = MatrixExpression.make(-105.05);
        double[][] testArray1 = {{5, 3}, {4, -1}};
        double[][] testArray2 = {{1.5, -5}, {3.1, 5}};
        MatrixExpression testMatExpr3 = MatrixExpression.make(testArray1);
        MatrixExpression testMatExpr4 = MatrixExpression.make(testArray2);
        MatrixExpression testProductExpr1 = MatrixExpression.make(testMatExpr3, testMatExpr1);
        MatrixExpression testProductExpr2 = MatrixExpression.make(testMatExpr2, testMatExpr4);
        MatrixExpression testProductExpr3 = MatrixExpression.make(testProductExpr1, testProductExpr2);
        MatrixExpression testOptimize1 = testProductExpr3.optimize();
        String Expected = "0.05 * -105.05 * [ [5.0 3.0][4.0 -1.0] ] * [ [1.5 -5.0][3.1 5.0] ]";
        System.out.println("Original Expression: " + testProductExpr3.toString());
        System.out.println("Optimized Expression: " + testOptimize1.toString());
        assertEquals(Expected, testOptimize1.toString());
    }
}
