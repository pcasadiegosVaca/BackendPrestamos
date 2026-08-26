import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Guardamos el token en una variable en memoria RAM usando Signals
  private token = signal<string | null>(null);
  private userEmail = signal<string | null>(null);
  private prestamosSignal = signal<any[]>([]);


  // Método simple para guardar el token
  setToken(jwt: string) {
    this.token.set(jwt);
  }

  // Método simple para obtener el token desde cualquier parte de la app
  getToken(): string | null {
    return this.token();
  }

// En tu archivo login.services.ts

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
 setPrestamos(lista: any[]) {
    this.prestamosSignal.set(lista);
    // Convertimos el arreglo a texto JSON para poder guardarlo en sessionStorage
    sessionStorage.setItem('mis_prestamos', JSON.stringify(lista));
  }

  // 3. Recupera la lista completa (intenta desde la Signal, si no, va a sessionStorage)
  getPrestamos(): any[] {
    if (this.prestamosSignal().length > 0) {
      return this.prestamosSignal();
    }

    // Si la señal se borró por refrescar la página, la recuperamos del almacenamiento
    const prestamosGuardados = sessionStorage.getItem('mis_prestamos');
    if (prestamosGuardados) {
      const listaParseada = JSON.parse(prestamosGuardados);
      this.prestamosSignal.set(listaParseada); // Rellenamos la Signal de nuevo
      return listaParseada;
    }

    return []; // Retorna lista vacía si no hay nada
  }
}
