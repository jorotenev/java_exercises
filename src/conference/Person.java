package conference;

/**
 * всеки човек може да е автор
 */
class Person implements Author {
    private String name;
    private String address;
    private String phone;

    private int numberOfPublications;


    public Person(String n, String a, String p) {
        this.name = n;
        this.address = a;
        this.phone = p;
    }


    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }

    @Override
    public int getNumberPublications() {
        return this.numberOfPublications;
    }

    @Override
    public void incrementNumberPublications() {
        this.numberOfPublications++;
    }

    @Override
    public void decrementNumberOfPublications() {
        this.numberOfPublications = this.numberOfPublications <= 0 ? 0 : this.numberOfPublications - 1; // can't fall below 0
    }
}
