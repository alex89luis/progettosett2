package data;

public class Riviste extends Catalogo {
    private Periodicità periodicità;

    public Riviste(String titolo, int annoPubblicazione, int numeroPagine, Periodicità periodicità) {
        super(titolo, annoPubblicazione, numeroPagine);
        this.periodicità = periodicità;
    }

    public Periodicità getPeriodicità() {
        return periodicità;
    }

    public void setPeriodicità(Periodicità periodicità) {
        this.periodicità = periodicità;
    }

    @Override
    public String toString() {
        return super.toString() + "Riviste{" +
                "periodicità=" + periodicità +
                '}';
    }

}
