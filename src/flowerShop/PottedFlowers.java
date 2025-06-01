package flowerShop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class PottedFlowers  extends Kwiatek{

	    private String rozmiar; 

	    public PottedFlowers(int id, String typ, String rod, String rozm, String kol, int iloscZ, double cenaZ, LocalDate dataZ,
	            int iloscS, double cenaS, LocalDate dataS, int dostepnych, String obrazek) {
	        super(id, typ, rod, kol, iloscZ, cenaZ, dataZ, iloscS, cenaS, dataS, dostepnych, obrazek);
	        this.rozmiar = rozm;
	    }

	    @Override
	    public List<String[]> fCzytaj() {
	        List<String[]> flowers = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader("kwiaty_doniczkowe_baza.txt"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] flowerData = new String[11]; // Nowa struktura: 11 pól
	                flowerData[0] = line; // ID
	                flowerData[1] = br.readLine(); // Typ
	                flowerData[2] = br.readLine(); // Rodzaj
	                flowerData[3] = br.readLine(); // Rozmiar
	                flowerData[4] = br.readLine(); // Kolor
	                flowerData[5] = br.readLine(); // Ilość zakupionych
	                flowerData[6] = br.readLine(); // Cena zakupu
	                flowerData[7] = br.readLine(); // Data zakupu
	                flowerData[8] = br.readLine(); // Cena sprzedaży
	                flowerData[9] = br.readLine(); // Dostępność
	                flowerData[10] = br.readLine(); // Obrazek ścieżka
	                flowers.add(flowerData);
	            }
	        } catch (IOException e) {
	            System.out.println("Nie mogę otworzyć pliku!");
	        }
	        return flowers;
	    }

	    @Override
	    public void fDodaj() {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter("kwiaty_doniczkowe_baza.txt", true))) {
	            bw.write(id + "\n" +
	                    typ + "\n" +
	                    rodzaj + "\n" +
	                    rozmiar + "\n" +
	                    kolor + "\n" +
	                    iloscZ + "\n" +
	                    cenaZ + "\n" +
	                    dataZ + "\n" +
	                    cenaS + "\n" +
	                    dostepnych + "\n" +
	                    obrazek + "\n");
	            System.out.println("Dodano kwiat do pliku.");
	            RaportZapis.zapiszRaport("Dodano nowy kwiat do pliku.");
	        } catch (IOException e) {
	            System.out.println("Nie mogę otworzyć pliku!");
	        }
	    }

	    @Override
	    public void fDodajPoId(int idToAddAfter, Kwiatek kwiatek) {
	        List<String[]> flowersData = fCzytaj();
	        int insertIndex = 0;
	        boolean idFound = false;

	        // Znajdź miejsce wstawienia
	        for (int i = 0; i < flowersData.size(); i++) {
	            if (Integer.parseInt(flowersData.get(i)[0]) == idToAddAfter) {
	                idFound = true;
	                insertIndex = i + 1;
	                break;
	            }
	        }

	        if (!idFound) {
	            insertIndex = flowersData.size();
	        }

	        String[] newFlowerData = {
	            String.valueOf(id),
	            typ,
	            rodzaj,
	            rozmiar,
	            kolor,
	            String.valueOf(iloscZ),
	            String.valueOf(cenaZ),
	            String.valueOf(dataZ),
	            String.valueOf(cenaS),
	            String.valueOf(dostepnych),
	            obrazek
	        };

	        flowersData.add(insertIndex, newFlowerData);

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("kwiaty_doniczkowe_baza.txt"))) {
	            for (String[] flower : flowersData) {
	                for (String data : flower) {
	                    writer.write(data);
	                    writer.newLine();
	                }
	            }
	            System.out.println("Dodano kwiat do pliku.");
	            RaportZapis.zapiszRaport("Dodano nowy kwiat do pliku.");
	        } catch (IOException e) {
	            System.out.println("Nie mogę otworzyć pliku!");
	        }
	    }

	    @Override
	    public void fUsun(int id) {
	        List<String[]> flowersData = fCzytaj();

	        flowersData.removeIf(flower -> Integer.parseInt(flower[0]) == id);

	        for (int i = 0; i < flowersData.size(); i++) {
	            flowersData.get(i)[0] = String.valueOf(i + 1); // Aktualizacja ID
	        }

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("kwiaty_doniczkowe_baza.txt"))) {
	            for (String[] flower : flowersData) {
	                for (String data : flower) {
	                    writer.write(data);
	                    writer.newLine();
	                }
	            }
	            System.out.println("Kwiat został usunięty.");
	            RaportZapis.zapiszRaport("Kwiat został usunięty z pliku.");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    
	    @Override
	    public void fUsunv2(int id) {
	        List<String[]> flowersData = fCzytaj();

	        flowersData.removeIf(flower -> Integer.parseInt(flower[0]) == id);

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("kwiaty_doniczkowe_baza.txt"))) {
	            for (String[] flower : flowersData) {
	                for (String data : flower) {
	                    writer.write(data);
	                    writer.newLine();
	                }
	            }
	            System.out.println("Kwiat został usunięty.");
	            RaportZapis.zapiszRaport("Kwiat został usunięty z pliku.");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    public String fObrazekById(int id) {
	        String obrazek = null;

	        // Otwórz plik z danymi
	        try (BufferedReader br = new BufferedReader(new FileReader("kwiaty_doniczkowe_baza.txt"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                // Jeśli ID wczytane z pliku zgadza się z przekazanym, pobierz ścieżkę do obrazka
	                int currentId = Integer.parseInt(line.trim());
	                if (currentId == id) {
	                    // Pomiń kolejne 9 linii, aby dotrzeć do ścieżki obrazka
	                    for (int i = 0; i < 9; i++) {
	                        br.readLine();
	                    }
	                    obrazek = br.readLine(); // Ścieżka do obrazka
	                    break;
	                } else {
	                    // Jeśli nie znaleziono, przejdź do następnego ID
	                    for (int i = 0; i < 10; i++) {
	                        br.readLine(); // Pomiń dane związane z aktualnym kwiatem
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas odczytu pliku!", "Błąd", JOptionPane.ERROR_MESSAGE);
	        }

	        return obrazek;
	    }
	}
