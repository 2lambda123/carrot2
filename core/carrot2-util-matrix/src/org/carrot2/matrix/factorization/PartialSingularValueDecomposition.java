/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2011, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.matrix.factorization;

import org.apache.mahout.math.matrix.DoubleMatrix2D;
import org.apache.mahout.math.matrix.linalg.SingularValueDecomposition;

/**
 * Performs matrix factorization using the Singular Value Decomposition algorithm.
 */
@SuppressWarnings("deprecation")
public class PartialSingularValueDecomposition extends MatrixFactorizationBase implements
    IMatrixFactorization
{
    /** The desired number of base vectors */
    protected int k;

    /** The default number of desired base vectors */
    protected static final int DEFAULT_K = -1;

    /** Singular values */
    private double [] S;

    /**
     * Computes a partial SVD of a matrix. Before accessing results, perform computations
     * by calling the {@link #compute()}method.
     * 
     * @param A matrix to be factorized
     */
    public PartialSingularValueDecomposition(DoubleMatrix2D A)
    {
        super(A);

        this.k = DEFAULT_K;
    }

    public void compute()
    {
        // Use Colt's SVD
        SingularValueDecomposition svd;
        if (A.columns() > A.rows())
        {
            svd = new SingularValueDecomposition(A.viewDice());
            V = svd.getU();
            U = svd.getV();
        }
        else
        {
            svd = new SingularValueDecomposition(A);
            U = svd.getU();
            V = svd.getV();
        }

        S = svd.getSingularValues();

        if (k > 0 && k < S.length)
        {
            U = U.viewPart(0, 0, U.rows(), k);
            V = V.viewPart(0, 0, V.rows(), k);
            S = org.apache.mahout.math.Arrays.trimToCapacity(S, k);
        }
    }

    public String toString()
    {
        return "SVD";
    }

    /**
     * Returns singular values of the matrix.
     */
    public double [] getSingularValues()
    {
        return S;
    }

    /**
     * Sets the number of base vectors <i>k </i>.
     * 
     * @param k the number of base vectors
     */
    public void setK(int k)
    {
        this.k = k;
    }

    /**
     * Returns the number of base vectors <i>k </i>.
     */
    public int getK()
    {
        return k;
    }
}
