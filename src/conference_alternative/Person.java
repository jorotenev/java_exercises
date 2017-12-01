package conference_alternative;

class Person {
    private String name;
    private String address;
    private String phone;



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


}
