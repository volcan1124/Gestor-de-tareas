# 📋 Gestor de Tareas (To-Do List)

Aplicación móvil desarrollada en Android Studio que permite gestionar tareas de manera sencilla utilizando **Firebase Firestore** como base de datos NoSQL en tiempo real.

## 🚀 Funcionalidades

✅ Crear tareas

🔍 Buscar tareas por ID

✏️ Editar tareas

❌ Eliminar tareas

📋 Visualizar lista de tareas en tiempo real

✅ Filtrar tareas completadas

🔄 Sincronización automática entre dispositivos mediante Firestore SnapshotListener

✔️ Validación de formularios con Material Design

---

## 🛠 Tecnologías utilizadas

- Java
- Android Studio
- Firebase Firestore (Base de datos NoSQL)
- Material Design Components
- RecyclerView
- CardView
- GridLayoutManager

---

## 📂 Estructura del proyecto

**MainActivity.java**
- Lógica principal de la aplicación
- CRUD con Firebase
- Validaciones
- Sincronización en tiempo real
- Manejo del RecyclerView

**Tarea.java**
- Modelo de datos (POJO)

**TareaAdapter.java**
- Adaptador del RecyclerView
- Visualización de las tarjetas de tareas

**AdminSQLiteOpenHelper.java**
- Base de datos SQLite utilizada en el primer seguimiento del proyecto

**activity_main.xml**
- Interfaz principal desarrollada con Material Design

**item_tarea.xml**
- Diseño de cada tarjeta mostrada en el RecyclerView

---

## ☁️ Base de datos

La aplicación utiliza **Firebase Cloud Firestore** para almacenar la información.

Cada documento de la colección **tareas** contiene los siguientes campos:

| Campo | Tipo |
|---------|---------|
| id | Integer |
| titulo | String |
| descripcion | String |
| completada | Boolean |

---

## 🔄 Operaciones CRUD

### Crear
Guarda una nueva tarea en Firestore.

### Leer
Consulta tareas por ID y muestra automáticamente todos los documentos mediante SnapshotListener.

### Actualizar
Modifica el título, descripción y estado de una tarea existente.

### Eliminar
Elimina una tarea utilizando su ID.

---

## 🎨 Características de la interfaz

- Material Design
- TextInputLayout
- MaterialButton
- SwitchMaterial
- ProgressBar durante operaciones de carga
- RecyclerView con CardViews
- Diseño responsive con GridLayoutManager

---



📸 Capturas de pantalla


<img width="307" height="655" alt="image" src="https://github.com/user-attachments/assets/2bd95263-e762-48d7-bfa6-20edf8162255" />


<img width="308" height="666" alt="image" src="https://github.com/user-attachments/assets/13933246-13e1-4b4e-8f36-2d2c00daed32" />

<img width="367" height="795" alt="image" src="https://github.com/user-attachments/assets/79c821dd-7905-410d-8594-0065046e7b0a" />

<img width="1569" height="607" alt="image" src="https://github.com/user-attachments/assets/2e7dc9b0-f52a-4008-8160-38b1a9cbbc50" />

<img width="1542" height="599" alt="image" src="https://github.com/user-attachments/assets/4412349b-b89a-4de6-b8e2-68b84cdb8a8f" />








👨‍💻 Autor
Johan Sebastián Mendez Rojas
