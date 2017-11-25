import java.util.Random;

public class WildWest_5 {
    public static void main(String[] args) {
        Villain john = new Villain("John", "h", "fw", "JJ", 10, 100);
        Villain billy = new Villain("Billy", "b", "fw", "BB", 20, 200);
        System.out.println("Beginning duel b/w " + john.getName() + " and " + billy.getName());
        Villain.Skrmish(john, billy);
        System.out.println("----");

        Sherriff sh = new Sherriff("Sheriff B", "h", "fw", 10);
        System.out.println("Beginning duel b/w " + sh.getName() + " and " + billy.getName());
        Villain.Skrmish(sh, billy);
    }
}


class WesternTown {
    private String location;
    private int foundingYear;
    private int numBars;
    private int numSheriffs;

    public WesternTown(String location, int foundingYear, int numBars, int numSheriffs) {
        this.location = location;
        this.foundingYear = foundingYear;
        this.numBars = numBars;
        this.numSheriffs = numSheriffs;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFoundingYear() {
        return this.foundingYear;
    }

    public void setFoundingYear(int foundingYear) {
        this.foundingYear = foundingYear;
    }

    public int getNumBars() {
        return this.numBars;
    }

    public void setNumBars(int numBars) {
        this.numBars = numBars;
    }

    public int getNumSheriffs() {
        return this.numSheriffs;
    }

    public void setNumSheriffs(int numSheriffs) {
        this.numSheriffs = numSheriffs;
    }
}

class RegisterTown extends WesternTown {
    Sherriff sherriff;

    public RegisterTown(String location, int foundingYear, int numBars, int numSheriffs, Sherriff sh) {
        super(location, foundingYear, numBars, numSheriffs);
        sherriff = sh;
    }


}

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

interface Shooter {
    int Shoot();
}

class Sherriff extends Villager implements Shooter {

    private int numberOfBullets;

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

class Villain extends Villager implements Shooter {
    private String nickName;
    private int numberOfBullets;
    private int priceForHead;

    public Villain(String name, String horseName, String favouriteWhiskey, String nickName, int numberOfBullets, int priceForHead) {
        super(name, horseName, favouriteWhiskey);
        this.noseOK = false;

        this.numberOfBullets = numberOfBullets;
        this.priceForHead = priceForHead;
        this.nickName = nickName;
    }

    @Override
    public int Shoot() {
        this.numberOfBullets = this.numberOfBullets == 0 ? 0 : this.numberOfBullets - 1;

        if (this.numberOfBullets == 0) {
            return 0;
        } else {
            return new Random().nextInt(100);
        }
    }

    // uses java generics so that we know the object we pass can both shoot and has a name
    public static <ShooterWithAName extends Villager & Shooter> void Skrmish(ShooterWithAName v1, ShooterWithAName v2) {
        int v1Result = v1.Shoot();
        int v2Result = v2.Shoot();

        ShooterWithAName winner = v1Result > v2Result ? v1 : v2;

        int winnerResult = Math.max(v1Result, v2Result);

        if (winnerResult >= 70) {
            System.out.println("Winner is " + winner.getName());
        } else if (v1Result == 0 && v2Result == 0) {
            System.out.println("No winner");
        } else {
            System.out.println("Winner for current round is " + winner.getName());
            Villain.Skrmish(v1, v2);
        }
    }
}