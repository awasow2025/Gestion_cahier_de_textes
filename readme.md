# Développement d'une application de gestion numérique du cahier de texte

## 1. Université Iba Der Thiam de Thiès
### UFR SET Département Informatique
### Licence 2 Informatique - Programmation Orientée Objet Java

### A. Intitulé du projet
Développement d'une application de gestion numérique du cahier de texte avec génération d'une fiche pédagogique.

### B. Contexte et justification
Le cahier de texte est un outil essentiel dans le suivi des activités pédagogiques, la planification des cours et la communication entre enseignants et administration. L'objectif est de dématérialiser ce cahier sous forme d'une application accessible à trois profils d'utilisateurs.

### C. Objectifs du projet
- Offrir au chef de département une vue globale sur les contenus.
- Faciliter au responsable de classe la consultation et la validation des tâches planifiées.
- Permettre aux enseignants une génération rapide de la fiche de suivi pédagogique.

### D. Utilisateurs et rôles

| Utilisateurs          | Rôles et fonctionnalités                                             |
|-----------------------|--------------------------------------------------------------------|
| **Chef de département** | Ajouter des enseignants et des responsables de classe. Assigner un/des cours à un enseignant. Générer une fiche de suivi pédagogique. |
| **Enseignant**          | Voir la liste de ses cours. Ajouter une séance et ajouter les informations relatives à la séance (contenu). |
| **Responsable de classe** | Consulter le cahier de texte. Valider le contenu ajouté par l'enseignant. |

### E. Technologies proposées
- **Langage** : Java (Swing ou JavaFX pour l'interface)
- **Base de données** : MySQL ou SQLite
- **IDE** : IntelliJ IDEA, Eclipse ou VSCODE
- **Gestion de projet** : GitHub

### F. Fonctionnalités communes
- **Authentification par rôle.**
- **Gestion des utilisateurs et attribution des droits.**
- **Interface pour la saisie et la consultation du cahier de texte.**
- **Visualisation des séances.**
- **Export des fiches de suivi (PDF ou Excel).**

### G. Planning prévisionnel
Le projet est à présenter le **vendredi 18 Avril à 08h 00mn**.

### H. Livrables attendus
- Application fonctionnelle exécutable.
- Fichier `readme.md`.

### I. Composition des équipes
Le projet se fait par équipe de deux ou en individuel.

## 2. Dossier `images`

Les images utilisées dans ce projet sont stockées dans le dossier `images`. Voici les images à insérer :

1. **Vue globale de l'application**  
   ![Vue globale de l'application](images/vue_globale.png)

2. **Interface utilisateur - Chef de département**  
   ![Interface utilisateur - Chef de département](images/chef_departement.png)

3. **Interface utilisateur - Enseignant**  
   ![Interface utilisateur - Enseignant](images/enseignant.png)

4. **Interface utilisateur - Responsable de classe**  
   ![Interface utilisateur - Responsable de classe](images/responsable_classe.png)

---

### Installation
1. Clonez le repository sur votre machine locale :
    ```bash
    git clone https://github.com/awasow2025/Gestion_cahier_de_textes
    ```

2. Allez dans le répertoire du projet :
    ```bash
    cd Gestion_cahier_de_textes
    ```

3. Ouvrez le projet dans votre IDE préféré (IntelliJ IDEA, Eclipse, etc.).

4. Configurez la base de données et l'application selon les instructions ci-dessous.

---

### Configuration de la base de données
- Créez une base de données MySQL ou SQLite.
- Importez le fichier SQL fourni dans le dossier `gestion_cahiers_textes.sql` pour configurer les tables nécessaires.

### Lancer l'application
Pour lancer l'application, ouvrez le fichier `Gestion_Cahiers_Textes.java` et exécutez-le dans votre IDE. Vous pouvez également exécuter le projet via la ligne de commande.

---

### Auteurs
- **Awa Sow** - Développeur Principal
- **Issa Ba** - Collaborateur
