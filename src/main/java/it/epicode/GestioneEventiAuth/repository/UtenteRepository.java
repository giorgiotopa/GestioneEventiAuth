package it.epicode.GestioneEventiAuth.repository;

import it.epicode.GestioneEventiAuth.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    public Optional<Utente> findByUsername(String username);
    public Optional<Utente> deleteByUsername(String username);
}
