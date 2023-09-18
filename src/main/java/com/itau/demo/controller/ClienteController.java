package com.itau.demo.controller;

import com.itau.demo.model.Cliente;
import com.itau.demo.respository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

    Logger log = LoggerFactory.getLogger(ClienteController.class);

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository){
        super();
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        log.info("Criando cliente!");
        clienteRepository.save(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<Cliente>> getById(@PathVariable Integer id){
        Optional<Cliente> cliente;
        try {
            cliente = clienteRepository.findById(id);
            if (!cliente.isPresent()) throw new NoSuchElementException();
            log.info("Retornando cliente!");
            return new ResponseEntity<Optional<Cliente>>(cliente, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.error("Cliente não encontrado!");
            return new ResponseEntity<Optional<Cliente>>(HttpStatus.NOT_FOUND);
        }

    }


}
