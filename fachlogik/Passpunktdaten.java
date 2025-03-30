package com.htw.Triangulation.fachlogik;

import com.htw.Triangulation.nutzerschnittstelle.Fenster;

import java.io.BufferedReader;

public class Passpunktdaten {

    // Projektionszentrum
    private double[][] punktePass = null;
    private int[] pnrPass = null;
    private int anzPasspunkte;

    public Passpunktdaten() {
    }

    public Passpunktdaten(BufferedReader br3, BufferedReader br4,
                          int anzPasspunkte, Fenster haupt) {

        this.anzPasspunkte = anzPasspunkte;

        // Den Array's ihre Dimension zuweisen, welche in der Klasse
        // "Hauptfenster"
        // zuvor ermittelt wurde
        punktePass = new double[anzPasspunkte][3];
        pnrPass = new int[anzPasspunkte];

        // System.out.println("\nPasspunkte");

        try {
            // ruft die Methode auf, welche das oben angelegte Array mit
            // den Werten aus den Textdateien f�llt
            pnrPass = haupt.getPasspunktnummer(pnrPass, br3);

            /*
             * for (int i = 0; i < pnrPass.length; i++) {
             * System.out.println(pnrPass[i]); }
             */

            punktePass = haupt.getPasspunktpunkte(punktePass, br4);

            /*
             * for (int i = 0; i < punktePass.length; i++) { for (int j = 0; j <
             * punktePass[0].length; j++) { System.out.print(punktePass[i][j] +
             * " "); } System.out.print("\n"); }
             */

            br3.close();
            br4.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    public int[] getpnrPass() {

        return pnrPass;
    }

    public double[][] getpunktePass() {

        return punktePass;
    }

    public int getanzPasspunkte() {

        return anzPasspunkte;
    }

    public void setpunktePass(double[][] punkte_Pass) {

        punktePass = punkte_Pass;
    }

    public void setpnrPass(int[] pnr_Pass) {

        pnrPass = pnr_Pass;
    }

    public void setanzPasspunkte(int anz_Passpunkte) {

        anzPasspunkte = anz_Passpunkte;
    }
}
