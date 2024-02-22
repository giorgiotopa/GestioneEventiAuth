package it.epicode.GestioneEventiAuth.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrenotazioneRequest {

    @NotBlank(message = "utente obbligatoria")
    Utente utente;
    @NotBlank(message = "evento obbligatoria")
    Evento evento;
}

