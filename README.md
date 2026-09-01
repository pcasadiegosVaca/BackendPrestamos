# Sistema de Gestión de Préstamos

Aplicación full-stack para gestionar solicitudes de préstamos. El sistema permite a los usuarios solicitar préstamos y a los administradores aprobar, rechazar o ver el estado de las solicitudes.

**Stack Tecnológico:**
- **Backend:** Spring Boot 4.1.1 (Java 17) con Spring Security y JWT
- **Frontend:** Angular 22.1
- **Base de Datos:** H2 (en memoria)
- **Autenticación:** JWT (JSON Web Tokens)

---

##  Requisitos Previos

- **Java 17** o superior instalado
- **Node.js** y **npm** instalados (versión 11.17.0 o superior)
- **Git** (opcional)

### Verificar instalación:
```bash
java -version
node -v
npm -v
```

---

##  Instalación y Configuración

### 1. Clonar o descargar el proyecto
```bash
git clone <url-del-repositorio>
cd makers
```

### 2. Estructura del Proyecto
```
makers/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/BackendPrestamos/
│   │       │   ├── config/           # Configuración de seguridad y JWT
│   │       │   ├── controller/       # Endpoints REST
│   │       │   ├── service/          # Lógica de negocio
│   │       │   ├── entity/           # Entidades JPA
│   │       │   ├── repository/       # Acceso a datos
│   │       │   ├── dto/              # Data Transfer Objects
│   │       │   └── error/            # Manejo de errores
│   │       └── resources/
│   │           ├── application.properties
│   │           └── data.sql          # Datos de prueba
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── componentes/
│   │   │   │   ├── login/
│   │   │   │   ├── user/
│   │   │   │   ├── menu/
│   │   │   │   └── admind/
│   │   │   └── app.ts
│   │   └── main.ts
│   ├── package.json
│   └── angular.json
└── README.md
```

---

##  Ejecución del Proyecto

### Backend (Spring Boot)

#### 1. Navegar a la carpeta del backend
```bash
cd backend
```

#### 2. Compilar y ejecutar (Windows)
```bash
.\mvnw spring-boot:run
```

#### 3. Compilar y ejecutar (Linux/Mac)
```bash
./mvnw spring-boot:run
```

#### 4. El servidor estará disponible en:
```
http://localhost:8080
```

#### 5. Consola H2 (base de datos):
```
http://localhost:8080/h2-console
```

---

### Frontend (Angular)

#### 1. Navegar a la carpeta del frontend
```bash
cd frontend
```

#### 2. Instalar dependencias
```bash
npm install
```

#### 3. Ejecutar el servidor de desarrollo
```bash
npm start
```

#### 4. La aplicación estará disponible en:
```
http://localhost:4200
```

---

##  Autenticación con JWT

### ¿Qué es JWT?

JWT (JSON Web Token) es un token de autenticación que se envía en el header de las peticiones HTTP para validar que el usuario está autorizado.

### Estructura del Token JWT

Cada token generado contiene:
- **Correo del usuario:** El email de la persona logueada
- **Rol del usuario:** Puede ser `ADMIN` o `USER`
- **Fecha de emisión y expiración:** El token expira después de 24 horas

### Ejemplo de uso en Postman

Después de hacer login exitoso, recibirás un token como este:
```json
{
  "message": "Login exitoso",
  "user": "ana.gomez@example.com",
  "role": "USER",
  "id": 1,
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN..."
}
```

Para las peticiones que requieren autenticación, debes agregar el token en el header:
```
Authorization: Bearer <tu-token-aqui>
```

---

## 👥 Credenciales de Prueba

El sistema viene con 2 usuarios preconfigurados:

### Usuario Regular
```
Correo: ana.gomez@example.com
Contraseña: password
Rol: USER
```

### Usuario Admin
```
Correo: carlos.admin@example.com
Contraseña: password
Rol: ADMIN
```

---

##  Endpoints de la API

###  Autenticación (Sin Token)

#### 1. **Crear Usuario (Registro)**
```
POST http://localhost:8080/user/crear
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan.perez@example.com",
  "password": "micontraseña123",
  "role": "USER"
}
```

**Respuesta (201 Created):**
```json
{
  "message": "Usuario creado exitosamente",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": "juan.perez@example.com",
  "role": "USER",
  "id": 3
}
```

---

