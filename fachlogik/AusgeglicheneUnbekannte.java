package com.htw.Triangulation.fachlogik;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

public class AusgeglicheneUnbekannte {

    // Konstruktor
    AusgeglicheneUnbekannte(double[][] xMatrix,
                            BufferedWriter brausgeglicheneUnbekannte,
                            Projektionsdaten projDaten, Passpunktdaten passDaten,
                            Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        // Nährungswerte
        int matrixAZeilen = (6 * projDaten.getanzProjektionszentren())
                + (3 * uebDaten.getanzUebertragungspunkte());

        double[][] naeherungswerteUnbekannte = new double[matrixAZeilen][1];

        int u = 0;

        // Näherungswerte aus dem Array der Projektionspunkte
        // (projDaten.getpunkteAndRotationAndCameraProj())
        // in neues Array schreiben (naeherungswerteUnbekannte)

        for (int i = 0; i < projDaten.getanzProjektionszentren(); i++) {

            // Näherungswerte X,Y,Z der Projektionszentren
            naeherungswerteUnbekannte[u][0] = projDaten
                    .getpunkteAndRotationAndCameraProj()[i][0];
            naeherungswerteUnbekannte[u + 1][0] = projDaten
                    .getpunkteAndRotationAndCameraProj()[i][1];
            naeherungswerteUnbekannte[u + 2][0] = projDaten
                    .getpunkteAndRotationAndCameraProj()[i][2];

            // Näherungswerte für Drehwinkel
            naeherungswerteUnbekannte[u + 3][0] = projDaten
                    .getpunkteAndRotationAndCameraProj()[i][3];
            naeherungswerteUnbekannte[u + 4][0] = projDaten
                    .getpunkteAndRotationAndCameraProj()[i][4];
            naeherungswerteUnbekannte[u + 5][0] = projDaten
                    .getpunkteAndRotationAndCameraProj()[i][5];

            u += 6;
            // Wert 6, da nach einem Durchlauf 6 Werte in das dafür vorgesehene
            // Array geschrieben werden
            // Bsp.: Bildanzahl ist 5, d.h. es gibt 5 Durchläufe
            // mit insgesamt: 5 * 6 = 30 Unbekannten
        }

        int m = 0;
        int w = u;

        for (int i = u; i < w + uebDaten.getanzUebertragungspunkte(); i++) {

            // Näherungswerte X,Y,Z der Übertragungspunkte
            naeherungswerteUnbekannte[u][0] = uebDaten.getpunkteUeb()[m][0];
            naeherungswerteUnbekannte[u + 1][0] = uebDaten.getpunkteUeb()[m][1];
            naeherungswerteUnbekannte[u + 2][0] = uebDaten.getpunkteUeb()[m][2];

            m += 1;
            // Wert 1, da jeder Uebpunkt dem neuen Array zugeordnet wird

            u += 3;
            // Wert 3, prinzipiell wie Schleife zuvor
            // Bsp.: Übertragunspunktanzahl ist 8, d.h. es gibt 8 Durchläufe
            // mit insgesamt: 8 * 3 = 24 Näherungswerte
        }
        // für das fingierte Beispiel (siehe CD-ROM) gibt es in Summe 54
        // Unbekannte

        /*
         * System.out.println("\nNäherungswerte\n\n");
         *
         * for (int i = 0; i < naeherungswerteUnbekannte.length; i++) { for (int
         * j = 0; j < naeherungswerteUnbekannte[0].length; j++) {
         * System.out.print(naeherungswerteUnbekannte[i][j] + " "); }
         * System.out.print("\n"); }
         */

        double[][] ausgeglicheneUnbekannteBilder = new double[(6 * projDaten
                .getanzProjektionszentren())][1];

        for (int i = 0; i < ausgeglicheneUnbekannteBilder.length; i++) {

            ausgeglicheneUnbekannteBilder[i][0] = naeherungswerteUnbekannte[i][0]
                    + xMatrix[i][0];
        }

        double[][] ausgeglicheneUnbekannteUebpunkte = new double[(3 * uebDaten
                .getanzUebertragungspunkte())][1];

        for (int i = 0; i < ausgeglicheneUnbekannteUebpunkte.length; i++) {

            ausgeglicheneUnbekannteUebpunkte[i][0] = naeherungswerteUnbekannte[6
                    * projDaten.getanzProjektionszentren() + i][0]
                    + xMatrix[6 * projDaten.getanzProjektionszentren() + i][0];
        }


        try {
            brausgeglicheneUnbekannte.append("äußere Orientierung der Bilder");
            brausgeglicheneUnbekannte.newLine();
            brausgeglicheneUnbekannte.newLine();
            brausgeglicheneUnbekannte
                    .append("Reihenfolge: Bildnummer, X0 [m], Y0 [m], Z0 [m], Kappa [DEG], Phi [DEG], Omega [DEG]");
            brausgeglicheneUnbekannte.newLine();
            brausgeglicheneUnbekannte.newLine();

            int f = 0;

            BigDecimal x0Dec;
            BigDecimal y0Dec;
            BigDecimal z0Dec;
            String strausgeglicheneUnbekannteX0;
            String strausgeglicheneUnbekannteY0;
            String strausgeglicheneUnbekannteZ0;
            for (int i = 0; i < 6 * projDaten.getanzProjektionszentren(); i += 6) {

                // Elemente der äußeren O.

                x0Dec = BigDecimal.valueOf(ausgeglicheneUnbekannteBilder[i][0]);
                // BigDecimal --> zur Rundung auf 3 Nachkommastellen
                x0Dec = x0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteX0 = String.valueOf(x0Dec);

                y0Dec = BigDecimal.valueOf(ausgeglicheneUnbekannteBilder[i + 1][0]);
                y0Dec = y0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteY0 = String.valueOf(y0Dec);

                z0Dec = BigDecimal.valueOf(ausgeglicheneUnbekannteBilder[i + 2][0]);
                z0Dec = z0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteZ0 = String.valueOf(z0Dec);

                BigDecimal kappaDec = new BigDecimal(180 / Math.PI
                        * ausgeglicheneUnbekannteBilder[i + 3][0]);
                kappaDec = kappaDec.round(new MathContext(8));
                String strausgeglicheneUnbekannteKappa = String.valueOf(kappaDec);

                BigDecimal phiDec = new BigDecimal(180 / Math.PI
                        * ausgeglicheneUnbekannteBilder[i + 4][0]);
                phiDec = phiDec.round(new MathContext(8));
                String strausgeglicheneUnbekanntePhi = String.valueOf(phiDec);

                BigDecimal omegaDec = new BigDecimal(180 / Math.PI
                        * ausgeglicheneUnbekannteBilder[i + 5][0]);
                omegaDec = omegaDec.round(new MathContext(8));
                String strausgeglicheneUnbekannteOmega = String.valueOf(omegaDec);

                brausgeglicheneUnbekannte.append(String.valueOf(projDaten.getpnrProj()[f])).append(" ").append(strausgeglicheneUnbekannteX0).append(" ").append(strausgeglicheneUnbekannteY0).append(" ").append(strausgeglicheneUnbekannteZ0).append(" ").append(strausgeglicheneUnbekannteKappa).append(" ").append(strausgeglicheneUnbekanntePhi).append(" ").append(strausgeglicheneUnbekannteOmega);
                f++;

                brausgeglicheneUnbekannte.newLine();
            }

            brausgeglicheneUnbekannte.newLine();
            brausgeglicheneUnbekannte.newLine();

            brausgeglicheneUnbekannte.append("Übertragungspunkte");

            brausgeglicheneUnbekannte.newLine();
            brausgeglicheneUnbekannte.newLine();

            brausgeglicheneUnbekannte
                    .append("Reihenfolge: Punktnummer, X [m], Y [m], Z [m]");

            brausgeglicheneUnbekannte.newLine();
            brausgeglicheneUnbekannte.newLine();

            f = 0;

            // Uebpunkte schreiben

            for (int a = 0; a < (3 * uebDaten.getanzUebertragungspunkte()); a += 3) {

                // Übertragungspunkte

                x0Dec = BigDecimal.valueOf(ausgeglicheneUnbekannteUebpunkte[a][0]);
                x0Dec = x0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteX0 = String.valueOf(x0Dec);

                y0Dec = BigDecimal.valueOf(ausgeglicheneUnbekannteUebpunkte[a + 1][0]);
                y0Dec = y0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteY0 = String.valueOf(y0Dec);

                z0Dec = BigDecimal.valueOf(ausgeglicheneUnbekannteUebpunkte[a + 2][0]);
                z0Dec = z0Dec.round(new MathContext(3));
                strausgeglicheneUnbekannteZ0 = String.valueOf(z0Dec);

                brausgeglicheneUnbekannte.append(String.valueOf(uebDaten.getpnrUeb()[f])).append(" ").append(strausgeglicheneUnbekannteX0).append(" ").append(strausgeglicheneUnbekannteY0).append(" ").append(strausgeglicheneUnbekannteZ0);
                f++;

                brausgeglicheneUnbekannte.newLine();
            }

            brausgeglicheneUnbekannte.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // System.out.println("\n****   ausgeglicheneUnbekannteBilder****\n\n");

        double[][] unbekannteBilder = new double[projDaten.getanzProjektionszentren()][7];

        u = 0;

        // wichtig, um den Ausgangszustand der eingelesenen Daten wieder
        // herzustellen, d.h. Bilder und Übertragungspunkte müssen wieder in
        // Zeilen als auch in Spalten aufgetrennt werden
        // erforderlich um weitere Iterationen durchführen zu können

        for (int i = 0; i < (6 * projDaten.getanzProjektionszentren()); i++) {

            unbekannteBilder[u][0] = ausgeglicheneUnbekannteBilder[i][0];
            unbekannteBilder[u][1] = ausgeglicheneUnbekannteBilder[i + 1][0];
            unbekannteBilder[u][2] = ausgeglicheneUnbekannteBilder[i + 2][0];
            unbekannteBilder[u][3] = ausgeglicheneUnbekannteBilder[i + 3][0];
            unbekannteBilder[u][4] = ausgeglicheneUnbekannteBilder[i + 4][0];
            unbekannteBilder[u][5] = ausgeglicheneUnbekannteBilder[i + 5][0];
            unbekannteBilder[u][6] = projDaten.getpunkteAndRotationAndCameraProj()[u][6];

            u += 1;
            i += 5;
        }

        u = 0;

        double[][] unbekannteUebpunkte = new double[uebDaten.getanzUebertragungspunkte()][3];

        for (int i = 0; i < (3 * unbekannteUebpunkte.length); i++) {

            unbekannteUebpunkte[u][0] = ausgeglicheneUnbekannteUebpunkte[i][0];
            unbekannteUebpunkte[u][1] = ausgeglicheneUnbekannteUebpunkte[i + 1][0];
            unbekannteUebpunkte[u][2] = ausgeglicheneUnbekannteUebpunkte[i + 2][0];

            u += 1;
            i += 2;
        }

        // die ursprünglich eingelesenen Daten werden mit den hier berechneten
        // Näherungswerten überschrieben u. erneut iteriert,
        // bis das Abbruch-Kriterium erreicht ist

        projDaten.setpunkteAndRotationProj(unbekannteBilder);
        projDaten.setpnrProj(projDaten.getpnrProj());
        projDaten.setanzProjektionszentren(projDaten.getanzProjektionszentren());

        passDaten.setpunktePass(passDaten.getpunktePass());
        passDaten.setpnrPass(passDaten.getpnrPass());
        passDaten.setanzPasspunkte(passDaten.getanzPasspunkte());

        uebDaten.setpunkteUeb(unbekannteUebpunkte);
        uebDaten.setpnrUeb(uebDaten.getpnrUeb());
        uebDaten.setanzUebertragungspunkte(uebDaten.getanzUebertragungspunkte());

        bildkoorDaten.setpunkteBildkoor(bildkoorDaten.getpunkteBilderkoord());
        bildkoorDaten.setpnrBildkoor(bildkoorDaten.getbildnrAndpnrGEM());
        bildkoorDaten.setanzBildkoor(bildkoorDaten.getanzBildkoordinaten());
    }
}