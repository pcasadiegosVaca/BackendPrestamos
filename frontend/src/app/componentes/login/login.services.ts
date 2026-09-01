import { Injectable, Service, signal } from '@angular/core';
import {ListPrestamoItem} from './listaprestamos'
import { Login } from './login';
import { jwtDecode } from 'jwt-decode';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenSignal = signal<string | null>(null);
  private prestamosSignal = signal<ListPrestamoItem[]>([]);
  private userEmail = signal<any>(null);
  private IdUser = signal<number | any>(null);


  setToken(token: string) {
    this.tokenSignal.set(token);
    sessionStorage.setItem('auth_token', token);
  }

  getToken() {
    if (this.tokenSignal()) {
      return this.tokenSignal();
    }
    return sessionStorage.getItem('auth_token');
  }
  setIdUser(id: string) {
    this.IdUser.set(id);
    sessionStorage.setItem('IdUser', id);
  }

  getIdUser(): any {
    if (this.IdUser()) {
     return this.IdUser();
    }
    return sessionStorage.getItem('IdUser');
  }

setUser(email: string) {
  this.userEmail.set(email);
  sessionStorage.setItem('user', email);
}

  getUser(): string | null {
    if (this.userEmail()) {
      return this.userEmail();
    }
    return sessionStorage.getItem('user');
  }
  setPrestamos(lista: ListPrestamoItem[]) {

    this.prestamosSignal.update((currentItems) => {
      return [...currentItems, ...lista]; // Agrega el nuevo al final de la lista
    });

  }

  setPrestamosnuevos(nuevosPrestamos: any[]) {
  this.prestamosSignal.set(nuevosPrestamos);
  sessionStorage.setItem('prestamos', JSON.stringify(nuevosPrestamos));
}
  getPrestamosnuevos() {
    return this.prestamosSignal();
}



  getPrestamos(): ListPrestamoItem[] {
    return this.prestamosSignal();
  }

  obtenerRolDesdeToken(): string | null {
    const token = sessionStorage.getItem('auth_token'); // Recuperas el token guardado
    console.log(token);
    if (!token) return null;
    try {
      const tokenDecodificado: any = jwtDecode(token);
          console.log(tokenDecodificado.role);

      return tokenDecodificado.role;
    } catch (error) {
      console.error("Error al desglosar el token", error);
      return null;
    }
  }
}
