package beans;

public class MuestraForense {

    private int id;
    private int codigoCaso;
    private String tipoMuestra;
    private int fechaRecogida;
    private String estadoCustodia;
    private CentroForense centroForense;
    private InformeForense informe;

    public MuestraForense() {
    }

    public MuestraForense(int id, int codigoCaso, String tipoMuestra, int fechaRecogida, String estadoCustodia, CentroForense centroForense, InformeForense informe) {
        this.id = id;
        this.codigoCaso = codigoCaso;
        this.tipoMuestra = tipoMuestra;
        this.fechaRecogida = fechaRecogida;
        this.estadoCustodia = estadoCustodia;
        this.centroForense = centroForense;
        this.informe = informe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoCaso() {
        return codigoCaso;
    }

    public void setCodigoCaso(int codigoCaso) {
        this.codigoCaso = codigoCaso;
    }

    public String getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(String tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    public int getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(int fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public String getEstadoCustodia() {
        return estadoCustodia;
    }

    public void setEstadoCustodia(String estadoCustodia) {
        this.estadoCustodia = estadoCustodia;
    }

    public CentroForense getCentroForense() {
        return centroForense;
    }

    public void setCentroForense(CentroForense centroForense) {
        this.centroForense = centroForense;
    }

    public InformeForense getInforme() {
        return informe;
    }

    public void setInforme(InformeForense informe) {
        this.informe = informe;
    }

    @Override
    public String toString() {
        return "MuestraForense{" +
                "id=" + id +
                ", codigoCaso=" + codigoCaso +
                ", tipoMuestra='" + tipoMuestra + '\'' +
                ", fechaRecogida=" + fechaRecogida +
                ", estadoCustodia='" + estadoCustodia + '\'' +
                ", centroForense=" + centroForense +
                ", informe=" + informe +
                '}';
    }
}
