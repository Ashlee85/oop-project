
package librarymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private final List<Book> books = new ArrayList<>();

    public boolean addBook(Book book) {
        if (book == null || book.getId().trim().isEmpty()
                || findBook(book.getId()) != null) {
            return false;
        }

        books.add(book);
        return true;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public Book findBook(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(id.trim())) {
                return book;
            }
        }

        return null;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return getBooks();
        }

        String search = keyword.trim().toLowerCase();

        for (Book book : books) {
            if (book.getId().toLowerCase().contains(search)
                    || book.getTitle().toLowerCase().contains(search)
                    || book.getAuthor().toLowerCase().contains(search)) {
                results.add(book);
            }
        }

        return results;
    }

    public boolean updateBook(String id, String title, String author) {
        Book book = findBook(id);

        if (book == null || title == null || title.trim().isEmpty()
                || author == null || author.trim().isEmpty()) {
            return false;
        }

        book.setTitle(title.trim());
        book.setAuthor(author.trim());
        return true;
    }

    public boolean deleteBook(String id) {
        Book book = findBook(id);

        if (book == null) {
            return false;
        }

        books.remove(book);
        return true;
    }

    public boolean issueBook(String id) {
        Book book = findBook(id);

        if (book == null || book.isBorrowed()) {
            return false;
        }

        book.issueBook();
        return true;
    }

    public boolean returnBook(String id) {
        Book book = findBook(id);

        if (book == null || !book.isBorrowed()) {
            return false;
        }

        book.returnBook();
        return true;
    }

    public int getTotalBooks() {
        return books.size();
    }

    public int getBorrowedCount() {
        int count = 0;

        for (Book book : books) {
            if (book.isBorrowed()) {
                count++;
            }
        }

        return count;
    }

    public int getAvailableCount() {
        return getTotalBooks() - getBorrowedCount();
    }
}
