import { Component , inject, OnInit } from '@angular/core';
import { RouterOutlet, Router } from "@angular/router";
//import { UserComponent } from '../user/user.service';
import { AuthService } from '../login/login.services';
@Component({
  imports: [RouterOutlet],
  selector: 'app-user',
  standalone: true,
  templateUrl: './user.html',
})
export class User {
[x: string]: any;
  private authService = inject(AuthService);
  private router = inject(Router);
  // 2. CREAMOS EL GETTER AQUÍ: Borra cualquier variable rota como 'usuarioLogueadoaux'
  get usuarioLogueado(): string | null {
    return this.authService.getUser();
  }

  get listaMisPrestamos(): any[] {
    return this.authService.getPrestamos();
  }
 logout() {
    // 1. Borramos los datos del almacenamiento de la sesión
    sessionStorage.clear();

    // 2. Redirigimos al usuario a la pantalla de inicio de sesión
    this.router.navigate(['/login']);
  }
  goToMenu() {
  throw new Error('Method not implemented.');
  }
}
