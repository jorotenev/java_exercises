package wild_west;

class RegisterTown extends WesternTown {
    Sherriff sherriff;

    public RegisterTown(String location, int foundingYear, int numBars, int numSheriffs, Sherriff sh) {
        super(location, foundingYear, numBars, numSheriffs);
        sherriff = sh;
    }


}
