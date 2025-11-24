#  MovieApp - Evaluación Parcial 4

Proyecto de aplicación móvil para la gestión y visualización de películas, integrando consumo de API externa, persistencia local y autenticación mediante microservicios propios.

##  Integrantes
* **[NOMBRE INTEGRANTE 1]** Cristobal Reyes
* **[NOMBRE INTEGRANTE 2]** Benjamin Ruz
* **Asignatura:** Desarrollo de Aplicaciones Móviles 
* **Sección:** DSY1105

---

##  Funcionalidades del Proyecto

### 1. Autenticación (Backend Propio)
* **Registro de Usuarios:** Permite crear una cuenta nueva validando que el correo no exista previamente. Los datos se almacenan en una base de datos H2 en el microservicio.
* **Inicio de Sesión:** Validación de credenciales contra el backend. Almacena la sesión localmente usando `DataStore`.
* **Persistencia de Sesión:** La app recuerda al usuario logueado al cerrar y abrir la aplicación.

### 2. Gestión de Películas (API Externa - TMDB)
* **Home:** Visualización de listas de películas (Tendencias, Populares, Terror, Romance) obtenidas en tiempo real desde la API de The Movie Database (TMDB).
* **Detalle:** Visualización de información detallada de cada película.

### 3. Base de Datos Local (Room)
* **Favoritos:** Permite guardar películas en una base de datos local (SQLite/Room) para acceder a ellas sin conexión.
* **Notas Personales:** Posibilidad de editar y guardar una nota personal para cada película favorita (CRUD completo).

### 4. Perfil de Usuario (Hardware)
* **Información:** Muestra el nombre y correo del usuario logueado recuperados de la sesión.
* **Cámara y Galería:** Integración con la cámara nativa y galería para seleccionar o tomar una foto de perfil.
* **Cerrar Sesión:** Opción para desloguearse y limpiar los datos del dispositivo.

---

##  Endpoints Utilizados

###  API Externa (The Movie Database)
Base URL: `https://api.themoviedb.org/3/`
* `GET /movie/popular`: Obtener películas populares.
* `GET /movie/top_rated`: Obtener películas mejor valoradas.
* `GET /movie/{movie_id}`: Obtener detalles.

###  Microservicios Propios (Spring Boot)
Base URL (Emulador): `http://10.0.2.2:8080/`
* `POST /api/auth/register`: 
    * **Body:** `{ "nombre": "...", "email": "...", "password": "..." }`
    * **Función:** Crea un nuevo usuario en la base de datos H2.
* `POST /api/auth/login`:
    * **Body:** `{ "email": "...", "password": "..." }`
    * **Función:** Valida credenciales y retorna los datos del usuario.

---

##  Pasos para Ejecutar el Proyecto

Para que la aplicación funcione correctamente, se deben seguir estos pasos en orden estricto:

### 1. Ejecutar el Backend (Microservicio)
1.  Abrir la carpeta `movieappbackend` en **IntelliJ IDEA**.
2.  Esperar a que Maven descargue las dependencias.
3.  Ejecutar el archivo principal `MovieappbackendApplication.java`.
4.  Esperar a ver el mensaje: `Tomcat started on port 8080`.
    * *Nota: La base de datos es H2 (en memoria). Si se reinicia el servidor, los usuarios se borran y deben registrarse nuevamente desde la App.*

### 2. Ejecutar la App Móvil
1.  Abrir la carpeta `MovieApp` en **Android Studio**.
2.  Sincronizar Gradle (`Sync Project with Gradle Files`).
3.  Seleccionar un Emulador y dar clic en **Run (Play)**.
4.  **Importante:** El emulador debe tener acceso a internet para cargar las imágenes de TMDB.

---

##  Gestión del Proyecto

* **Tablero de Trello:** [PEGAR TU ENLACE DE TRELLO AQUÍ]
    * *Evidencia de planificación y distribución de tareas.*

---

##  Entregables Técnicos

### APK Firmado y Keystore
Se adjuntan en el repositorio los archivos necesarios para la distribución:
1.  **APK Firmado:** `app-release.apk` (Ubicado en carpeta raíz o `/release`).
2.  **Llave:** `keystore.jks`

### Captura de Evidencia (APK Generado)
![Captura del APK Firmado](![alt text](image_apk_generado.png))
MovieApp\app\release

---
