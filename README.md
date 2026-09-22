# Eigenfaces — PCA-Based Facial Recognition

Project developed for the **Laboratory and Project I (LAPR1)** course at **Instituto Superior de Engenharia do Porto (ISEP)**, during the 1st year of the Bachelor's degree in Computer Engineering.

The system implements the **Eigenfaces** algorithm, a classic facial recognition technique based on **Principal Component Analysis (PCA)**, built entirely in Java without any computer vision libraries — only linear algebra.

---

## Features

The program offers four core features, accessible via an interactive menu or through command-line arguments (non-interactive mode):

**Feature 1 — Spectral Decomposition of Matrices**
Takes a symmetric matrix in CSV format, performs eigenvalue decomposition (eigenvectors/eigenvalues), reconstructs the matrix using *k* eigenvectors, and computes the **Mean Absolute Error (MAE)** between the original and reconstructed matrix.

**Feature 2 — Image Reconstruction via Eigenfaces**
Given a database of face images in CSV format, computes the eigenfaces of the dataset, projects each image onto the eigenface space using *k* components, and reconstructs the images, saving the results as `.jpg` files.

**Feature 3 — Facial Identification**
Given a new face image (in CSV format), compares it against all images in the database using **Euclidean distance** in the eigenface space and identifies the closest match.

**Feature 4 — Synthetic Image Generation**
Generates a new synthetic face image by combining the dataset's eigenfaces with random weights within the statistical bounds defined by the eigenvalues, producing plausible but non-existent faces.

---

## Technologies

- **Java** (core logic and manual linear algebra)
- **Apache Commons Math 3** (eigenvalue decomposition via `EigenDecomposition`)
- **Java AWT / ImageIO** (image creation and JPG export)
- **CSV** format for all inputs and outputs

---

## Project Structure

```
├── bin/
│   ├── Menu.java               # Main interface and program flow
│   ├── Funcionalidade1.java    # Spectral decomposition
│   ├── Funcionalidade2.java    # Image reconstruction
│   ├── Funcionalidade3.java    # Facial identification
│   └── Funcionalidade4.java    # Synthetic image generation
├── Inputs/                     # Sample images in CSV (2x2, 8x8, 64x64)
├── eigenfaces/                 # Generated eigenfaces in CSV
├── ImagensReconstruidas/       # Reconstructed images in JPG
└── Testes/                     # Unit test data
```

---

## How to Run

**Interactive mode:**
```bash
java -jar teste.jar
```

**Non-interactive mode (command-line arguments):**
```bash
# Feature 1
java -jar teste.jar 1 <matrix_path.csv> <k>

# Feature 2
java -jar teste.jar 2 <image_database_path/> <k>

# Feature 3
java -jar teste.jar 3 <image_database_path/> <k> <new_image_path.csv>

# Feature 4
java -jar teste.jar 4 <image_database_path/> <k>
```

> Use `-1` for the `k` parameter to use the maximum number of available eigenfaces.

---

## Academic Context

| | |
|:---:|:---:|
| **Course** | Laboratory and Project I (LAPR1) |
| **Institution** | ISEP — Instituto Superior de Engenharia do Porto |
| **Degree** | B.Sc. in Computer Engineering |
| **Academic Year** | 2024/2025 |
| **Team** | Henri Fagundes, Miguel Ribeiro, Rodrigo Guimaraes, Iva |

