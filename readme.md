# Gestion de Cahier de texte



## Diagramme de cas d'utilisation 
### Cas d'Utilisation : Gestion numérique du cahier de texte avec génération de fiches pédagogiques
## Acteurs :
- Chef de département

- Enseignant

- Responsable de classe

## Cas d'utilisation :
1. **Chef de département**

**Description** : Le chef de département gère les utilisateurs et supervise l'attribution des cours.

- Ajouter des enseignants et des responsables de classe

   - Pré-condition : Le chef de département doit être authentifié.

   - Action : Le chef de département peut ajouter de nouveaux enseignants et responsables de classe dans le système.

   - Post-condition : Les utilisateurs ajoutés sont désormais disponibles dans le système.

- Assigner un/des cours à un enseignant

   - Pré-condition : L'enseignant doit être enregistré dans le système.

   - Action : Le chef de département assigne des cours spécifiques à chaque enseignant.

   - Post-condition : Les enseignants ont les cours qui leur sont affectés.

- Générer une fiche de suivi pédagogique

    - Pré-condition : Le chef de département doit avoir une vue d'ensemble des cours enseignés.

    - Action : Le chef de département génère une fiche de suivi pédagogique en fonction des informations de cours et de leurs progressions.

    - Post-condition : Une fiche de suivi est générée et disponible en format PDF ou Excel.

2. **Enseignant**
**Description** : L'enseignant est responsable de la saisie des informations liées aux cours et de la génération des fiches pédagogiques.

- Voir la liste de ses cours

     - Pré-condition : L'enseignant doit être authentifié dans le système.

     - Action : L'enseignant consulte la liste de ses cours assignés, qui est affichée dans l'application.

    - Post-condition : L'enseignant voit ses cours dans une interface conviviale.

- Ajouter une séance

    - Pré-condition : L'enseignant doit être authentifié et avoir accès aux cours qui lui sont assignés.

    - Action : L'enseignant ajoute une nouvelle séance avec les informations nécessaires (date, contenu, etc.).

    - Post-condition : La séance est enregistrée dans le système.

- Ajouter des informations relatives à la séance

    - Pré-condition : L'enseignant doit être dans la section de la séance qu'il souhaite modifier.

    - Action : L'enseignant ajoute des détails supplémentaires pour chaque séance, comme le contenu, les objectifs, etc.

    - Post-condition : Les informations sont enregistrées et accessibles pour consultation.

3. **Responsable de classe**
**Description** : Le responsable de classe est chargé de superviser le contenu ajouté par l'enseignant et de valider les informations.

- Consulter le cahier de texte

    - Pré-condition : Le responsable de classe doit être authentifié dans le système.

    - Action : Le responsable de classe accède à la liste des tâches et séances planifiées pour chaque classe sous sa responsabilité.

    - Post-condition : Le responsable de classe a accès aux informations complètes sur le cahier de texte de la classe.

- Valider le contenu ajouté par l'enseignant

     - Pré-condition : Le responsable de classe doit avoir accès aux séances ajoutées par les enseignants.

     - Action : Le responsable de classe examine les informations ajoutées par l'enseignant et valide ou demande des modifications.

    - Post-condition : Le contenu est validé ou modifié selon les retours du responsable de classe.

## Fonctionnalités communes :
**Authentification par rôle** : L'application vérifie le rôle de l'utilisateur et lui permet d'accéder aux fonctionnalités spécifiques à son rôle.

**Gestion des utilisateurs et attribution des droits** : Les administrateurs peuvent gérer les utilisateurs et attribuer des rôles (chef de département, enseignant, responsable de classe).

**Interface pour la saisie et la consultation du cahier de texte** : L'application permet la saisie de contenu pédagogique et la consultation des informations planifiées.

**Visualisation des séances** : Les utilisateurs peuvent consulter les séances programmées, leurs contenus et leurs objectifs.

**Export des fiches de suivi** : Les fiches pédagogiques peuvent être exportées sous différents formats, comme PDF ou Excel, pour un suivi externe.

**Scénarios d'usage** :
1. **Chef de département** se connecte et ajoute un **enseignant**.

2. **Enseignant** se connecte, voit ses cours, ajoute une nouvelle séance et renseigne les détails de la séance.

3. **Responsable de classe** se connecte, consulte le cahier de texte et valide les séances ajoutées par l'enseignant.

4. **Chef de département** génère une fiche de suivi pédagogique pour l'ensemble des cours.

## Diagramme de cas d'utilisation (Description textuelle) :
- Acteur : Chef de département, Enseignant, Responsable de classe

    - Cas d'utilisation :

       - Ajouter des utilisateurs

       - Assigner des cours

       - Générer des fiches de suivi

       - Voir la liste de ses cours

       - Ajouter des séances et des informations

       - Valider les séances

       - Consulter le cahier de texte

       - Authentification
## Architecture du projet 
```
gestion-cahier-texte/
├── docs/
│   ├── README.md                  # Documentation du projet
│   ├── UML_Diagram.md             # Diagrammes UML et architecture du système
├── src/                           # Code source de l'application
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── projet/
│   │   │   │   │   ├── controller/ # Contrôleurs de l'application
│   │   │   │   │   ├── model/      # Modèles de données
│   │   │   │   │   ├── view/       # Interfaces utilisateur (JavaFX ou Swing)
├── lib/                            # Librairies et dépendances tierces
├── resources/                      # Fichiers de configuration (par ex., fichiers XML, JSON)
├── tests/                          # Tests unitaires
│   ├── test/                       # Tests d'unités et d'intégration
│   │   ├── TestController.java
├── pom.xml                         # Fichier de configuration Maven
├── .gitignore                      # Fichier .gitignore
```
@