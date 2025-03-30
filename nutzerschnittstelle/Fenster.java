package com.htw.Triangulation.nutzerschnittstelle;

import com.htw.Triangulation.fachlogik.Verarbeitung;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.StringTokenizer;

public class Fenster extends JFrame {
    private static final long serialVersionUID = 1L;
    public String desktopPath = System.getProperty("user.home") + "/Schreibtisch/";
    // Größe des Bildschirms ermitteln
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JPanel jPanelNorth;
    private final JPanel jPanelSouth;
    private int proj;
    private int pass;
    private int ueb;
    private int bildkord = 0;
    private int bild;
    private int bildPoint;
    private String lese;
    private File lastDir;
    // Projektionszentrum
    private String bildProjection;
    private String xWertProjection;
    private String yWertProjection;
    private String zWertProjection;
    private double xProjection;
    private double yProjection;
    private double zProjection;
    private String kappaProjection;
    private String phiProjection;
    private String omegaProjection;
    private double kProjection;
    private double pProjection;
    private double oProjection;
    private String cameraProjection;
    private double cProjection;
    private int i;
    private int anzProjektionszentren;
    private BufferedReader br3;
    private BufferedReader br4;
    private File fileProjektionszentren;
    private JFileChooser projectionFile;
    // Passpunkte
    private File filePasspunkte;
    private BufferedReader br5;
    private BufferedReader br6;
    private int anzPasspunkte;
    private JFileChooser passpunkteFile;
    // Übertragungspunkte
    private File fileUebertragungspunkte;
    private JFileChooser uebertragungspunkteFile;
    private BufferedReader br7;
    private BufferedReader br8;
    private int anzUebertragungspunkte;
    // Bildkoordinaten
    private String bildPunkt;
    private File fileBildkoordinaten;
    private JFileChooser bildkoordinatenpunkteFile;
    private BufferedReader br9;
    private BufferedReader br10;
    private int anzBildkoordinaten;
    // Protokolle der Ergebnisse
    private File fileausgeglicheneUnbekannte;
    private BufferedWriter brausgeglicheneUnbekannte;
    private File fileweitere_Parameter;

    // Objekterzeugung
    private BufferedWriter brfileweitere_Parameter;
    /**
     * @uml.property name="verarbeiten"
     * @uml.associationEnd
     */
    private Verarbeitung verarbeiten;
    /**
     * @uml.property name="erg"
     * @uml.associationEnd
     */
    private final Ergebnisse erg = new Ergebnisse(this, true);
    private final JLabel label1;
    private final JLabel labelProjection;
    private final JLabel labelPasspunkte;
    private final JLabel labelUebertragungspunkte;
    private final JLabel labelBildkoordinaten;
    private final JButton jButtonAusgleichnung;
    private final JButton jButtonHelp;
    private final JButton jButtonProjektion;
    private final JButton jButtonPasspunkte;
    private final JButton jButtonUebertragungspunkte;
    private final JButton jButtonBildkoordinaten;
    // JScrollPane scrollPane;
    private final JPanel jPanelCenter;
    // Abbruchparameter
    private double abbruchKriterium = 0;
    private double testeKriterium = 0;
    private int stop = 0;

    /**
     * Create the frame.
     */
    public Fenster() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // North
        label1 = new JLabel("Triangulation - Bündelblockausgleichung");
        label1.setFont(new Font("SansSerif", Font.PLAIN, 18));
        label1.setForeground(Color.WHITE); // Color.WHITE --> Konstante

        jPanelNorth = new JPanel();
        jPanelNorth.setBackground(Color.BLACK);

        // 6. horizontale BOX
        jButtonHelp = new JButton("?");
        jPanelNorth.add(jButtonHelp);
        jButtonHelp.setFont(new Font("Dialog", Font.PLAIN, 12));
        jButtonHelp.setPreferredSize(new Dimension(40, 20));
        jPanelNorth.add(label1);

        // Vertikale Box auf Panel Center setzen
        jPanelCenter = new JPanel();
        jPanelCenter.setBackground(Color.orange);
        jPanelCenter.setLayout(null);

