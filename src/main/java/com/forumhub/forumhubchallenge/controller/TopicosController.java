package com.forumhub.forumhubchallenge.controller;

import com.forumhub.forumhubchallenge.model.Topico;
import com.forumhub.forumhubchallenge.service.TopicoService;
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
    private TopicoService topicoService;

    @GetMapping
    public List<Topico> listarTopicos() {
        return topicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalharTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoService.buscarPorId(id);
        return topico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Topico> criarTopico(@RequestBody @Valid Topico topico, UriComponentsBuilder uriBuilder) {
        Topico topicoSalvo = topicoService.criar(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(topicoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Long id, @RequestBody Topico topicoAtualizado) {
        Optional<Topico> topico = topicoService.atualizar(id, topicoAtualizado);
        return topico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTopico(@PathVariable Long id) {
        if (topicoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}