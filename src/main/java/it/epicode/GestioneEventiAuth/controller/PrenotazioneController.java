package it.epicode.GestioneEventiAuth.controller;

import it.epicode.GestioneEventiAuth.exception.BadRequestException;
import it.epicode.GestioneEventiAuth.exception.NotFoundException;
import it.epicode.GestioneEventiAuth.model.Evento;
import it.epicode.GestioneEventiAuth.model.Prenotazione;
import it.epicode.GestioneEventiAuth.model.PrenotazioneRequest;
import it.epicode.GestioneEventiAuth.model.Utente;
import it.epicode.GestioneEventiAuth.repository.EventoRepository;
import it.epicode.GestioneEventiAuth.repository.UtenteRepository;
import it.epicode.GestioneEventiAuth.service.EventoService;
import it.epicode.GestioneEventiAuth.service.PrenotazioneService;
import it.epicode.GestioneEventiAuth.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/prenotazioni")
    public Page<Prenotazione> getAll(Pageable pageable){
        return prenotazioneService.getAllPrenotazioni(pageable);
    }

    @GetMapping("/prenotazioni/{id}")
    public Prenotazione getDipendenteById(@PathVariable int id){
        return prenotazioneService.getPrenotazioneById(id);
    }

//    @PostMapping("/prenotazioni")
//    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneRequest prenotazioneRequest, BindingResult bindingResult){
//        if (bindingResult.hasErrors()){
//            throw new BadRequestException(bindingResult.getAllErrors().toString());
//        }
//        return prenotazioneService.savePrenotazione(prenotazioneRequest);
//    }
@PostMapping("/prenotazioni")
public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneRequest prenotazioneRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        throw new BadRequestException(bindingResult.getAllErrors().toString());
    }

    Utente utente = utenteRepository.findById(Integer.parseInt(String.valueOf(prenotazioneRequest.getUtente())))
            .orElseThrow(() -> new NotFoundException("Utente non trovato con id=" + prenotazioneRequest.getUtente()));

    Evento evento = eventoRepository.findById(Integer.parseInt(String.valueOf(prenotazioneRequest.getEvento())))
            .orElseThrow(() -> new NotFoundException("Evento non trovato con id=" + prenotazioneRequest.getEvento()));

    PrenotazioneRequest updatedRequest = new PrenotazioneRequest();
    updatedRequest.setUtente(utente.getId());
    updatedRequest.setEvento(evento.getId());

    return prenotazioneService.savePrenotazione(updatedRequest);
}
    @DeleteMapping("/prenotazioni/{id}")
    public void deletePrenotazione(@PathVariable int id){
        prenotazioneService.deletePrenotazione(id);
    }
}
