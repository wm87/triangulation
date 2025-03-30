package com.htw.Triangulation.fachlogik;

/**
 * @author weidemann
 */
public class Zuschlaege {

    private double[][] zuschlaege = null;

    Zuschlaege(double[][] Q_Matrix, double[][] h_Vektor,
               Projektionsdaten projDaten, Passpunktdaten passDaten,
               Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        int matrixAZeilen = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        zuschlaege = new double[matrixAZeilen][1];

        for (int i = 0; i < Q_Matrix.length; i++) {
            for (int k = 0; k < Q_Matrix[0].length; k++) {
                zuschlaege[i][0] += Q_Matrix[i][k] * h_Vektor[k][0];
            }
        }
    }

    /**
     * @uml.property name="zuschlaege"
     */
    public double[][] getZuschlaege() {

        return zuschlaege;
    }
}