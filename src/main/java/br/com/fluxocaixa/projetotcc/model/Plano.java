package br.com.fluxocaixa.projetotcc.model;

public enum Plano {

    GRATIS("Gratis"),
    PREMIUM("Premium"),
    PREMIUMIA("Premiumia");

    private final String descrição;

    Plano(String descrição){ this.descrição = descrição; }

    public String getDescrição() { return descrição; }
}
