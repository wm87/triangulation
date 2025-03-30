package com.htw.Triangulation.fachlogik;

public class Verbesserungsvektor {

    private double[][] v_Vektor = null;

    Verbesserungsvektor(Designmatrix DesignMatrix, double[][] zuschlaege,
                        double[][] lstrichMatrix, Projektionsdaten projDaten,
                        Passpunktdaten passDaten, Uebertragungspunktdaten uebDaten,
                        Bildkoordinatendaten bildkoorDaten) {

        int matrixAZeilen = bildkoorDaten.getanzBildkoordinaten() * 2;

        v_Vektor = new double[matrixAZeilen][1];

        double[][] aMalxMatrix = new double[matrixAZeilen][1];

        for (int i = 0; i < DesignMatrix.getDesignmatrix().length; i++) {
            for (int k = 0; k < DesignMatrix.getDesignmatrix()[0].length; k++) {
                aMalxMatrix[i][0] += DesignMatrix.getDesignmatrix()[i][k]
                        * zuschlaege[k][0];
            }
        }

        // v-Matrix

        for (int i = 0; i < v_Vektor.length; i++) {
            v_Vektor[i][0] = aMalxMatrix[i][0] - lstrichMatrix[i][0];
        }

        /*
         * System.out.println("\nvMatrix\n\n");
         *
         * for (int i = 0; i < v_Vektor.length; i++) { for (int j = 0; j <
         * v_Vektor[0].length; j++) { System.out.print(v_Vektor[i][j] + ""); }
         * System.out.print("\n"); }
         */
    }

    public double[][] getv_Vektor() {

        return v_Vektor;
    }
}