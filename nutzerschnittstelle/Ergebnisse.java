package com.htw.Triangulation.nutzerschnittstelle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Ergebnisse extends JDialog {

    // Größe des Bildschirms ermitteln
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // ausgeglichene Unbekannte
    private File fileausgeglicheneUnbekannte;

    // weitere Parameter
    private File fileweitere_Parameter;

    private JLabel label1;
    private JLabel label2;
    private JLabel labelleereZeile;
    private JLabel labelleereZeile2;
    private JLabel labels0;
    private JLabel labelweitereParameter;
    private JLabel labelUnbekannte;

    private JButton ausgeglichenUnbekannte;
    private JButton weitereParameter;

    private Box unbekannte;
    private Box weitere_Parameter;
    private Box vertikaleAnordnung;

    private JPanel jPanelNorth;
    private JPanel jPanelCenter;
    private JPanel jPanelSouth;

    public String desktopPath = System.getProperty("user.home") + "/Schreibtisch/";

    // Modaler Dialog, d.h. dass andere geöffnete Fenster (z.B. Hauptfenster)
    // erst wieder Eingaben annehmen, wenn der modale Dialog geschlossen ist
    public Ergebnisse(JFrame owner, boolean modal) {

        super(owner, modal);
        initComponents();
    }

    private void initComponents() {

        {
            // Set Look & Feel
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        // North
        jPanelNorth = new JPanel();
        jPanelNorth.setBackground(Color.BLACK);

        label1 = new JLabel("Resultat");
        label1.setFont(new Font("sansserif", Font.BOLD, 18));
        label1.setForeground(Color.WHITE);
        jPanelNorth.add(label1);

        // Center
        jPanelCenter = new JPanel();
        jPanelCenter.setBackground(Color.ORANGE);

        labelleereZeile = new JLabel(" ");
        labelleereZeile2 = new JLabel(" ");

        labelUnbekannte = new JLabel("ausgeglichene Unbekannte");
        labelUnbekannte.setFont(new Font("sansserif", Font.BOLD, 14));
        ausgeglichenUnbekannte = new JButton("Anzeigen");
        ausgeglichenUnbekannte.setSize(new Dimension(175, 25));
        ausgeglichenUnbekannte.addActionListener(new ausgeglUnbekannte());

        // 1. Horizontale Box
        unbekannte = Box.createHorizontalBox();
        unbekannte.add(labelUnbekannte);
        unbekannte.add(Box.createHorizontalStrut(20));
        unbekannte.add(ausgeglichenUnbekannte);

        labelweitereParameter = new JLabel("        weitere Parameter        ");
        labelweitereParameter.setFont(new Font("sansserif", Font.BOLD, 14));
        weitereParameter = new JButton("Anzeigen");
        weitereParameter.setPreferredSize(new Dimension(175, 25));
        weitereParameter.addActionListener(new ausgeglichenMesswerte());

        // 2. Horizontale Box
        weitere_Parameter = Box.createHorizontalBox();
        weitere_Parameter.add(labelweitereParameter);
        weitere_Parameter.add(Box.createHorizontalStrut(20));
        weitere_Parameter.add(weitereParameter);

        labels0 = new JLabel();
        labels0.setFont(new Font("sansserif", Font.BOLD, 14));
        labels0.setForeground(Color.black);

        // Vertikale Box
        vertikaleAnordnung = Box.createVerticalBox();
        vertikaleAnordnung.add(labelleereZeile);
        vertikaleAnordnung.add(unbekannte);
        vertikaleAnordnung.add(labelleereZeile2);
        vertikaleAnordnung.add(weitere_Parameter);

        jPanelCenter.add(vertikaleAnordnung);

        // South
        jPanelSouth = new JPanel();
        jPanelSouth.setBackground(Color.BLACK);

        label2 = new JLabel("Weitere Informationen finden Sie in der Hilfe");
        label2.setForeground(Color.WHITE);

        jPanelSouth.add(label2);

        // Übergabe der Hauptkomponenten an das ContentPane
        add(jPanelNorth, BorderLayout.NORTH);
        add(jPanelCenter, BorderLayout.CENTER);
        add(jPanelSouth, BorderLayout.SOUTH);

        // Dateiinitialisierung
        fileausgeglicheneUnbekannte = new File(desktopPath + "ausgeglichene_Unbekannte.txt");
        fileweitere_Parameter = new File(desktopPath + "weitere_Parameter.txt");

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

    class ausgeglUnbekannte implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (fileausgeglicheneUnbekannte.exists()) {
                try {
                    Runtime.getRuntime().exec("gedit " + fileausgeglicheneUnbekannte);
                } catch (IOException ex) {
                    // TODO Auto-generated catch block
                    // ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Beispiel nicht existent!");
            }
        }
    }

    class ausgeglichenMesswerte implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (fileweitere_Parameter.exists()) {
                try {
                    Runtime.getRuntime().exec("gedit " + fileweitere_Parameter);
                } catch (IOException ex) {
                    // TODO Auto-generated catch block
                    // ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Beispiel nicht existent!");
            }
        }
    }
}