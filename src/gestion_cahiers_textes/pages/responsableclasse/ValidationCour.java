package gestion_cahiers_textes.pages.responsableclasse;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ValidationCour extends JFrame {
    private JTable table;
    private JButton btnValider, btnSuivant, btnPrecedent;
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private Connection connection;
    private List<Object[]> allData = new ArrayList<>();
    private int currentPage = 0;
    private final int rowsPerPage = 10;

    public ValidationCour() {
        setTitle("Validation des Cours");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER
        add(createHeader(), BorderLayout.NORTH);

        // CONTENU PRINCIPAL
        add(createContent(), BorderLayout.CENTER);

        // Connexion BDD
        connectDatabase();

        // Charger données
        chargerSeances();
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        headerPanel.setBackground(new Color(230, 230, 250));

        // Logo
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/gestion_cahiers_textes/assets/logout.png"));
            logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(150, 60, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            logoLabel.setText("Logo");
        }
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Menu
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        navPanel.setOpaque(false);
        navPanel.add(createNavButton("Accueil", "accueil"));
        navPanel.add(createNavButton("Emplois du temps", "emplois du temps"));
        navPanel.add(createNavButton("Liste de la classe", "liste de la classe"));
        navPanel.add(createNavButton("Voir les cours", "voir les cours"));
        navPanel.add(createNavButton("Se déconnecter", "logout"));
        headerPanel.add(navPanel, BorderLayout.CENTER);

        // Burger Menu
        JButton btnBurger = new JButton("☰");
        btnBurger.setFont(new Font("Arial", Font.BOLD, 20));
        btnBurger.setBackground(new Color(74, 125, 180));
        btnBurger.setForeground(Color.WHITE);
        btnBurger.setFocusPainted(false);
        headerPanel.add(btnBurger, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Search field
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterData(); }
            public void removeUpdate(DocumentEvent e) { filterData(); }
            public void changedUpdate(DocumentEvent e) { filterData(); }
        });
        topPanel.add(new JLabel("Recherche : "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID Séance", "Nom du Cours", "Date Séance", "Heure Début", "Heure Fin", "Cours validé"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 5) ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPrecedent = new JButton("Précédent");
        btnSuivant = new JButton("Suivant");
        btnValider = new JButton("Enregistrer la Validation");

        btnPrecedent.addActionListener(e -> { if (currentPage > 0) { currentPage--; afficherPage(); } });
        btnSuivant.addActionListener(e -> { if ((currentPage + 1) * rowsPerPage < allData.size()) { currentPage++; afficherPage(); } });
        btnValider.addActionListener(e -> validerSeances());

        bottomPanel.add(btnPrecedent);
        bottomPanel.add(btnSuivant);
        bottomPanel.add(btnValider);

        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void chargerSeances() {
        allData.clear();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM seance")) {
            while (rs.next()) {
                allData.add(new Object[]{
                    rs.getInt("ID_SEANCE"),
                    rs.getString("NOM_COUR"),
                    rs.getString("DATE_SEANCE"),
                    rs.getString("HEURE_DEBUT"),
                    rs.getString("HEURE_FIN"),
                    rs.getInt("VALIDE") == 1
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        currentPage = 0;
        afficherPage();
    }

    private void afficherPage() {
        tableModel.setRowCount(0);
        int start = currentPage * rowsPerPage;
        int end = Math.min(start + rowsPerPage, allData.size());
        for (int i = start; i < end; i++) {
            tableModel.addRow(allData.get(i));
        }
    }

    private void filterData() {
        String searchText = searchField.getText().toLowerCase();
        List<Object[]> filtered = new ArrayList<>();
        for (Object[] row : allData) {
            if (row[1].toString().toLowerCase().contains(searchText)) {
                filtered.add(row);
            }
        }
        tableModel.setRowCount(0);
        for (Object[] row : filtered) {
            tableModel.addRow(row);
        }
    }

    private void validerSeances() {
        int rowCount = table.getRowCount();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE seance SET VALIDE = ?, VALIDE_PAR = ?, DATE_VALIDATION = NOW() WHERE ID_SEANCE = ?"
            );
            for (int row = 0; row < rowCount; row++) {
                stmt.setInt(1, (boolean) table.getValueAt(row, 5) ? 1 : 0);
                stmt.setInt(2, 1); // ID responsable (à rendre dynamique plus tard)
                stmt.setInt(3, (int) table.getValueAt(row, 0));
                stmt.addBatch();
            }
            stmt.executeBatch();
            connection.commit();
            JOptionPane.showMessageDialog(this, "Mises à jour enregistrées avec succès !");
            chargerSeances();
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    private JButton createNavButton(String text, String link) {
        JButton button = new JButton(text);
        button.setBackground(new Color(74, 125, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            if (link.equals("accueil")) {
                 new ResponsableClassePage().setVisible(true);
                 dispose();
            } else if (link.equals("logout")) {
                if (JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir vous déconnecter ?", "Déconnexion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    dispose();
                }
            } else {
                System.out.println("Navigation vers: " + text);
            }
             if (link.equals("emplois du temps")) {
                 new EmploisDuTemps().setVisible(true);
                 dispose();
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ValidationCour().setVisible(true));
    }
}
