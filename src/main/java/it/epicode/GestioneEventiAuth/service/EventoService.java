package it.epicode.GestioneEventiAuth.service;

import it.epicode.GestioneEventiAuth.exception.NotFoundException;
import it.epicode.GestioneEventiAuth.model.Evento;
import it.epicode.GestioneEventiAuth.model.EventoRequest;
import it.epicode.GestioneEventiAuth.model.Prenotazione;
import it.epicode.GestioneEventiAuth.repository.EventoRepository;
import it.epicode.GestioneEventiAuth.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public Page<Evento> getAllEventi(Pageable pageable){

        return eventoRepository.findAll(pageable);
    }

    public Evento getEventoById(int id){
        return eventoRepository.findById(id).
                orElseThrow(()->new NotFoundException("Evento con id="+ id + " non trovato"));
    }
    public Evento saveEvento(EventoRequest eventoRequest){
        Evento evento = new Evento(eventoRequest.getTitolo(),eventoRequest.getDescrizione(),eventoRequest.getData(),eventoRequest.getLuogo(),eventoRequest.getPostiDisponibili());
        return eventoRepository.save(evento);
    }

    public Evento updateEvento(int id, EventoRequest eventoRequest) throws NotFoundException {
        Evento e = getEventoById(id);

        e.setTitolo(eventoRequest.getTitolo());
        e.setDescrizione(eventoRequest.getDescrizione());
        e.setData(eventoRequest.getData());
        e.setLuogo(eventoRequest.getLuogo());
        e.setPostidisponibili(eventoRequest.getPostiDisponibili());
        return eventoRepository.save(e);
    }

    public void deleteEvento (int id) throws NotFoundException {
        Evento evento = getEventoById(id);
        for (Prenotazione prenotazione : evento.getPrenotazioni()) {
            prenotazione.setEvento(null);
            prenotazioneRepository.save(prenotazione);

        }
        eventoRepository.delete(evento);
    }
}
