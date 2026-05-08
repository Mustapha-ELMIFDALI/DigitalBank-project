# DigitalBank

Application complète de gestion bancaire digitale — backend Spring Boot + frontend Angular.

**Auteur :** Mustapha ELMIFDALI  
**GitHub :** [Mustapha-ELMIFDALI](https://github.com/Mustapha-ELMIFDALI)

---

## Structure du projet

```
DigitalBank-project/
├── backend/    # Spring Boot REST API (Java 25, MySQL)
└── frontend/   # Angular 21 SPA (Bootstrap 5, Chart.js)
```

---

## Backend

| Technologie | Version |
|---|---|
| Java | 25 |
| Spring Boot | 4.0.6 |
| Spring Data JPA | — |
| MySQL | 8.x |
| Lombok | — |
| SpringDoc OpenAPI | 3.x |

**Lancement :**
```bash
cd backend
./mvnw spring-boot:run
```
API disponible sur `http://localhost:8085`  
Swagger UI : `http://localhost:8085/swagger-ui.html`

---

## Frontend

| Technologie | Version |
|---|---|
| Angular | 21 |
| Bootstrap | 5.3 |
| Chart.js | 4.x |
| TypeScript | 5.x |

**Lancement :**
```bash
cd frontend
npm install
ng serve
```
Application disponible sur `http://localhost:4200`

---

## Fonctionnalités

- **Dashboard** — statistiques globales, graphiques analytiques (doughnut, bar, line)
- **Clients** — liste, création, modification, suppression
- **Comptes bancaires** — comptes courants & épargne, filtres, statuts
- **Opérations** — débit, crédit, virement avec historique paginé + graphiques
