package gestion_cahiers_textes.pages.enseignant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class VisualiserCour extends JFrame {
    private JTable coursTable;
    private DefaultTableModel tableModel;
    private int currentPage = 1;
    private final int maxRowsPerPage = 10;
    private int totalRows = 0;

    public VisualiserCour() {
        setTitle("Visualiser les cours");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER
        add(createHeader(), BorderLayout.NORTH);

        // TABLEAU
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom du Cours", "Date de Séance", "Heure Début", "Heure Fin", "ID Classe"}, 0);
        coursTable = new JTable(tableModel);
        coursTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Rend le tableau responsive
        JScrollPane scrollPane = new JScrollPane(coursTable);
        add(scrollPane, BorderLayout.CENTER);

        // PAGINATION
        JPanel paginationPanel = new JPanel();
        JButton prevButton = new JButton("Précédent");
        JButton nextButton = new JButton("Suivant");

        prevButton.addActionListener(e -> afficherPage(currentPage - 1));
        nextButton.addActionListener(e -> afficherPage(currentPage + 1));

        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);

        add(paginationPanel, BorderLayout.SOUTH);

        // Calcul du total de lignes
        compterTotalCours();

        // Première page
        afficherPage(currentPage);

        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(1000, 80));
        header.setBackground(new Color(45, 85, 145));

        header.add(createLogoPanel(), BorderLayout.WEST);
        header.add(createNavPanel(), BorderLayout.CENTER);
        header.add(createBurgerMenu(), BorderLayout.EAST);

        return header;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false);
        JLabel logoLabel = new JLabel(new ImageIcon("src/gestion_cahiers_textes/assets/logout.png"));
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        return logoPanel;
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 25));
        navPanel.setOpaque(false);
        String[] navItems = {"Accueil", "Liste des enseignants", "Voir les cours", "Se déconnecter"};

        for (String item : navItems) {
            navPanel.add(createNavButton(item));
        }
        return navPanel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(45, 85, 145));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            if (text.equals("Se déconnecter")) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir vous déconnecter ?", "Déconnexion", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                }
            } else if (text.equals("Accueil")) {
                // Aller à l'accueil
                new EnseignantPage().setVisible(true);
                dispose();
            } else if (text.equals("Voir les cours")) {
                // Rafraîchir
                new VisualiserCour().setVisible(true);
                dispose();
            } else if (text.equals("Liste des enseignants")) {
                // Afficher liste (à décommenter selon ta classe existante)
                // new ListeEnseignantsPage().setVisible(true);
                dispose();
            }
        });

        return button;
    }

    private JPanel createBurgerMenu() {
        JPanel burgerMenu = new JPanel(new BorderLayout());
        burgerMenu.setOpaque(false);
        JButton burgerButton = createStyledButton("☰", new Color(74, 125, 180), 24);
        burgerMenu.add(burgerButton, BorderLayout.CENTER);
        return burgerMenu;
    }

    private JButton createStyledButton(String text, Color bgColor, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }

    private void afficherPage(int page) {
        if (page < 1) {
            return;
        }

        int maxPage = (int) Math.ceil((double) totalRows / maxRowsPerPage);
        if (page > maxPage) {
            return;
        }

        currentPage = page;
        tableModel.setRowCount(0); // Réinitialiser le tableau

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "")) {
            String query = "SELECT ID_SEANCE, NOM_COUR, DATE_SEANCE, HEURE_DEBUT, HEURE_FIN, ID_CLASSE FROM seance LIMIT ?, ?";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                int startRow = (currentPage - 1) * maxRowsPerPage;
                pst.setInt(1, startRow);
                pst.setInt(2, maxRowsPerPage);

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Vector<String> row = new Vector<>();
                    row.add(rs.getString("ID_SEANCE"));
                    row.add(rs.getString("NOM_COUR"));
                    row.add(rs.getString("DATE_SEANCE"));
                    row.add(rs.getString("HEURE_DEBUT"));
                    row.add(rs.getString("HEURE_FIN"));
                    row.add(rs.getString("ID_CLASSE"));
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données.");
        }
    }

    private void compterTotalCours() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "")) {
            String query = "SELECT COUNT(*) AS total FROM seance";
            try (Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    totalRows = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            totalRows = 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VisualiserCour::new);
    }
}
