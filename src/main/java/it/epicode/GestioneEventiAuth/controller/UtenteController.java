package it.epicode.GestioneEventiAuth.controller;


import it.epicode.GestioneEventiAuth.exception.BadRequestException;
import it.epicode.GestioneEventiAuth.model.Utente;
import it.epicode.GestioneEventiAuth.model.UtenteRequest;
import it.epicode.GestioneEventiAuth.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    @GetMapping("/utenti")
    public List<Utente> getAll(){
        return utenteService.getAllUtenti();
    }

    @GetMapping("/utenti/{username}")
    public Utente getUtenteByUsername(@PathVariable String username){
        return utenteService.getUtenteByUsername(username);
    }

    @PutMapping("/utenti/{username}")
    public Utente updateUtente(@PathVariable String username, @RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return utenteService.updateUtente(username, utenteRequest);

    }
    @PostMapping("/utenti")
    public Utente save(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return utenteService.save(utenteRequest);
    }

    @DeleteMapping("/utenti/{id}")
    public void deleteUtente(@PathVariable int id){
        utenteService.deleteUtente(id);
    }

    @PatchMapping("/utenti/{username}")
    public Utente changeTipoUtente(@PathVariable String username, @RequestBody String tipoUtente){
        return utenteService.updateTipoUtente(username, tipoUtente);

    }
}
