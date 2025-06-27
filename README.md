# Bündelblockausgleichung mit generischem Ansatz

Dieses Projekt implementiert in **Java** eine generische Bündelblockausgleichung zur simultanen Optimierung der Kameraparameter und Objektpunkte. Grundlage sind Bildmessungen, Passpunkte und Übertragungspunkte.

## Features

- ✅ Java-Anwendung mit modularem Aufbau
- ✅ Unterstützung beliebiger Kameramodelle  
- ✅ Flexible Einlesung von Textdateien mit Beobachtungen und Startwerten (siehe Eingabedateien)
- ✅ Ausgabe aller relevanten Ergebnisse inkl. Standardabweichungen und angepasster Messwerte  
- ✅ Konvergenz- und Iterationskontrolle  

---

## Eingabedateien

Alle Dateien liegen als Textdateien im Hauptverzeichnis.

### `Bilder.txt`

Startparameter der äußeren Orientierung je Bild:  
BildNr X Y Z Omega Phi Kappa Brennweite

**Beispiel:**
```tèxt
1 4205 3200 800 0 0 0 -120.013
2 4365 3200 800 0 0 0 -120.013
3 4525 3200 800 0 0 0 -120.013
4 4685 3200 800 0 0 0 -120.013
5 4845 3200 800 0 0 0 -120.013
```

---

### `Bildkoordinaten.txt`

Bildmessungen pro Bild:  
BildNr PunktNr x [mm] y [mm]

**Beispiel:**
```tèxt
1 1 0.316 36.395
1 2 -1.164 -38.261
1 1000 32.093 35.720
1 1001 32.245 -0.531
1 1002 32.270 -38.776
2 1 -31.868 36.659
2 2 -33.305 -38.313
2 3 33.007 0.252
2 1000 -0.012 35.932
2 1001 -0.790 -0.484
2 1002 0.016 -38.884
2 1003 31.979 35.731
2 1004 31.948 -38.910
3 3 0.280 0.959
3 1000 -31.808 36.089
3 1001 -33.058 -0.117
3 1002 -31.037 -38.293
3 1003 0.292 36.227
3 1004 0.630 -38.011
3 1005 31.929 36.281
3 1006 32.741 0.607
3 1007 31.944 -38.015
4 3 -32.781 1.010
4 4 31.993 37.573
4 5 33.239 -38.593
4 1003 -31.311 36.305
4 1004 -31.478 -37.970
4 1005 0.121 36.308
4 1006 -0.247 0.599
4 1007 0.003 -38.036
5 4 0.334 37.340
5 5 1.157 -38.837
5 1005 -31.724 36.069
5 1006 -33.170 0.310
5 1007 -31.772 -38.278
```

---

### `PassPunkte.txt`

Bekannte Objektpunkte (Passpunkte):  
PunktNr X [m] Y [m] Z [m]

**Beispiel:**
```tèxt
1 4206.258 3385.680 200.139
2 4200.570 3010.597 195.667
3 4525.226 3205.890 220.687
4 4845.226 3389.640 196.268
5 4848.488 3008.560 203.256
```

---

### `UPunkte.txt`

Übertragungspunkte mit vorläufig bekannten Koordinaten:  
PunktNr X [m] Y [m] Z [m]

**Beispiel:**
```tèxt
1000 4365 3380 200
1001 4365 3200 215
1002 4365 3010 200
1003 4525 3385 195
1004 4525 3010 200
1005 4685 3380 200
1006 4685 3205 220
1007 4685 3010 200
```

---

## Ausgabedateien

Nach erfolgreicher Ausgleichung werden folgende Dateien erzeugt:

### `ausgeglichene_Unbekannte.txt`

```tèxt
**Äußere Orientierung der Bilder:**  
Reihenfolge: Bildnummer, X0 [m], Y0 [m], Z0 [m], Kappa [DEG], Phi [DEG], Omega [DEG]

1 4.21E+3 3.20E+3 801 0.26834107 0.011982148 0.25839310
2 4.37E+3 3.20E+3 798 0.35701581 0.016509327 0.21245459
3 4.52E+3 3.20E+3 801 -0.22583987 0.11124492 0.20015401
4 4.69E+3 3.20E+3 801 -0.12203020 0.14687423 0.15572347
5 4.84E+3 3.20E+3 800 -0.12467445 0.14382091 0.012589055

**Übertragungspunkte:**  
Reihenfolge: Punktnummer, X [m], Y [m], Z [m]

1000 4.37E+3 3.38E+3 201
1001 4.36E+3 3.20E+3 216
1002 4.37E+3 3.01E+3 200
1003 4.53E+3 3.38E+3 196
1004 4.53E+3 3.01E+3 202
1005 4.68E+3 3.38E+3 200
1006 4.68E+3 3.20E+3 219
1007 4.68E+3 3.01E+3 198
```

---

### `weitere_Parameter.txt`
```tèxt
Enthält:

- **Ausgeglichene Messwerte x' und y'**  
  Reihenfolge: Bildnummer, Punktnummer, x' [mm], y' [mm]

- **Punktlagestandardabweichung der Beobachtungen [mm]**

- **Punktlagestandardabweichung der Unbekannten**

**Beispiel (Auszug):**

******** Ausgeglichene Messwerte x' und y' ********

1 1 0.316 36.4
1 2 -1.16 -38.3
1 1000 32.1 35.7
1 1001 32.2 -0.531
1 1002 32.3 -38.8
...


Punktlagestandardabweichung der Beobachtungen [mm]: 2.556916024381353E-4

******** Punktlagestandardabweichung der Unbekannten ********

Bilder

Reihenfolge: Bildnummer, s_X0 [m], s_Y0 [m], s_Z0 [m], s_Kappa [DEG], s_Phi [DEG], s_Omega [DEG]

1 0.0207 0.0155 0.00288 0.00024609556 0.0019586184 0.0013556720
2 0.0111 0.00732 0.00247 0.00016340008 0.0010196876 0.00065961904
3 0.0111 0.00943 0.00389 0.00022053301 0.0010565904 0.00086002902
4 0.0111 0.00730 0.00245 0.00016313046 0.0010188647 0.00065550743
5 0.0206 0.0149 0.00280 0.00024425363 0.0019582114 0.0013065412

Übertragungspunkte

Reihenfolge: Punktnummer, s_X [m], s_Y [m], s_Z [m]

1000 0.00112 0.00199 0.00690
1001 0.00102 0.00117 0.00515
1002 0.00112 0.00206 0.00686
1003 0.00106 0.00174 0.00571
1004 0.00106 0.00178 0.00550
1005 0.00112 0.00198 0.00691
1006 0.00102 0.00118 0.00506
1007 0.00112 0.00205 0.00706

Insgesamt wurden 2 Iterationen durchgeführt.
```
