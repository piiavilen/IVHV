package org.example.ohtkoodia;


import java.io.*;
import java.util.List;

public class LaskutListanHallinta implements Serializable {
    private static final String LASKUTIEDOSTO = "Laskutiedosto.txt";


    public static void TallennaLaskuTiedostoon (List<Laskut> laskudata){
        try {
            FileOutputStream fos = new FileOutputStream(LASKUTIEDOSTO);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(laskudata);
            oos.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("Virhe tiedostoon kirjoittamisessa. ");
        }
    }

    protected static List<Laskut> LueLaskuTiedostosta(){
        List<Laskut> laskudata = null;
        try {
            FileInputStream fis = new FileInputStream(LASKUTIEDOSTO);
            ObjectInputStream ois = new ObjectInputStream(fis);

            laskudata = (List<Laskut>) ois.readObject();
            ois.close();
        }
        catch (EOFException e){
            System.err.println("Tiedosto on tyhjä");
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("Jotain meni mönkään :( ");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            System.err.println("Luokkaa ei löytynyt");
        }
        return laskudata;
    }
}

