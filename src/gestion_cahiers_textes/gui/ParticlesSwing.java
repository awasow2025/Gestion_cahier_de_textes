package gestion_cahiers_textes.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class ParticlesSwing extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PARTICLE_COUNT = 80;
    private static final int MAX_DISTANCE = 150;
    private ArrayList<Particle> particles;
    private Timer timer;

    public ParticlesSwing() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(222, 222, 222)); // Gris #DEDEDE
        particles = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            particles.add(new Particle(
                    rand.nextInt(WIDTH),
                    rand.nextInt(HEIGHT),
                    rand.nextDouble() * 2 - 1, // vitesse X entre -1 et 1
                    rand.nextDouble() * 2 - 1 // vitesse Y entre -1 et 1
            ));
        }

        timer = new Timer(16, this); // environ 60 FPS
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dessiner les lignes entre particules proches
        g2.setColor(Color.BLACK);
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                double distance = p1.distance(p2);
                if (distance < MAX_DISTANCE) {
                    float opacity = (float) (1.0 - distance / MAX_DISTANCE);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                    g2.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                }
            }
        }

        // Dessiner les particules
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        for (Particle p : particles) {
            g2.fillOval((int) p.x - 2, (int) p.y - 2, 5, 5);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Particle p : particles) {
            p.update(WIDTH, HEIGHT);
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particles.js en Java Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ParticlesSwing());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class Particle {
        double x, y;
        double vx, vy;

        public Particle(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public void update(int width, int height) {
            x += vx;
            y += vy;

            // Rebondir sur les bords
            if (x < 0 || x > width)
                vx = -vx;
            if (y < 0 || y > height)
                vy = -vy;
        }

        public double distance(Particle other) {
            return Math.hypot(this.x - other.x, this.y - other.y);
        }
    }
}