#### 2. **Login (Iniciar Sesión)**
```
POST http://localhost:8080/user/login
Content-Type: application/json

{
  "correo": "ana.gomez@example.com",
  "password": "password"
}
```

**Respuesta (200 OK):**
```json
{
  "message": "Login exitoso",
  "user": "ana.gomez@example.com",
  "role": "USER",
  "id": 1,
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN..."
}
```

---

#### 3. **Obtener Todos los Usuarios**
```
GET http://localhost:8080/user/BuscarTodos
Content-Type: application/json
```

**Respuesta (200 OK):**
```json
{
  "users": [
    {
      "id": 1,
      "nombre": "Ana",
      "apellido": "Gomez",
      "correo": "ana.gomez@example.com",
      "role": "USER"
    },
    {
      "id": 2,
      "nombre": "Carlos",
      "apellido": "Admin",
      "correo": "carlos.admin@example.com",
      "role": "ADMIN"
    }
  ]
}
```

---

### 💰 Gestión de Préstamos (Requieren Token)

#### 4. **Crear Préstamo**
```
POST http://localhost:8080/prestamos/crear
Content-Type: application/json
Authorization: Bearer <tu-token>

{
  "idUser": 1,
  "monto": 50000,
  "plazoDate": "2026-12-31",
  "correo": "ana.gomez@example.com"
}
```

**Parámetros requeridos:**
- `idUser`: ID del usuario (número)
- `monto`: Cantidad solicitada (número)
- `plazoDate`: Fecha límite en formato YYYY-MM-DD
- `correo`: Email del usuario solicitante

**Respuesta (200 OK):**
```json
{
  "message": "Prestamo creado exitosamente",
  "status": "success",
  "data": [
    {
      "id": 3,
      "idUser": 1,
      "monto": 50000,
      "status": "PENDING"
    }
  ]
}
```

---

#### 5. **Buscar Préstamos de un Usuario**
```
GET http://localhost:8080/prestamos/buscarpretamos/1
Content-Type: application/json
Authorization: Bearer <tu-token>
```

**Parámetro de ruta:**
- `{id}`: ID del usuario

**Respuesta (200 OK):**
```json
{
  "status": "success",
  "mensaje": "Préstamos recuperados correctamente.",
  "data": [
    {
      "id": 1,
      "idUser": 1,
      "monto": 15000,
      "status": "PENDING"
    },
    {
      "id": 2,
      "idUser": 1,
      "monto": 8500,
      "status": "APPROVED"
    }
  ]
}
```

---

#### 6. **Actualizar Estado del Préstamo** ⚠️ Solo Admins
```
PATCH http://localhost:8080/prestamos/actualizar
Content-Type: application/json
Authorization: Bearer <token-admin>

{
  "idPrestamo": 1,
  "correo_user": "ana.gomez@example.com",
  "status": "APPROVED",
  "role": "ADMIN"
}
```

**Parámetros requeridos:**
- `idPrestamo`: ID del préstamo a actualizar
- `correo_user`: Email del usuario propietario del préstamo
- `status`: Nuevo estado (`PENDING`, `APPROVED`, `REJECTED`)
- `role`: Debe ser `ADMIN` para poder actualizar

**Valores válidos para status:**
- `PENDING` - Solicitud pendiente
- `APPROVED` - Préstamo aprobado
- `REJECTED` - Préstamo rechazado

**Respuesta (200 OK):**
```json
{
  "message": "Prestamo actualizado exitosamente desde el service",
  "data": {
    "prestamos": [
      {
        "id": 1,
        "idUser": 1,
        "monto": 15000,
        "status": "APPROVED"
      }
    ]
  }
}
```

---

####  **Obtener Todos los Préstamos** ⚠️ Solo Admins
```
GET http://localhost:8080/prestamos/ObtenerTodosPrestamos
Content-Type: application/json
Authorization: Bearer <token-admin>
```

**Respuesta (200 OK):**
```json
{
  "message": "Usuarios obtenidos exitosamente",
  "prestamos": [
    {
      "id": 1,
      "idUser": 1,
      "monto": 15000,
      "plazoDate": "2026-09-15",
      "correo": "ana.gomez@example.com",
      "status": "PENDING"
    },
    {
      "id": 2,
      "idUser": 1,
      "monto": 8500,
      "plazoDate": "2026-10-30",
      "correo": "ana.gomez@example.com",
      "status": "APPROVED"
    }
  ]
}
```

---

##  Guía Rápida para Postman

