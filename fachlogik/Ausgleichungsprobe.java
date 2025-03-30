package com.htw.Triangulation.fachlogik;

public class Ausgleichungsprobe {

    Ausgleichungsprobe(double[][] transAMalP, double[][] vMatrix,
                       Projektionsdaten projDaten, Passpunktdaten passDaten,
                       Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        int matrixAZeilen = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        double[][] probe = new double[matrixAZeilen][1];

        for (int i = 0; i < transAMalP.length; i++) {
            for (int j = 0; j < vMatrix[0].length; j++) {
                for (int k = 0; k < transAMalP[0].length; k++) {
                    probe[i][j] += transAMalP[i][k] * vMatrix[k][j];
                }
            }
        }

        // Ausgleichungsprobe muss gegen 0 gehen

        System.out.println("\nAusgleichungsprobe\n\n");

        for (double[] doubles : probe) {
            for (int j = 0; j < probe[0].length; j++) {
                System.out.print(doubles[j] + " ");
            }
            System.out.print("\n");
        }
    }
}