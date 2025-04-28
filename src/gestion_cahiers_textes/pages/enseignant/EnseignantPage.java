package gestion_cahiers_textes.pages.enseignant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EnseignantPage extends JFrame {

    public EnseignantPage() {
        setupFrame();
        add(createHeader());
        add(createLeftPanel());
        add(createRightPanel());
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Page d'accueil - Enseignant");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245)); // Fond clair global
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBounds(0, 0, 1200, 80);
        header.setBackground(new Color(45, 85, 145)); // Couleur du header

        header.add(createLogoPanel(), BorderLayout.WEST);
        header.add(createNavPanel(), BorderLayout.CENTER);
        header.add(createBurgerMenu(), BorderLayout.EAST);

        return header;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new BorderLayout());
        JLabel logoLabel = new JLabel(resizeIcon("/gestion_cahiers_textes/assets/logout.png", 150, 60));
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        return logoPanel;
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        navPanel.setOpaque(false);

        String[][] navItems = {
            {"Ajouter un cour", "ajouter un cour"},
            {"Mes cours", "mes cours"},
            {"Liste des enseignants", "liste des enseignants"},
            {"Voir les cours", "voir les cours"},
            {"Se déconnecter", "logout"}
        };

        for (String[] item : navItems) {
            navPanel.add(createNavButton(item[0], item[1]));
        }

        return navPanel;
    }

    private JPanel createBurgerMenu() {
        JPanel burgerMenu = new JPanel(new BorderLayout());
        JButton burgerButton = createStyledButton("☰", new Color(74, 125, 180), 20);
        burgerButton.setFocusPainted(true); // Mettre en surbrillance au survol
        burgerMenu.add(burgerButton, BorderLayout.CENTER);
        return burgerMenu;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel() {
            private final Image background = new ImageIcon(getClass().getResource("/gestion_cahiers_textes/assets/back2.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(0, 0, 0, 80)); // Opacité du fond
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        leftPanel.setBounds(0, 80, 720, 620);
        leftPanel.setLayout(null);

        JLabel welcomeLabel = new JLabel("<html><b>BIENVENUE<br> SUR VOTRE<br>PLATEFORME</b></html>");
        welcomeLabel.setFont(new Font("Inter", Font.BOLD, 38));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 30, 600, 200);
        leftPanel.add(welcomeLabel);

        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonGrid.setOpaque(false);
        buttonGrid.setBounds(50, 300, 600, 200);

        addGridButton(buttonGrid, "Ajouter un cour", e -> {
            new EnregistrerCourPage().setVisible(true);
            dispose();
        });
        addGridButton(buttonGrid, "Mes cours", e -> {
            new VisualiserCour().setVisible(true);
            dispose();
        });
        addGridButton(buttonGrid, "Voir les cours", null);
        addGridButton(buttonGrid, "Liste des enseignants", null);

        leftPanel.add(buttonGrid);
        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(new Color(240, 240, 240)); // Légèrement plus clair que la couleur de fond
        rightPanel.setBounds(720, 80, 480, 620);

        JLabel illustration = new JLabel(resizeIcon("/gestion_cahiers_textes/assets/teaching-animate.png", 440, 580));
        illustration.setBounds(20, 20, 440, 580);
        rightPanel.add(illustration);

        return rightPanel;
    }

    private void addGridButton(JPanel container, String text, ActionListener action) {
        JButton button = createStyledButton(text, new Color(74, 125, 180), 18);
        if (action != null) {
            button.addActionListener(action);
        }
        container.add(button);
    }

    private JButton createStyledButton(String text, Color bgColor, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setPreferredSize(new Dimension(150, 50)); // Ajustement des dimensions des boutons
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter()); // Changement de couleur au survol
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor); // Retour à la couleur originale
            }
        });
        return button;
    }

    private JButton createNavButton(String text, String link) {
        JButton button = createStyledButton(text, new Color(74, 125, 180), 14);

        button.addActionListener(e -> {
            if ("logout".equals(link)) {
                int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir vous déconnecter ?", "Déconnexion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    // new LoginPage().setVisible(true); // Décommenter si LoginPage existe
                }
            } else {
                System.out.println("Naviguer vers : " + link);
            }
            if ("mes cours".equals(link)) {
                 new VisualiserCour().setVisible(true);
                    dispose();
                    // new LoginPage().setVisible(true); // Décommenter si LoginPage existe
            }
            if ("ajouter un cour".equals(link)) {
                  new EnregistrerCourPage().setVisible(true);
            dispose();
                    // new LoginPage().setVisible(true); // Décommenter si LoginPage existe
            }
        });

        return button;
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnseignantPage::new);
    }
}
