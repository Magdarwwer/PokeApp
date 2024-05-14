package edu.url.salle.magdalena.morag.pokeapp.model;


public class Pokemon {
        private int number;
        private String name;
        private String url;

    public Pokemon(String name, String pokemonUrl) {
        this.name = name;
        this.url = pokemonUrl;
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

   /* private int id;
    private String name;
    private String imageUrl;
    private List<String> types;
    private String description;
    private List<String> abilities;
    private List<String> stats;
    private boolean isCaptured;
    private int frontImage;

    public Pokemon(int id, String name, String imageUrl,
                   List<String> types, String description,
                   List<String> abilities, List<String> stats) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.types = types;
        this.description = description;
        this.abilities = abilities;
        this.stats = stats;
        this.isCaptured = false;
    }

    public Pokemon(String name, int frontImage, String imageUrl) {
        this.name = name;
        this.frontImage = frontImage;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public List<String> getStats() {
        return stats;
    }

    public int getFrontImage() {
        return frontImage;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void capturePokemon() {
        isCaptured = true;
    }

    public String chooseAbility() {
        Random random = new Random();
        int randomValue = random.nextInt(100);

        if (abilities.size() == 1) {
            return abilities.get(0);
        } else {
            if (randomValue < 25) {
                return abilities.get(abilities.size() - 1); // Hidden ability
            } else if (randomValue < 75) {
                return abilities.get(random.nextInt(abilities.size() - 1));
            } else {
                return abilities.get(random.nextInt(abilities.size() - 1));
            }
        }
    }*/

