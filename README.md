# Keller's Quicktips

Dies ist ein Quicktipp-Generator für Lotto und Eurojackpot.

## Inhalt

- [Beschreibung](#beschreibung)
- [Funktionen](#funktionen)
- [Installation](#installation)
- [Nutzung](#nutzung)

## Beschreibung

Keller's Quicktips ist eine Java-basierte Kommandozeilen-Applikation, die es erlaubt, Quicktipps für Lotto- und Eurojackpotspiele zu generieren.
Es ist möglich Unglückszahlen zu speichern, löschen und aktualisieren, sodass diese Zahlen nicht in den generierten Quicktipps enthalten sind.

## Funktionen

- Generierung von Quicktipps für Lotto und Eurojackpot
- Berücksichtigung von Unglückszahlen
- Einfache Navigation per Kommandozeile

## Installation

1. Stellen Sie sicher, dass ein Java Development Kit (JDK) auf ihrer Maschine installiert ist.
2. Klonen Sie dieses Repository oder laden Sie die Dateien als ZIP-Datei herunter und entpacken diese.
3. Öffnen Sie ein Terminal bzw. eine Kommandozeile und navigieren Sie in den Ordner `main`
4. Kompilieren Sie die Java-Dateien mit dem Kommando: 
      
       javac *.java
       
5. Navigieren Sie in den Überordner vom `main`-Ordner.
6. Starten Sie die Applikation mit dem folgenden Kommando. Die Angabe der Option `lotto` bzw. `eurojackpot` ist optional, per default wird ohne Angabe eines Parameters `lotto` gewählt.
      
       java main/QuicktipGenerator [lotto|eurojackpot]

## Nutzung

- Sobald die Applikation gestartet wurde, wird eine Tippreihe für das jeweilige Spiel direkt generiert und angezeigt.
- Danach wird Ihnen ein Menü präsentiert, in dem Sie auswählen können, für welches Spiel (Lotto oder Eurojackpot) Sie gerne (noch) einen Quicktipp generieren würden.
- Wählen Sie eine Option aus, indem Sie das korrespondierende Wort im Terminal eingeben und drücken Sie `Enter`.
- ...
- Wenn Sie ausreichend Quicktipps generiert haben, lässt sich die Applikation mit `exit` schließen.