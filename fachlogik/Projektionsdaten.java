package com.htw.Triangulation.fachlogik;

import com.htw.Triangulation.nutzerschnittstelle.Fenster;

import java.io.BufferedReader;

public class Projektionsdaten {

    // Projektionszentrum
    private double[][] punkteAndRotationProj = null;
    private int[] pnrProj = null;
    private int anzProjektionszentren;

    public Projektionsdaten() {
    }

    public Projektionsdaten(BufferedReader br1, BufferedReader br2,
                            int anzProjektionszentren, Fenster haupt) {

        this.anzProjektionszentren = anzProjektionszentren;

        punkteAndRotationProj = new double[anzProjektionszentren][7];
        pnrProj = new int[anzProjektionszentren];

        // System.out.println("\nProjektionszentren");

        try {
            pnrProj = haupt.getProjectionsnummer(pnrProj, br1);
            punkteAndRotationProj = haupt.getProjectionspunkteAndCamera(punkteAndRotationProj, br2);

            br1.close();
            br2.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    public int[] getpnrProj() {

        return pnrProj;
    }

    public double[][] getpunkteAndRotationAndCameraProj() {

        return punkteAndRotationProj;
    }

    public int getanzProjektionszentren() {

        return anzProjektionszentren;
    }

    public void setpunkteAndRotationProj(double[][] neueNaehrungswerteBilder) {

        punkteAndRotationProj = neueNaehrungswerteBilder;
    }

    public void setpnrProj(int pnr_Proj[]) {

        pnrProj = pnr_Proj;
    }

    public void setanzProjektionszentren(int anz_Projektionszentren) {

        anzProjektionszentren = anz_Projektionszentren;
    }
}
