package conference_alternative;
/*
 * В това решение отново имаме клас conference_alternative.Person (crf conference.Person).
 * Този път имаме клас Authorship който наследява Person и съдържа методи и атрибути за човек който
 * може да авторства.
 * Authorship e наследен от Organiser и от Author - защото и организаторите и авторите могат да авторстват.
 *
 * Предимства на това решение:
 * - не всеки човек е автор (за разлика от решението в conference пакета).
 *
 */


public class Conference_3_alternative {
    public static void main(String... args) {
        Guest guest_1 = new Guest("Ivan", "address", "phone");
        Guest guest_2 = new Guest("Pesho", "address", "phone");

        Author author_1 = new Author("Avtorut Ivan", "address", "phone");
        Author author_2 = new Author("Avtorut Boris", "address", "phone");

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
                    "text", new Authorship[]{organiser_and_author_3, author_1, author_2}, new Integer[]{50, 30, 20});
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


