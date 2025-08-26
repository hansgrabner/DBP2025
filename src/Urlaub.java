public class Urlaub {
    public int urlaubId;

    public Mitarbeitende getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeitende mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public int getUrlaubId() {
        return urlaubId;
    }

    public void setUrlaubId(int urlaubId) {
        this.urlaubId = urlaubId;
    }

    public Urlaubsart getUrlaubsart() {
        return urlaubsart;
    }

    public void setUrlaubsart(Urlaubsart urlaubsart) {
        this.urlaubsart = urlaubsart;
    }

    public String getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(String startdatum) {
        this.startdatum = startdatum;
    }

    public String getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(String enddatum) {
        this.enddatum = enddatum;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    //O/R-Mapping Object -- Relation -- Object
    public Mitarbeitende mitarbeiter;
    public Urlaubsart urlaubsart;
    public String startdatum;
    public String enddatum;
    public String kommentar;


}
