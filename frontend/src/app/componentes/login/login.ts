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

// ... Todo el inicio de tu clase se mantiene igual ...

    this.http.post<any>('http://localhost:8080/user/login', body).subscribe({
      next: (loginResponse) => {
        console.log('Login exitoso:', loginResponse);

        // Guardamos token y usuario en el servicio
        this.authService.setToken(loginResponse.token);
        this.authService.setUser(loginResponse.user);
        this.authService.setIdUser(loginResponse.id);

        const idUsuario = loginResponse.id;

        const tokenReal = loginResponse.token;

        const headers = new HttpHeaders({
          'Authorization': `Bearer ${tokenReal}`
        });

        this.http.get<any>(`http://localhost:8080/prestamos/buscarpretamos/${idUsuario}`, { headers }).subscribe({
          next: (prestamoResponse) => {
            console.log('¡Préstamos descargados con token!:', prestamoResponse);


            this.authService.setPrestamos(prestamoResponse.data);
            if(this.authService.obtenerRolDesdeToken() === 'ADMIN'){
              alert(this.authService.obtenerRolDesdeToken());

              this.router.navigate(['/admind']);
            }else{
              alert(this.authService.obtenerRolDesdeToken());

              this.router.navigate(['/user']);

            }
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
    });
  }
}
