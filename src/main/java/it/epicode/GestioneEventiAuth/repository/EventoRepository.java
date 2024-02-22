package it.epicode.GestioneEventiAuth.repository;

import it.epicode.GestioneEventiAuth.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer>, PagingAndSortingRepository<Evento, Integer> {
}
