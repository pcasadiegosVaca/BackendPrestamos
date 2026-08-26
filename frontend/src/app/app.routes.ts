import { Routes } from '@angular/router';
import { MenuComponent } from './componentes/menu/menu';
import { Admind } from './componentes/admind/admind';
import { User } from './componentes/user/user';
import { Login } from './componentes/login/login';


export const routes: Routes = [
  { path: 'menu', component: MenuComponent },
  { path: 'admind', component: Admind },
  { path: 'user', component: User },
  { path: 'login', component: Login },

];
