package br.com.site.forum.dto;

import br.com.site.forum.modelo.StatusTopico;
import br.com.site.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDtoComDetalhes {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime data;
    private StatusTopico statusTopico;
    private String nomeAutor;
    private List<RespostaDto> respostas;

    public TopicoDtoComDetalhes(Topico topico) {

        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.data = topico.getDataCriacao();
        this.statusTopico = topico.getStatus();
        this.nomeAutor = topico.getAutor().getNome();
        this.respostas = topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public StatusTopico getStatusTopico() {
        return statusTopico;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public List<RespostaDto> getRespostas() {
        return respostas;
    }
}
