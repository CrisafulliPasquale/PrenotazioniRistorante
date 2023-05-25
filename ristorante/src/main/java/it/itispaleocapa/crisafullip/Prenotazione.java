package it.itispaleocapa.crisafullip;

import java.util.Date;
public class Prenotazione {
    Date inserimentoPrenotazione;
    Date dataPrenotazione;
    Cliente cliente;
    int coperti;
    String id;
    public Prenotazione(Date date, int coperti, Cliente cliente) {
        this.inserimentoPrenotazione = new Date();
        this.dataPrenotazione = date;
        this.cliente = cliente;
        this.coperti = coperti;
    }
    public String getId(){
        return this.inserimentoPrenotazione.toString();
    }
}
