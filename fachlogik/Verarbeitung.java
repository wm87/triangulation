package com.htw.Triangulation.fachlogik;

import com.htw.Triangulation.nutzerschnittstelle.Fenster;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * @author weidemann
 */
public class Verarbeitung {

    // Objekte
    /**
     * @uml.property name="haupt"
     * @uml.associationEnd
     */
    private final Fenster haupt = new Fenster();
    /**
     * @uml.property name="projDaten"
     * @uml.associationEnd
     */
    private Projektionsdaten projDaten;
    /**
     * @uml.property name="passDaten"
     * @uml.associationEnd
     */
    private Passpunktdaten passDaten;
    /**
     * @uml.property name="uebDaten"
     * @uml.associationEnd
     */
    private Uebertragungspunktdaten uebDaten;
    /**
     * @uml.property name="bildkoorDaten"
     * @uml.associationEnd
     */
    private Bildkoordinatendaten bildkoorDaten;
    private double[][] pMatrix = null;
    /**
     * @uml.property name="einheitsmatrix"
     * @uml.associationEnd
     */
    private Einheitsmatrix einheitsmatrix;
    /**
     * @uml.property name="designMatrix"
     * @uml.associationEnd
     */
    private Designmatrix DesignMatrix;
    /**
     * @uml.property name="pos"
     * @uml.associationEnd
     */
    private Positionen pos;
    /**
     * @uml.property name="n_Matrix"
     * @uml.associationEnd
     */
    private NormalgleichungsMatrix N_Matrix;
    /**
     * @uml.property name="q_Matrix"
     * @uml.associationEnd
     */
    private GewichtsreziprokenMatrix Q_Matrix;
    /**
     * @uml.property name="h_Vektor"
     * @uml.associationEnd
     */
    private H_Vektor h_Vektor;
    /**
     * @uml.property name="zuschlaege"
     * @uml.associationEnd
     */
    private Zuschlaege zuschlaege;
    /**
     * @uml.property name="v_Vektor"
     * @uml.associationEnd
     */
    private Verbesserungsvektor v_Vektor;
    /**
     * @uml.property name="pvv_Matrix"
     * @uml.associationEnd
     */
    private PVV_Matrix pvv_Matrix;
    /**
     * @uml.property name="ausgeglicheneMesswerte"
     * @uml.associationEnd
     */
    private AusgeglicheneMesswerte ausgeglicheneMesswerte;
    /**
     * @uml.property name="stab_s0"
     * @uml.associationEnd
     */
    private Standardabweichung_S0 stab_s0;
    /**
     * @uml.property name="r_Matrix"
     * @uml.associationEnd
     */
    @SuppressWarnings("unused")
    private RedundanzMatrix r_Matrix;
    /**
     * @uml.property name="neueGewichte"
     * @uml.associationEnd
     */
    private NeueGewichte neueGewichte;
    /**
     * @uml.property name="stababweichungderUnbekannten"
     * @uml.associationEnd
     */
    @SuppressWarnings("unused")
    private Standardabweichung_der_Unbekannten stababweichungderUnbekannten;

    // Anzahl Iterationen
    private int anzahlIterationen;

    // weitere Parameter -> Ergebnisdialog
    private BufferedWriter brweitereParameter;

    // Konstruktor
    public Verarbeitung() {
    }

    // Array'S für alle Textdateien werden angelegt und gefüllt
    public void erzeugeProjektionsdaten(BufferedReader br1, BufferedReader br2, int anzProjektionszentren) {
        projDaten = new Projektionsdaten(br1, br2, anzProjektionszentren, haupt);
    }

    public void erzeugePasspunktdaten(BufferedReader br3, BufferedReader br4, int anzPasspunkte) {
        passDaten = new Passpunktdaten(br3, br4, anzPasspunkte, haupt);
    }

    public void erzeugeUebertragungspunktdaten(BufferedReader br5, BufferedReader br6, int anzUebertragungspunkte) {
        uebDaten = new Uebertragungspunktdaten(br5, br6, anzUebertragungspunkte, haupt);
    }

    public void erzeugeBildkoordinatendaten(BufferedReader br7, BufferedReader br8, int anzBildkoordinaten) {
        bildkoorDaten = new Bildkoordinatendaten(br7, br8, anzBildkoordinaten, haupt);
    }

