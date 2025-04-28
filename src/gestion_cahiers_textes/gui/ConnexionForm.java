package gestion_cahiers_textes.gui;

import gestion_cahiers_textes.database.DatabaseConnection;
import gestion_cahiers_textes.pages.responsableclasse.ResponsableClassePage;
import gestion_cahiers_textes.pages.chefdepartement.ChefDepartementPage;
import gestion_cahiers_textes.pages.enseignant.EnseignantPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnexionForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public ConnexionForm() {
        setTitle("Page de Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        setLocationRelativeTo(null);

        // Utiliser ParticlesSwing comme arrière-plan
        ParticlesSwing backgroundPanel = new ParticlesSwing();
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        // Conteneur pour les champs de texte et le bouton
        JPanel container = createContainer();
        backgroundPanel.add(container);

        setVisible(true);
    }

    // Méthode pour créer le conteneur avec les champs et le bouton
    private JPanel createContainer() {
        JPanel container = new JPanel();
        container.setBackground(new Color(251, 249, 249, 180)); // Couleur semi-transparente pour voir les particules
        container.setPreferredSize(new Dimension(400, 400));
        container.setLayout(new GridLayout(6, 1, 10, 10));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        container.add(titleLabel);

        // Champs de texte pour l'utilisateur et le mot de passe
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Nom d'utilisateur"));
        container.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Mot de passe"));
        container.add(passwordField);

        // Panel pour les boutons
        JPanel buttonPanel = createButtonPanel();
        container.add(new JLabel()); // Espace vide
        container.add(buttonPanel);

        return container;
    }

    // Méthode pour créer le panel des boutons
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Important : pour garder la transparence du container
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton submitButton = new JButton("Connexion");
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        JButton resetButton = new JButton("Annuler");
        resetButton.setBackground(new Color(244, 67, 54));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        return buttonPanel;
    }

    // Méthode de connexion
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            String role = DatabaseConnection.checkCredentials(username, password);

            if (role != null) {
                JOptionPane.showMessageDialog(this, "Connexion réussie en tant que : " + role);

                // Redirection selon le rôle
                redirectBasedOnRole(role);

                // Fermer la fenêtre actuelle
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            showError("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    // Redirection en fonction du rôle de l'utilisateur
    private void redirectBasedOnRole(String role) {
        if (role.equalsIgnoreCase("Responsable de classe")) {
            new ResponsableClassePage().setVisible(true);
        } else if (role.equalsIgnoreCase("Chef de département")) {
            new ChefDepartementPage().setVisible(true);
        } else if (role.equalsIgnoreCase("Enseignant")) {
            new EnseignantPage().setVisible(true);
        } else {
            showError("Rôle inconnu.");
        }
    }

    // Méthode pour afficher les erreurs
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // Réinitialiser les champs
    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConnexionForm());
    }
}
