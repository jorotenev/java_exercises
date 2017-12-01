package wild_west;

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
