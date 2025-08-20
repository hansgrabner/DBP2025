public class Mitarbeitende {
    public int id;
    public String vorname;
    public String nachname;
    public String email;
    public String eintrittsdatum;

    public Mitarbeitende(int id, String vorname, String nachname, String email, String eintrittsdatum) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.eintrittsdatum = eintrittsdatum;
    }

    @Override
    public String toString() {
        return "Mitarbeitende: " +
                " ID= " + id +
                ", Vorname= " + vorname +
                ", Nachname= " + nachname +
                ", eMail= " + email +
                ", Eintrittsdatum= " + eintrittsdatum + '\'';
    }

}