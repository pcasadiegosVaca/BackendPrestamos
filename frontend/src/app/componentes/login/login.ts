import { Component, inject } from '@angular/core';
import { RouterOutlet } from "@angular/router";
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './login.services';

@Component({
  imports: [RouterOutlet, FormsModule],
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.html',
})
export class Login {

  // Variables que se amarran al formulario HTML
  correo = '';
  password = '';

  // Inyecciones modernas con 'inject'
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private router = inject(Router);

  goToLogin() {
    // 1. Armamos el objeto con tus datos para el backend
    const body = {
      correo: this.correo,
      password: this.password
    };

    // 2. Hacemos la petición POST de autenticación
    this.http.post<any>('http://localhost:8080/user/login', body).subscribe({
      next: (loginResponse) => {
        console.log('Login exitoso:', loginResponse);

        // 3. Guardamos los datos de sesión iniciales
        this.authService.setToken(loginResponse.token);
        this.authService.setUser(loginResponse.user);

        // DINÁMICO: Si el backend te devuelve el id del usuario en la respuesta del login,
        // úsalo directamente en lugar de dejar el número 1 fijo:
        const idUsuario = loginResponse.user?.id || 1;
        const tokenReal = loginResponse.token;
        const headers = new HttpHeaders({
          'Authorization': `Bearer ${tokenReal}`
        });
        // 4. Consultamos los préstamos usando el ID obtenido
        this.http.get<any>(`http://localhost:8080/user/buscarpretamos/${idUsuario}`, { headers }).subscribe({
          next: (prestamoResponse) => {
            console.log('¡Préstamos descargados con token!:', prestamoResponse);
            this.authService.setPrestamos(prestamoResponse.data);

            this.router.navigate(['/user']);
          },
          error: (err) => {
            console.error('Error en el GET (revisa si el token expiró o la ruta cambió):', err);
            this.router.navigate(['/user']);
          }
        });
      },
      error: (err) => {
        console.error('Error en el login:', err);
        alert('Credenciales incorrectas o error en el servidor');
      }
    }); // Cierre del subscribe de login
  }
}
