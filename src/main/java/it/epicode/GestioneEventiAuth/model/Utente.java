package it.epicode.GestioneEventiAuth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.GestioneEventiAuth.enums.TipoUtente;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String cognome;

    @Column(unique = true)
    private String username;
    private String password;

    @Column(name = "tipo_utente")
    @Enumerated(EnumType.STRING)
    private TipoUtente tipoUtente;

    @JsonIgnore
    @OneToMany(mappedBy = "utente")
    private List<Prenotazione> prenotazioni;

    public Utente() {
    }

    public Utente(String nome, String cognome, String username, String password, TipoUtente tipoUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.tipoUtente = tipoUtente;
    }

    public void addPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
    }
    public void removePrenotazione(Prenotazione prenotazione) {
        prenotazioni.remove(prenotazione);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipoUtente.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
