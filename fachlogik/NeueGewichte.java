package com.htw.Triangulation.fachlogik;

public class NeueGewichte {

    private double[][] pMatrix = null;

    NeueGewichte(double[][] transponiertedesignMatrix,
                 Designmatrix DesignMatrix, double[][] gewichtsreziprokenMatrix,
                 double[][] p_Matrix, double[][] vMatrix,
                 Projektionsdaten projDaten, Passpunktdaten passDaten,
                 Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        this.pMatrix = p_Matrix;

        for (int i = 0; i < pMatrix.length; i++) {
            for (int j = 0; j < pMatrix[0].length; j++) {

                pMatrix[i][i] = 1;
            }
        }
    }

    public double[][] getpMatrix() {

        return pMatrix;
    }
}