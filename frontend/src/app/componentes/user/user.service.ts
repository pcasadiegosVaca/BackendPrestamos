import { Component, inject, Injectable } from '@angular/core';
import { AuthService } from '../login/login.services';
import { RouterOutlet } from "@angular/router"; // Revisa tu ruta de carpetas

@Injectable({
  providedIn: 'root'
})
export class UserComponent {
[x: string]: any;
  // Inyectamos el servicio global
  private authService = inject(AuthService);

  // Creamos un Getter que apunta directo a la señal de tu servicio
  get usuarioLogueado(): string | null {
    return this.authService.getUser();
  }
  get listaMisPrestamos(): any[] {
    return this.authService.getPrestamos();
  }
}
