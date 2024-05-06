import data.Libri;
import org.slf4j.LoggerFactory;


import org.slf4j.Logger;
import servizi.FileArchivio;

public class Main {

    public static void main(String[] args){

        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Ciao");
    Libri libro1 = new Libri("luca",2023,20,"dipe","maschio");
    Libri libro2 = new Libri("mario",2022,30,"varano","maschio");
    Libri libro3 = new Libri("fede",2021,50,"mass","maschio");

    FileArchivio archivio = new FileArchivio();

    archivio.add(libro1);
    archivio.add(libro2);
    archivio.add(libro3);



        System.out.println("Archivio completo:");
        archivio.getLista().forEach(System.out::println);

        // Test della ricerca per ISBN
        System.out.println("\nRicerca per ISBN:");
        archivio.getISBN(1).ifPresentOrElse(System.out::println, () -> System.out.println("Elemento non trovato"));

        // Test della ricerca per autore
        System.out.println("\nRicerca per autore:");
        archivio.getAutore("varano");

        // Test della ricerca per anno di pubblicazione
        System.out.println("\nRicerca per anno di pubblicazione:");
        archivio.getByAnno(2022).forEach(System.out::println);

        // Test rimozione elemento
        archivio.deleteISBN(1);

        // Stampa archivio completo dopo eliminazione
        System.out.println("Archivio completo:");
        archivio.getLista().forEach(System.out::println);
    }
}
