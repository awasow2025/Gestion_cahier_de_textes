package gestion_cahiers_textes.pages.chefdepartement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ListeResponsableClasse {

    private int currentPage = 1;
    private final int itemsPerPage = 5;
    private List<ResponsableClasse> responsables = new ArrayList<>();
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JFrame frame;

    public void setVisible(boolean b) {
        frame = new JFrame("Liste des Responsables");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre

        contentPanel = new JPanel(new BorderLayout());

        // Ajouter l'en-tête
        contentPanel.add(createHeader(), BorderLayout.NORTH);

        // Initialiser le tableau
        String[] columns = { "ID Responsable", "Enseignant", "Classe", "Année académique" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter les boutons de pagination
        JPanel paginationPanel = new JPanel();
        JButton prevButton = new JButton("Précédent");
        JButton nextButton = new JButton("Suivant");

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadPage(currentPage);
            }
        });

        nextButton.addActionListener(e -> {
            if (currentPage * itemsPerPage < responsables.size()) {
                currentPage++;
                loadPage(currentPage);
            }
        });

        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);
        contentPanel.add(paginationPanel, BorderLayout.SOUTH);

        frame.add(contentPanel);
        frame.setVisible(b);

        // Chargement initial des données
        responsables = getListeResponsables();
        loadPage(currentPage);
    }

    private void loadPage(int page) {
        if (responsables == null || responsables.isEmpty()) return;

        int start = (page - 1) * itemsPerPage;
        int end = Math.min(page * itemsPerPage, responsables.size());
        List<ResponsableClasse> pageResponsables = responsables.subList(start, end);

        // Vider l'ancien contenu du tableau
        tableModel.setRowCount(0);

        for (ResponsableClasse responsable : pageResponsables) {
            tableModel.addRow(new Object[] {
                responsable.getIdResponsable(),
                responsable.getNomEnseignant() + " " + responsable.getPrenomEnseignant(),
                responsable.getLibClasse(),
                responsable.getAnneeAcademique()
            });
        }
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setBackground(new Color(45, 85, 145));

        // Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(
                new ImageIcon(new ImageIcon(getClass().getResource("/gestion_cahiers_textes/assets/logout.png"))
                        .getImage().getScaledInstance(150, 60, Image.SCALE_SMOOTH)));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Navigation Buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        navPanel.setOpaque(false);
        String[] navItems = { "Accueil", "Ajouter Utilisateur", "Liste Enseignants", "Voir Cours", "Déconnexion" };
        for (String item : navItems) {
            navPanel.add(createNavButton(item));
        }
        headerPanel.add(navPanel, BorderLayout.CENTER);

        return headerPanel;
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
            if (text.equals("Déconnexion")) {
                int confirmed = JOptionPane.showConfirmDialog(
                        frame,
                        "Êtes-vous sûr de vouloir vous déconnecter ?",
                        "Confirmation de déconnexion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    // new LoginPage().setVisible(true);
                }
            } else if (text.equals("Accueil")) {
                new ChefDepartementPage().setVisible(true);
                frame.dispose();
            } else if (text.equals("Liste Enseignants")) {
                new ListeEnseignantPage().setVisible(true);
                frame.dispose();
            }else if (text.equals("Ajouter Utilisateur")) {
                new EnregistrerUtilisateurPage().setVisible(true);
                frame.dispose();
            }
            // Ajoutez les autres navigations ici
        });

        return button;
    }

    public static List<ResponsableClasse> getListeResponsables() {
        List<ResponsableClasse> responsables = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String url = "jdbc:mysql://localhost:3306/gestion_cahiers_textes";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);

            String sql = "SELECT r.ID_RESPONSABLE, e.NOM, e.PRENOM, c.LIB_CLASSE, r.ANNEE_ACADEMIQUE " +
                    "FROM responsable_classe r " +
                    "JOIN enseignant e ON r.ID_ENSEIGNANT = e.ID_ENSEIGNANT " +
                    "JOIN classe c ON r.ID_CLASSE = c.ID_CLASSE " +
                    "ORDER BY r.ANNEE_ACADEMIQUE";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                responsables.add(new ResponsableClasse(
                        resultSet.getInt("ID_RESPONSABLE"),
                        resultSet.getString("NOM"),
                        resultSet.getString("PRENOM"),
                        resultSet.getString("LIB_CLASSE"),
                        resultSet.getString("ANNEE_ACADEMIQUE")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return responsables;
    }

    public static class ResponsableClasse {
        private int idResponsable;
        private String nomEnseignant;
        private String prenomEnseignant;
        private String libClasse;
        private String anneeAcademique;

        public ResponsableClasse(int idResponsable, String nomEnseignant, String prenomEnseignant, String libClasse, String anneeAcademique) {
            this.idResponsable = idResponsable;
            this.nomEnseignant = nomEnseignant;
            this.prenomEnseignant = prenomEnseignant;
            this.libClasse = libClasse;
            this.anneeAcademique = anneeAcademique;
        }

        public int getIdResponsable() { return idResponsable; }
        public String getNomEnseignant() { return nomEnseignant; }
        public String getPrenomEnseignant() { return prenomEnseignant; }
        public String getLibClasse() { return libClasse; }
        public String getAnneeAcademique() { return anneeAcademique; }
    }

    public static void main(String[] args) {
        ListeResponsableClasse app = new ListeResponsableClasse();
        app.setVisible(true);
    }
}
