package flowerShop;


import java.time.LocalDate;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;

abstract class Kwiatek {
    protected int id;
    protected String typ;
    protected String rodzaj;
    protected String kolor;
    protected int iloscZ;
    protected int iloscS;
    protected double cenaZ;
    protected double cenaS;
    protected LocalDate dataZ;
    protected LocalDate dataS;
    protected int dostepnych;
    protected String obrazek;

    public Kwiatek(int id, String typ, String rod, String kol, int iloscZ, double cenaZ, LocalDate dataZ, int iloscS, double cenaS, LocalDate dataS, int dostepnych, String obrazek) {
    	 this.id = id;
         this.typ = typ;
         this.rodzaj = rod;
         this.kolor = kol;
         this.iloscZ = iloscZ;
         this.cenaZ = cenaZ;
         this.dataZ = dataZ;
         this.iloscS = iloscS;
         this.cenaS = cenaS;
         this.dataS = dataS;
         this.dostepnych = dostepnych;
         this.obrazek = obrazek;
    }

    public abstract void fUsun(int id); 
    public abstract void fUsunv2(int id); 
    public abstract List<String[]> fCzytaj();
    public abstract void fDodaj();
    public abstract void fDodajPoId(int idToAddAfter, Kwiatek kawiatek);
    public abstract String fObrazekById(int id);
}
