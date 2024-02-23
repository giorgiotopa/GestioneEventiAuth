package it.epicode.GestioneEventiAuth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    String titolo;
    String descrizione;
    LocalDate data;
    String luogo;

    @Column(name = "posti_disponibili")
    int postidisponibili;

    @JsonIgnore
    @OneToMany(mappedBy = "evento")
    private List<Prenotazione> prenotazioni;

    public Evento() {
    }

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int postidisponibili) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.postidisponibili = postidisponibili;
    }

    public void addPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
    }
    public void removePrenotazione(Prenotazione prenotazione) {
        prenotazioni.remove(prenotazione);
    }
}
