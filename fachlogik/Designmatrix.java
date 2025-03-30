package com.htw.Triangulation.fachlogik;

/**
 * @author Christiane
 */
public class Designmatrix {

    /**
     * @uml.property name="projDaten"
     * @uml.associationEnd
     */
    private final Projektionsdaten projDaten;
    /**
     * @uml.property name="passDaten"
     * @uml.associationEnd
     */
    private final Passpunktdaten passDaten;
    /**
     * @uml.property name="uebDaten"
     * @uml.associationEnd
     */
    private final Uebertragungspunktdaten uebDaten;
    private double[][] designMatrix = null;
    private double[][] l0Matrix = null;
    private int u;
    private int gefundenWert;
    private double R;
    private double S;
    private double T;
    private double X;
    private double X0;
    private double Y;
    private double Y0;
    private double Z;
    private double Z0;
    private double kappa;
    private double phi;
    private double omega;
    /**
     * @uml.property name="pos"
     * @uml.associationEnd
     */
    private final Positionen pos;

    // Kamerakonstante in mm --> fingiertes Bsp. c oder ck = -120.013 mm
    private double c;

    Designmatrix(Positionen pos, Projektionsdaten projDaten, Passpunktdaten passDaten, Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        this.pos = pos;
        this.projDaten = projDaten;
        this.passDaten = passDaten;
        this.uebDaten = uebDaten;

        // Dimension der A-Matrix ermitteln
        int matrixAZeilen = bildkoorDaten.getanzBildkoordinaten() * 2;
        int matrixASpalten = (6 * projDaten.getanzProjektionszentren()) + (3 * uebDaten.getanzUebertragungspunkte());

        // Dimension des Vektor l0
        l0Matrix = new double[2 * bildkoorDaten.getanzBildkoordinaten()][1];

        designMatrix = new double[matrixAZeilen][matrixASpalten];

        // System.out.println("\nA-Matrix\n\n");

        u = 0;

        for (int i = 0; i < bildkoorDaten.getbildnrAndpnrGEM().length; i++) {

            int m = bildkoorDaten.getbildnrAndpnrGEM()[i][0];
            int sucheWert = m;

            // sucht die Position der Drehwinkel für die
            // Stellen X,Y,Z
            for (int r = 0; r < projDaten.getpnrProj().length; r++) {

                if (projDaten.getpnrProj()[r] == sucheWert) {
                    gefundenWert = r;
                }
            }

            // Drehwinkel - Näherungswerte
            kappa = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][3];
            phi = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][4];
            omega = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][5];

            // Kamerakonstante ck der Bilder
            c = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][6];

            // alle Bildkoordinatenpunkte kleiner 1000
            // sind Passpunkte
            int w;
            int q;
            int x;
            if (bildkoorDaten.getbildnrAndpnrGEM()[i][1] < uebDaten.getpnrUeb()[0]) {

                // Anweisungen für Passpunkte

                // speichert den aktuellen Wert der Bildnummer
                // an der Stelle i in die Variable m
                m = bildkoorDaten.getbildnrAndpnrGEM()[i][0];

                // w --> Cursor damit die Werte auch an der
                // richtgen Stelle eingetragen werden
                // Bsp.: Bildnummer ist 2, d.h. der 1. Wert kommt
                // an die 6. Spalte im Array (da mit 0 beginnend)
                w = m * 6 - 6;
                x = 1; // für Passpunkt

                Fuellel0Matrix(u, i, x); // l' = lgem - l0

                /* *****************************************************
                 * sorgfältig lesen - Knackpunkt der Designmatrix !!!  *
                 ***************************************************** */

                // u ist die jeweils aktuelle Zeile für x'
                // u + 1 für y'
                for (int y = u; y <= u + 1; y++) {
                    q = 7;

                    // w ist der Cursor - siehe oben
                    // wenn Cursorposition 7 (bei Bildnummer 2) ist, dann ist
                    // der letzte Wert bei Position 12
                    for (int z = w; z <= w + 5; z++) {

                        // für z.B. Position 7 wird der Wert 6 eingetragen
                        // für Pos. 8 eine 5 --> bis Pos. 12 mit dem Wert 1
                        q = q - 1;
                        designMatrix[y][z] = q;
                        // 6,5,4,3,2,1 an die entsprechende Position

                        // Methodenaufruf, welcher die Werte zwischen 6 und
                        // 1 übergibt
                        // und je nach Wert einen passenden Algorithmus
                        // anwendet
                        WerteSechsBisEinsFuellen(y, z, i, x);
                        // Index i wird benötigt um die jeweils aktuelle
                        // Position (über Klasse Positionen) des Passpunktes,
                        // des Projektionzentrums und auch die
                        // des Übertragungspunktes (siehe folgenden else-Zweig)
                        // zu bestimmen
                    }
                }
            } else {

                // Anweisungen für Übertragungspunkte
                // mit Werten ab Punktnummer 1000

                int l = bildkoorDaten.getbildnrAndpnrGEM()[i][1];
                m = bildkoorDaten.getbildnrAndpnrGEM()[i][0];

                w = m * 6 - 6; // Cursor, analog zu den Passpunkten
                x = 2; // für Uebpunkt

                Fuellel0Matrix(u, i, x);

                // analog zu den Passpunkten
                for (int y = u; y <= u + 1; y++) {
                    q = 7;
                    for (int z = w; z <= w + 5; z++) {
                        q = q - 1;
                        designMatrix[y][z] = q;

                        // für x' u. y' 6,5,4,3,2,1
                        WerteSechsBisEinsFuellen(y, z, i, x);
                    }
                }

                // die nachfolgenden Anweisungen (bis vor die Zeile u += 2;)
                // beziehen sich auf die partiellen Ableitungen der Koord. der
                // Uebpunkte

                sucheWert = l;

                // sucht die Position des Übertragungspunktes für die
                // Stellen X,Y,Z in dem Array für Uebpunkte
                for (int r = 0; r < uebDaten.getpnrUeb().length; r++) {

                    if (uebDaten.getpnrUeb()[r] == sucheWert) {
                        gefundenWert = r + 1;
                        // Wert muss größer 0 sein, da sonst der
                        // folgende Cursor (neuePosition) falsch bestimmt wird
                    }
                }

                // neuePosition --> Cursor für die Koordinaten der
                // Übertragungspunkte (=Uebpunkte)
                int neuePosition = (6 * projDaten.getanzProjektionszentren()) + (3 * gefundenWert) - 3;
                // Warum "-3" ? Grund: es ist die Startposition X
                // des Übertragungspunktes von Interesse

                // zusätzlich zu der Herangehensweise wie bei den
                // Passpunkten werden die Werte 9 bis 7 für die
                // Übertragunspunkte und deren Algorithmen benötigt
                for (int y = u; y <= u + 1; y++) {
                    q = 10;
                    for (int z = neuePosition; z <= neuePosition + 2; z++) {
                        q = q - 1;
                        // Zahl 9 --> X, Zahl 8 --> Y, Zahl 7 --> Z
                        designMatrix[y][z] = q;

                        // für x' u. y' 9,8,7
                        WerteNeunBisSiebenFuellen(y, z, i);
                    }
                }
            }
            u += 2; // da ein Durchlauf x' und y' abarbeitet
        }
    }

    // Füllen des l0 Vektor für Passpunkte
    public void Fuellel0Matrix(int u, int i, int x) {

        if (x == 1) {
            // ermittelt die Zeile des Passpunktes
            gefundenWert = pos.getPosPasspunkt(i);

            // übergibt die Werte aus dem passDaten.getpunktePass() - Array an
            // die Variablen X, Y, Z
            X = passDaten.getpunktePass()[gefundenWert][0];
            Y = passDaten.getpunktePass()[gefundenWert][1];
            Z = passDaten.getpunktePass()[gefundenWert][2];
        } else {
            // ermittelt die Zeile des Uebertragunspunktes
            gefundenWert = pos.getPosUebpunkt(i);

            X = uebDaten.getpunkteUeb()[gefundenWert][0];
            Y = uebDaten.getpunkteUeb()[gefundenWert][1];
            Z = uebDaten.getpunkteUeb()[gefundenWert][2];
        }

        // ermittelt die Zeile der Projektionspunktnummer
        gefundenWert = pos.getPosProj(i);

        // übergibt die Werte aus dem
        // Array (projDaten.getpunkteAndRotationAndCameraProj()) an die
        // Variablen X0, Y0, Z0
        X0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][0];
        Y0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][1];
        Z0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][2];

        // Berechnung von Zähler und Nenner der Kollinaritätsgleichungen
        // die Formeln befinden sich weiter unten im Quelltext
        R = getR(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);
        S = getS(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);
        T = getT(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);

        // füllen des l0 Vektor
        // für x'
        l0Matrix[u][0] = getxStrich(c, R, T);
        // und für y'
        l0Matrix[u + 1][0] = getyStrich(c, S, T);
    }

    // Füllen der Matrix A für die Werte 9,8,7
    public void WerteNeunBisSiebenFuellen(int y, int z, int i) {

        gefundenWert = pos.getPosUebpunkt(i);

        X = uebDaten.getpunkteUeb()[gefundenWert][0];
        Y = uebDaten.getpunkteUeb()[gefundenWert][1];
        Z = uebDaten.getpunkteUeb()[gefundenWert][2];

        gefundenWert = pos.getPosProj(i);

        X0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][0];
        Y0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][1];
        Z0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][2];

        R = getR(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);
        S = getS(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);
        T = getT(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);

        // für x'
        if (u == y) {

            if (designMatrix[y][z] == 9) {

                designMatrix[y][z] = getDXDX(R, T);
            }
            if (designMatrix[y][z] == 8) {

                designMatrix[y][z] = getDXDY(R, T);
            }
            if (designMatrix[y][z] == 7) {

                designMatrix[y][z] = getDXDZ(R, T);
            }
        }
        // für y'
        if (u < y) {

            if (designMatrix[y][z] == 9) {

                designMatrix[y][z] = getDYDX(S, T);
            }
            if (designMatrix[y][z] == 8) {

                designMatrix[y][z] = getDYDY(S, T);
            }
            if (designMatrix[y][z] == 7) {

                designMatrix[y][z] = getDYDZ(S, T);
            }
        }
    }

    // Füllen der Matrix A für die Werte 6,5,4,3,2,1 der Passpunkte

    public void WerteSechsBisEinsFuellen(int y, int z, int i, int x) {

        if (x == 1) {
            gefundenWert = pos.getPosPasspunkt(i);

            X = passDaten.getpunktePass()[gefundenWert][0];
            Y = passDaten.getpunktePass()[gefundenWert][1];
            Z = passDaten.getpunktePass()[gefundenWert][2];

        } else {

            gefundenWert = pos.getPosUebpunkt(i);

            X = uebDaten.getpunkteUeb()[gefundenWert][0];
            Y = uebDaten.getpunkteUeb()[gefundenWert][1];
            Z = uebDaten.getpunkteUeb()[gefundenWert][2];
        }

        gefundenWert = pos.getPosProj(i);

        X0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][0];
        Y0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][1];
        Z0 = projDaten.getpunkteAndRotationAndCameraProj()[gefundenWert][2];

        R = getR(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);
        S = getS(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);
        T = getT(X, X0, Y, Y0, Z, Z0, kappa, phi, omega);

        // für x'
        if (u == y) {
            if (designMatrix[y][z] == 6) {

                designMatrix[y][z] = getDXDX0(R, T);
            }
            if (designMatrix[y][z] == 5) {

                designMatrix[y][z] = getDXDY0(R, T);
            }
            if (designMatrix[y][z] == 4) {

                designMatrix[y][z] = getDXDZ0(R, T);
            }
            if (designMatrix[y][z] == 3) {

                designMatrix[y][z] = getDXDKAPPA(S, T);
            }
            if (designMatrix[y][z] == 2) {

                designMatrix[y][z] = getDXDPHI(R, T, X, X0, Y, Y0, Z, Z0);
            }
            if (designMatrix[y][z] == 1) {

                designMatrix[y][z] = getDXDOMEGA(R, T, Y, Y0, Z, Z0);
            }
        }

        // für y'
        if (u < y) {
            if (designMatrix[y][z] == 6) {

                designMatrix[y][z] = getDYDX0(S, T);
            }
            if (designMatrix[y][z] == 5) {

                designMatrix[y][z] = getDYDY0(S, T);
            }
            if (designMatrix[y][z] == 4) {

                designMatrix[y][z] = getDYDZ0(S, T);
            }
            if (designMatrix[y][z] == 3) {

                designMatrix[y][z] = getDYDKAPPA(R, T);
            }
            if (designMatrix[y][z] == 2) {

                designMatrix[y][z] = getDYDPHI(S, T, X, X0, Y, Y0, Z, Z0);
            }
            if (designMatrix[y][z] == 1) {

                designMatrix[y][z] = getDYDOMEGA(S, T, X, X0, Y, Y0, Z, Z0);
            }
        }
    }

    // Koeffizienten

    public double getA11(double kappa, double phi, double omega) {

        return Math.cos(kappa) * Math.cos(phi);
    }

    public double getA12(double kappa, double phi, double omega) {

        return Math.cos(kappa) * Math.sin(phi) * Math.sin(omega) + Math.sin(kappa) * Math.cos(omega);
    }

    public double getA13(double kappa, double phi, double omega) {

        return (-1) * Math.cos(kappa) * Math.sin(phi) * Math.cos(omega) + Math.sin(kappa) * Math.sin(omega);
    }

    public double getA21(double kappa, double phi, double omega) {

        return (-1) * Math.sin(kappa) * Math.cos(phi);
    }

    public double getA22(double kappa, double phi, double omega) {

        return (-1) * Math.sin(kappa) * Math.sin(phi) * Math.sin(omega) + Math.cos(kappa) * Math.cos(omega);
    }

    public double getA23(double kappa, double phi, double omega) {

        return Math.sin(kappa) * Math.sin(phi) * Math.cos(omega) + Math.cos(kappa) * Math.sin(omega);
    }

    public double getA31(double kappa, double phi, double omega) {

        return Math.sin(phi);
    }

    public double getA32(double kappa, double phi, double omega) {

        return (-1) * Math.cos(phi) * Math.sin(omega);
    }

    public double getA33(double kappa, double phi, double omega) {

        return Math.cos(phi) * Math.cos(omega);
    }

    // Zähler x'
    public double getR(double x, double x0, double y, double y0, double z, double z0, double kappa, double phi, double omega) {
        R = getA11(kappa, phi, omega) * (x - x0) + getA12(kappa, phi, omega) * (y - y0) + getA13(kappa, phi, omega) * (z - z0);
        return R;
    }

    // Zähler y'
    public double getS(double x, double x0, double y, double y0, double z, double z0, double kappa, double phi, double omega) {
        S = getA21(kappa, phi, omega) * (x - x0) + getA22(kappa, phi, omega) * (y - y0) + getA23(kappa, phi, omega) * (z - z0);
        return S;
    }

    // Nenner x' u. y'
    public double getT(double x, double x0, double y, double y0, double z, double z0, double kappa, double phi, double omega) {
        T = getA31(kappa, phi, omega) * (x - x0) + getA32(kappa, phi, omega) * (y - y0) + getA33(kappa, phi, omega) * (z - z0);
        return T;
    }

    // Kollinaritätsgleichungen

    // x' des l0 Vektor
    public double getxStrich(double c, double R, double T) {

        return c * (R / T);
    }

    // y' des l0 Vektor
    public double getyStrich(double c, double S, double T) {

        return c * (S / T);
    }

    // Differentialquotienten des Projektionszentrum X0,Y0,Z0 für x'

    // dx'/dx0

    public double getDXDX0(double R, double T) {

        return c * (R * getA31(kappa, phi, omega) - T * getA11(kappa, phi, omega)) / Math.pow(T, 2);
    }

    // dx'/dy0

    public double getDXDY0(double R, double T) {

        return c * (R * getA32(kappa, phi, omega) - T * getA12(kappa, phi, omega)) / Math.pow(T, 2);
    }

    // dx'/dz0

    public double getDXDZ0(double R, double T) {

        return c * (R * getA33(kappa, phi, omega) - T * getA13(kappa, phi, omega)) / Math.pow(T, 2);
    }

    // Differentialquotienten des Projektionszentrum X0,Y0,Z0 für y'

    // dy'/dx0

    public double getDYDX0(double S, double T) {

        return c * (S * getA31(kappa, phi, omega) - T * getA21(kappa, phi, omega)) / Math.pow(T, 2);
    }

    // dy'/dy0

    public double getDYDY0(double S, double T) {

        return c * (S * getA32(kappa, phi, omega) - T * getA22(kappa, phi, omega)) / Math.pow(T, 2);
    }

    // dy'/dz0

    public double getDYDZ0(double S, double T) {

        return c * (S * getA33(kappa, phi, omega) - T * getA23(kappa, phi, omega)) / Math.pow(T, 2);
    }

    // Differentialquotienten des Projektionszentrum kappa,phi,omega für x'

    // dx'/dkappa

    public double getDXDKAPPA(double S, double T) {

        return c * (S / T);
    }

    // dx'/dphi

    public double getDXDPHI(double R, double T, double x, double x0, double y, double y0, double z, double z0) {

        return c * (-Math.pow(T, 2) * Math.cos(kappa) - R * (Math.cos(phi) * (x - x0) + Math.sin(phi) * Math.sin(omega) * (y - y0) - Math.sin(phi) * Math.cos(omega) * (z - z0))) / (Math.pow(T, 2));
    }

    // dx'/domega

    public double getDXDOMEGA(double R, double T, double y, double y0, double z, double z0) {

        return c * (T * (getA12(kappa, phi, omega) * (z - z0) - getA13(kappa, phi, omega) * (y - y0)) - R * (getA32(kappa, phi, omega) * (z - z0) - getA33(kappa, phi, omega) * (y - y0))) / (Math.pow(T, 2));
    }

    // Differentialquotienten des Projektionszentrum kappa,phi,omega für y'

    // dy'/dkappa

    public double getDYDKAPPA(double R, double T) {

        return c * (-R / T);
    }

    // dy'/dphi

    public double getDYDPHI(double S, double T, double x, double x0, double y, double y0, double z, double z0) {

        return c * (Math.pow(T, 2) * Math.sin(kappa) - S * (Math.cos(phi) * (x - x0) + Math.sin(phi) * Math.sin(omega) * (y - y0) - Math.sin(phi) * Math.cos(omega) * (z - z0))) / (Math.pow(T, 2));
    }

    // dy'/domega

    public double getDYDOMEGA(double S, double T, double x, double x0, double y, double y0, double z, double z0) {

        return c * (T * (getA22(kappa, phi, omega) * (z - z0) - getA23(kappa, phi, omega) * (y - y0)) - S * (getA32(kappa, phi, omega) * (z - z0) - getA33(kappa, phi, omega) * (y - y0))) / (Math.pow(T, 2));
    }

    // Differentialquotienten der Übertragungspunkte X,Y,Z für x'

    // dx'/dx

    public double getDXDX(double R, double T) {

        return c * (T * getA11(kappa, phi, omega) - R * getA31(kappa, phi, omega)) / (Math.pow(T, 2));
    }

    // dx'/dy

    public double getDXDY(double R, double T) {

        return c * (T * getA12(kappa, phi, omega) - R * getA32(kappa, phi, omega)) / (Math.pow(T, 2));
    }

    // dx'/dz

    public double getDXDZ(double R, double T) {

        return c * (T * getA13(kappa, phi, omega) - R * getA33(kappa, phi, omega)) / (Math.pow(T, 2));
    }

    // Differentialquotienten der Übertragungspunkte X,Y,Z für y'

    // dy'/dx

    public double getDYDX(double S, double T) {

        return c * (T * getA21(kappa, phi, omega) - S * getA31(kappa, phi, omega)) / (Math.pow(T, 2));
    }

    // dy'/dy

    public double getDYDY(double S, double T) {

        return c * (T * getA22(kappa, phi, omega) - S * getA32(kappa, phi, omega)) / (Math.pow(T, 2));
    }

    // dy'/dz

    public double getDYDZ(double S, double T) {

        return c * (T * getA23(kappa, phi, omega) - S * getA33(kappa, phi, omega)) / (Math.pow(T, 2));
    }

    public double[][] getDesignmatrix() {

        return designMatrix;
    }

    public double[][] getl0Matrix() {

        return l0Matrix;
    }
}