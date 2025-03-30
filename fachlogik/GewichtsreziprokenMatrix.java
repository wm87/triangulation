package com.htw.Triangulation.fachlogik;

public class GewichtsreziprokenMatrix {

    private double[][] gewichtsreziprokenMatrix = null;

    GewichtsreziprokenMatrix(double[][] N_Matrix, Projektionsdaten projDaten,
                             Passpunktdaten passDaten, Uebertragungspunktdaten uebDaten,
                             Bildkoordinatendaten bildkoorDaten) {

        int matrixASpalten = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        double[][] d = new double[matrixASpalten][matrixASpalten];

        for (int i = 0; i < N_Matrix.length; i++) {
            for (int j = 0; j < N_Matrix[0].length; j++) {
                d[i][j] = N_Matrix[i][j];
            }
        }

        // N^-1 Matrix
        gewichtsreziprokenMatrix = new double[matrixASpalten][matrixASpalten];

        // nach dem Gauß-Jordan Algorithmus
        gewichtsreziprokenMatrix = InverseMatrix.invert(d);
    }

    public double[][] getgewichtsreziprokenMatrix() {

        return gewichtsreziprokenMatrix;
    }
}