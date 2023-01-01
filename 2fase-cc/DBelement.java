package parsingDataBase;

import java.util.*;

public class DBelement {
    private String parametro;
    private String tipoValor;
    private String valor;
    private String ttl;

    public DBelement(String parametro, String tipoValor, String valor, String ttl){
        this.parametro = parametro;
        this.tipoValor = tipoValor;
        this.valor = valor;
        this.ttl = ttl;
    }


    public DBelement(DBelement dataBase) {
        this.parametro = dataBase.getParametro();
        this.tipoValor = dataBase.getTipoValor();
        this.valor = dataBase.getValor();
        this.ttl = dataBase.getTtl();
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBelement dBelement = (DBelement) o;
        return Objects.equals(getParametro(), dBelement.getParametro()) && Objects.equals(getTipoValor(), dBelement.getTipoValor()) && Objects.equals(getValor(), dBelement.getValor()) && Objects.equals(getTtl(), dBelement.getTtl()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParametro(), getTipoValor(), getValor(), getTtl());
    }

    public DBelement clone() {
        return new DBelement(this);
    }

    @Override
    public String toString() {
        return this.parametro + " " + this.tipoValor + " " + this.valor + " " + this.ttl;
    }
}
