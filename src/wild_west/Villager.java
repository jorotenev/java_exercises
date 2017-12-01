package wild_west;

class Villager {
    private String name;
    private String horseName;
    private String favouriteWhiskeyName;
    private int numLegs = 2;
    private int numArms = 2;
    private int numEyes = 2;
    protected boolean noseOK = true;

    public Villager(String name, String horseName, String favouriteWhiskeyName) {

        this.name = name;
        this.horseName = horseName;
        this.favouriteWhiskeyName = favouriteWhiskeyName;
    }

    public String getName() {
        return name;
    }

    public String getFavouriteWhiskeyName() {
        return favouriteWhiskeyName;
    }
}
