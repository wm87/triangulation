package com.htw.Triangulation.fachlogik;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

public class Standardabweichung_der_Unbekannten {

    Standardabweichung_der_Unbekannten(int anzahlIterationen, double s0,
                                       double[][] gewichtsreziprokenMatrix,
                                       BufferedWriter brweitere_Parameter, Projektionsdaten projDaten,
                                       Passpunktdaten passDaten, Uebertragungspunktdaten uebDaten,
                                       Bildkoordinatendaten bildkoorDaten) {

        int matrixAZeilen = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        double[][] sxi = new double[matrixAZeilen][1];

        for (int i = 0; i < gewichtsreziprokenMatrix.length; i++) {

            sxi[i][0] = s0 * Math.sqrt(gewichtsreziprokenMatrix[i][i]);
        }

        try {
            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();
            brweitere_Parameter
                    .append("******** Punktlagestandardabweichung der Unbekannten ********");
            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();
            brweitere_Parameter.append("Bilder");
            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();
            brweitere_Parameter
                    .append("Reihenfolge: Bildnummer, s_X0 [m], s_Y0 [m], s_Z0 [m], s_Kappa [DEG], s_Phi [DEG], s_Omega [DEG]");
            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();

            int h = 0;
            int f = 0;

            BigDecimal x0Dec;
            BigDecimal y0Dec;
            BigDecimal z0Dec;
            String strausgeglicheneUnbekannteX0;
            String strausgeglicheneUnbekannteY0;
            String strausgeglicheneUnbekannteZ0;
            for (int i = 0; i < 6 * projDaten.getanzProjektionszentren(); i += 6) {

                // Elemente der �u�eren O.

                x0Dec = BigDecimal.valueOf(sxi[i][0]);
                x0Dec = x0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteX0 = String.valueOf(x0Dec);

                y0Dec = BigDecimal.valueOf(sxi[i + 1][0]);
                y0Dec = y0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteY0 = String.valueOf(y0Dec);

                z0Dec = BigDecimal.valueOf(sxi[i + 2][0]);
                z0Dec = z0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteZ0 = String.valueOf(z0Dec);

                BigDecimal kappaDec = new BigDecimal(180 / Math.PI * sxi[i + 3][0]);
                kappaDec = kappaDec.round(new MathContext(8));
                String strausgeglicheneUnbekannteKappa = String.valueOf(kappaDec);

                BigDecimal phiDec = new BigDecimal(180 / Math.PI * sxi[i + 4][0]);
                phiDec = phiDec.round(new MathContext(8));
                String strausgeglicheneUnbekanntePhi = String.valueOf(phiDec);

                BigDecimal omegaDec = new BigDecimal(180 / Math.PI * sxi[i + 5][0]);
                omegaDec = omegaDec.round(new MathContext(8));
                String strausgeglicheneUnbekannteOmega = String.valueOf(omegaDec);

                h = i;

                brweitere_Parameter.append(String.valueOf(projDaten.getpnrProj()[f])).append(" ").append(strausgeglicheneUnbekannteX0).append(" ").append(strausgeglicheneUnbekannteY0).append(" ").append(strausgeglicheneUnbekannteZ0).append(" ").append(strausgeglicheneUnbekannteKappa).append(" ").append(strausgeglicheneUnbekanntePhi).append(" ").append(strausgeglicheneUnbekannteOmega);
                f++;

                brweitere_Parameter.newLine();
            }

            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();
            brweitere_Parameter.append("Übertragungspunkte");
            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();
            brweitere_Parameter
                    .append("Reihenfolge: Punktnummer, s_X [m], s_Y [m], s_Z [m]");
            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();

            f = 0;

            for (int a = h + 6; a < h + 6
                    + (3 * uebDaten.getanzUebertragungspunkte()); a += 3) {

                // Übertragungspunkte

                x0Dec = BigDecimal.valueOf(sxi[a][0]);
                x0Dec = x0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteX0 = String.valueOf(x0Dec);

                y0Dec = BigDecimal.valueOf(sxi[a + 1][0]);
                y0Dec = y0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteY0 = String.valueOf(y0Dec);

                z0Dec = BigDecimal.valueOf(sxi[a + 2][0]);
                z0Dec = z0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteZ0 = String.valueOf(z0Dec);

                brweitere_Parameter.append(String.valueOf(uebDaten.getpnrUeb()[f])).append(" ").append(strausgeglicheneUnbekannteX0).append(" ").append(strausgeglicheneUnbekannteY0).append(" ").append(strausgeglicheneUnbekannteZ0);
                f++;

                brweitere_Parameter.newLine();
            }

            brweitere_Parameter.newLine();
            brweitere_Parameter.newLine();
            brweitere_Parameter.append("Insgesamt wurden ").append(String.valueOf(anzahlIterationen)).append(" Iterationen durchgeführt.");
            brweitere_Parameter.newLine();
            brweitere_Parameter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
}