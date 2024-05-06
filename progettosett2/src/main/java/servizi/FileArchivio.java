package servizi;

import data.Catalogo;
import data.Libri;
import data.Periodicità;
import data.Riviste;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileArchivio implements Archivio {

    private static final Logger logger = LoggerFactory.getLogger(FileArchivio.class);

    private final ArrayList<Catalogo> listaCatalogo = new ArrayList<>();
    private ArrayList<Catalogo> loadListaCatalogo = new ArrayList<>();
    // File CSV di destinazione
    private File f = new File("./catalogo.csv");
    // Metodo per salvare i cataloghi nel file CSV
    public void save() {
        try {
            // Elimina il file CSV esistente
            FileUtils.delete(f);
        } catch (IOException err) {
            IOException e = err;
            logger.error("Eccezione durante l'eliminazione", e);
        }
        listaCatalogo.stream().forEach(c -> {
            try {
                // Verifica il tipo di catalogo (Libro o Rivista) e scrivi le informazioni corrispondenti
                if (c instanceof Libri) {
                    var lines = Arrays.asList(c.getISBN().toString() + "," + c.getTitolo() + "," + c.getAnnoPubblicazione() + "," + c.getNumeroPagine() + "," + ((Libri) c).getAutore() + "," + ((Libri) c).getGenere());
                    FileUtils.writeLines(f, StandardCharsets.ISO_8859_1.name(), lines, true);

                } else {
                    var lines = Arrays.asList(c.getISBN().toString() + "," + c.getTitolo()+ "," + c.getAnnoPubblicazione()+ ","+ c.getNumeroPagine()+ ","+((Riviste) c).getPeriodicità());
                    FileUtils.writeLines(f, StandardCharsets.ISO_8859_1.name(), lines, true);
                }

            } catch (IOException e) {
                logger.error("Eccezione:", e);
            }
        });
    }
    // Metodo per caricare i cataloghi dal file CSV
    public void load(){
        // Leggi le linee dal file CSV e stampa la lista temporanea dei cataloghi caricati
        List<String> l = leggiFile(f);
        System.out.println(loadListaCatalogo);
    }
    // Aggiungo un nuovo catalogo alla lista e salva l'archivio aggiornato
    @Override
    public void add(Catalogo catalogo) {
        listaCatalogo.add(catalogo);
        this.save();
    }
    // Rimuovo un catalogo in base all'ISBN e salva l'archivio aggiornato
    @Override
    public void deleteISBN(Integer ISBN) {
        listaCatalogo.removeIf(e -> e.getISBN().equals(ISBN));
        this.save();
    }

    @Override
    public Optional<Catalogo> getISBN(Integer ISBN) {
        // Trova un catalogo per ISBN e restituisci un Optional
        var elemento = listaCatalogo.stream()
                .filter(catalogo -> catalogo.getISBN().equals(ISBN))
                .findFirst();
        return elemento;

    }

    @Override
    public Optional<Catalogo> getAnno(Integer anno) {
        return Optional.empty();
    }

    @Override
    public void getAutore(String autore) {
        // Trova un catalogo per ISBN e restituisci un Optional
        listaCatalogo.stream().filter(el -> el instanceof Libri && ((Libri) el).getAutore().equals(autore)).forEach(System.out::println);
    }
    // Metodo per ottenere una lista di cataloghi per autore
    public List<Catalogo> getByAutore(String autore) {
        var autoreL = this.listaCatalogo.stream().filter((el) -> el instanceof Libri && ((Libri) el).getAutore().equals(autore))
                .toList();
        return autoreL;
    }
    // Metodo per ottenere una lista di cataloghi per anno di pubblicazione
    public List<Catalogo> getByAnno(Integer anno) {
        var annoP = this.listaCatalogo.stream().filter(el -> el.getAnnoPubblicazione().equals(anno))
                .toList();
        return annoP;
    }
    // Metodo per restituire l'intera lista di cataloghi
    public ArrayList<Catalogo> getLista() {
        return listaCatalogo;
    }
    // Metodo per leggere le informazioni dal file CSV e aggiungere i cataloghi alla lista
        public List<String> leggiFile (File file){
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] el = line.split(",");
                    String titolo = el[1];
                    String annoPubblicazioneStr = el[2];
                    String numeroPagineStr = el[3];
                    int annoPubblicazione;
                    int numeroPagine;
                    try {
                        annoPubblicazione = Integer.parseInt(annoPubblicazioneStr);
                        numeroPagine = Integer.parseInt(numeroPagineStr);
                    } catch (NumberFormatException e) {
                        System.err.println("Errore di conversione: " + e.getMessage());
                        continue;
                    }
                    if (el.length == 5) {
                        String periodicitàStr = el[4];
                        Periodicità periodicità = Periodicità.valueOf(periodicitàStr);
                        var rivista = new Riviste(titolo, annoPubblicazione, numeroPagine, periodicità);
                        listaCatalogo.add(rivista);
                    } else if (el.length == 6) {
                        String autore = el[4];
                        String genere = el[5];
                        var libro = new Libri(titolo, annoPubblicazione, numeroPagine, autore, genere);
                        listaCatalogo.add(libro);
                    }
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return lines;
        }
    }

