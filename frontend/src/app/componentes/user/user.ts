import { Component , inject, OnInit } from '@angular/core';
import { RouterOutlet, Router } from "@angular/router";
import { FormsModule } from '@angular/forms';

import { HttpClient,HttpHeaders } from '@angular/common/http';
import {ListPrestamoItem} from '../login/listaprestamos'

//import { UserComponent } from '../user/user.service';
import { AuthService } from '../login/login.services';
@Component({
  imports: [RouterOutlet,FormsModule],
  selector: 'app-user',
  standalone: true,
  templateUrl: './user.html',
})
export class User {


  // Inyecciones modernas con 'inject'
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private router = inject(Router);
  user='';
  monto = '';


  [x: string]: any;

  // 2. CREAMOS EL GETTER AQUÍ: Borra cualquier variable rota como 'usuarioLogueadoaux'
  get usuarioLogueado(): string | null {
    return this.authService.getUser();
  }

  get listaMisPrestamos(): any[] {
    return this.authService.getPrestamos();
  }
  get  getTokenUser(){
    return this.authService.getToken();
  }
    get  getId(){
    return this.authService.getIdUser();
  }
 logout() {
    // 1. Borramos los datos del almacenamiento de la sesión
    sessionStorage.clear();
    this.router.navigate(['/']);
    // 2. Redirigimos al usuario a la pantalla de inicio de sesión

  }
// solocitamos prestamo y tambien actualizamos la vista.
  goToSolicitarPrestamo() {

  // Variables que se amarran al formulario HTML
    // 1. Armamos el objeto con tus datos para el backend
    /*
    	"id_user": 1,
    "plazoDate": "2026-02-20",
    "monto" : 2000
    */
    const user_id = this.getId;
    const tokenReal = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${tokenReal}`
    });
    const fecha = new Date().toISOString().split('T')[0];
    const body = {
      id_user : user_id,
      plazoDate: fecha,
      monto: this.monto,
    };

// ... Todo el inicio de tu clase se mantiene igual ...

    this.http.post<any>('http://localhost:8080/prestamos/crear', body,{ headers }).subscribe({
      next: (CrearResponse) => {

        alert("Prestamo creado correctamente");
        this.authService.setPrestamos(CrearResponse.data);
        this.router.navigate(['/user']);
        // Guardamos token y usuario en el servicio


       /* this.http.get<any>(`http://localhost:8080/prestamos/buscarpretamos/${user_id}`, { headers }).subscribe({
          next: (prestamoResponse) => {
            console.log('¡Préstamos descargados con token!:', prestamoResponse);

            alert(prestamoResponse.data+"Respuesta2");

            this.authService.setPrestamos(prestamoResponse.data);

            this.router.navigate(['/user']);
          },
          error: (err) => {

            console.error('Error en el GET (revisa si el token expiró o la ruta cambió):', err);
            this.router.navigate(['/user']);
          }
        });*/
      },
      error: (err) => {
        alert('Error al Registrar el prestamo');
      }
    });
  }

}
