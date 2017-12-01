package conference;

class Organiser extends Person implements Reviewer {
    private String password;

    public Organiser(String n, String a, String p) {
        super(n, a, p);
    }

    public Organiser(String n, String a, String p, String password) {
        this(n, a, p); // call the other constructor to avoid code duplication
        this.password = password;
    }

    @Override
    public Boolean verifyPass(String p) {
        return p != null && p.equals(this.password); // check agaings the case when this.password is null
    }
}
