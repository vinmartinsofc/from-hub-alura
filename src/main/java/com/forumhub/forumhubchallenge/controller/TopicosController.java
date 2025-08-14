package com.forumhub.forumhubchallenge.controller;

import com.forumhub.forumhubchallenge.model.Topico;
import com.forumhub.forumhubchallenge.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public List<Topico> listarTopicos() {
        return topicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalharTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Topico> criarTopico(@RequestBody @Valid Topico topico, UriComponentsBuilder uriBuilder) {
        Topico topicoSalvo = topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(topicoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Long id, @RequestBody Topico topicoAtualizado) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = optional.get();
        topico.setTitulo(topicoAtualizado.getTitulo());
        topico.setMensagem(topicoAtualizado.getMensagem());

        Topico topicoAtualizadoSalvo = topicoRepository.save(topico);
        return ResponseEntity.ok(topicoAtualizadoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTopico(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}