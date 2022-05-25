package br.com.site.forum.config.validation;

public class FormErroDto {

    private String campo;
    private String mensagem;

    public FormErroDto(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
