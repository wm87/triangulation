package com.htw.Triangulation.fachlogik;

import com.htw.Triangulation.nutzerschnittstelle.Fenster;

import java.io.BufferedReader;

public class Uebertragungspunktdaten {

    // Projektionszentrum
    private double[][] punkteUeb = null;
    private int[] pnrUeb = null;
    private int anzUebertragungspunkte;

    public Uebertragungspunktdaten() {
    }

    public Uebertragungspunktdaten(BufferedReader br5, BufferedReader br6,
                                   int anzUebertragungspunkte, Fenster haupt) {

        this.anzUebertragungspunkte = anzUebertragungspunkte;

        punkteUeb = new double[anzUebertragungspunkte][3];
        pnrUeb = new int[anzUebertragungspunkte];

        // System.out.println("\nUebertragungspunkte");

        try {
            pnrUeb = haupt.getUebertragungspunktnummer(pnrUeb, br5);
            punkteUeb = haupt.getUebertragungspunktpunkte(punkteUeb, br6);

            br5.close();
            br6.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    public int[] getpnrUeb() {

        return pnrUeb;
    }

    public double[][] getpunkteUeb() {

        return punkteUeb;
    }

    public int getanzUebertragungspunkte() {

        return anzUebertragungspunkte;
    }

    public void setpunkteUeb(double punkte_Ueb[][]) {

        punkteUeb = punkte_Ueb;
    }

    public void setpnrUeb(int pnr_Ueb[]) {

        pnrUeb = pnr_Ueb;
    }

    public void setanzUebertragungspunkte(int anz_anzUebertragungspunkte) {

        anzUebertragungspunkte = anz_anzUebertragungspunkte;
    }
}