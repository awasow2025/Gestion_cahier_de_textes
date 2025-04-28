package gestion_cahiers_textes.pages.enseignant;

import gestion_cahiers_textes.pages.enseignant.EnseignantPage;
import gestion_cahiers_textes.pages.enseignant.VisualiserCour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.util.Date;

public class EnregistrerCourPage extends JFrame {
    // Composants
    private JComboBox<String> enseignantComboBox;
    private JComboBox<String> classeComboBox;
    private JSpinner dateSpinner, heureDebutSpinner, heureFinSpinner;
    private JTextField materielField;
    private JButton fichierButton, enregistrerButton;
    private File fichierSelectionne;

    public EnregistrerCourPage() {
        setTitle("Enregistrer un Cours");
        setMinimumSize(new Dimension(600, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer
        setLayout(new BorderLayout());

        // Ajouter le header
        add(createHeader(), BorderLayout.NORTH);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(new JScrollPane(mainPanel), BorderLayout.CENTER); // Ajout Scroll

        // Composants du formulaire
        enseignantComboBox = new JComboBox<>();
        chargerEnseignants();
        mainPanel.add(createLabeledComponent("Enseignant:", enseignantComboBox));

        classeComboBox = new JComboBox<>();
        chargerClasses();
        mainPanel.add(createLabeledComponent("Classe:", classeComboBox));

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        mainPanel.add(createLabeledComponent("Date (yyyy-MM-dd):", dateSpinner));

        heureDebutSpinner = new JSpinner(new SpinnerDateModel());
        heureDebutSpinner.setEditor(new JSpinner.DateEditor(heureDebutSpinner, "HH:mm:ss"));
        mainPanel.add(createLabeledComponent("Heure Début (HH:mm:ss):", heureDebutSpinner));

        heureFinSpinner = new JSpinner(new SpinnerDateModel());
        heureFinSpinner.setEditor(new JSpinner.DateEditor(heureFinSpinner, "HH:mm:ss"));
        mainPanel.add(createLabeledComponent("Heure Fin (HH:mm:ss):", heureFinSpinner));

        materielField = new JTextField();
        mainPanel.add(createLabeledComponent("Matériel:", materielField));

        fichierButton = new JButton("Choisir un fichier");
        fichierButton.addActionListener(e -> choisirFichier());
        mainPanel.add(createLabeledComponent("Fichier:", fichierButton));

        // Bouton Enregistrer
        enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.setPreferredSize(new Dimension(150, 40));
        enregistrerButton.addActionListener(e -> enregistrerSeance());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(enregistrerButton);
        mainPanel.add(buttonPanel);

        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(1200, 80));
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

        // Action pour le bouton de déconnexion
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Se déconnecter")) {
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
                new EnseignantPage().setVisible(true);
                dispose(); // Ferme la fenêtre actuelle si vous souhaitez
            }else if (text.equals("Voir les cours")) {
                // Ouvre la page ChefDepartementPage
                new VisualiserCour().setVisible(true);
                dispose(); // Ferme la fenêtre actuelle si vous souhaitez
            }else if (text.equals("Liste des enseignants")) {
                // Ouvre la page ChefDepartementPage
                //new ListeResponsableClasse().setVisible(true);
                dispose(); // Ferme la fenêtre actuelle si vous souhaitez
            }
                // Ajoutez ici d'autres actions pour les autres boutons si nécessaire
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

    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        return panel;
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

  

    private void choisirFichier() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            fichierSelectionne = fileChooser.getSelectedFile();
            fichierButton.setText(fichierSelectionne.getName());
        }
    }

    private void chargerEnseignants() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NOM FROM enseignant")) {
            while (rs.next()) {
                enseignantComboBox.addItem(rs.getString("NOM"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chargerClasses() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT LIB_CLASSE FROM classe")) {
            while (rs.next()) {
                classeComboBox.addItem(rs.getString("LIB_CLASSE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void enregistrerSeance() {
        String enseignantNom = (String) enseignantComboBox.getSelectedItem();
        String classeNom = (String) classeComboBox.getSelectedItem();
        Date selectedDate = (Date) dateSpinner.getValue();
        Date heureDebut = (Date) heureDebutSpinner.getValue();
        Date heureFin = (Date) heureFinSpinner.getValue();
        String materiel = materielField.getText();

        if (enseignantNom == null || classeNom == null || fichierSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs et choisir un fichier.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_cahiers_textes", "root", "")) {
            String sql = "INSERT INTO seance (ID_ENS, ID_CLASSE, DATE_SEANCE, HEURE_DEBUT, HEURE_FIN, NOM_COUR, MATERIEL, VALIDE, VALIDE_PAR) VALUES (?, ?, ?, ?, ?, ?, ?, 0, NULL)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, getIdEnseignant(enseignantNom, conn));
            pst.setInt(2, getIdClasse(classeNom, conn));
            pst.setDate(3, new java.sql.Date(selectedDate.getTime()));
            pst.setTime(4, new java.sql.Time(heureDebut.getTime()));
            pst.setTime(5, new java.sql.Time(heureFin.getTime()));
            pst.setString(6, fichierSelectionne.getName());
            pst.setString(7, materiel);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Séance enregistrée avec succès.");
            resetFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getIdEnseignant(String nom, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("SELECT ID_ENSEIGNANT FROM enseignant WHERE NOM = ?");
        pst.setString(1, nom);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt("ID_ENSEIGNANT");
        }
        throw new SQLException("Enseignant non trouvé");
    }

    private int getIdClasse(String libelle, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("SELECT ID_CLASSE FROM classe WHERE LIB_CLASSE = ?");
        pst.setString(1, libelle);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt("ID_CLASSE");
        }
        throw new SQLException("Classe non trouvée");
    }

    private void resetFields() {
        enseignantComboBox.setSelectedIndex(0);
        classeComboBox.setSelectedIndex(0);
        dateSpinner.setValue(new Date());
        heureDebutSpinner.setValue(new Date());
        heureFinSpinner.setValue(new Date());
        materielField.setText("");
        fichierButton.setText("Choisir un fichier");
    }
}
