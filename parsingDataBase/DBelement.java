package parsingDataBase;

import java.util.*;

public class DBelement {
    private String parametro;
    private String tipoValor;
    private String valor;
    private int ttl;
    private int prioridade;

    public DBelement(String parametro, String tipoValor, String valor, int ttl,int prioridade){
        this.parametro = parametro;
        this.tipoValor = tipoValor;
        this.valor = valor;
        this.ttl = ttl;
        this.prioridade = prioridade;
    }

    public DBelement(DBelement dataBase) {
        this.parametro = dataBase.getParametro();
        this.tipoValor = dataBase.getTipoValor();
        this.valor = dataBase.getValor();
        this.ttl = dataBase.getTtl();
        this.prioridade = dataBase.getPrioridade();
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

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBelement dataBase = (DBelement) o;
        return Objects.equals(getParametro(), dataBase.getParametro()) && Objects.equals(getTipoValor(), dataBase.getTipoValor()) && Objects.equals(getValor(), dataBase.getValor()) && Objects.equals(getTtl(), dataBase.getTtl());
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
        return "DataBase{" +
                "parametro='" + parametro + '\'' +
                ", tipoValor='" + tipoValor + '\'' +
                ", valor='" + valor + '\'' +
                ", ttl='" + ttl + '\'' +
                '}';
    }
}
