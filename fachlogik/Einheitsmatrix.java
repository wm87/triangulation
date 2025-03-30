package com.htw.Triangulation.fachlogik;

public class Einheitsmatrix {

    private double[][] pMatrix = null;

    Einheitsmatrix(Projektionsdaten projDaten, Passpunktdaten passDaten,
                   Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        int matrixASpalten = bildkoorDaten.getanzBildkoordinaten() * 2;

        // P --> Gewichtsmatrix
        pMatrix = new double[matrixASpalten][matrixASpalten];

        // Gewichtsmatrix P erzeugen (P = E)
        for (int i = 0; i < pMatrix.length; i++) {
            for (int j = 0; j < pMatrix[0].length; j++) {
                pMatrix[i][j] = 0;
                pMatrix[j][j] = 1;
            }
        }
    }

    public double[][] getpMatrix() {

        return pMatrix;
    }
}
