# 📋 Gestor de Tareas (To-Do List)

Aplicación móvil desarrollada en **Android Studio** utilizando **Java** y **Firebase**, diseñada para la gestión de tareas mediante un sistema CRUD, autenticación de usuarios y administración de roles.

---

# 🚀 Funcionalidades

## 👤 Autenticación

- Inicio de sesión con Firebase Authentication.
- Registro de nuevos usuarios.
- Cierre de sesión.
- Persistencia de sesión mediante SharedPreferences.

---

## 📋 Gestión de tareas

- ✅ Crear tareas.
- ✏️ Editar tareas.
- ❌ Eliminar tareas.
- 🔍 Buscar tareas por nombre.
- 📅 Registrar fecha de cada tarea.
- 🚩 Asignar prioridad:
  - 🔴 Alta
  - 🟡 Media
  - 🟢 Baja
- ✔️ Marcar tareas como completadas.
- 📃 Visualizar todas las tareas del usuario.
- Las tareas únicamente pueden ser visualizadas por su propietario.

---

## 👨‍💼 Administración de usuarios

El sistema implementa control de acceso por roles.

### Administrador

Puede:

- Ver todos los usuarios registrados.
- Consultar correo electrónico.
- Consultar nombre.
- Visualizar el rol de cada usuario.
- Cambiar el rol entre:
  - Administrador
  - Empleado

### Empleado

Puede:

- Administrar únicamente sus tareas.

---

# 🔐 Roles del sistema

| Rol | Permisos |
|------|-----------|
| Administrador | Gestionar usuarios y tareas propias |
| Empleado | Gestionar únicamente sus tareas |

---

# ☁️ Firebase

El proyecto utiliza:

- Firebase Authentication
- Firebase Cloud Firestore

## Colección usuarios

```text
usuarios
│
├── uid
│     ├── nombre
│     ├── correo
│     └── rol
```

### Campos

| Campo | Tipo |
|--------|------|
| nombre | String |
| correo | String |
| rol | String |

---

## Colección tareas

```text
tareas
│
├── id
├── tarea
├── fecha
├── prioridad
├── completada
└── uidUsuario
```

### Campos

| Campo | Tipo |
|--------|------|
| id | String |
| tarea | String |
| fecha | String |
| prioridad | String |
| completada | Boolean |
| uidUsuario | String |

---

# 🔄 Operaciones CRUD

## Crear

Permite registrar una nueva tarea en Firestore.

## Consultar

Obtiene todas las tareas del usuario autenticado.

## Actualizar

Permite modificar:

- Nombre
- Fecha
- Prioridad
- Estado

## Eliminar

Elimina una tarea seleccionada.

---

# 🛠 Tecnologías utilizadas

- Java
- Android Studio
- Firebase Authentication
- Firebase Firestore
- RecyclerView
- CardView
- Material Design
- SharedPreferences
- Gradle

---

# 📂 Estructura del proyecto

```
app
│
├── activities
│     ├── LoginActivity
│     ├── RegistroActivity
│     ├── MenuPrincipalActivity
│     ├── TareasActivity
│     └── UsuariosActivity
│
├── adapters
│     ├── TareaAdapter
│     └── UsuariosAdapter
│
├── models
│     ├── Tarea
│     └── Usuario
│
└── Firebase
```

---

# 🎨 Características de la interfaz

- Material Design
- RecyclerView
- CardView
- Interfaz moderna
- Tarjetas con prioridad por colores
- Estado visual de tareas
- Navegación sencilla
- Diseño adaptable




📸 Capturas de pantalla


<img width="307" height="655" alt="image" src="https://github.com/user-attachments/assets/2bd95263-e762-48d7-bfa6-20edf8162255" />


<img width="308" height="666" alt="image" src="https://github.com/user-attachments/assets/13933246-13e1-4b4e-8f36-2d2c00daed32" />

<img width="367" height="795" alt="image" src="https://github.com/user-attachments/assets/79c821dd-7905-410d-8594-0065046e7b0a" />

<img width="1569" height="607" alt="image" src="https://github.com/user-attachments/assets/2e7dc9b0-f52a-4008-8160-38b1a9cbbc50" />

<img width="1542" height="599" alt="image" src="https://github.com/user-attachments/assets/4412349b-b89a-4de6-b8e2-68b84cdb8a8f" />

<img width="374" height="780" alt="image" src="https://github.com/user-attachments/assets/c4753743-91d2-41e4-96fe-a46354879a82" />

<img width="368" height="786" alt="image" src="https://github.com/user-attachments/assets/c7d402dd-a483-42c0-a292-733eb82f621e" />

<img width="441" height="804" alt="image" src="https://github.com/user-attachments/assets/a3660d15-8fe1-4b44-8645-bc441e804950" />

<img width="382" height="820" alt="image" src="https://github.com/user-attachments/assets/e7e33f85-6b9d-44b2-ad0f-616d1140a0e5" />

<img width="436" height="829" alt="image" src="https://github.com/user-attachments/assets/e000079d-3d5e-4998-bb4f-086763fb9633" />









👨‍💻 Autor
Johan Sebastián Mendez Rojas
