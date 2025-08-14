package com.forumhub.forumhubchallenge.service;

import com.forumhub.forumhubchallenge.model.Topico;
import com.forumhub.forumhubchallenge.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public List<Topico> listarTodos() {
        return topicoRepository.findAll();
    }

    public Optional<Topico> buscarPorId(Long id) {
        return topicoRepository.findById(id);
    }

    public Topico criar(Topico topico) {
        return topicoRepository.save(topico);
    }

    public Optional<Topico> atualizar(Long id, Topico topicoAtualizado) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    topico.setTitulo(topicoAtualizado.getTitulo());
                    topico.setMensagem(topicoAtualizado.getMensagem());
                    return topicoRepository.save(topico);
                });
    }

    public boolean deletar(Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}