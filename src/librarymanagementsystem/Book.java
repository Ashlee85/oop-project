
package librarymanagementsystem;

public class Book extends LibraryItem implements Borrowable {
    private boolean borrowed;

    public Book(String id, String title, String author) {
        super(id, title, author);
        this.borrowed = false;
    }

    @Override
    public String getDescription() {
        return getTitle() + " by " + getAuthor();
    }

    @Override
    public void issueBook() {
        borrowed = true;
    }

    @Override
    public void returnBook() {
        borrowed = false;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public String getStatus() {
        return borrowed ? "Borrowed" : "Available";
    }
}
