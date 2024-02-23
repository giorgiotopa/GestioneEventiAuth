package it.epicode.GestioneEventiAuth.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    Evento evento;

    private int postiPrenotati;

    public Prenotazione() {
    }
    public Prenotazione(Utente utente, Evento evento, int postiPrenotati) {
        this.utente = utente;
        this.evento = evento;
        this.postiPrenotati = postiPrenotati;
    }
}
