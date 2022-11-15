package parsingConfServer;

import java.util.Objects;

public class ConfElements {
    private String parametro;
    private String tipoValor;
    private String valorAssParametro;

    public ConfElements(String parametro, String tipoValor, String valorAssParametro) {
        this.parametro = parametro;
        this.tipoValor = tipoValor;
        this.valorAssParametro = valorAssParametro;
    }


    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public String getValorAssParametro() {
        return valorAssParametro;
    }

    public void setValorAssParametro(String valorAssParametro) {
        this.valorAssParametro = valorAssParametro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfElements that = (ConfElements) o;
        return Objects.equals(getParametro(), that.getParametro()) && Objects.equals(getTipoValor(), that.getTipoValor()) && Objects.equals(getValorAssParametro(), that.getValorAssParametro());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParametro(), getTipoValor(), getValorAssParametro());
    }

    @Override
    public String toString() {
        return "ConfElements{" +
                "parametro='" + parametro + '\'' +
                ", tipoValor='" + tipoValor + '\'' +
                ", valorAssParametro='" + valorAssParametro + '\'' +
                '}';
    }
}
