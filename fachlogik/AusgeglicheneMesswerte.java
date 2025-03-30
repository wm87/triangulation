package com.htw.Triangulation.fachlogik;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

public class AusgeglicheneMesswerte {

    private final BufferedWriter brweitereParameter;

    AusgeglicheneMesswerte(double[][] vMatrix,
                           BufferedWriter brweitere_Parameter, Projektionsdaten projDaten,
                           Passpunktdaten passDaten, Uebertragungspunktdaten uebDaten,
                           Bildkoordinatendaten bildkoorDaten) {

        this.brweitereParameter = brweitere_Parameter;

        // Nährungswerte
        int matrixAZeilen = (2 * bildkoorDaten.getanzBildkoordinaten());
        double[][] naeherungswerteMesswerte = new double[matrixAZeilen][1];

        int u = 0;

        for (int i = 0; i < bildkoorDaten.getanzBildkoordinaten(); i++) {

            naeherungswerteMesswerte[u][0] = bildkoorDaten.getpunkteBilderkoord()[i][0];
            naeherungswerteMesswerte[u + 1][0] = bildkoorDaten.getpunkteBilderkoord()[i][1];
            u += 2;
        }

        double[][] ausgeglicheneMesswerte = new double[matrixAZeilen][1];

        for (int i = 0; i < ausgeglicheneMesswerte.length; i++) {
            ausgeglicheneMesswerte[i][0] = naeherungswerteMesswerte[i][0]
                    + vMatrix[i][0];
        }

        try {
            brweitereParameter.append("******** Ausgeglichene Messwerte x' und y' ********");
            brweitereParameter.newLine();
            brweitereParameter.newLine();
            brweitereParameter.append("Reihenfolge: Bildnummer, Punktnummer, x' [mm], y' [mm]");
            brweitereParameter.newLine();
            brweitereParameter.newLine();

            int f = 0;

            for (int i = 0; i < ausgeglicheneMesswerte.length; i += 2) {
                // System.out.print(ausgeglicheneMesswerte[i][j] + " ");

                String strausgeglicheneMesswerteBildnr = (bildkoorDaten
                        .getbildnrAndpnrGEM()[f][0] + " ");
                String strausgeglicheneMesswertePunktnr = (bildkoorDaten
                        .getbildnrAndpnrGEM()[f][1] + " ");

                BigDecimal xstrichDec = BigDecimal.valueOf(ausgeglicheneMesswerte[i][0]);
                xstrichDec = xstrichDec.round(new MathContext(3));
                String strausgeglicheneMesswerteXstrich = (xstrichDec + " ");

                BigDecimal ystrichDec = BigDecimal.valueOf(ausgeglicheneMesswerte[i + 1][0]);
                ystrichDec = ystrichDec.round(new MathContext(3));
                String strausgeglicheneMesswerteYstrich = (ystrichDec + " ");

                brweitereParameter.append(strausgeglicheneMesswerteBildnr).append(strausgeglicheneMesswertePunktnr).append(strausgeglicheneMesswerteXstrich).append(strausgeglicheneMesswerteYstrich);
                f++;

                // System.out.print("\n");
                brweitereParameter.newLine();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public BufferedWriter getbrweitereParameter() {

        return brweitereParameter;
    }

}