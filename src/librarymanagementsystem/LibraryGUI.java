
package librarymanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryGUI extends JFrame {

    private final LibraryManager manager = new LibraryManager();

    private final Color green = new Color(36, 91, 68);
    private final Color darkGreen = new Color(25, 66, 49);
    private final Color background = new Color(245, 247, 245);
    private final Color textColor = new Color(45, 55, 49);

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField searchField;

    private JTable booksTable;
    private JTable borrowingTable;

    private DefaultTableModel booksModel;
    private DefaultTableModel borrowingModel;

    private JLabel totalBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel borrowedBooksLabel;

    public LibraryGUI() {
        setTitle("Library Management System");
        setSize(1150, 720);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(background);

        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(background);

        contentPanel.add(createDashboard(), "Dashboard");
        contentPanel.add(createBooksPanel(), "Books");
        contentPanel.add(createBorrowingPanel(), "Borrowing");

        add(contentPanel, BorderLayout.CENTER);

        refreshAll();
        showPage("Dashboard");
    }

    // SIDEBAR

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(215, 0));
        sidebar.setBackground(darkGreen);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));

        JLabel logo = new JLabel(
                "<html>LIBRARY<br>MANAGEMENT</html>");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 22));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Book Management System");
        subtitle.setForeground(new Color(210, 225, 215));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(45));

        sidebar.add(createMenuButton("Dashboard", "Dashboard"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createMenuButton("Books", "Books"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createMenuButton("Borrowing", "Borrowing"));

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("Library System v1.0");
        footer.setForeground(new Color(210, 225, 215));
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(footer);

        return sidebar;
    }

    private JButton createMenuButton(String label, String page) {
        JButton button = new JButton(label);

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setPreferredSize(new Dimension(185, 44));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(green);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> showPage(page));

        return button;
    }

    private void showPage(String page) {
        cardLayout.show(contentPanel, page);

        if (page.equals("Dashboard")) {
            refreshDashboard();
        } else if (page.equals("Books")) {
            refreshBookTable(manager.getBooks());
        } else if (page.equals("Borrowing")) {
            refreshBorrowingTable();
        }
    }

    // DASHBOARD

    private JPanel createDashboard() {
        JPanel panel = createPagePanel();

        JPanel header = createHeader(
                "Dashboard",
                "Overview of your library collection"
        );

        JPanel cards = new JPanel(new GridLayout(1, 3, 15, 15));
        cards.setOpaque(false);

        totalBooksLabel = new JLabel("0");
        availableBooksLabel = new JLabel("0");
        borrowedBooksLabel = new JLabel("0");

        cards.add(createStatCard("Total Books", totalBooksLabel, green));
        cards.add(createStatCard(
                "Available Books", availableBooksLabel,
                new Color(62, 125, 85)));
        cards.add(createStatCard(
                "Borrowed Books", borrowedBooksLabel,
                new Color(180, 125, 55)));

        JLabel info = new JLabel(
                "<html><b>Welcome to the Library Management System</b>"
                        + "<br><br>Use the Books page to add, search, "
                        + "update, and delete books."
                        + "<br>Use the Borrowing page to issue and return books."
                        + "</html>"
        );
        info.setFont(new Font("SansSerif", Font.PLAIN, 15));
        info.setForeground(textColor);
        info.setOpaque(true);
        info.setBackground(Color.WHITE);
        info.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton booksButton = createButton("Manage Books", green);
        JButton borrowingButton = createButton(
                "Manage Borrowing", new Color(75, 110, 88));

        booksButton.addActionListener(e -> showPage("Books"));
        borrowingButton.addActionListener(e -> showPage("Borrowing"));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        actions.setOpaque(false);
        actions.add(booksButton);
        actions.add(borrowingButton);

        JPanel center = new JPanel(new BorderLayout(15, 20));
        center.setOpaque(false);
        center.add(cards, BorderLayout.NORTH);
        center.add(info, BorderLayout.CENTER);
        center.add(actions, BorderLayout.SOUTH);

        panel.add(header, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(
            String title, JLabel value, Color accent) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, accent),
                new EmptyBorder(20, 20, 20, 20)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        value.setFont(new Font("SansSerif", Font.BOLD, 32));
        value.setForeground(accent);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(value);

        return card;
    }

    private void refreshDashboard() {
        totalBooksLabel.setText(
                String.valueOf(manager.getTotalBooks()));
        availableBooksLabel.setText(
                String.valueOf(manager.getAvailableCount()));
        borrowedBooksLabel.setText(
                String.valueOf(manager.getBorrowedCount()));
    }

    // BOOKS PAGE

    private JPanel createBooksPanel() {
        JPanel panel = createPagePanel();

        JPanel header = createHeader(
                "Book Management",
                "Add, search, update, and delete books"
        );

        JPanel body = new JPanel(new BorderLayout(10, 12));
        body.setOpaque(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(12, 12, 12, 12));

        idField = new JTextField(15);
        titleField = new JTextField(15);
        authorField = new JTextField(15);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(form, gbc, 0, "Book ID:", idField);
        addFormRow(form, gbc, 1, "Title:", titleField);
        addFormRow(form, gbc, 2, "Author:", authorField);

        JButton addButton = createButton("Add Book", green);
        JButton updateButton = createButton(
                "Update", new Color(67, 111, 146));
        JButton deleteButton = createButton(
                "Delete", new Color(170, 65, 65));
        JButton clearButton = createButton("Clear", Color.GRAY);

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        clearButton.addActionListener(e -> clearFields());

        JPanel formButtons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 5));
        formButtons.setOpaque(false);
        formButtons.add(addButton);
        formButtons.add(updateButton);
        formButtons.add(deleteButton);
        formButtons.add(clearButton);

        JPanel formArea = new JPanel(new BorderLayout(5, 5));
        formArea.setOpaque(false);
        formArea.add(form, BorderLayout.CENTER);
        formArea.add(formButtons, BorderLayout.SOUTH);

        searchField = new JTextField(18);

        JButton searchButton = createButton("Search", green);
        JButton showAllButton = createButton("Show All", Color.GRAY);

        searchButton.addActionListener(e -> searchBooks());
        searchField.addActionListener(e -> searchBooks());

        showAllButton.addActionListener(e -> {
            searchField.setText("");
            refreshBookTable(manager.getBooks());
        });

        JPanel searchPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 5));
        searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("Search ID, title, or author:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        booksModel = createTableModel();
        booksTable = new JTable(booksModel);
        styleTable(booksTable);

        booksTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = booksTable.getSelectedRow();

                if (row >= 0) {
                    idField.setText(
                            booksModel.getValueAt(row, 0).toString());
                    titleField.setText(
                            booksModel.getValueAt(row, 1).toString());
                    authorField.setText(
                            booksModel.getValueAt(row, 2).toString());
                }
            }
        });

        JScrollPane tableScroll = new JScrollPane(booksTable);

        JPanel tableArea = new JPanel(new BorderLayout(5, 8));
        tableArea.setOpaque(false);
        tableArea.add(searchPanel, BorderLayout.NORTH);
        tableArea.add(tableScroll, BorderLayout.CENTER);

        body.add(formArea, BorderLayout.NORTH);
        body.add(tableArea, BorderLayout.CENTER);

        panel.add(header, BorderLayout.NORTH);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private void addFormRow(
            JPanel panel, GridBagConstraints gbc,
            int row, String label, JTextField field) {

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0;

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void addBook() {
        String id = idField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            showMessage("Please fill in all book fields.");
            return;
        }

        Book book = new Book(id, title, author);

        if (!manager.addBook(book)) {
            showMessage("This Book ID already exists.");
            return;
        }

        refreshAll();
        clearFields();
        showMessage("Book added successfully.");
    }

    private void updateBook() {
        String id = idField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            showMessage("Select a book and fill in all fields.");
            return;
        }

        if (!manager.updateBook(id, title, author)) {
            showMessage("Book not found or information is invalid.");
            return;
        }

        refreshAll();
        clearFields();
        showMessage("Book updated successfully.");
    }

    private void deleteBook() {
        String id = idField.getText().trim();

        if (id.isEmpty()) {
            showMessage("Enter or select a Book ID first.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this book?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (!manager.deleteBook(id)) {
            showMessage("Book not found.");
            return;
        }

        refreshAll();
        clearFields();
        showMessage("Book deleted successfully.");
    }

    private void searchBooks() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            refreshBookTable(manager.getBooks());
            return;
        }

        List<Book> results = manager.searchBooks(keyword);
        refreshBookTable(results);

        if (results.isEmpty()) {
            showMessage("No matching books found.");
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");

        if (booksTable != null) {
            booksTable.clearSelection();
        }

        idField.requestFocusInWindow();
    }

    // BORROWING PAGE

    private JPanel createBorrowingPanel() {
        JPanel panel = createPagePanel();

        JPanel header = createHeader(
                "Borrowing Management",
                "Issue books and record returned books"
        );

        borrowingModel = createTableModel();
        borrowingTable = new JTable(borrowingModel);
        styleTable(borrowingTable);

        JButton issueButton = createButton("Issue Book", green);
        JButton returnButton = createButton(
                "Return Book", new Color(67, 111, 146));
        JButton refreshButton = createButton("Refresh", Color.GRAY);

        issueButton.addActionListener(e -> changeBorrowingStatus(true));
        returnButton.addActionListener(e -> changeBorrowingStatus(false));
        refreshButton.addActionListener(e -> refreshBorrowingTable());

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttons.setOpaque(false);
        buttons.add(issueButton);
        buttons.add(returnButton);
        buttons.add(refreshButton);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setOpaque(false);
        center.add(new JLabel(
                        "Select a book from the table before choosing an action."),
                BorderLayout.NORTH);
        center.add(new JScrollPane(borrowingTable), BorderLayout.CENTER);
        center.add(buttons, BorderLayout.SOUTH);

        panel.add(header, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private void changeBorrowingStatus(boolean issue) {
        int row = borrowingTable.getSelectedRow();

        if (row < 0) {
            showMessage("Please select a book from the table first.");
            return;
        }

        String id = borrowingModel.getValueAt(row, 0).toString();
        boolean success;

        if (issue) {
            success = manager.issueBook(id);
        } else {
            success = manager.returnBook(id);
        }

        if (!success) {
            showMessage(issue
                    ? "Book not found or already borrowed."
                    : "Book not found or it is not borrowed.");
            return;
        }

        refreshAll();

        showMessage(issue
                ? "Book issued successfully."
                : "Book returned successfully.");
    }

    // SHARED TABLE METHODS

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new String[]{"Book ID", "Title", "Author", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void refreshBookTable(List<Book> books) {
        if (booksModel == null) {
            return;
        }

        booksModel.setRowCount(0);

        for (Book book : books) {
            booksModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getStatus()
            });
        }
    }

    private void refreshBorrowingTable() {
        if (borrowingModel == null) {
            return;
        }

        borrowingModel.setRowCount(0);

        for (Book book : manager.getBooks()) {
            borrowingModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getStatus()
            });
        }
    }

    private void refreshAll() {
        refreshBookTable(manager.getBooks());
        refreshBorrowingTable();
        refreshDashboard();
    }

    // GENERAL UI METHODS

    private JPanel createPagePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(background);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        return panel;
    }

    private JPanel createHeader(String title, String subtitle) {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(background);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
        titleLabel.setForeground(textColor);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);

        header.add(titleLabel);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitleLabel);

        return header;
    }

    private JButton createButton(String label, Color color) {
        JButton button = new JButton(label);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(9, 14, 9, 14));
        return button;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(218, 234, 223));
        table.setSelectionForeground(textColor);
        table.setGridColor(new Color(230, 234, 230));
        table.setFillsViewportHeight(true);

        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(
                new Color(232, 238, 233));
        table.getTableHeader().setForeground(textColor);

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i)
                    .setCellRenderer(renderer);
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
