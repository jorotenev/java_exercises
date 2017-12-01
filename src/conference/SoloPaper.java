package conference;

class SoloPaper extends Paper {

    public SoloPaper(String name, String annotation, String[] keywords, String text, Author author) throws Exception {
        super(name, annotation, keywords, text);
        this.authors.add(author);
    }

}
