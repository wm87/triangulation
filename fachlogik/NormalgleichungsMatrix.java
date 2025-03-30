package com.htw.Triangulation.fachlogik;

public class NormalgleichungsMatrix {

    private double[][] transponiertedesignMatrix = null;
    private double[][] normalgleichunsMatrix = null;
    private double[][] temptransAMalP = null;
    private double[][] pMatrix = null;

    NormalgleichungsMatrix(double[][] p_Matrix, Designmatrix DesignMatrix,
                           Projektionsdaten projDaten, Passpunktdaten passDaten,
                           Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        this.pMatrix = p_Matrix;
        // Transponierte Matrix A
        // zuerst Zeilen mit Spalten vertauschen
        int matrixASpalten = bildkoorDaten.getanzBildkoordinaten() * 2;
        int matrixAZeilen = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        transponiertedesignMatrix = new double[matrixAZeilen][matrixASpalten];

        for (int i = 0; i < transponiertedesignMatrix.length; i++) {
            for (int j = 0; j < transponiertedesignMatrix[0].length; j++) {
                transponiertedesignMatrix[i][j] = 0;
            }
        }

        // die Position [i][j] in der Design Matrix wird zur Position [j][i] in
        // der transponierten Design Matrix
        for (int i = 0; i < DesignMatrix.getDesignmatrix().length; i++) {
            for (int j = 0; j < DesignMatrix.getDesignmatrix()[0].length; j++) {
                transponiertedesignMatrix[j][i] = DesignMatrix
                        .getDesignmatrix()[i][j];
            }
        }

        /*
         * System.out.println("\nTrn A-Matrix\n");
         *
         * for (int i = 0; i < transponiertedesignMatrix.length; i++) { for (int
         * j = 0; j < transponiertedesignMatrix[0].length; j++) {
         * System.out.print(transponiertedesignMatrix[i][j] + "	"); }
         * System.out.print("\n"); }
         *
         * // Gewichtsmatrix P erzeugen (P = E) for (int i = 0; i <
         * pMatrix.length; i++) { for (int j = 0; j < pMatrix[0].length; j++) {
         * pMatrix[i][j] = 0; pMatrix[j][j] = 1; } }
         */

        temptransAMalP = new double[matrixAZeilen][matrixASpalten];

        // trans Mat A * Mat P
        for (int i = 0; i < transponiertedesignMatrix.length; i++) {
            for (int j = 0; j < pMatrix[0].length; j++) {
                for (int k = 0; k < transponiertedesignMatrix[0].length; k++) {
                    temptransAMalP[i][j] += transponiertedesignMatrix[i][k]
                            * pMatrix[k][j];
                    // normalgleichunsMatrix[i][j] += bedeutet die Aufsummierung
                    // bei der Multiplikation
                }
            }
        }

        // Normalgleichunsmatrix N
        // Spaltenanzahl muss gleich Zeilenanzahl sein
        matrixASpalten = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        normalgleichunsMatrix = new double[matrixASpalten][matrixASpalten];

        // N - Matrix
        for (int i = 0; i < temptransAMalP.length; i++) {
            for (int j = 0; j < DesignMatrix.getDesignmatrix()[0].length; j++) {
                for (int k = 0; k < temptransAMalP[0].length; k++) {
                    normalgleichunsMatrix[i][j] += temptransAMalP[i][k]
                            * DesignMatrix.getDesignmatrix()[k][j];
                    // normalgleichunsMatrix[i][j] += bedeutet die Aufsummierung
                    // bei der Multiplikation
                }
            }
        }

        /*
         * System.out.println("\nN-Matrix\n");
         *
         * for (int i = 0; i < normalgleichunsMatrix.length; i++) { for (int j =
         * 0; j < normalgleichunsMatrix[0].length; j++) {
         * System.out.print(normalgleichunsMatrix[i][j] + "	"); }
         * System.out.print("\n"); }
         */
    }

    public double[][] gettransponiertedesignMatrix() {

        return transponiertedesignMatrix;
    }

    public double[][] getnormalgleichungsMatrix() {

        return normalgleichunsMatrix;
    }

    public double[][] gettemptransAMalP() {

        return temptransAMalP;
    }

    public double[][] getpMatrix() {

        return pMatrix;
    }
}
