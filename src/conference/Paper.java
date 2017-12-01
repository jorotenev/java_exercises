package conference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    protected static final String MSG_ON_INVALID_REVIEWER = "The reviewer doesn't have permissions to change the status";

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
