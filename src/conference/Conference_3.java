package conference;/*
 * В това решение имаме клас conference.Person. Той имплементира интерфейса conference.Author (т.е всеки човек може да е автор).
 * Специализация на класа conference.Person e класът conference.Organiser, който име два конструктора - с парола или без парола.
 * Класа conference.Organiser имплементира интерфейса conference.Reviewer (т.е. организаторите могат да са рецензенти).
 * <p>
 * Тази организация позволява:
 * - всеки човек да може да е автор
 * - организаторите, понеже са хора, също да могат да са автори
 * - организаторите, понеже са рецензенти(имплементира conference.Reviewer), да могат да рецензират статии
 * <p>
 * Недостатъци на това решение:
 * - По този начин всички хора са автори
 */

import java.util.*;

public class Conference_3 {
    public static void main(String... args) {
        Person guest_1 = new Person("Ivan", "address", "phone");
        Person guest_2 = new Person("Pesho", "address", "phone");

        Author author_1 = new Person("Avtorut Ivan", "address", "phone");
        Author author_2 = new Person("Avtorut Boris", "address", "phone");

        Organiser organiser_and_reviewer = new Organiser("Organizatorut Todor", "address", "phone", "pass");
        Organiser organiser_and_author_3 = new Organiser("Organizatorut Ignat", "address", "phone");

        // make a solo paper; add a reviewer and the same reviewer accepts the article
        try {
            Paper paper_1 = new SoloPaper("Name of paper #1", "annotation", new String[]{"k1", "k2"},
                    "text", organiser_and_author_3);
            paper_1.changeStatus(organiser_and_reviewer, "pass");
            paper_1.changeStatus(organiser_and_reviewer, "pass", Status.readyAccepted);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // make a collaborative article
        Paper paper_2 = null;
        try {
            // change stage to accepted
            paper_2 = new CollaborativePaper("Name paper #2", "annotation", new String[]{"k"},
                    "text", new Author[]{organiser_and_author_3, author_1, author_2}, new Integer[]{50, 30, 20});
            // add an organiser as a reviewer (ok, cuz he's not amongst the author)
            paper_2.changeStatus(organiser_and_reviewer, "pass");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        try {
            // now, try to add as a reviewer an organiser, who's also an author of the article - should throw an Exception
            paper_2.changeStatus(organiser_and_author_3, "pass", Status.readyAccepted);
            throw new Exception("Previous line should have thrown an exception.");
        } catch (Exception e) {
            if (e.getMessage().equals(Paper.MSG_ON_INVALID_REVIEWER)) {  // очаквана грешка
                System.out.println("As expected, an organiser can't review his own article");
            } else {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}

enum Status {
    newStatus, // нова (няма назначен рецензент)
    processing, // процес на рецензиране (има назначен рецензент, но още не е готова)
    readyAccepted,
    readyRejected,
}


interface Author {
    int getNumberPublications();

    void incrementNumberPublications();

    void decrementNumberOfPublications();
}

interface Reviewer {
    public Boolean verifyPass(String p);
}

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


class Organiser extends Person implements Reviewer {
    private String password;

    public Organiser(String n, String a, String p) {
        super(n, a, p);
    }

    public Organiser(String n, String a, String p, String password) {
        this(n, a, p); // call the other constructor
        this.password = password;
    }

    @Override
    public Boolean verifyPass(String p) {
        return p != null && p.equals(this.password); // this.password can be null
    }
}


// abstract just so that we enforce instantiating only objects of type conference.SoloPaper or conference.CollaborativePaper
abstract class Paper {

    private String name;
    private String annotation;
    private ArrayList<String> keywords = new ArrayList<>(); // array-like, but no need to know the size up front
    private String text;
    private Status status = Status.newStatus;

    protected ArrayList<Author> authors = new ArrayList<>();
    private Set<Reviewer> reviewers = new HashSet<>();

    private final int MAX_KEYWORDS = 4;
    public static final String MSG_ON_INVALID_REVIEWER = "The reviewer doesn't have permissions to change the status";

    public Paper(String name, String annotation, String[] keywords, String text) throws Exception {
        this.name = name;
        this.annotation = annotation;
        for (String keyword : keywords) {
            this.addKeyword(keyword);
        }

        this.text = text;
    }

    public Author getAuthor() {
        return this.authors.get(0);
    }


    public void getStatus() {
        System.out.println(this.status);
    }

    /**
     * Changes the status of the paper.
     * Manages edges cases - if a paper is already accepted, and the new status is not accepted,
     * then reduce the number of publications of the authors.
     */
    public void changeStatus(Reviewer reviewer, String password, Status st) throws Exception {
        changeStatus(reviewer, password);

        if (this.status.equals(Status.readyAccepted) && !st.equals(Status.readyAccepted)) {
            for (Author a : this.authors) {
                a.decrementNumberOfPublications();
            }
        }
        this.status = st;

        System.out.println("The paper has new status:\n" + this.toString());

        if (this.status.equals(Status.readyAccepted)) {
            for (Author a : this.authors) {
                a.incrementNumberPublications();
            }
        }

    }

    // just adds a reviewer to the paper (without changing its status)
    public void changeStatus(Reviewer r, String password) throws Exception {
        this.ensureReviewerValid(r, password);
        this.reviewers.add(r);

        if (this.status.equals(Status.newStatus)) { // if that's the first reviewer, set the status to processing
            this.status = Status.processing;
            System.out.println(this.toString() + " is in processing");
        }

    }


    /**
     * Verifies the password and ensures the reviewer is not among the authors of the paper
     */

    private void ensureReviewerValid(Reviewer reviewer, String password) throws Exception {
        if (!reviewer.verifyPass(password)) {
            throw new Exception(this.MSG_ON_INVALID_REVIEWER);
        }
        this.ensureReviewerNotAuthor(reviewer);

    }

    private void ensureReviewerNotAuthor(Reviewer reviewer) throws Exception {
        for (Author a : this.authors) {
            if (a.equals(reviewer)) {
                throw new Exception("The reviewer is the same as the author!");
            }
        }
    }

    /**
     * @throws Exception - if adding the new keyword would result in more keywords than MAX_KEYWORDS
     */

    private void addKeyword(String keyword) throws Exception {
        if (this.keywords.size() >= this.MAX_KEYWORDS) {
            throw new Exception("Не можем да добавим повече от " + this.MAX_KEYWORDS + " keywords");
        }

        this.keywords.add(keyword);
    }

    @Override
    public String toString() {
        String authors = "";
        for (Author a : this.authors) {
            Person p = (Person) a; // TODO
            authors += p.getName() + ", ";
        }
        String reviewers = "";
        for (Reviewer p : this.reviewers) {
            Person person = (Person) p; // TODO
            reviewers += person.getName() + ", ";
        }
        return String.format("[%s] Title: %s | conference.Author(s): [%s] | Reviewers: [%s]",
                this.status.name(), this.name, authors, reviewers);
    }
}

class SoloPaper extends Paper {

    public SoloPaper(String name, String annotation, String[] keywords, String text, Author author) throws Exception {
        super(name, annotation, keywords, text);
        this.authors.add(author);
    }
}

class CollaborativePaper extends Paper {

    // map b/w indeces of authors in this.authors and their contribution
    private HashMap<Integer, Integer> contribution = new HashMap<>();

    public CollaborativePaper(String name, String annotation, String[] keywords, String text, Author[] authors,
                              Integer[] authorContributions) throws Exception {
        super(name, annotation, keywords, text);
        if (authors.length != authorContributions.length) {
            throw new Exception("There must be contributions provided for each author");
        }


        int sumOfContributions = Arrays.stream(authorContributions).reduce(0, (acc, contr) -> acc + contr); // reduce aka left fold
        if (sumOfContributions != 100) {
            throw new Exception("Contributions should sum up to 100");
        }

        Collections.addAll(this.authors, authors);
        for (int i = 0; i < authors.length; i++) {
            this.contribution.put(i, authorContributions[i]);
        }
    }

    public Author getAuthor(int i) {
        return this.authors.get(i);
    }

}
