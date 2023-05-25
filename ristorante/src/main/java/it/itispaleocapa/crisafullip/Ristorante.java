package it.itispaleocapa.crisafullip;

import java.util.*;
public class Ristorante {
    HashMap<Cliente, LinkedList<Prenotazione>> prenotazioni;
    public Ristorante(){
        this.prenotazioni = new HashMap<>();
    }
    public void aggiungiCliente(String nome, String cognome){
        Cliente c = new Cliente(nome, cognome);
        this.prenotazioni.put(c, new LinkedList<>());
    }
    public void aggiungiCliente(Cliente c){
        this.prenotazioni.put(c, new LinkedList<>());
    }
    public void modificaCliente(String id, String newNome, String newCognome){
        Optional<Cliente> cl = this.prenotazioni.keySet().stream().filter(c -> c.getId().equals(id)).findFirst();

        if (cl.isPresent()) {
            cl.get().nome = newNome;
            cl.get().cognome = newCognome;
        } else {
            System.out.println("cliente non trovato");
        }
    }
    public void eliminaCliente(String id) throws ClienteNonEsistenteException{
        Optional<Cliente> cl = this.prenotazioni.keySet().stream().filter(c -> c.getId().equals(id)).findFirst();

        if (cl.isPresent()) {
            this.prenotazioni.remove(cl.get());
        } else {
            throw new ClienteNonEsistenteException();
        }
    }
    public void aggiungiPrenotazione(Date data, int coperti, Cliente cliente){
        Prenotazione p = new Prenotazione(data, coperti, cliente);
        if(prenotazioni.get(cliente) == null) aggiungiCliente(cliente);
        prenotazioni.get(cliente).add(p);
    }
    public void aggiungiPrenotazione(Prenotazione p, Cliente cliente){
        prenotazioni.get(cliente).add(p);
    }
    public void modificaPrenotazione(String id, int newCoperti) throws PrenotazioneNonEsistenteException{
        //Predicate<Prenotazione> filterById = p -> p.getId().equals(id);

        Optional<Prenotazione> pr = this.prenotazioni.values().stream()
                .flatMap(Collection::stream)
                .filter(p -> p.getId().equals(id))
                .findFirst();
        // () stream
        // [] collection

        // [linkedlist<1,2,3>, linkedlist<4,5,6>, linkedlist<7,8,9>] .values
        // (linkedlist<1,2,3>, linkedlist<4,5,6>, linkedlist<7,8,9>) .stream
        // (1,2,3,4,5,6,7,8,9) .flatmap
        // (7) .filter
        // 7 .findfirst

        if (pr.isPresent()) {
            pr.get().coperti = newCoperti;
            //pr.get().dataPrenotazione = newDataPrenotazione;
        } else {
            throw new PrenotazioneNonEsistenteException();
        }
    }
    public void eliminaPrenotazione(Cliente c, String id) throws PrenotazioneNonEsistenteException{
        Optional<Prenotazione> pr = this.prenotazioni.get(c).stream().filter(p -> p.getId().equals(id)).findFirst();

        if (pr.isPresent()) {
            this.prenotazioni.get(c).remove(pr.get());
        } else {
            throw new PrenotazioneNonEsistenteException();
        }
    }
    public Prenotazione ricercaPrenotazione(Cliente c, String id) throws PrenotazioneNonEsistenteException{
        Optional<Prenotazione> pr = this.prenotazioni.get(c).stream().filter(p -> p.getId().equals(id)).findFirst();

        if (pr.isPresent()) {
            return pr.get();
        } else {
            throw new PrenotazioneNonEsistenteException();
        }
        //return null;
    }
    public Prenotazione ricercaPrenotazione(Date date) throws PrenotazioneNonEsistenteException{
        Optional<Prenotazione> pr = this.prenotazioni.values().stream()
                .flatMap(Collection::stream)
                .filter(p -> p.dataPrenotazione.equals(date))
                .findFirst();

        // [linkedlist<1,2,3>, linkedlist<4,5,6>, linkedlist<7,8,9>] .values
        // (linkedlist<1,2,3>, linkedlist<4,5,6>, linkedlist<7,8,9>) .stream
        // (1,2,3,4,5,6,7,8,9) .flatmap
        // (7) .filter
        // 7 .findfirst

        if (pr.isPresent()) {
            return pr.get();
        } else {
            throw new PrenotazioneNonEsistenteException();
        }
        //return null
        
    }
    public int numeroCoperti(Date date){
        int n = 0;
        n = this.prenotazioni.values().stream()
                .flatMap(Collection::stream)
                .filter(p -> p.dataPrenotazione.equals(date))
                .map(p -> p.coperti)
                .reduce(0, Integer::sum); // ---> UGUALE A : ".reduce(0, (acc, next) -> acc + next);" 
                
        return n;

        // [linkedlist<p,p,p>, linkedlist<p,p,p>, linkedlist<p,p,p>] .values
        // (linkedlist<p,p,p>, linkedlist<p,p,p>, linkedlist<p,p,p>) .stream
        // (p,p,p,p,p,p,p,p,p) .flatmap
        // (p,p,p,p,p) .filter
        // (10,15,5,2,6) .map
        // 28 .reduce
    }
    public int numeroCoperti(Date date1, Date date2) throws DataNonValidaException{
        if(date1.after(date2)){
            /*Date temp = date1;
            date1 = date2;
            date2 = temp;*/
            throw new DataNonValidaException();
        }
        int n = 0;
        n = this.prenotazioni.values().stream()
                .flatMap(Collection::stream)
                .filter(p -> p.dataPrenotazione.after(date1) && p.dataPrenotazione.before(date2))
                .map(p -> p.coperti)
                .reduce(0, Integer::sum);
                //.reduce(0, (acc, next) -> acc + next); uguale
        return n;
    }
    public Date giornoConMassimoCoperti(){
        Prenotazione p = this.prenotazioni.values().stream()
                .flatMap(Collection::stream)
                .reduce(this.prenotazioni.values().stream().flatMap(Collection::stream).findFirst().get(), (acc, next) -> {
                    if(next.coperti > acc.coperti) return next;
                    return acc;
                });
        return p.dataPrenotazione;

        // [linkedlist<p1,p2,p3>, linkedlist<p4,p5,p6>, linkedlist<p7,p8,p9>] .values
        // (linkedlist<p1,p2,p3>, linkedlist<p4,p5,p6>, linkedlist<p7,p8,p9>) .stream
        // (p1=40,p2=10,p3=70,p4=50,p5=6,p6=25,p7=12,p8=10,p9=90) .flatmap
        // p3 .reduce(p1, (acc, next))
    }
    public Cliente clienteConMassimoPrenotazioni(){
        return this.prenotazioni.keySet().stream()
                .reduce(this.prenotazioni.keySet().stream().findFirst().get(), (acc, next) -> {
                    if(this.prenotazioni.get(acc).size() < this.prenotazioni.get(next).size()) return next;
                    return acc;
                });
    }
}

class PrenotazioneNonEsistenteException extends Exception{}
class ClienteNonEsistenteException extends Exception{}
class DataNonValidaException extends Exception{}