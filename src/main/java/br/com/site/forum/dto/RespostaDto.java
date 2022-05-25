package br.com.site.forum.dto;

import br.com.site.forum.modelo.Resposta;

import java.time.LocalDateTime;

public class RespostaDto {

    private Long id;
    private String mensagem;
    private String nomeAutor;
    private LocalDateTime data;

    public RespostaDto(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.nomeAutor = resposta.getAutor().getNome();
        this.data = resposta.getDataCriacao();
    }
}
