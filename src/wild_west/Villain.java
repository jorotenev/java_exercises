package wild_west;

import java.util.Random;

class Villain extends Shooter {
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
    public static void Skrmish(Shooter v1, Shooter v2) {
        int v1Result = v1.Shoot();
        int v2Result = v2.Shoot();

        Shooter winner = v1Result > v2Result ? v1 : v2;

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
