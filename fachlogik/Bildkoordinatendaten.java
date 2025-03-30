package com.htw.Triangulation.fachlogik;

import com.htw.Triangulation.nutzerschnittstelle.Fenster;

import java.io.BufferedReader;

public class Bildkoordinatendaten {

    // Projektionszentrum
    private double[][] punkteBilderkoord = null;
    private int[][] bildnrAndpnrGEM = null;
    private int anzBildkoordinaten;

    public Bildkoordinatendaten() {
    }

    public Bildkoordinatendaten(BufferedReader br7, BufferedReader br8,
                                int anzBildkoordinaten, Fenster haupt) {

        this.anzBildkoordinaten = anzBildkoordinaten;

        punkteBilderkoord = new double[anzBildkoordinaten][2];
        bildnrAndpnrGEM = new int[anzBildkoordinaten][2];

        try {
            bildnrAndpnrGEM = haupt.getBildkoordinatenpunktnummerAndBild(bildnrAndpnrGEM, br7);

            // erhält die Projektionsdaten
            punkteBilderkoord = haupt.getBildkoordinatenpunkte(punkteBilderkoord, br8);

            br7.close();
            br8.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    public int[][] getbildnrAndpnrGEM() {

        return bildnrAndpnrGEM;
    }

    public double[][] getpunkteBilderkoord() {

        return punkteBilderkoord;
    }

    public int getanzBildkoordinaten() {

        return anzBildkoordinaten;
    }

    public void setpunkteBildkoor(double punkte_Bilderkoord[][]) {

        punkteBilderkoord = punkte_Bilderkoord;
    }

    public void setpnrBildkoor(int bild_nrAndpnrGEM[][]) {

        bildnrAndpnrGEM = bild_nrAndpnrGEM;
    }

    public void setanzBildkoor(int anz_Bildkoordinaten) {

        anzBildkoordinaten = anz_Bildkoordinaten;
    }
}