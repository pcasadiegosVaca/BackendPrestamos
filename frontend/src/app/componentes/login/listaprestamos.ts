export interface ListPrestamoItem {
  id: number;
  idUser: number;
  correo: string;
  monto: number;
  status: string;
  PlazoDate: Date| null;
}
