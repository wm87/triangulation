package com.htw.Triangulation.fachlogik;

public class PVV_Matrix {

    private double[][] transpvMatrixMalvMatrix = null;

    PVV_Matrix(double[][] vMatrix, Bildkoordinatendaten bildkoorDaten) {

        int matrixAZeilen = bildkoorDaten.getanzBildkoordinaten() * 2;

        double[][] transpvMatrix = new double[1][matrixAZeilen];

        for (int i = 0; i < vMatrix.length; i++) {

            transpvMatrix[0][i] = vMatrix[i][0];
        }

        // Minimum pvv
        transpvMatrixMalvMatrix = new double[1][1];

        for (int i = 0; i < transpvMatrix.length; i++) {
            for (int k = 0; k < transpvMatrix[0].length; k++) {
                transpvMatrixMalvMatrix[i][0] += transpvMatrix[i][k]
                        * vMatrix[k][0];
            }
        }

        // System.out.println("\npvv: " + transpvMatrixMalvMatrix[0][0]);
    }

    public double getMinimum() {

        return transpvMatrixMalvMatrix[0][0];
    }
}