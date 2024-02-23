package it.epicode.GestioneEventiAuth.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequest {

    @NotBlank(message = "titolo obbligatorio")
    String titolo;
    @NotBlank(message = "descrizione obbligatoria")
    String descrizione;
    @NotNull(message = "Il campo 'data' non può essere nullo")
    LocalDate data;
    @NotBlank(message = "luogo obbligatorio")
    String luogo;
    @NotNull(message = "Il campo 'posti disponibili' non può essere nullo")
    Integer postiDisponibili;
}