### 1. Crear una nueva colección
- Crear carpeta: `Sistema Préstamos`
- Crear subcarpetas: `Auth`, `User Requests`, `Admin`

### 2. Ejemplo de flujo completo:

#### Paso 1: Login
```bash
POST http://localhost:8080/user/login
Body (raw JSON):
{
  "correo": "ana.gomez@example.com",
  "password": "password"
}
```

#### Paso 2: Copiar el token recibido

#### Paso 3: Crear un Préstamo
```bash
POST http://localhost:8080/prestamos/crear
Headers:
  - Authorization: Bearer <tu-token>

Body (raw JSON):
{
  "idUser": 1,
  "monto": 25000,
  "plazoDate": "2026-11-30",
  "correo": "ana.gomez@example.com"
}
```

#### Paso 4: Consultar Préstamos
```bash
GET http://localhost:8080/prestamos/buscarpretamos/1
Headers:
  - Authorization: Bearer <tu-token>
```

---

##  Base de Datos H2

### Acceder a la consola H2
1. En el navegador, ir a: `http://localhost:8080/h2-console`
2. Dejar los valores por defecto y hacer clic en **Connect**

### Tablas principales
```sql
-- Tabla de usuarios
CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255),
  apellido VARCHAR(255),
  correo VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  role VARCHAR(50)
);

-- Tabla de préstamos
CREATE TABLE pretamo_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_user BIGINT,
  monto DECIMAL(10, 2),
  plazo_date DATE,
  correo VARCHAR(255),
  status VARCHAR(50) DEFAULT 'PENDING'
);
```

---

## Seguridad

### Configuración de JWT
- **Algoritmo:** HS256 (HMAC SHA-256)
- **Tiempo de expiración:** 24 horas
- **Clave secreta:** Configurada en `application.properties`

### Protección de rutas
- Las rutas públicas (`/user/crear`, `/user/login`) no requieren token
- Las rutas protegidas requieren un token válido en el header `Authorization`
- Solo los ADMIN pueden actualizar el estado de préstamos

---

## Solución de Problemas

### Backend no inicia
- Verificar que el puerto 8080 esté disponible
- Asegurarse de tener Java 17 instalado: `java -version`
- Limpiar y recompilar: `mvnw clean install`

### Frontend no carga
- Verificar que el puerto 4200 esté disponible
- Eliminar `node_modules`: `rm -r node_modules` (o `rmdir /s node_modules` en Windows)
- Reinstalar dependencias: `npm install`

### Error de CORS
- Verificar que el backend está corriendo en `http://localhost:8080`
- Verificar que el frontend está corriendo en `http://localhost:4200`
- Los headers CORS están configurados en los controladores

### Token expirado
- Si recibes error 401, debes hacer login nuevamente
- El token expira después de 24 horas

---

##  Notas Importantes

1. **Base de datos H2:** Todos los datos se pierden al reiniciar la aplicación. Los datos de prueba se cargan automáticamente desde `data.sql`

2. **Contraseñas:** Se almacenan encriptadas con BCrypt

3. **CORS:** La aplicación Angular (puerto 4200) está autorizada para hacer peticiones al backend (puerto 8080)

4. **JWT en SessionStorage:** El frontend guarda el token en `sessionStorage` del navegador

5. **Roles:**
   - **USER:** Puede crear solicitudes de préstamos y ver sus propios préstamos
   - **ADMIN:** Puede ver todos los préstamos y cambiar sus estados

---

## Estructura de Clases Principales

### DTOs (Data Transfer Objects)
- `LoginDto`: Datos para login (correo, password)
- `UserDto`: Datos para crear usuario (nombre, apellido, correo, password, role)
- `PrestamoDto`: Datos para crear préstamo
- `EditarStatus`: Datos para actualizar estado de préstamo

### Entidades
- `UserRegistro`: Usuario del sistema
- `PretamoUser`: Solicitud de préstamo

### Servicios
- `UserService`: Gestión de usuarios y autenticación
- `PrestamoUserService`: Gestión de préstamos
- `JwtService`: Generación y validación de tokens JWT

### Controladores
- `ControllerUser`: Endpoints de usuarios (/user)
- `PresamosController`: Endpoints de préstamos (/prestamos)

---
### Notas
Existen usuarios ya creados en el backend para iniciar pruebas desde frontend
los cuales son :

user: ana.gomez@example.com
password: password
type : usuario

user : carlos.admin@example.com
password : password
type : admin


