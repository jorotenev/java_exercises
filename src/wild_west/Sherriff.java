package wild_west;

import java.util.Random;

class Sherriff extends Shooter {


    public Sherriff(String name, String horseName, String favouriteWhiskeyName, int numberOfBullets) {
        super(name, horseName, favouriteWhiskeyName);
        this.numberOfBullets = numberOfBullets;
    }

    @Override
    public int Shoot() {
        this.numberOfBullets = this.numberOfBullets == 0 ? 0 : this.numberOfBullets - 1;

        if (this.numberOfBullets == 0) {
            return 0;
        } else {
            return Math.max(20, new Random().nextInt(100));
        }
    }
}
