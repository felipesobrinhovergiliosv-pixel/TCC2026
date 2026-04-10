package br.com.fluxocaixa.projetotcc.model;

public enum TipoMidia {
    VIDEO("Atendido"),
    IMAGEM("Imagem"),
    AUDIO("Audio"),
    NENHUM("Nenhum");

    private final String descrição;

    TipoMidia(String descrição){ this.descrição = descrição; }

    public String getDescrição() { return descrição; }
}
