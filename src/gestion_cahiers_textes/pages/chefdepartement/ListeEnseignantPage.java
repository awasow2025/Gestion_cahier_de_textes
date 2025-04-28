package gestion_cahiers_textes.pages.chefdepartement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListeEnseignantPage extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private JButton btnPrecedent, btnSuivant;
    private JLabel lblPageInfo;
    private List<Enseignant> enseignants = new ArrayList<>();
    private int pageSize = 10;
    private int currentPage = 1;
    private int totalPages = 1;

    public ListeEnseignantPage() {
        setTitle("Liste des Enseignants");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createHeader(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createPaginationPanel(), BorderLayout.SOUTH);

        loadEnseignants();
        updateTable();

        // Redimensionnement dynamique
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustTableColumns();
            }
        });
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(new Color(33, 97, 140));

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/gestion_cahiers_textes/assets/logout.png"))
                .getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH)));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
        navPanel.setOpaque(false);

        String[] navItems = { "Accueil", "Ajouter Utilisateur", "Liste Responsable", "Voir Cours", "Déconnexion" };
        for (String item : navItems) {
            navPanel.add(createNavButton(item));
            navPanel.add(Box.createHorizontalStrut(10)); // Espacement flexible
        }

        headerPanel.add(navPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> handleNavigation(text));
        return button;
    }

    private void handleNavigation(String action) {
        if (action.equals("Déconnexion")) {
            int confirmed = JOptionPane.showConfirmDialog(
                    this, "Êtes-vous sûr de vouloir vous déconnecter ?", 
                    "Déconnexion", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                dispose();
            }
        } else if (action.equals("Accueil")) {
            new ChefDepartementPage().setVisible(true);
            dispose();
        } else if (action.equals("Ajouter Utilisateur")) {
            new EnregistrerUtilisateurPage().setVisible(true);
            dispose();
        }else if (action.equals("Liste Responsable")) {
            new ListeResponsableClasse().setVisible(true);
            dispose();
        }
        // autres navigations
    }

    private JPanel createTablePanel() {
        model = new DefaultTableModel(new String[] { "ID", "Nom", "Prénom", "Département", "Spécialité", "Grade" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(100, 100));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPaginationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        btnPrecedent = createPaginationButton("◀ Précédent");
        btnSuivant = createPaginationButton("Suivant ▶");
        lblPageInfo = new JLabel("Page 1/1", SwingConstants.CENTER);
        lblPageInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnPrecedent.addActionListener(e -> previousPage());
        btnSuivant.addActionListener(e -> nextPage());

        gbc.gridx = 0; panel.add(btnPrecedent, gbc);
        gbc.gridx = 1; panel.add(lblPageInfo, gbc);
        gbc.gridx = 2; panel.add(btnSuivant, gbc);

        return panel;
    }

    private JButton createPaginationButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return button;
    }

    private void loadEnseignants() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "")) {

            String query = """
                    SELECT e.ID_ENSEIGNANT, e.NOM, e.PRENOM, d.LIB_DEPARTEMENT,
                           s.LIB_SPECIALITE, g.LIB_GRADE
                    FROM enseignant e
                    JOIN departement d ON e.ID_DEPARTEMENT = d.ID_DEPARTEMENT
                    JOIN specialite s ON e.ID_SPECIALITE = s.ID_SPECIALITE
                    JOIN grade g ON e.ID_GRADE = g.ID_GRADE
                    """;

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                enseignants.clear();
                while (rs.next()) {
                    enseignants.add(new Enseignant(
                            rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6)
                    ));
                }
                totalPages = (int) Math.ceil((double) enseignants.size() / pageSize);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.\n" + e.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        model.setRowCount(0);
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, enseignants.size());

        for (int i = start; i < end; i++) {
            Enseignant e = enseignants.get(i);
            model.addRow(new Object[] { e.getId(), e.getNom(), e.getPrenom(), e.getDepartement(), e.getSpecialite(), e.getGrade() });
        }

        lblPageInfo.setText("Page " + currentPage + " / " + totalPages);
        btnPrecedent.setEnabled(currentPage > 1);
        btnSuivant.setEnabled(currentPage < totalPages);
        adjustTableColumns();
    }

    private void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updateTable();
        }
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    }

    private void adjustTableColumns() {
        TableColumnModel columnModel = table.getColumnModel();
        int totalWidth = scrollPane.getViewport().getWidth();
        int colCount = columnModel.getColumnCount();
        int colWidth = totalWidth / colCount;

        for (int i = 0; i < colCount; i++) {
            columnModel.getColumn(i).setPreferredWidth(colWidth);
        }
    }

    class Enseignant {
        private int id;
        private String nom, prenom, departement, specialite, grade;

        public Enseignant(int id, String nom, String prenom, String departement, String specialite, String grade) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.departement = departement;
            this.specialite = specialite;
            this.grade = grade;
        }

        public int getId() { return id; }
        public String getNom() { return nom; }
        public String getPrenom() { return prenom; }
        public String getDepartement() { return departement; }
        public String getSpecialite() { return specialite; }
        public String getGrade() { return grade; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListeEnseignantPage().setVisible(true));
    }
}