        // South
        jPanelSouth = new JPanel(); // Panel im S�den
        jPanelSouth.setBackground(Color.BLACK);
        jButtonAusgleichnung = new JButton("Ausgleichung");
        jButtonAusgleichnung.setFont(new Font("sansserif", Font.ROMAN_BASELINE, 13));
        jPanelSouth.add(jButtonAusgleichnung);

        // Übergabe der Hauptkomponenten an das ContentPane
        getContentPane().add(jPanelNorth, BorderLayout.NORTH);
        getContentPane().add(jPanelCenter, BorderLayout.CENTER);
        jButtonBildkoordinaten = new JButton("Einlesen");
        jButtonBildkoordinaten.setFont(new Font("Dialog", Font.PLAIN, 12));
        jButtonBildkoordinaten.setBounds(300, 140, 92, 25);
        jPanelCenter.add(jButtonBildkoordinaten);
        jButtonUebertragungspunkte = new JButton("Einlesen");
        jButtonUebertragungspunkte.setFont(new Font("Dialog", Font.PLAIN, 12));
        jButtonUebertragungspunkte.setBounds(300, 100, 92, 25);
        jPanelCenter.add(jButtonUebertragungspunkte);
        jButtonPasspunkte = new JButton("Einlesen");
        jButtonPasspunkte.setFont(new Font("Dialog", Font.PLAIN, 12));
        jButtonPasspunkte.setBounds(300, 60, 92, 25);
        jPanelCenter.add(jButtonPasspunkte);
        jButtonProjektion = new JButton("Einlesen");
        jButtonProjektion.setFont(new Font("Dialog", Font.PLAIN, 12));
        jButtonProjektion.setBounds(300, 20, 92, 25);
        jPanelCenter.add(jButtonProjektion);

        // 4. horizontale BOX
        labelBildkoordinaten = new JLabel("Bildkoordinaten");
        labelBildkoordinaten.setHorizontalAlignment(SwingConstants.RIGHT);
        labelBildkoordinaten.setBounds(11, 140, 240, 17);
        jPanelCenter.add(labelBildkoordinaten);
        labelBildkoordinaten.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // 3. horizontale BOX
        labelUebertragungspunkte = new JLabel("Übertragungspunkte");
        labelUebertragungspunkte.setHorizontalAlignment(SwingConstants.RIGHT);
        labelUebertragungspunkte.setBounds(11, 100, 240, 17);
        jPanelCenter.add(labelUebertragungspunkte);
        labelUebertragungspunkte.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // 2. horizontale BOX
        labelPasspunkte = new JLabel("Passpunkte");
        labelPasspunkte.setHorizontalAlignment(SwingConstants.RIGHT);
        labelPasspunkte.setBounds(11, 60, 240, 17);
        jPanelCenter.add(labelPasspunkte);
        labelPasspunkte.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // 1. horizontale BOX
        labelProjection = new JLabel("Projektionszentren der Bilder");
        labelProjection.setHorizontalAlignment(SwingConstants.RIGHT);
        labelProjection.setBounds(11, 25, 240, 17);
        jPanelCenter.add(labelProjection);
        labelProjection.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Action Buttons
        jButtonProjektion.addActionListener(new ProjectionListener());
        jButtonPasspunkte.addActionListener(new PasspunkteListener());
        jButtonUebertragungspunkte.addActionListener(new UebertragungspunkteListener());
        jButtonBildkoordinaten.addActionListener(new BildkoordinatenListener());
        jButtonAusgleichnung.addActionListener(new AusgleichungListener());
        jButtonHelp.addActionListener(new HelpListener());
        getContentPane().add(jPanelSouth, BorderLayout.SOUTH);

        // JFrame soll 460px * 300 px groß sein
        Dimension frameSize = new Dimension(460, 300);

        // Größe zuordnen
        this.setSize(frameSize);

