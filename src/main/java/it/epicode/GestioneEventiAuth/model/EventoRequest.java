package it.epicode.GestioneEventiAuth.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequest {

    @NotBlank(message = "titolo obbligatorio")
    String titolo;
    @NotBlank(message = "descrizione obbligatoria")
    String descrizione;
    @NotBlank(message = "data obbligatoria")
    LocalDate data;
    @NotBlank(message = "luogo obbligatorio")
    String luogo;
    @NotBlank(message = "nome obbligatorio")
    int postidisponibili;
}
