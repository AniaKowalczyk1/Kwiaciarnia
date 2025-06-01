package flowerShop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RaportZapis {

    // Metoda do zapisywania raportu do pliku
    public static void zapiszRaport(String akcja) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("raport.txt", true))) {
            // Pobranie aktualnej daty i godziny
            String dataGodzina = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Zapisanie raportu do pliku
            writer.write("Data: " + dataGodzina + " - " + akcja);
            writer.newLine(); // Nowa linia dla kolejnej operacji
        } catch (IOException e) {
            System.out.println("Błąd przy zapisywaniu raportu!");
            e.printStackTrace();
        }
    }
}
