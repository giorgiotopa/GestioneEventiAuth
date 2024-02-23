package it.epicode.GestioneEventiAuth.controller;

import it.epicode.GestioneEventiAuth.exception.BadRequestException;
import it.epicode.GestioneEventiAuth.model.Prenotazione;
import it.epicode.GestioneEventiAuth.model.PrenotazioneRequest;
import it.epicode.GestioneEventiAuth.service.PrenotazioneService;
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

    @GetMapping("/prenotazioni")
    public Page<Prenotazione> getAll(Pageable pageable){
        return prenotazioneService.getAllPrenotazioni(pageable);
    }

    @GetMapping("/prenotazioni/{id}")
    public Prenotazione getDipendenteById(@PathVariable int id){
        return prenotazioneService.getPrenotazioneById(id);
    }

    @PostMapping("/prenotazioni")
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneRequest prenotazioneRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return prenotazioneService.savePrenotazione(prenotazioneRequest);
    }
    @DeleteMapping("/prenotazioni/{id}")
    public void deletePrenotazione(@PathVariable int id){
        prenotazioneService.deletePrenotazione(id);
    }
}
