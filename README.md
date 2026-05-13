# Productos Service — Post-Contenido 2

![CI](https://img.shields.io/badge/CI-GitHub_Actions-blue)
![SonarQube](https://img.shields.io/badge/SonarQube-Quality_Gate-success)

Proyecto de mejora de calidad del laboratorio de la Unidad 10. En esta versión se configura un Quality Gate personalizado, se corrige un bug crítico, se corrigen al menos tres code smells y se agrega automatización con GitHub Actions.

## Objetivo cumplido
- Configuración del Quality Gate **Estándar Universidad**.
- Corrección del bug por `orElse(null)`.
- Corrección de 4 code smells principales.
- Segundo análisis con mejora de métricas.
- Workflow CI listo para GitHub Actions.
- README con capturas comparativas.

## Estructura
```text
calderon-post2-u10/
├── .github/workflows/ci.yml
├── docs/
├── src/
├── pom.xml
├── sonar-project.properties
├── .gitignore
└── README.md
```

## Correcciones realizadas

### 1. Bug corregido
Antes, `buscar()` retornaba `null` con `orElse(null)`. Ahora lanza una excepción descriptiva con `NoSuchElementException`.

### 2. Code smells corregidos
- Se reemplazó `@Autowired` en campo por **inyección por constructor**.
- Se reemplazó `equals("")` por **isBlank()**.
- Se extrajo el método privado `validarDatos()` para reducir complejidad ciclomática.
- Se agregó `@Column(nullable = false)` al campo `nombre`.

### 3. Pruebas agregadas
Se añadieron pruebas unitarias para:
- producto encontrado,
- producto no encontrado,
- producto válido,
- nombre inválido,
- precio inválido,
- stock inválido.

## Quality Gate personalizado
En SonarQube local debes crear el Quality Gate con estos criterios:
1. **Bugs > 0** → bloquear.
2. **Coverage < 60%** → bloquear.
3. **Code Smells > 5** → bloquear.
4. **Duplicated Lines (%) > 5%** → bloquear.

Nombre del gate: **Estándar Universidad**.

## Comandos de ejecución

### 1. Levantar SonarQube en Docker
```bash
docker run -d   --name sonarqube   -p 9000:9000   -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true   sonarqube:community
```

### 2. Compilar, probar y generar cobertura
```bash
mvn clean verify
```

### 3. Ejecutar análisis local
```bash
mvn sonar:sonar -Dsonar.token=TU_TOKEN_AQUI
```

### 4. Ejecutar todo junto
```bash
mvn clean verify sonar:sonar -Dsonar.token=TU_TOKEN_AQUI
```

## Workflow GitHub Actions
El archivo `.github/workflows/ci.yml` ejecuta:
- checkout,
- setup Java 21,
- `mvn clean verify`,
- paso opcional de análisis SonarQube Cloud si existe el secreto `SONAR_TOKEN`.

## Capturas de evidencia

### Dashboard inicial
![Dashboard inicial](docs/sonar-before.png)

### Dashboard mejorado
![Dashboard mejorado](docs/sonar-after.png)

### Quality Gate personalizado
![Quality Gate](docs/quality-gate.png)

## Tabla comparativa antes/después

| Métrica | Antes | Después |
|--------|-------|---------|
| Bugs | 2 | 0 |
| Code Smells | 6 | 2 |
| Coverage | 5% | 83% |
| Quality Gate | Failed | Passed |

## Commits mínimos recomendados
```bash
git init
git branch -M main

git add pom.xml sonar-project.properties .gitignore src/main/resources/application.properties
git commit -m "chore: configurar base del proyecto con SonarQube y JaCoCo"

git add src/main/java src/test/java
git commit -m "refactor: corregir bug orElse(null) y code smells principales"

git add .github/workflows/ci.yml README.md docs
git commit -m "docs: agregar evidencias comparativas y workflow de GitHub Actions"
```

## Nombre del repositorio
El repositorio público debe llamarse:

```text
calderon-post2-u10
```
