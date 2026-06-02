package beans;

public class InformeForense {

    private int id;
    private boolean adnPositivo;
    private int nivelRiesgo;
    private String conclusion;

    public InformeForense() {
    }

    public InformeForense(int id, boolean adnPositivo, int nivelRiesgo, String conclusion) {
        this.id = id;
        this.adnPositivo = adnPositivo;
        this.nivelRiesgo = nivelRiesgo;
        this.conclusion = conclusion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdnPositivo() {
        return adnPositivo;
    }

    public void setAdnPositivo(boolean adnPositivo) {
        this.adnPositivo = adnPositivo;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(int nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        return "InformeForense{" +
                "id=" + id +
                ", adnPositivo=" + adnPositivo +
                ", nivelRiesgo=" + nivelRiesgo +
                ", conclusion='" + conclusion + '\'' +
                '}';
    }
}
