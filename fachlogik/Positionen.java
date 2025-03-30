package com.htw.Triangulation.fachlogik;

/**
 * @author Christiane
 */
public class Positionen {

    private final Projektionsdaten projDaten;
    private final Passpunktdaten passDaten;
    private final Uebertragungspunktdaten uebDaten;
    private final Bildkoordinatendaten bildkoorDaten;
    private int gefundenWert;

    Positionen(Projektionsdaten projDaten, Passpunktdaten passDaten,
               Uebertragungspunktdaten uebDaten, Bildkoordinatendaten bildkoorDaten) {

        this.projDaten = projDaten;
        this.passDaten = passDaten;
        this.uebDaten = uebDaten;
        this.bildkoorDaten = bildkoorDaten;
    }

    // Position der Punktnummer des Passpunktes in der Passpunkt.txt

    public int getPosPasspunkt(int i) {

        for (int r = 0; r < passDaten.getpnrPass().length; r++) {

            if (passDaten.getpnrPass()[r] == bildkoorDaten.getbildnrAndpnrGEM()[i][1]) {
                gefundenWert = r;
                break;
            }
        }
        return gefundenWert;
    }

    // Position der Punktnummer des �bertragungspunktes in der UPunkte.txt

    public int getPosUebpunkt(int i) {

        for (int r = 0; r < uebDaten.getpnrUeb().length; r++) {

            if (uebDaten.getpnrUeb()[r] == bildkoorDaten.getbildnrAndpnrGEM()[i][1]) {
                gefundenWert = r;
                break;
            }
        }
        return gefundenWert;
    }

    // Position der Punktnummer des Projetionszentrum in der Bilder.txt

    public int getPosProj(int i) {
        for (int r = 0; r < projDaten.getpnrProj().length; r++) {

            if (projDaten.getpnrProj()[r] == bildkoorDaten.getbildnrAndpnrGEM()[i][0]) {
                gefundenWert = r;
                break;
            }
        }
        return gefundenWert;
    }
}
