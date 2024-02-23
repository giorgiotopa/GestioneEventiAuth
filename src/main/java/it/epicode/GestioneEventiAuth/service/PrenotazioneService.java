package it.epicode.GestioneEventiAuth.service;

import it.epicode.GestioneEventiAuth.exception.BadRequestException;
import it.epicode.GestioneEventiAuth.exception.NotFoundException;
import it.epicode.GestioneEventiAuth.model.Evento;
import it.epicode.GestioneEventiAuth.model.Prenotazione;
import it.epicode.GestioneEventiAuth.model.PrenotazioneRequest;
import it.epicode.GestioneEventiAuth.model.Utente;
import it.epicode.GestioneEventiAuth.repository.EventoRepository;
import it.epicode.GestioneEventiAuth.repository.PrenotazioneRepository;
import it.epicode.GestioneEventiAuth.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private EventoService eventoService;

    public Page<Prenotazione> getAllPrenotazioni(Pageable pageable){

        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione getPrenotazioneById(int id){
        return prenotazioneRepository.findById(id).
                orElseThrow(()->new NotFoundException("Prenotazione con id="+ id + " non trovato"));
    }

public Prenotazione savePrenotazione(PrenotazioneRequest prenotazioneRequest) {
    // Trova gli oggetti Utente ed Evento utilizzando gli identificatori
    Utente utente = utenteRepository.findById(prenotazioneRequest.getUtenteId())
            .orElseThrow(() -> new NotFoundException("Utente non trovato con id=" + prenotazioneRequest.getUtenteId()));

    Evento evento = eventoRepository.findById(prenotazioneRequest.getEventoId())
            .orElseThrow(() -> new NotFoundException("Evento non trovato con id=" + prenotazioneRequest.getEventoId()));

    // Crea una nuova istanza di Prenotazione con gli oggetti Utente ed Evento
    Prenotazione prenotazione = new Prenotazione(utente, evento, prenotazioneRequest.getNumeroPosti());

    int postiDisponibili = evento.getPostidisponibili();
    int postiPrenotati = prenotazioneRequest.getNumeroPosti();

    if (postiDisponibili >= postiPrenotati) {
        // Riduci il numero di posti disponibili
        evento.setPostidisponibili(postiDisponibili - postiPrenotati);
        eventoRepository.save(evento);
        // Aggiungi la prenotazione alle liste di Utente ed Evento
        utente.addPrenotazione(prenotazione);
        evento.addPrenotazione(prenotazione);

        // Salva gli oggetti Utente ed Evento
        utenteRepository.save(utente);
        eventoRepository.save(evento);

        // Infine, salva la Prenotazione
        return prenotazioneRepository.save(prenotazione);
    } else {
        throw new BadRequestException("Posti disponibili insufficienti per l'evento");
    }
}


public void deletePrenotazione(int id) throws NotFoundException {
    Prenotazione prenotazione = getPrenotazioneById(id);

    Utente utente = prenotazione.getUtente();
    Evento evento = prenotazione.getEvento();

    if (utente != null) {
        utente.removePrenotazione(prenotazione);
        utenteRepository.save(utente);
    }

    if (evento != null) {
        evento.removePrenotazione(prenotazione);
        eventoRepository.save(evento);
    }

    prenotazioneRepository.delete(prenotazione);
}
    public Prenotazione addUtente(int id, int utenteId){
        Prenotazione prenotazione = getPrenotazioneById(id);
        Utente utente = utenteService.getUtenteById(utenteId);
        prenotazione.setUtente(utente);
        return prenotazioneRepository.save(prenotazione);
    }
    public Prenotazione addEvento(int id, int eventoId){
        Prenotazione prenotazione = getPrenotazioneById(id);
        Evento evento = eventoService.getEventoById(eventoId);
        prenotazione.setEvento(evento);
        return prenotazioneRepository.save(prenotazione);
    }


}
