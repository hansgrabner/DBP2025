public class Urlaubsart {

    public int UrlaubsartId;
    public String Code;

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public Urlaubsart(int urlaubsartId, String code, String bezeichnung) {
        UrlaubsartId = urlaubsartId;
        Code = code;
        Bezeichnung = bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getUrlaubsartId() {
        return UrlaubsartId;
    }

    public void setUrlaubsartId(int urlaubsartId) {
        UrlaubsartId = urlaubsartId;
    }

    public String Bezeichnung;
}
