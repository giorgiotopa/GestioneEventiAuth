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

    public Prenotazione() {
    }

    public Prenotazione(Utente utente, Evento evento) {
        this.utente = utente;
        this.evento = evento;
    }
}
