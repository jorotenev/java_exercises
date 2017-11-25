
import java.util.*;

public class Conference_3 {
    public static void main(String... args) {
        Person guest_1 = new Person("Ivan", "address", "phone", new Role[]{Role.guest});
        Person guest_2 = new Person("Pesho", "address", "phone", new Role[]{Role.guest});

        Author author_1 = new Person("Avtorut Ivan", "address", "phone", new Role[]{Role.author});
        Author author_2 = new Person("Avtorut Boris", "address", "phone", new Role[]{Role.author});

        Organiser organiser_1 = new Organiser("Organizatorut Georgi", "address", "phone", new Role[]{Role.organiser});
        Organiser organiser_2 = null;

        try {
            organiser_2 = new Organiser("Organizatorut Todor", "address", "phone", new Role[]{Role.organiser, Role.reviewer}, "pass");
            Organiser organiser_3 = new Organiser("Organizatorut Ignat", "address", "phone", new Role[]{Role.organiser, Role.author});
            Paper paper_1 = new SoloPaper("Name of paper #1", "annotation", new String[]{"k1", "k2"},
                    "text", organiser_3);
            // добавяме нов reviewer
            paper_1.changeStatus(organiser_2, "pass");
            // сменяме статуса на одобрен
            paper_1.changeStatus(organiser_2, "pass", Status.readyAccepted);
            Paper paper_2 = new CollaborativePaper("Name paper #2", "annotation", new String[]{"k"},
                    "text", new Author[]{organiser_3, author_1}, new Integer[]{50, 50});

            paper_2.changeStatus(organiser_2, "pass");
            paper_2.changeStatus(organiser_3, "pass", Status.readyAccepted);
            System.err.println("НЕ ТРЯБВА ДА СТИГНЕМ ДО ТОЗИ РЕД");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

enum Role {
    guest,
    author,
    reviewer,
    organiser,
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


    // даден човек може да има повече от една роля
    private Set<Role> roles = new HashSet<>();

    public Person(String n, String a, String p, Role[] roles) {
        this.name = n;
        this.address = a;
        this.phone = p;

        Collections.addAll(this.roles, roles);
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

    public Role[] getRoles() {
        return this.roles.toArray(new Role[0]);
    }

    public Boolean hasRole(Role r) {
        return this.roles.contains(r);
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
        this.numberOfPublications = this.numberOfPublications <= 0 ? 0 : this.numberOfPublications--;
    }
}


class Organiser extends Person implements Reviewer {
    private String password;

    public Organiser(String n, String a, String p, Role[] roles) {
        super(n, a, p, roles);
    }

    public Organiser(String n, String a, String p, Role[] roles, String password) throws Exception {
        this(n, a, p, roles); // call the other constructor

        if (this.hasRole(Role.guest) && this.hasRole(Role.organiser)) {
            throw new Exception("Nonsensical to be both an organiser and a guest");
        }

        if (!this.hasRole(Role.organiser)) {
            throw new Exception("Organisers that are not reviewers don't need to have a password");
        } else {
            this.password = password;
        }
    }

    @Override
    public Boolean verifyPass(String p) {
        return p != null && p.equals(this.password); // this.password can be null
    }
}


// abstract just so that we enforce instantiating only objects of type SoloPaper or CollaborativePaper
abstract class Paper {
    private String name;
    private String annotation;
    private ArrayList<String> keywords = new ArrayList<>(); // array-like, но няма нужда да обявяваме предварително колко елемента ще има
    private String text;
    private Status status = Status.newStatus;

    protected ArrayList<Author> authors = new ArrayList<>();
    private Set<Reviewer> reviewers = new HashSet<>();

    private final int MAX_KEYWORDS = 4;

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

    public void ensureReviewerNotAuthor(Reviewer reviewer) throws Exception {
        for (Author a : this.authors) {
            if (a.equals(reviewer)) {
                throw new Exception("The reviewer is the same as the author!");
            }
        }
    }

    public Status getStatus() {
        return this.status;
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

        System.out.println("Status changed of: \n" + this.toString());

        if (this.status.equals(Status.readyAccepted)) {
            for (Author a : this.authors) {
                a.incrementNumberPublications();
            }
        }

    }

    public void changeStatus(Reviewer r, String password) throws Exception {
        this.ensureReviewerValid(r, password);
        if (this.status.equals(Status.newStatus)) {
            this.status = Status.processing;
            System.out.println(this.toString() + " is in processing");
        }

        this.reviewers.add(r);
    }

    private void ensureReviewerValid(Reviewer reviewer, String password) throws Exception {
        if (!reviewer.verifyPass(password)) {
            throw new Exception("The reviewer doesn't have permissions to change the status");
        }
        this.ensureReviewerNotAuthor(reviewer);

    }


    /**
     * @param keyword
     * @throws Exception - if there are more keywords than MAX_KEYWORDS
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
        return String.format("[%s] Title: %s | Author(s): [%s] | Reviewers: [%s]",
                this.status.name(), this.name, authors, reviewers);
    }
}

class SoloPaper extends Paper {


    public SoloPaper(String name, String annotation, String[] keywords, String text, Author author) throws Exception {
        super(name, annotation, keywords, text);
        this.authors.add(author);
    }
}

/**
 * статия в колектив
 */
class CollaborativePaper extends Paper {

    // map b/w indeces of authors in this.authors and their contribution
    HashMap<Integer, Integer> contribution = new HashMap<>();

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