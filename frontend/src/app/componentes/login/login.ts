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

  correo = '';
  password = '';

  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private router = inject(Router);

  goToLogin() {
    const body = {
      correo: this.correo,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/user/login', body).subscribe({
      next: (loginResponse) => {
        console.log('Login exitoso:', loginResponse);

        this.authService.setToken(loginResponse.token);
        this.authService.setUser(loginResponse.user);
        this.authService.setIdUser(loginResponse.id);
        if(this.authService.obtenerRolDesdeToken() === 'ADMIN'){
              alert(this.authService.obtenerRolDesdeToken());

              this.router.navigate(['/admind']);
            }else{
              alert(this.authService.obtenerRolDesdeToken());

              this.router.navigate(['/user']);

            }
      },
      error: (err) => {
        console.error('Error en el login:', err);
        alert('Credenciales incorrectas o error en el servidor');
      }
    });
  }
}
