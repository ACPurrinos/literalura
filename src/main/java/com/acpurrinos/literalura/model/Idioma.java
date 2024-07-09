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


}

