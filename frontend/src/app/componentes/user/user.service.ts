import { Component, inject, Injectable } from '@angular/core';
import { AuthService } from '../login/login.services';
import { RouterOutlet } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserComponent {
[x: string]: any;
  private authService = inject(AuthService);

  get usuarioLogueado(): string | null {
    return this.authService.getUser();
  }
  get listaMisPrestamos(): any[] {
    return this.authService.getPrestamos();
  }
}
