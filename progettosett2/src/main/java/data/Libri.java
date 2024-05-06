package data;

public class Libri extends Catalogo {

    private String autore;
    private String genere;

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Libri( String titolo, Integer annoPubblicazione, Integer numeroPagine, String autore, String genere) {
        super(titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }
        @Override
        public String toString() {
            return super.toString() + "Libri{" +
                    "autore='" + autore + '\'' +
                    ", genere='" + genere + '\'' +
                    '}';


    }
}
