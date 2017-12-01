package conference_alternative;

class SoloPaper extends Paper {

    public SoloPaper(String name, String annotation, String[] keywords, String text, Authorship author) throws Exception {
        super(name, annotation, keywords, text);
        this.authors.add(author);
    }

}