        // Position des JFrames errechnen
        int left = (screenSize.width - frameSize.width) / 2;
        int top = (screenSize.height - frameSize.height) / 2;

        // Position zuordnen
        this.setLocation(left, top);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Fenster frame = new Fenster();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public BufferedWriter schreibeausgeglUnbekannte() {

        // Protokoll initialisieren
        fileausgeglicheneUnbekannte = new File(desktopPath + "ausgeglichene_Unbekannte.txt");

        try {
            // Daten werden jedesmal neugeschrieben
            brausgeglicheneUnbekannte = new BufferedWriter(new FileWriter(fileausgeglicheneUnbekannte));
            // Daten werden immer nur angehängt --> Zusatz true erforderlich
            // brausgeglicheneUnbekannte = new BufferedWriter(new
            // FileWriter(fileausgeglicheneUnbekannte, true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return brausgeglicheneUnbekannte;
    }

    public BufferedWriter schreibeweitereParameter() {

        // Protokoll initialisieren
        fileweitere_Parameter = new File(desktopPath + "weitere_Parameter.txt");

        try {
            brfileweitere_Parameter = new BufferedWriter(new FileWriter(fileweitere_Parameter));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return brfileweitere_Parameter;
    }

    public void liesDateiProjektionszentren() {

        try {
            BufferedReader br; // liest Zeile für Zeile
            br = new BufferedReader(new FileReader(fileProjektionszentren));
            // Filereader übergibt BufferedReader die Zeichen

            i = 0;

            while ((lese = br.readLine()) != null) {
                i++;
            }

            // Anzahl der Projektionszentren --> wichtig für die Dimension des
            // Projektions-Array
            this.anzProjektionszentren = i;

            br.close();

            jButtonProjektion.setEnabled(false);
            proj = 1;

        } catch (IOException io) {
            System.out.println(io.getMessage());

        }
        try {
            br3 = new BufferedReader(new FileReader(fileProjektionszentren));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            br4 = new BufferedReader(new FileReader(fileProjektionszentren));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int[] getProjectionsnummer(int[] pnrProj, BufferedReader br3) {

        try {
            i = 0;

            while ((lese = br3.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();
                bild = Integer.parseInt(bildProjection);

                pnrProj[i] = bild;

                i++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return pnrProj;
    }

    public double[][] getProjectionspunkteAndCamera(
            double[][] punkteAndRotationProj, BufferedReader br4) {

        try {
            i = 0;

            while ((lese = br4.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();

                xWertProjection = st.nextToken();
                xProjection = Double.parseDouble(xWertProjection);
                punkteAndRotationProj[i][0] = xProjection;

                yWertProjection = st.nextToken();
                yProjection = Double.parseDouble(yWertProjection);
                punkteAndRotationProj[i][1] = yProjection;

                zWertProjection = st.nextToken();
                zProjection = Double.parseDouble(zWertProjection);
                punkteAndRotationProj[i][2] = zProjection;

                kappaProjection = st.nextToken();
                kProjection = Double.parseDouble(kappaProjection);

                // eingelesene Werte in RAD umrechnen
                punkteAndRotationProj[i][3] = kProjection * Math.PI / 180;

                phiProjection = st.nextToken();
                pProjection = Double.parseDouble(phiProjection);
                punkteAndRotationProj[i][4] = pProjection * Math.PI / 180;

                omegaProjection = st.nextToken();
                oProjection = Double.parseDouble(omegaProjection);
                punkteAndRotationProj[i][5] = oProjection * Math.PI / 180;

                cameraProjection = st.nextToken();
                cProjection = Double.parseDouble(cameraProjection);
                punkteAndRotationProj[i][6] = cProjection;

                i++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return punkteAndRotationProj;
    }

    public void liesDateiPasspunkte() {

        try {
            BufferedReader br; // liest Zeile für Zeile
            br = new BufferedReader(new FileReader(filePasspunkte));
            // Filereader übergibt BufferedReader die Zeichen

            i = 0;

            while ((lese = br.readLine()) != null) {
                i++;
            }

            this.anzPasspunkte = i;

            br.close();

            jButtonPasspunkte.setEnabled(false);
            pass = 1;

        } catch (IOException io) {
            System.out.println(io.getMessage());

        }
        try {
            br5 = new BufferedReader(new FileReader(filePasspunkte));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            br6 = new BufferedReader(new FileReader(filePasspunkte));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int[] getPasspunktnummer(int[] pnrPass, BufferedReader br5) {

        try {
            i = 0;

            while ((lese = br5.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();
                bild = Integer.parseInt(bildProjection);

                pnrPass[i] = bild;

                i++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return pnrPass;
    }

    public double[][] getPasspunktpunkte(double[][] punktePass,
                                         BufferedReader br6) {

        try {
            i = 0;

            while ((lese = br6.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();

                xWertProjection = st.nextToken();
                xProjection = Double.parseDouble(xWertProjection);
                punktePass[i][0] = xProjection;

                yWertProjection = st.nextToken();
                yProjection = Double.parseDouble(yWertProjection);
                punktePass[i][1] = yProjection;

                zWertProjection = st.nextToken();
                zProjection = Double.parseDouble(zWertProjection);
                punktePass[i][2] = zProjection;

                i++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return punktePass;
    }

    public void liesDateiUebertragungspunkte() {

        try {
            BufferedReader br; // liest Zeile für Zeile
            br = new BufferedReader(new FileReader(fileUebertragungspunkte));
            // Filereader übergibt BufferedReader die Zeichen

            i = 0;

            while ((lese = br.readLine()) != null) {
                i++;
            }

            this.anzUebertragungspunkte = i;
            ueb = 1;

            br.close();

            jButtonUebertragungspunkte.setEnabled(false);

        } catch (IOException io) {
            System.out.println(io.getMessage());

        }
        try {
            br7 = new BufferedReader(new FileReader(fileUebertragungspunkte));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            br8 = new BufferedReader(new FileReader(fileUebertragungspunkte));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int[] getUebertragungspunktnummer(int[] pnrUeb, BufferedReader br7) {

        try {
            i = 0;

            while ((lese = br7.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();
                bild = Integer.parseInt(bildProjection);

                pnrUeb[i] = bild;

                i++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return pnrUeb;
    }

    public double[][] getUebertragungspunktpunkte(double[][] punkteUeb,
                                                  BufferedReader br8) {

        try {
            i = 0;

            while ((lese = br8.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();

                xWertProjection = st.nextToken();
                xProjection = Double.parseDouble(xWertProjection);
                punkteUeb[i][0] = xProjection;

                yWertProjection = st.nextToken();
                yProjection = Double.parseDouble(yWertProjection);
                punkteUeb[i][1] = yProjection;

                zWertProjection = st.nextToken();
                zProjection = Double.parseDouble(zWertProjection);
                punkteUeb[i][2] = zProjection;

                i++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return punkteUeb;
    }

    public void liesDateiBildkoordinaten() {

        try {
            BufferedReader br; // liest Zeile für Zeile
            br = new BufferedReader(new FileReader(fileBildkoordinaten));
            // Filereader übergibt BufferedReader die Zeichen

            i = 0;

            while ((lese = br.readLine()) != null) {
                i++;
            }

            this.anzBildkoordinaten = i;
            bildkord = 1;

            br.close();

            jButtonBildkoordinaten.setEnabled(false);

        } catch (IOException io) {
            System.out.println(io.getMessage());

        }

        try {
            br9 = new BufferedReader(new FileReader(fileBildkoordinaten));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            br10 = new BufferedReader(new FileReader(fileBildkoordinaten));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // UEB

    public int[][] getBildkoordinatenpunktnummerAndBild(
            int[][] bildnrAndpnrGEM, BufferedReader br9) {

        try {
            i = 0;

            while ((lese = br9.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();
                bild = Integer.parseInt(bildProjection);
                bildnrAndpnrGEM[i][0] = bild;

                bildPunkt = st.nextToken();
                bildPoint = Integer.parseInt(bildPunkt);
                bildnrAndpnrGEM[i][1] = bildPoint;

                i++;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return bildnrAndpnrGEM;
    }

    public double[][] getBildkoordinatenpunkte(double[][] punkteBildkoord,
                                               BufferedReader br10) {

        try {
            i = 0;

            while ((lese = br10.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(lese, " ");

                bildProjection = st.nextToken();

                bildPunkt = st.nextToken();

                xWertProjection = st.nextToken();
                xProjection = Double.parseDouble(xWertProjection);
                punkteBildkoord[i][0] = xProjection;

                yWertProjection = st.nextToken();
                yProjection = Double.parseDouble(yWertProjection);
                punkteBildkoord[i][1] = yProjection;

                i++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return punkteBildkoord;
    }

    class AusgleichungListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                // sind alle Textdateien eingelesen, kann die Ausgleichung
                // erfolgen
                if (proj == 1 && pass == 1 && ueb == 1 && bildkord == 1) {

                    // Klasse Verarbeitung enthällt alle Objekte, die für diese
                    // Ausgleichung notwendig sind
                    verarbeiten = new Verarbeitung();

                    verarbeiten.erzeugeProjektionsdaten(br3, br4, anzProjektionszentren);

                    verarbeiten.erzeugePasspunktdaten(br5, br6, anzPasspunkte);

                    verarbeiten.erzeugeUebertragungspunktdaten(br7, br8, anzUebertragungspunkte);

                    verarbeiten.erzeugeBildkoordinatendaten(br9, br10, anzBildkoordinaten);

                    verarbeiten.erzeugeEinheitsmatrix(); // P = E

                    // mind. 1 Iteration wird ausgeführt

                    i = 0;
                    stop = 0;
                    testeKriterium = 0;
                    abbruchKriterium = 0;

                    do {

                        verarbeiten.erzeugeDesignMatrix(); // A-Matrix
                        verarbeiten.erzeugeNormalgleichungsMatrix(); // N-Matrix
                        verarbeiten.erzeugeGewichtsreziprokenMatrix(); // N^-1-Matrix
                        verarbeiten.erzeugehMatrix(); // h-Vektor
                        verarbeiten.Zuschlaege(); // Zuschläge
                        verarbeiten.erzeugeausgeglicheneUnbekannten(schreibeausgeglUnbekannte());
                        // ausgeglichene Unbekannte

                        verarbeiten.erzeugeVerbesserungen(); // v-Vektor

                        abbruchKriterium = verarbeiten.erzeuge_pvv_Matrix(); // pvv-Matrix
                        // [pvv] = Minimum
                        // testeKriterium = pvv(i-1)
                        // abbruchKriterium = pvv(i)

                        // pvv(i-1) < pvv(i) --> Abbruch mittels Divergenztest
                        if (testeKriterium < abbruchKriterium
                                && testeKriterium != 0) {

                            stop = 1;
                            break;
                        }

                        testeKriterium = abbruchKriterium;

                        verarbeiten.erzeugeneueGewichte(); // aktuell P = E
                        // Berechnung der neuen Gewichte (muss noch umgesetzt
                        // werden)

                        i++;

                    } while (stop != 1);

                    verarbeiten.setAnzahlIterationen(i);

                    verarbeiten.erzeugeausgeglicheneMesswerte(schreibeweitereParameter());
                    // ausgeglichene Messwerte

                    // verarbeiten.erzeugeAusgleichungsprobe();
                    // Ausgleichungsprobe

                    verarbeiten.erzeugestababweichungS0();
                    // Standardabweichung der Gewichtseinheit
                    // entspricht der Stababweichung der Beobachtungen sli (vom
                    // Gewicht p = 1)

                    verarbeiten.erzeugeRedundanzMatrix();

                    // Standardabweichungen sxi
                    verarbeiten.erzeugestababweichungderUnbekannten(); // sxi

                    erg.setResizable(false);
                    erg.setVisible(true);

                    // Einlese-Button wieder freigeben
                    jButtonProjektion.setEnabled(true);
                    jButtonPasspunkte.setEnabled(true);
                    jButtonUebertragungspunkte.setEnabled(true);
                    jButtonBildkoordinaten.setEnabled(true);

                    proj = 0;
                    pass = 0;
                    ueb = 0;
                    bildkord = 0;

                } else {
                    JOptionPane.showMessageDialog(null,
                            "Es sind noch Daten einzulesen");

                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Bitte prüfen Sie ihre Daten.", "Fehler",
                        JOptionPane.ERROR_MESSAGE);

                // Einlese-Button wieder freigeben
                jButtonProjektion.setEnabled(true);
                jButtonPasspunkte.setEnabled(true);
                jButtonUebertragungspunkte.setEnabled(true);
                jButtonBildkoordinaten.setEnabled(true);

                proj = 0;
                pass = 0;
                ueb = 0;
                bildkord = 0;
            }
        }
    }

    class HelpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            HELP help = new HELP();
            help.setResizable(false);
            help.setVisible(true);
        }
    }

    // Bildkoord

    class ProjectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            projectionFile = new JFileChooser();
            projectionFile.setDialogTitle("Projektionszentren");
            projectionFile.setApproveButtonText("Auswählen");

            // eliminiert die Auswahl "Alle Dateien"
            projectionFile.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdateien", "txt");
            projectionFile.setFileFilter(filter);

            projectionFile.setCurrentDirectory(lastDir);

            int returnValue = projectionFile.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                fileProjektionszentren = projectionFile.getSelectedFile();

                // lastDir --> merkt sich den zuletzt ge�ffneten Ordnerpfad
                lastDir = projectionFile.getCurrentDirectory();
                liesDateiProjektionszentren();
            }
        }
    }

    class PasspunkteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            passpunkteFile = new JFileChooser();
            passpunkteFile.setDialogTitle("Passpunkte");
            passpunkteFile.setApproveButtonText("Auswählen");

            // eliminiert die Auswahl "Alle Dateien"
            passpunkteFile.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Textdateien", "txt");
            passpunkteFile.setFileFilter(filter);

            passpunkteFile.setCurrentDirectory(lastDir);

            int returnValue = passpunkteFile.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                filePasspunkte = passpunkteFile.getSelectedFile();
                lastDir = passpunkteFile.getCurrentDirectory();
                liesDateiPasspunkte();
            }
        }
    }

    class UebertragungspunkteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            uebertragungspunkteFile = new JFileChooser();
            uebertragungspunkteFile.setDialogTitle("übertragungspunkte");
            uebertragungspunkteFile.setApproveButtonText("Auswählen");

            // eliminiert die Auswahl "Alle Dateien"
            uebertragungspunkteFile.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Textdateien", "txt");
            uebertragungspunkteFile.setFileFilter(filter);

            uebertragungspunkteFile.setCurrentDirectory(lastDir);

            int returnValue = uebertragungspunkteFile.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                fileUebertragungspunkte = uebertragungspunkteFile
                        .getSelectedFile();
                lastDir = uebertragungspunkteFile.getCurrentDirectory();
                liesDateiUebertragungspunkte();
            }
        }
    }

    class BildkoordinatenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            bildkoordinatenpunkteFile = new JFileChooser();
            bildkoordinatenpunkteFile.setDialogTitle("Bildkoordinaten");
            bildkoordinatenpunkteFile.setApproveButtonText("Auswählen");

            // eliminiert die Auswahl "Alle Dateien"
            bildkoordinatenpunkteFile.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Textdatei", "txt");
            bildkoordinatenpunkteFile.setFileFilter(filter);

            bildkoordinatenpunkteFile.setCurrentDirectory(lastDir);

            int returnValue = bildkoordinatenpunkteFile.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                fileBildkoordinaten = bildkoordinatenpunkteFile
                        .getSelectedFile();
                lastDir = bildkoordinatenpunkteFile.getCurrentDirectory();
                liesDateiBildkoordinaten();
            }
        }
    }
}