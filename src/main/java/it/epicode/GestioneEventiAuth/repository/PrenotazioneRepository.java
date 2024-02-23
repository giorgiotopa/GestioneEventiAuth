package it.epicode.GestioneEventiAuth.repository;

import it.epicode.GestioneEventiAuth.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer>, PagingAndSortingRepository<Prenotazione, Integer> {
}
