package com.htw.Triangulation.fachlogik;

import java.io.BufferedWriter;
import java.io.IOException;

public class Standardabweichung_S0 {

    private double s_Beobachtungen;
    private double[][] transpvMatrix = null;
    private BufferedWriter brweitereParameter;

    Standardabweichung_S0(BufferedWriter brweitere_Parameter,
                          double[][] v_Vektor, double[][] pMatrix, Projektionsdaten projDaten,
                          Passpunktdaten passDaten, Uebertragungspunktdaten uebDaten,
                          Bildkoordinatendaten bildkoorDaten) {

        this.brweitereParameter = brweitere_Parameter;

        // Transponierte Verbesserungs Matrix
        // zuerst Zeilen mit Spalten vertauschen
        int matrixAZeilen = bildkoorDaten.getanzBildkoordinaten() * 2;

        transpvMatrix = new double[1][matrixAZeilen];

        // die Position [i][j] in der Design Matrix wird zur Position [j][i] in
        // der transponierten Design Matrix
        for (int i = 0; i < v_Vektor.length; i++) {
            transpvMatrix[0][i] = v_Vektor[i][0];
        }

        // transpVMalP --> Zwischenschritt
        double[][] transpVMalP = new double[1][matrixAZeilen];

        for (int i = 0; i < transpvMatrix.length; i++) {
            for (int j = 0; j < pMatrix[0].length; j++) {
                for (int k = 0; k < transpvMatrix[0].length; k++) {
                    transpVMalP[i][j] += transpvMatrix[i][k] * pMatrix[k][j];
                }
            }
        }

        matrixAZeilen = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        // transpVMalPMalV --> direkte Berechnung
        double[][] transpVMalPMalV = new double[1][1];

        for (int i = 0; i < transpVMalP.length; i++) {
            for (int k = 0; k < transpVMalP[0].length; k++) {
                transpVMalPMalV[i][0] += transpVMalP[i][k] * v_Vektor[k][0];
            }
        }

        // So a posteriori

        s_Beobachtungen = Math.sqrt(transpVMalPMalV[0][0]
                / ((bildkoorDaten.getanzBildkoordinaten() * 2) - (6 * projDaten
                .getanzProjektionszentren() + 3 * uebDaten
                .getanzUebertragungspunkte())));

        // System.out.println("\ns0: " + s0 + "\n\n");

        try {
            brweitereParameter.newLine();
            brweitereParameter.newLine();
            brweitereParameter.append("Punktlagestandardabweichung der Beobachtungen [mm]:  ").append(String.valueOf(s_Beobachtungen));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    public BufferedWriter getbrweitereParameter() {

        return brweitereParameter;
    }

    public double getS0() {

        return s_Beobachtungen;
    }
}