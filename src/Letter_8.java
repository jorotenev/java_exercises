import java.util.ArrayList;

public class Letter_8 {
    public static void main(String[] args) {
        String nameR = "Georgi";
        String addrR = "Sofia";
        String nameSender = "Pesho";
        String addrSender = "Pld";
        String letterNumber = "1";

        Letter letter_1 = new Letter(nameR, addrR, nameSender, addrSender, letterNumber);
        letter_1.toReturn();
        letter_1.forSend();

        Package package_1 = new Package();

        Dispatcher dispatcher = new Dispatcher();

        try {
            dispatcher.Add(letter_1);
            dispatcher.Add(package_1);
        } catch (AddLetterException e) {
            System.err.println("Letter with number " + letter_1.getLetterNumber() + " already exists");
        }
    }
}


class Letter {
    private String nameRecipient;
    private String addressRecipient;

    private String nameSender;
    private String addressSender;

    private String letterNumber;

    public Letter(String nameRecipient, String addressRecipient, String nameSender, String addressSender, String letterNumber) {

        this.nameRecipient = nameRecipient;
        this.addressRecipient = addressRecipient;
        this.nameSender = nameSender;
        this.addressSender = addressSender;
        this.letterNumber = letterNumber;
    }


    public String getLetterNumber() {
        return this.letterNumber;
    }

    public String getNameRecipient() {
        return this.nameRecipient;
    }

    public String getAddressRecipient() {
        return this.addressRecipient;
    }

    public String getNameSender() {
        return this.nameSender;
    }

    public String getAddressSender() {
        return this.addressSender;
    }


    void forSend() {
        System.out.printf("Name receiver: %s, Address receiver: %s %n", this.nameRecipient, this.addressRecipient);
    }

    void toReturn() {
        System.out.printf("Name sender: %s, Address sender: %s %n", this.nameSender, this.addressSender);
    }
}

class Package extends Letter {
    private double weight;

    public Package(String nameRecipient, String addressRecipient, String nameSender, String addressSender, String letterNumber) {
        super(nameRecipient, addressRecipient, nameSender, addressSender, letterNumber);
    }

    /**
     * Напишете конструктор по подразбиране, в който тежестта се инициализира с 0.
     */
    public Package() {
        super("", "", "", "", "");
        this.weight = 0;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    void forSend() {
        System.out.printf("Name receiver: %s, Address receiver: %s, Weight %d %n", this.getNameRecipient(), this.getAddressRecipient(), this.weight);
    }

    @Override
    void toReturn() {
        System.out.printf("Name sender: %s, Address sender: %s, Weight %d %n", this.getNameSender(), this.getAddressSender(), this.weight);
    }
}

class Dispatcher {
    static ArrayList<Letter> letters;

    public Dispatcher() {
        Dispatcher.letters = new ArrayList<>();
    }

    void Add(Letter newLetter) throws AddLetterException {
        for (Letter l : Dispatcher.letters) {
            if (l.getLetterNumber().equals(l.getLetterNumber())) {
                throw new AddLetterException();
            }
        }

        Dispatcher.letters.add(newLetter);
    }

    Letter Get() {
        return Dispatcher.letters.remove(Dispatcher.letters.size() - 1); // removes the last letter and returns it
    }

}

class AddLetterException extends Exception {
//    public AddLetterException(String msg) {
//        super(msg);
//    }
}