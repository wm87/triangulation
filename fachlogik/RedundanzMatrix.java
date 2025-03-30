package com.htw.Triangulation.fachlogik;

public class RedundanzMatrix {

    private int matrixAZeilen;
    private int matrixASpalten;
    private double[][] temp1 = null;
    private double[][] temp2 = null;
    private double[][] temp3 = null;
    private double[][] Einheitsmatrix = null;
    private double[][] rMatrix = null;

    RedundanzMatrix() {
    }

    RedundanzMatrix(double[][] pMatrix, double[][] transponiertedesignMatrix,
                    double[][] gewichtsreziprokenMatrix, Designmatrix DesignMatrix,
                    Projektionsdaten projDaten, Passpunktdaten passDaten,
                    Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        matrixAZeilen = bildkoorDaten.getanzBildkoordinaten() * 2;
        matrixASpalten = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        temp1 = new double[matrixAZeilen][matrixASpalten];

        // rechne Mat A * Mat N^-1 --> temp1

        for (int i = 0; i < DesignMatrix.getDesignmatrix().length; i++) {
            for (int j = 0; j < gewichtsreziprokenMatrix[0].length; j++) {
                for (int k = 0; k < DesignMatrix.getDesignmatrix()[0].length; k++) {
                    temp1[i][j] += DesignMatrix.getDesignmatrix()[i][k]
                            * gewichtsreziprokenMatrix[k][j];
                }
            }
        }

        // temp2 --> Kofakt. Matrix der ausgegl. Beobachtungen
        // temp2 = Mat A * Mat Q * Trn Mat A
        temp2 = new double[matrixAZeilen][matrixAZeilen];

        // rechne Mat temp1 * Trn Mat A --> temp2
        for (int i = 0; i < temp1.length; i++) {
            for (int j = 0; j < transponiertedesignMatrix[0].length; j++) {
                for (int k = 0; k < temp1[0].length; k++) {
                    temp2[i][j] += temp1[i][k]
                            * transponiertedesignMatrix[k][j];
                }
            }
        }

        // rechne Mat temp2 * Trn Mat A --> temp3

        temp3 = new double[matrixAZeilen][matrixAZeilen];

        for (int i = 0; i < temp2.length; i++) {
            for (int j = 0; j < pMatrix[0].length; j++) {
                for (int k = 0; k < temp2[0].length; k++) {
                    temp3[i][j] += temp2[i][k] * pMatrix[k][j];
                }
            }
        }

        // Einheitsmatrix

        Einheitsmatrix = new double[matrixAZeilen][matrixAZeilen];

        for (int i = 0; i < Einheitsmatrix.length; i++) {
            Einheitsmatrix[i][i] = 1;
        }

        // rechne Mat E - Mat temp3 --> Mat R

        rMatrix = new double[matrixAZeilen][matrixAZeilen];

        for (int i = 0; i < rMatrix.length; i++) {
            for (int j = 0; j < temp3[0].length; j++) {
                rMatrix[i][j] = Einheitsmatrix[i][j] - temp3[i][j];
            }
        }
    }

}