package it.epicode.GestioneEventiAuth.service;

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

    public Page<Prenotazione> getAllPrenotazioni(Pageable pageable){

        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione getPrenotazioneById(int id){
        return prenotazioneRepository.findById(id).
                orElseThrow(()->new NotFoundException("Prenotazione con id="+ id + " non trovato"));
    }

//    public Prenotazione savePrenotazione(PrenotazioneRequest prenotazioneRequest){
//        Prenotazione prenotazione = new Prenotazione(prenotazioneRequest.getUtente(),prenotazioneRequest.getEvento());
//        Utente utente = prenotazioneRequest.getUtente();
//        Evento evento = prenotazioneRequest.getEvento();
//
//        utente.addPrenotazione(prenotazione);
//        evento.addPrenotazione(prenotazione);
//        utenteRepository.save(utente);
//        eventoRepository.save(evento);
//
//        return prenotazioneRepository.save(prenotazione);
//    }
public Prenotazione savePrenotazione(PrenotazioneRequest prenotazioneRequest) {
    // Trova gli oggetti Utente ed Evento utilizzando gli identificatori
    Utente utente = utenteRepository.findById(prenotazioneRequest.getUtente())
            .orElseThrow(() -> new NotFoundException("Utente non trovato con id=" + prenotazioneRequest.getUtente()));

    Evento evento = eventoRepository.findById(prenotazioneRequest.getEvento())
            .orElseThrow(() -> new NotFoundException("Evento non trovato con id=" + prenotazioneRequest.getEvento()));

    // Crea una nuova istanza di Prenotazione con gli oggetti Utente ed Evento
    Prenotazione prenotazione = new Prenotazione(utente, evento);

    // Aggiungi la prenotazione alle liste di Utente ed Evento
    utente.addPrenotazione(prenotazione);
    evento.addPrenotazione(prenotazione);

    // Salva gli oggetti Utente ed Evento
    utenteRepository.save(utente);
    eventoRepository.save(evento);

    // Infine, salva la Prenotazione
    return prenotazioneRepository.save(prenotazione);
}

    public void deletePrenotazione(int id) throws NotFoundException {
        Prenotazione prenotazione = getPrenotazioneById(id);

        Utente utente = prenotazione.getUtente();
        Evento evento = prenotazione.getEvento();

        utente.removePrenotazione(prenotazione);
        evento.removePrenotazione(prenotazione);

        utenteRepository.save(utente);
        eventoRepository.save(evento);

        prenotazioneRepository.delete(prenotazione);
    }


}
