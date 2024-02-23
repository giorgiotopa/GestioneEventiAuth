package it.epicode.GestioneEventiAuth.model;

import it.epicode.GestioneEventiAuth.enums.TipoUtente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UtenteRequest {
    @NotBlank(message = "nome obbligatorio")
    private String nome;
    @NotBlank(message = "cognome obbligatorio")
    private String cognome;
    @NotBlank(message = "username obbligatorio")
    private String username;
    @NotBlank(message = "password obbligatoria")
    private String password;

}
