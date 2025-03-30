package com.htw.Triangulation.nutzerschnittstelle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serial;

public class HELP extends JFrame {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    // Größe des Bildschirms ermitteln
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JLabel label1;
    private JLabel label2;

    private JTextArea ta;
    private JScrollPane scrollPane;

    private JPanel jPanelNorth;
    private JPanel jPanelCenter;
    private JPanel jPanelSouth;

    // Bilder
    private File fileBilder;

    // Passpunkte
    private File filePasspunkte;

    // Übertragungpunkte
    private File fileUebpunkte;

    // Bildkoordinaten
    private File fileBildkoordinaten;

    private JButton Bilder;
    private JButton Passpunkte;
    private JButton UebPunkte;
    private JButton Bildkoordinaten;

    public HELP() {
        super();
        initComponents();
    }

    private void initComponents() {
        // North
        label1 = new JLabel("FIRST AID");
        label1.setFont(new Font("sansserif", Font.PLAIN, 18));
        label1.setForeground(Color.WHITE);

        jPanelNorth = new JPanel();
        jPanelNorth.setBackground(Color.black);
        jPanelNorth.add(label1);

        // Center
        ta = new JTextArea();
        // ta.setFont(new Font("sansserif", Font.ITALIC, 12));
        ta.setForeground(Color.black);
        ta.setBackground(Color.LIGHT_GRAY);
        // ta.setSize(new Dimension(620, 400));
        // ta.setPreferredSize(new Dimension(620, 400));
        ta.setEditable(false);

        ta
                .setText("Was ist bei den einzulesenden Daten zu beachten ?\n\nDie Daten für die Ausgleichung müssen strickt nach den Mustern \ngestaltet werden. Die Näherungswerte für die Drehwinkel sind dem \nArbeitsblatt zu entnehmen und die der Translationen aus der Karte.\n\nDie Passpunkte gehen von 1 bis 999. \nAb der Punktnummer 1000 folgen die Übertragungspunkte.\n\nHinweis: \nFür eine reibungslose Ausgleichung ist es zwingend \nerforderlich, alle Passpunkte und Übertragungspunkte \nin der Bildkoordinaten.txt zu verwenden.\nWeiterhin ist an dieser Stelle zu erwähnen, das Passpunkte, \nÜbertragungspunkte sowie die Bilder fortlaufend zu nummerieren sind.\nBei einem Stereobildpaar (2 Luftbilder) sind mindestens \n3 Übertragungspunkte in der Zone der 3-fach Überlappung notwendig,\nda für die Verknüpfung zweier Bilder mindestens 5 (besser mehr)\nhomologe Bildpunkte zu messen sind.");
        ta
                .append("\n\nFolgende Reihenfolge ist zu beachten: \n\n> Bilder.txt: Bildnummer, X0, Y0, Z0, kappa, phi, omega, -ck\n> Passpunkte.txt: Punktnummer, X, Y, Z\n> Übertragungspunkte: Punktnummer, X, Y, Z\n> Bildkoordinaten: Bildnummer, Punktnummer, x', y'\n");
        ta
                .append("\nSollten Sie als Ergebnis Dateien mit dem Inhalt NaN,\noder eine Fehlermeldung erhalten ist die Ausgleichung fehlgeschlagen.\nSie sollten daraufhin ihre Daten überprüfen.\nDie erreichten Genauigkeiten sind auf Plausibilität zu prüfen.");
        ta
                .append("\n\nBei weiteren Fragen wenden Sie sich bitte an den Hersteller");

        ta.setCaretPosition(0); // setzt den Textcursor an die Pos. 0

        scrollPane = new JScrollPane(ta);

        scrollPane.setViewportView(ta);
        scrollPane.setPreferredSize(new Dimension(455, 194));

        jPanelCenter = new JPanel();
        jPanelCenter.setBackground(Color.BLACK);
        jPanelCenter.add(scrollPane);

        // South
        label2 = new JLabel("Muster:");
        label2.setFont(new Font("sansserif", Font.PLAIN, 14));
        label2.setForeground(Color.WHITE);

        jPanelSouth = new JPanel();
        jPanelSouth.setBackground(Color.BLACK);
        jPanelSouth.add(label2);

        Bilder = new JButton("Bilder");
        Passpunkte = new JButton("Passpunkte");
        UebPunkte = new JButton("UebPunkte");
        Bildkoordinaten = new JButton("Bildkoordinaten");

        Bilder.setBackground(Color.orange);
        Bilder.setForeground(Color.black);
        Bilder.setFont(new Font("Dialog", Font.PLAIN, 12));

        Passpunkte.setBackground(Color.orange);
        Passpunkte.setForeground(Color.black);
        Passpunkte.setFont(new Font("Dialog", Font.PLAIN, 12));

        UebPunkte.setBackground(Color.orange);
        UebPunkte.setForeground(Color.black);
        UebPunkte.setFont(new Font("Dialog", Font.PLAIN, 12));

        Bildkoordinaten.setBackground(Color.orange);
        Bildkoordinaten.setForeground(Color.black);
        Bildkoordinaten.setFont(new Font("Dialog", Font.PLAIN, 12));

        jPanelSouth.add(Bilder);
        Bilder.addActionListener(new Bilder());

        jPanelSouth.add(Passpunkte);
        Passpunkte.addActionListener(new Passpunkte());

        jPanelSouth.add(UebPunkte);
        UebPunkte.addActionListener(new UebPunkte());

        jPanelSouth.add(Bildkoordinaten);
        Bildkoordinaten.addActionListener(new Bildkoordinaten());

        // Übergabe der Hauptkomponenten an das ContentPane
        add(jPanelNorth, BorderLayout.NORTH);
        add(jPanelCenter, BorderLayout.CENTER);
        add(jPanelSouth, BorderLayout.SOUTH);

        // Dateiinitialisierung
        // Bilder
        fileBilder = new File("/home/weideman/intellij/workbench/src/main/java/com/htw/Triangulation/Muster/Bilder.txt");

        // Passpunkte
        filePasspunkte = new File("/home/weideman/intellij/workbench/src/main/java/com/htw/Triangulation/Muster/PassPunkte.txt");

        // Übertragungspunkte
        fileUebpunkte = new File("/home/weideman/intellij/workbench/src/main/java/com/htw/Triangulation/Muster/UPunkte.txt");

        // Bildkoordinaten
        fileBildkoordinaten = new File("/home/weideman/intellij/workbench/src/main/java/com/htw/Triangulation/Muster/Bildkoordinaten.txt");

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

    class Bilder implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (fileBilder.exists()) {
                try {
                    Runtime.getRuntime().exec("gedit " + fileBilder);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Beispiel nicht existent!");
            }
        }
    }

    class Passpunkte implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (filePasspunkte.exists()) {
                try {
                    Runtime.getRuntime().exec("gedit " + filePasspunkte);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Beispiel nicht existent!");
            }
        }
    }

    class UebPunkte implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (fileUebpunkte.exists()) {
                try {
                    Runtime.getRuntime().exec("gedit " + fileUebpunkte);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Beispiel nicht existent!");
            }
        }
    }

    class Bildkoordinaten implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (fileBildkoordinaten.exists()) {
                try {
                    Runtime.getRuntime().exec("gedit " + fileBildkoordinaten);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Beispiel nicht existent!");
            }
        }
    }
}