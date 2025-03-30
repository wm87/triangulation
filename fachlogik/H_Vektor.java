package com.htw.Triangulation.fachlogik;

public class H_Vektor {

    private double[][] lstrich = null;
    private double[][] h_Vektor = null;

    private int u;

    H_Vektor(double[][] p_Matrix, double[][] transpDesign_Matrix,
             Designmatrix DesignMatrix, Projektionsdaten projDaten,
             Passpunktdaten passDaten, Uebertragungspunktdaten uebDaten,
             Bildkoordinatendaten bildkoorDaten) {

        double[][] lgem = new double[2 * bildkoorDaten.getanzBildkoordinaten()][1];

        for (int i = 0; i < bildkoorDaten.getpunkteBilderkoord().length; i++) {
            for (int j = 0; j < bildkoorDaten.getpunkteBilderkoord()[0].length; j++) {
                lgem[u][0] = bildkoorDaten.getpunkteBilderkoord()[i][0];
                lgem[u + 1][0] = bildkoorDaten.getpunkteBilderkoord()[i][1];
            }
            u += 2;
        }

        lstrich = new double[2 * bildkoorDaten.getanzBildkoordinaten()][1];

        for (int i = 0; i < lstrich.length; i++) {

            lstrich[i][0] = lgem[i][0]
                    - DesignMatrix.getl0Matrix()[i][0];
        }

        // l' --> Vektor der verkürzten Messungen

        int matrixAZeilen = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());
        int matrixASpalten = bildkoorDaten.getanzBildkoordinaten() * 2;

        // transp Mat A * P Mat

        double[][] transAMalP = new double[matrixAZeilen][matrixASpalten];

        for (int i = 0; i < transpDesign_Matrix.length; i++) {
            for (int j = 0; j < p_Matrix[0].length; j++) {
                for (int k = 0; k < transpDesign_Matrix[0].length; k++) {
                    transAMalP[i][j] += transpDesign_Matrix[i][k]
                            * p_Matrix[k][j];
                }
            }
        }

        // h-Vektor

        h_Vektor = new double[matrixAZeilen][1];

        for (int i = 0; i < transAMalP.length; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < transAMalP[0].length; k++) {
                    h_Vektor[i][j] += transAMalP[i][k] * lstrich[k][j];
                }
            }
        }
    }

    public double[][] geth_Vektor() {

        return h_Vektor;
    }

    public double[][] getlstrich() {

        return lstrich;
    }
}