    public void erzeugeEinheitsmatrix() {

        einheitsmatrix = new Einheitsmatrix(projDaten, passDaten, uebDaten, bildkoorDaten);
        pMatrix = einheitsmatrix.getpMatrix();
    }

    public void erzeugeDesignMatrix() {
        pos = new Positionen(projDaten, passDaten, uebDaten, bildkoorDaten);
        DesignMatrix = new Designmatrix(pos, projDaten, passDaten, uebDaten, bildkoorDaten);
    }

    public void erzeugeNormalgleichungsMatrix() {
        N_Matrix = new NormalgleichungsMatrix(pMatrix, DesignMatrix, projDaten, passDaten, uebDaten, bildkoorDaten);
    }

    public void erzeugeGewichtsreziprokenMatrix() {
        Q_Matrix = new GewichtsreziprokenMatrix(N_Matrix.getnormalgleichungsMatrix(), projDaten, passDaten, uebDaten, bildkoorDaten);
    }

    public void erzeugehMatrix() {
        h_Vektor = new H_Vektor(N_Matrix.getpMatrix(), N_Matrix.gettransponiertedesignMatrix(), DesignMatrix, projDaten, passDaten, uebDaten, bildkoorDaten);
    }

    public void Zuschlaege() {

        zuschlaege = new Zuschlaege(Q_Matrix.getgewichtsreziprokenMatrix(),
                h_Vektor.geth_Vektor(), projDaten, passDaten, uebDaten,
                bildkoorDaten);
    }

    public void erzeugeVerbesserungen() {

        v_Vektor = new Verbesserungsvektor(DesignMatrix, zuschlaege
                .getZuschlaege(), h_Vektor.getlstrich(), projDaten, passDaten,
                uebDaten, bildkoorDaten);
    }

    public double erzeuge_pvv_Matrix() {

        pvv_Matrix = new PVV_Matrix(v_Vektor.getv_Vektor(), bildkoorDaten);

        return pvv_Matrix.getMinimum();
    }

    public void erzeugeausgeglicheneUnbekannten(
            BufferedWriter brausgeglicheneUnbekannte) {

        new AusgeglicheneUnbekannte(zuschlaege.getZuschlaege(),
                brausgeglicheneUnbekannte, projDaten, passDaten, uebDaten,
                bildkoorDaten);
    }

    public void erzeugeneueGewichte() {

        neueGewichte = new NeueGewichte(
                N_Matrix.gettransponiertedesignMatrix(), DesignMatrix, Q_Matrix
                .getgewichtsreziprokenMatrix(), N_Matrix.getpMatrix(),
                v_Vektor.getv_Vektor(), projDaten, passDaten, uebDaten,
                bildkoorDaten);

        pMatrix = neueGewichte.getpMatrix();
    }

    /**
     * @uml.property name="anzahlIterationen"
     */
    public void setAnzahlIterationen(int anzahl_Iterationen) {

        this.anzahlIterationen = anzahl_Iterationen;
    }

    public void erzeugeausgeglicheneMesswerte(
            BufferedWriter brausgeglicheneMesswerte) {

        this.brweitereParameter = brausgeglicheneMesswerte;

        ausgeglicheneMesswerte = new AusgeglicheneMesswerte(v_Vektor
                .getv_Vektor(), brweitereParameter, projDaten, passDaten,
                uebDaten, bildkoorDaten);
    }

    public void erzeugestababweichungS0() {

        stab_s0 = new Standardabweichung_S0(ausgeglicheneMesswerte
                .getbrweitereParameter(), v_Vektor.getv_Vektor(), N_Matrix
                .getpMatrix(), projDaten, passDaten, uebDaten, bildkoorDaten);
    }

    public void erzeugeRedundanzMatrix() {

        r_Matrix = new RedundanzMatrix(N_Matrix.getpMatrix(), N_Matrix
                .gettransponiertedesignMatrix(), Q_Matrix
                .getgewichtsreziprokenMatrix(), DesignMatrix, projDaten,
                passDaten, uebDaten, bildkoorDaten);
    }

    public void erzeugestababweichungderUnbekannten() {

        stababweichungderUnbekannten = new Standardabweichung_der_Unbekannten(
                anzahlIterationen, stab_s0.getS0(), Q_Matrix
                .getgewichtsreziprokenMatrix(), stab_s0
                .getbrweitereParameter(), projDaten, passDaten,
                uebDaten, bildkoorDaten);
    }
}