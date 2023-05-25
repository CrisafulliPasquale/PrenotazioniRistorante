package it.itispaleocapa.crisafullip;

import java.util.Date;

public class Cliente {
    String nome;
    String cognome;
    public Cliente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }
    public String getId(){
        return this.nome + this.cognome + new Date().toString();
    }
}
