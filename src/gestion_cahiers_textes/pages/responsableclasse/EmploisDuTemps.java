package gestion_cahiers_textes.pages.responsableclasse;

import gestion_cahiers_textes.pages.enseignant.EnseignantPage;
import gestion_cahiers_textes.pages.enseignant.VisualiserCour;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmploisDuTemps extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private Connection connection;
    private String currentSearch = "";

    private JPanel navPanel;
    private JButton burgerButton;
    private JPopupMenu burgerMenu;

    public EmploisDuTemps() {
        setTitle("Emplois du Temps");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);

        // Connexion BDD
        connectDatabase();

        // RECHERCHE
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // TABLEAU
        JScrollPane tablePane = createTablePanel();
        add(tablePane, BorderLayout.CENTER);

        // Charger les données
        chargerEmploi();
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(40, 90, 140));

        // Logo
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/gestion_cahiers_textes/assets/logout.png"));
            Image resized = logoIcon.getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(resized));
        } catch (Exception e) {
            logoLabel.setText("Logo");
            logoLabel.setForeground(Color.WHITE);
            logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        }
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // NAVIGATION PANEL
        navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        navPanel.setOpaque(false);
        String[] menuItems = { "Accueil", "Validation cours", "Liste de la classe", "Voir les cours", "Se déconnecter" };
        for (String item : menuItems) {
            navPanel.add(createNavButton(item));
        }
        headerPanel.add(navPanel, BorderLayout.CENTER);

        // BURGER MENU
        burgerButton = new JButton("☰");
        burgerButton.setFont(new Font("Arial", Font.BOLD, 24));
        burgerButton.setBackground(new Color(30, 70, 120));
        burgerButton.setForeground(Color.WHITE);
        burgerButton.setBorderPainted(false);
        burgerButton.setFocusPainted(false);
        burgerButton.addActionListener(e -> burgerMenu.show(burgerButton, 0, burgerButton.getHeight()));
        headerPanel.add(burgerButton, BorderLayout.EAST);

        burgerMenu = new JPopupMenu();
        for (String item : menuItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(e -> handleNavigation(item));
            burgerMenu.add(menuItem);
        }

        // Adaptation au redimensionnement
        headerPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (headerPanel.getWidth() < 800) {
                    navPanel.setVisible(false);
                    burgerButton.setVisible(true);
                } else {
                    navPanel.setVisible(true);
                    burgerButton.setVisible(false);
                }
            }
        });

        return headerPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        searchPanel.setBackground(new Color(230, 230, 250));

        JLabel searchLabel = new JLabel("Classe :");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        searchButton = new JButton("Rechercher");
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.addActionListener(e -> chargerEmploi());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { chargerEmploi(); }
            public void removeUpdate(DocumentEvent e) { chargerEmploi(); }
            public void changedUpdate(DocumentEvent e) { chargerEmploi(); }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private JScrollPane createTablePanel() {
        String[] columnNames = { "Classe", "UE", "Enseignant", "Date", "Heure Début", "Heure Fin", "Volume Horaire" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
        return new JScrollPane(table);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 70, 120));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        button.addActionListener(e -> handleNavigation(text));
        return button;
    }

    private void handleNavigation(String text) {
        switch (text) {
            case "Se déconnecter":
                int confirmed = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir vous déconnecter ?",
                        "Déconnexion", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION)
                    dispose();
                break;
            case "Accueil":
                new EnseignantPage().setVisible(true);
                dispose();
                break;
            case "Voir les cours":
                new VisualiserCour().setVisible(true);
                dispose();
                break;
            // Ajoute ici d'autres pages si nécessaire
        }
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void chargerEmploi() {
        try {
            tableModel.setRowCount(0);

            String baseQuery = "SELECT classe.LIB_CLASSE, ue.LIB_UE, CONCAT(enseignant.PRENOM, ' ', enseignant.NOM) AS ENSEIGNANT, enseigner.DATE_ENS, enseigner.DEBUT_ENS, enseigner.FIN_ENS, enseigner.VOL_ENS " +
                    "FROM enseigner " +
                    "JOIN enseignant ON enseigner.ID_ENSEIGNANT = enseignant.ID_ENSEIGNANT " +
                    "JOIN classe ON enseigner.ID_CLASSE = classe.ID_CLASSE " +
                    "JOIN ue ON enseigner.ID_UE = ue.ID_UE " +
                    "WHERE classe.LIB_CLASSE LIKE ? " +
                    "ORDER BY enseigner.DATE_ENS DESC";

            PreparedStatement stmt = connection.prepareStatement(baseQuery);
            stmt.setString(1, "%" + searchField.getText().trim() + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("LIB_CLASSE"),
                        rs.getString("LIB_UE"),
                        rs.getString("ENSEIGNANT"),
                        rs.getString("DATE_ENS"),
                        rs.getString("DEBUT_ENS"),
                        rs.getString("FIN_ENS"),
                        rs.getFloat("VOL_ENS")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmploisDuTemps().setVisible(true));
    }
}
