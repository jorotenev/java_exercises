package conference;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

class CollaborativePaper extends Paper {

    // map b/w indices of authors in this.authors and their authorContributions
    private HashMap<Integer, Integer> authorContributions = new HashMap<>();

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
            this.authorContributions.put(i, authorContributions[i]);
        }
    }

    public Author getAuthor(int i) {
        return this.authors.get(i);
    }

}
