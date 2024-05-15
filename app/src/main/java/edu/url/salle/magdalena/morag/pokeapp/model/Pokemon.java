package edu.url.salle.magdalena.morag.pokeapp.model;


public class Pokemon {
        private int number;
        private String name;
        private String url;
        private String pokeballImage;

    public Pokemon(String name, String pokemonUrl) {
        this.name = name;
        this.url = pokemonUrl;
    }

    public Pokemon(String name, String frontImage, String pokeballImage) {
        this.name = name;
        this.url = frontImage;
        this.pokeballImage = pokeballImage;
    }
    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getNumber() {
            String[] urlNumber = url.split("/");
            return Integer.parseInt(urlNumber[urlNumber.length - 1]);
        }

        public void setNumber(int number) {
            this.number = number;
        }
}

