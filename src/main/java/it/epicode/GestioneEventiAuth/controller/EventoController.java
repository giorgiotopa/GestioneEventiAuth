package it.epicode.GestioneEventiAuth.controller;

import it.epicode.GestioneEventiAuth.exception.BadRequestException;
import it.epicode.GestioneEventiAuth.model.Evento;
import it.epicode.GestioneEventiAuth.model.EventoRequest;
import it.epicode.GestioneEventiAuth.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @GetMapping("/eventi")
    public Page<Evento> getAll(Pageable pageable){
        return eventoService.getAllEventi(pageable);
    }

    @GetMapping("/eventi/{id}")
    public Evento getDipendenteById(@PathVariable int id){
        return eventoService.getEventoById(id);
    }

    @PostMapping("/dipendenti")
    public Evento saveEvento(@RequestBody @Validated EventoRequest eventoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return eventoService.saveEvento(eventoRequest);
    }
    @PutMapping("/dipendenti/{id}")
    public Evento updateEvento(@PathVariable int id, @RequestBody @Validated EventoRequest eventoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return eventoService.updateEvento(id, eventoRequest);
    }
    @DeleteMapping("/dipendenti/{id}")
    public void deleteEvento(@PathVariable int id){
        eventoService.deleteEvento(id);
    }


}
