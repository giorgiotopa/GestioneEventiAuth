package it.epicode.GestioneEventiAuth.service;

import it.epicode.GestioneEventiAuth.enums.TipoUtente;
import it.epicode.GestioneEventiAuth.exception.NotFoundException;
import it.epicode.GestioneEventiAuth.model.Evento;
import it.epicode.GestioneEventiAuth.model.Prenotazione;
import it.epicode.GestioneEventiAuth.model.Utente;
import it.epicode.GestioneEventiAuth.model.UtenteRequest;
import it.epicode.GestioneEventiAuth.repository.PrenotazioneRepository;
import it.epicode.GestioneEventiAuth.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private PasswordEncoder encoder;

    public Utente save(UtenteRequest utenteRequest){

        Utente utente = new Utente();
        utente.setNome(utenteRequest.getNome());
        utente.setCognome(utenteRequest.getCognome());
        utente.setUsername(utenteRequest.getUsername());
        utente.setPassword(encoder.encode(utenteRequest.getPassword()));
        utente.setTipoUtente(TipoUtente.NORMALE);

        return utenteRepository.save(utente);
    }

    public Utente getUtenteById(int id){
        return utenteRepository.findById(id).orElseThrow(()->new NotFoundException("Utente non trovato"));
    }

    public Utente getUtenteByUsername(String username){
        return utenteRepository.findByUsername(username).orElseThrow(()->new NotFoundException("Username non trovato"));
    }
    public List<Utente> getAllUtenti(){
        return utenteRepository.findAll();
    }

    public Utente updateUtente(String username, UtenteRequest utenteRequest){
        Utente utente = getUtenteByUsername(username);
        utente.setNome(utenteRequest.getNome());
        utente.setCognome(utenteRequest.getCognome());
        utente.setUsername(utenteRequest.getUsername());
        utente.setPassword(utenteRequest.getPassword());

        return utenteRepository.save(utente);
    }

    public Utente updateTipoUtente(String username,String tipoUtente){
        Utente utente = getUtenteByUsername(username);
        utente.setTipoUtente(TipoUtente.valueOf(tipoUtente));
        return utenteRepository.save(utente);
    }

    public void deleteUtente (int id) throws NotFoundException {
        Utente utente = getUtenteById(id);
        for (Prenotazione prenotazione : utente.getPrenotazioni()) {
            prenotazione.setEvento(null);
            prenotazioneRepository.save(prenotazione);

        }
        utenteRepository.delete(utente);
    }
}
