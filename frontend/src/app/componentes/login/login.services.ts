import { Injectable, Service, signal } from '@angular/core';
import {ListPrestamoItem} from './listaprestamos'
import { Login } from './login';
import { jwtDecode } from 'jwt-decode';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Signals para almacenar el estado en memoria activa
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
  // Guardamos una copia en el almacenamiento de la sesión
  sessionStorage.setItem('user', email);
}

  getUser(): string | null {
    if (this.userEmail()) {
      return this.userEmail();
    }
    // Si la señal está vacía por la navegación, lee directo del almacenamiento
    return sessionStorage.getItem('user');
  }
  /*setPrestamos(lista: any[]) {
    this.prestamosSignal.update((currentItems) => {
    // 1. Creamos una copia del estado actual para modificarla de forma segura
    let updatedItems = [...currentItems];

    // 2. Recorremos la lista que llega por parámetro
    lista.forEach((nuevoPrestamo) => {
      // Buscamos si ya existe un préstamo con el mismo monto en el estado actual
      const existingIndex = updatedItems.findIndex((item) => item.monto === nuevoPrestamo.monto);

      if (existingIndex !== -1) {
        // Si existe, actualizamos su estatus (y cualquier otra propiedad necesaria)
        updatedItems[existingIndex] = {
          ...updatedItems[existingIndex],
          status: nuevoPrestamo.status // Actualiza el estatus con el nuevo valor
        };
      } else {
        // Si no existe, lo agregamos como un nuevo elemento al arreglo
        updatedItems.push({
          monto: nuevoPrestamo.monto,
          status: nuevoPrestamo.status
        });
      }
    });


    // 3. Retornamos el nuevo estado para actualizar la Signal
    return updatedItems;

  }*/

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
