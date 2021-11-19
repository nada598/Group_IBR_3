package cpit.pkg251.project;

public class Book {

    private String bookTitle;
    private String authorName;
    private String classification;
    private String ISBN;
    private int availability;
    private boolean status;
//            private Connection conn;

    public Book(String bookTitle, String authorName, String classification, String ISBN, boolean status, int availability) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.classification = classification;
        this.ISBN = ISBN;
        this.status = status;
        this.availability = availability;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    Book() {
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
