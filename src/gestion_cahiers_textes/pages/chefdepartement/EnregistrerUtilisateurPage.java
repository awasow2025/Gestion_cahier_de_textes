package gestion_cahiers_textes.pages.chefdepartement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class EnregistrerUtilisateurPage extends JFrame {

    // === Database Connection ===
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/gestion_cahiers_textes";
        String username = "root";
        String password = "";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, "Erreur : " + message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private ArrayList<String> fetchList(String query, String columnLabel) {
        ArrayList<String> list = new ArrayList<>();
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString(columnLabel));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement : " + e.getMessage());
        }
        return list;
    }

    // === Constructor ===
    public EnregistrerUtilisateurPage() {
        initUI();
    }

    private void initUI() {
        setTitle("Enregistrer un Utilisateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
    }

    // === UI Components ===

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
        String[] navItems = { "Accueil", "Liste Responsable", "Liste Enseignants", "Voir Cours", "Déconnexion" };
        for (String item : navItems) {
            navPanel.add(createNavButton(item));
        }
        headerPanel.add(navPanel, BorderLayout.CENTER);

        return headerPanel;
    }
    // Fonction de deconnexion

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(45, 85, 145));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Action pour le bouton de déconnexion
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Déconnexion")) {
                    int confirmed = JOptionPane.showConfirmDialog(
                            null,
                            "Êtes-vous sûr de vouloir vous déconnecter ?",
                            "Confirmation de déconnexion",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        dispose(); // Ferme la fenêtre actuelle
                        // Optionnel : Ouvrir la page de connexion
                        // new LoginPage().setVisible(true);
                    }
                }else if (text.equals("Accueil")) {
                // Ouvre la page ChefDepartementPage
                new ChefDepartementPage().setVisible(true);
                dispose(); // Ferme la fenêtre actuelle si vous souhaitez
            }else if (text.equals("Liste Enseignants")) {
                // Ouvre la page ChefDepartementPage
                new ListeEnseignantPage().setVisible(true);
                dispose(); // Ferme la fenêtre actuelle si vous souhaitez
            }else if (text.equals("Liste Responsable")) {
                // Ouvre la page ChefDepartementPage
                new ListeResponsableClasse().setVisible(true);
                dispose(); // Ferme la fenêtre actuelle si vous souhaitez
            }
                // Ajoutez ici d'autres actions pour les autres boutons si nécessaire
            }
        });

        return button;
    }

    // Fonction de Creation de formulaire
    private JPanel createForm() {
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // === Fields ===
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleComboBox = new JComboBox<>(
                new String[] { "Chef de département", "Enseignant", "Responsable de classe" });
        JComboBox<String> classeComboBox = new JComboBox<>();
        JComboBox<String> departementComboBox = new JComboBox<>();
        JComboBox<String> enseignantComboBox = new JComboBox<>();

        // Preload options
        fetchList("SELECT LIB_DEPARTEMENT FROM departement", "LIB_DEPARTEMENT").forEach(departementComboBox::addItem);
        fetchList("SELECT LIB_CLASSE FROM classe", "LIB_CLASSE").forEach(classeComboBox::addItem);

        // Labels
        formPanel.add(new JLabel("Nom d'utilisateur :"));
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Rôle :"));
        formPanel.add(roleComboBox);

        formPanel.add(new JLabel("Classe (si Responsable) :"));
        formPanel.add(classeComboBox);

        formPanel.add(new JLabel("Département (si Chef) :"));
        formPanel.add(departementComboBox);

        formPanel.add(new JLabel("Nom de l'Enseignant :"));
        formPanel.add(enseignantComboBox);

        // Initialement cacher
        classeComboBox.setVisible(false);
        departementComboBox.setVisible(false);
        enseignantComboBox.setVisible(false);

        // Role selection
        roleComboBox.addActionListener(e -> {
            String selected = (String) roleComboBox.getSelectedItem();
            classeComboBox.setVisible("Responsable de classe".equals(selected));
            departementComboBox.setVisible("Chef de département".equals(selected));
            enseignantComboBox.setVisible("Enseignant".equals(selected));

            if ("Enseignant".equals(selected) && enseignantComboBox.getItemCount() == 0) {
                fetchList("SELECT CONCAT(NOM, ' ', PRENOM) AS full_name FROM enseignant", "full_name")
                        .forEach(enseignantComboBox::addItem);
            }
            formPanel.revalidate();
            formPanel.repaint();
        });

        // === Submit Button ===
        JButton saveButton = new JButton("Enregistrer");
        styleButton(saveButton);
        saveButton.addActionListener(e -> saveUser(usernameField, passwordField, roleComboBox,
                classeComboBox, departementComboBox, enseignantComboBox));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.add(saveButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        formContainer.add(formPanel, gbc);

        gbc.gridy++;
        formContainer.add(buttonsPanel, gbc);

        return formContainer;
    }
    // Fonction de Deconnection

    private void styleButton(JButton button) {
        button.setBackground(new Color(45, 85, 145));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void saveUser(JTextField usernameField, JPasswordField passwordField, JComboBox<String> roleComboBox,
            JComboBox<String> classeComboBox, JComboBox<String> departementComboBox,
            JComboBox<String> enseignantComboBox) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        String classe = (String) classeComboBox.getSelectedItem();
        String departement = (String) departementComboBox.getSelectedItem();
        String enseignant = (String) enseignantComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Tous les champs doivent être remplis !");
            return;
        }

        try (Connection conn = getConnection()) {
            // 1. Vérifier si le username existe déjà
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM utilisateur WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                showError("Le nom d'utilisateur est déjà utilisé. Veuillez en choisir un autre !");
                return;
            }

            // 2. Insérer le nouvel utilisateur
            PreparedStatement stmt;
            if ("Enseignant".equals(role)) {
                stmt = conn.prepareStatement(
                        "INSERT INTO utilisateur (username, password, role, id_enseignant) VALUES (?, ?, ?, (SELECT ID_ENSEIGNANT FROM enseignant WHERE CONCAT(NOM, ' ', PRENOM) = ?))");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.setString(4, enseignant);
            } else if ("Chef de département".equals(role)) {
                stmt = conn.prepareStatement(
                        "INSERT INTO utilisateur (username, password, role, id_departement) VALUES (?, ?, ?, (SELECT ID_DEPARTEMENT FROM departement WHERE LIB_DEPARTEMENT = ?))");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.setString(4, departement);
            } else { // Responsable de classe
                stmt = conn.prepareStatement(
                        "INSERT INTO utilisateur (username, password, role, id_user) VALUES (?, ?, ?, (SELECT ID_CLASSE FROM classe WHERE LIB_CLASSE = ?))");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.setString(4, classe);
            }
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Utilisateur enregistré avec succès !");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }

    // === Main Method for Testing ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnregistrerUtilisateurPage().setVisible(true));
    }
}
