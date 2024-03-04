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
3. Öffnen Sie ein Terminal bzw. eine Kommandozeile und navigieren Sie in den Ordner `src`
4. Kompilieren Sie die Java-Dateien mit dem Kommando: 
      
       javac main/java/*.java
       
5. Navigieren Sie in den Überordner vom `main`-Ordner.
6. Starten Sie die Applikation mit dem folgenden Kommando. 

	   java main/java/QuicktipGenerator

   Zusätzlich kann die Option `lotto` bzw. `eurojackpot` ausgewählt werden, um das Spiel zu wählen, für das der erste Quicktipp generiert wird; per default wird ohne Angabe eines Parameters `lotto` gewählt. Also bspw.:
      
       java main/java/QuicktipGenerator eurojackpot
   
   Außerdem kann eine Reihe von (bis zu sechs) neuen Unglückszahlen direkt beim Start angegeben werden, welche auch direkt bei der Generierung des ersten Quicktipps berücksichtigt werden. Wichtig ist, dass sie die Zahlen mit Kommata trennen und keine Leerzeichen zwischen den einzelnen Zahlen und Kommata einfügen. Also bspw.:
   
       java main/java/QuicktipGenerator 13,26,39
	
   Desweiteren können sie beim Start eine beide Parameter angeben. Bspw.:
   
       java main/java/QuicktipGenerator lotto 14,28,42

## Nutzung

- Sobald die Applikation gestartet wurde, wird eine Tippreihe für das jeweilige Spiel direkt generiert und angezeigt.
- Danach wird Ihnen ein Menü präsentiert, mit dem Sie auswählen können, für welches Spiel (Lotto oder Eurojackpot) Sie gerne (noch) einen Quicktipp generieren würden. Desweiteren können die Unglückzahlen angezeigt, gelöscht und aktualisiert werden.
- Wählen Sie eine Option aus, indem Sie das korrespondierende Wort im Terminal eingeben und drücken Sie die `Enter`-Taste.
- Für die Eingabe neuer Unglückszahlen wird ein Untermenü geöffnet, hier können Sie neue Zahlen eingeben und diese speichern oder den Vorgang abbrechen und zum Hauptmenü zurückkehren.
- Wenn Sie fertig sind, lässt sich die Applikation mit `exit` schließen.