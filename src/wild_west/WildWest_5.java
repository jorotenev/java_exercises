package wild_west;


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


