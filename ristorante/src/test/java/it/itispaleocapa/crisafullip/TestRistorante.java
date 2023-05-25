package it.itispaleocapa.crisafullip;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRistorante {
    @Test
    void aggiungiCliente() {
        Ristorante r = new Ristorante();
        r.aggiungiCliente("pasquale", "crisafulli");
        assertEquals(r.prenotazioni.values().size(), 1);
    }

    @Test
    void modificaCliente(){
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        r.aggiungiCliente(c);
        r.modificaCliente(c.getId(), "Pasquale", "Crisafulli");

        Optional<Cliente> cl = r.prenotazioni.keySet().stream().filter(cli -> cli.getId().equals(c.getId())).findFirst();

        if (cl.isPresent()) {
            assertEquals(cl.get().nome, "Pasquale");
            assertEquals(cl.get().cognome, "Crisafulli");
        }
    }

    @Test
    void eliminaCliente() throws ClienteNonEsistenteException{
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        r.aggiungiCliente(c);
        r.eliminaCliente(c.getId());
        assertEquals(r.prenotazioni.values().size(), 0);
    }

    @Test
    void aggiungiPrenotazione(){
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(new Date(), 10, c);
        assertEquals(r.prenotazioni.get(c).size(), 1);
    }

    @Test
    void modificaPrenotazione() throws PrenotazioneNonEsistenteException{
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Prenotazione p = new Prenotazione(new Date(), 10, c);
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(p, c);
        r.modificaPrenotazione(p.getId(), 20);
        assertEquals(r.prenotazioni.get(c).get(0).coperti, 20);
    }

    @Test
    void eliminaPrenotazione() throws PrenotazioneNonEsistenteException{
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Prenotazione p = new Prenotazione(new Date(), 10, c);
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(p, c);
        r.eliminaPrenotazione(c, p.getId());
        assertFalse(r.prenotazioni.get(c).contains(p));
    }

    @Test
    void ricercaPrenotazione() throws PrenotazioneNonEsistenteException{
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Prenotazione p = new Prenotazione(new Date(), 10, c);
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(p, c);
        assertEquals(r.ricercaPrenotazione(c, p.getId()), p);
    }

    @Test
    void ricercaPrenotazioneData() throws PrenotazioneNonEsistenteException{
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Date d = new Date(2020, 5, 10);
        Prenotazione p = new Prenotazione(d, 10, c);
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(p, c);
        assertEquals(r.ricercaPrenotazione(d) ,p);
    }

    @Test
    void numeroCoperti(){
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Date d = new Date(2020, 5, 10);
        Prenotazione p = new Prenotazione(d, 10, c);
        Prenotazione p2 = new Prenotazione(d, 50, c);
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(p, c);
        r.aggiungiPrenotazione(p2, c);
        assertEquals(r.numeroCoperti(d), 60);
    }

    @Test
    void numeroCopertiTraDate() throws DataNonValidaException{
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Date d = new Date(2020, 10, 5);
        Date d2 = new Date(2020, 10, 25);
        Prenotazione p = new Prenotazione(new Date(2020, 10, 15), 20, c);
        Prenotazione p2 = new Prenotazione(new Date(2020, 10, 20), 20, c);
        Prenotazione p3 = new Prenotazione(new Date(2020, 10, 4), 20, c);
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(p, c);
        r.aggiungiPrenotazione(p2, c);
        r.aggiungiPrenotazione(p3, c);
        assertEquals(r.numeroCoperti(d, d2), 40);
    }

    @Test
    public void giornoConMassimoCoperti(){
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Date d = new Date(2020, 10, 5);
        Date d2 = new Date(2020, 10, 25);
        Prenotazione p = new Prenotazione(d, 3, c);
        Prenotazione p2 = new Prenotazione(d2, 25, c);
        r.aggiungiCliente(c);
        r.aggiungiPrenotazione(p, c);
        r.aggiungiPrenotazione(p2, c);
        assertEquals(r.giornoConMassimoCoperti(), d2);
    }

    @Test
    public void clienteConMassimoPrenotazioni(){
        Ristorante r = new Ristorante();
        Cliente c = new Cliente("pasquale", "crisafulli");
        Cliente c2 = new Cliente("michele", "mastroianni");
        Date d = new Date(2020, 10, 5);
        Date d2 = new Date(2020, 10, 25);
        Prenotazione p = new Prenotazione(d, 20, c);
        Prenotazione p2 = new Prenotazione(d2, 25, c2);
        Prenotazione p3 = new Prenotazione(d2, 85, c2);
        r.aggiungiCliente(c);
        r.aggiungiCliente(c2);
        r.aggiungiPrenotazione(p, c);
        r.aggiungiPrenotazione(p2, c2);
        r.aggiungiPrenotazione(p3, c2);
        assertEquals(r.clienteConMassimoPrenotazioni(), c2);
    }
}