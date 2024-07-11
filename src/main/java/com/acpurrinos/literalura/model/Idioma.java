package com.acpurrinos.literalura.model;

public enum Idioma {

    ESPAÑOL("es"),

    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("pt");

    private String idiomaAPI;

    Idioma(String idiomaAPI) {
        this.idiomaAPI = idiomaAPI;
    }

    public static Idioma fromString(String text){
        for (Idioma idiomas : Idioma.values()){
            if(idiomas.idiomaAPI.equalsIgnoreCase(text)){
                return idiomas;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: " + text);
    }
    public String getIdiomaAPI() {
        return idiomaAPI;
    }
    public static Idioma fromIdiomaAPI(String idiomaAPI) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.getIdiomaAPI().equalsIgnoreCase(idiomaAPI)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado para el código: " + idiomaAPI);
    }

}

