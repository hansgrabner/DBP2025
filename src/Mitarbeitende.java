import java.util.ArrayList;

public class Mitarbeitende {
    public int id;
    public String vorname;
    public String nachname;
    public String email;
    public String eintrittsdatum;
    public int urlaubstageU;

    public int getUrlaubstageZA() {
        return urlaubstageZA;
    }

    public void setUrlaubstageZA(int urlaubstageZA) {
        this.urlaubstageZA = urlaubstageZA;
    }

    public int getUrlaubstageAV() {
        return urlaubstageAV;
    }

    public void setUrlaubstageAV(int urlaubstageAV) {
        this.urlaubstageAV = urlaubstageAV;
    }

    public ArrayList<Urlaub> getMeineUrlaube() {
        return MeineUrlaube;
    }

    public void setMeineUrlaube(ArrayList<Urlaub> meineUrlaube) {
        MeineUrlaube = meineUrlaube;
    }

    public int getUrlaubstageU() {
        return urlaubstageU;
    }

    public void setUrlaubstageU(int urlaubstageU) {
        this.urlaubstageU = urlaubstageU;
    }

    public String getEintrittsdatum() {
        return eintrittsdatum;
    }

    public void setEintrittsdatum(String eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int urlaubstageZA;
    public int urlaubstageAV;
    public ArrayList<Urlaub> MeineUrlaube;

    public Mitarbeitende(){

    }
    public Mitarbeitende(int id, String vorname, String nachname, String email, String eintrittsdatum) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.eintrittsdatum = eintrittsdatum;
    }

    @Override
    public String toString() {
        return "Mitarbeitende{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", email='" + email + '\'' +
                ", eintrittsdatum='" + eintrittsdatum + '\'' +
                ", urlaubstageU=" + urlaubstageU +
                ", urlaubstageZA=" + urlaubstageZA +
                ", urlaubstageAV=" + urlaubstageAV +
                ", MeineUrlaube=" + MeineUrlaube +
                '}';
    }
}