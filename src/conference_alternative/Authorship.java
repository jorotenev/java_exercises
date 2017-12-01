package conference_alternative;

public class Authorship extends Person {
    private int numberOfPublications;

    public Authorship(String n, String a, String p) {
        super(n, a, p);
    }


    public int getNumberPublications() {
        return this.numberOfPublications;
    }


    public void incrementNumberPublications() {
        this.numberOfPublications++;
    }


    public void decrementNumberOfPublications() {
        this.numberOfPublications = this.numberOfPublications <= 0 ? 0 : this.numberOfPublications - 1; // can't fall below 0
    }
}
