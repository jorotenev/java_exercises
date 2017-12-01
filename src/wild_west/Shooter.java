package wild_west;

abstract class Shooter extends wild_west.Villager {
    protected int numberOfBullets;

    public Shooter(String name, String horseName, String favouriteWhiskeyName) {
        super(name, horseName, favouriteWhiskeyName);
    }

    abstract int Shoot();
}
