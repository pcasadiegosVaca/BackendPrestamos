import { Component , inject, OnInit } from '@angular/core';
import { RouterOutlet, Router } from "@angular/router";
import { FormsModule } from '@angular/forms';

import { HttpClient,HttpHeaders } from '@angular/common/http';
import {ListPrestamoItem} from '../login/listaprestamos'

import { AuthService } from '../login/login.services';
@Component({
  imports: [RouterOutlet,FormsModule],
  selector: 'app-user',
  standalone: true,
  templateUrl: './user.html',
})
export class User {

  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private router = inject(Router);
  user='';
  monto = '';


  [x: string]: any;

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

  ngOnInit() {
    this.cargarPrestamos();
  }
  cargarPrestamos(){

        const idUsuario = this.authService.getIdUser();

        const tokenReal = this.authService.getToken();


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

  }

 logout() {
    sessionStorage.clear();
    this.router.navigate(['/']);

  }
  goToSolicitarPrestamo() {

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

    this.http.post<any>('http://localhost:8080/prestamos/crear', body,{ headers }).subscribe({
      next: (CrearResponse) => {

        alert("Prestamo creado correctamente");
        this.authService.setPrestamos(CrearResponse.data);
        this.router.navigate(['/user']);
      },
      error: (err) => {
        alert('Error al Registrar el prestamo');
      }
    });
  }

}
