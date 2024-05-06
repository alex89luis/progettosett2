package servizi;
import java.util.Optional;
import data.Catalogo;

public interface Archivio {
    void add(Catalogo c);
    void deleteISBN(Integer ISBN);
    Optional<Catalogo> getISBN (Integer ISBN);
    Optional<Catalogo> getAnno(Integer anno);
    void getAutore(String autore);

}
