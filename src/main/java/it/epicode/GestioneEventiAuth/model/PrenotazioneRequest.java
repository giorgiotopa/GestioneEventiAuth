package it.epicode.GestioneEventiAuth.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrenotazioneRequest {

    @NotNull(message = "Utente obbligatorio")
    Integer utente;
    @NotNull(message = "Evento obbligatorio")
    Integer evento;
}

