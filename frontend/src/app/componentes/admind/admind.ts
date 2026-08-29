import { Component,inject } from '@angular/core';
import { RouterOutlet,Router} from "@angular/router";
import { AuthService } from '../login/login.services';
import { HttpClient,HttpHeaders } from '@angular/common/http';


@Component({
  imports: [RouterOutlet],
  selector: 'app-admind',
  styleUrl: './admind.css',
  templateUrl: './admind.html',
})
export class Admind {
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private router = inject(Router);

get listaMisPrestamos(): any[] {
  return this.authService.getPrestamosnuevos();
}

ngOnInit() {
    this.cargarPrestamos();
  }

  cargarPrestamos() {
    const tokenReal = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${tokenReal}`
    });

    this.http.get<any>(`http://localhost:8080/prestamos/ObtenerTodosPrestamos`, { headers }).subscribe({
          next: (prestamoResponse) => {
            console.log('¡Préstamos descargados con token!:', prestamoResponse);

            alert("Bienvenido...");

            this.authService.setPrestamosnuevos(prestamoResponse.prestamos);

            this.router.navigate(['/admind']);
          },
          error: (err) => {

            console.error('Error en el GET (revisa si el token expiró o la ruta cambió):', err);
            alert("error al cargar los prestamos de los usuarios");
            this.router.navigate(['/admind']);
          }
        });
  }

goToReject(id_prestamo:string, correo_user:string) {
   this.cambiarStatus(id_prestamo,correo_user,"REJECTED");
  }


goToAprove(id_prestamo:string, correo_user:string) {
   this.cambiarStatus(id_prestamo,correo_user,"APPROVED");

}

 cambiarStatus( id_prestamo : string, correo_user : string, status_prestamo : string) {
    const correo = correo_user;
    const id = id_prestamo;
    const status = status_prestamo;

    const tokenReal = this.authService.getToken();
    const role = this.authService.obtenerRolDesdeToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${tokenReal}`
    });

    const body = {
      idPrestamo :id,
      correo_user : correo,
      role: role,
      status:status,
    };

// ... Todo el inicio de tu clase se mantiene igual ...

    this.http.patch<any>('http://localhost:8080/prestamos/actualizar', body,{ headers }).subscribe({
      next: (CrearResponse) => {
        console.log('Login exitoso:', CrearResponse);

        alert("Estado cambiado correctamente");
        return this.authService.setPrestamosnuevos(CrearResponse.data.prestamos);
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
        alert('Error al cambiar el Estado');
      }
    });
 }


}
