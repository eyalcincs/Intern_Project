import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import {Bookregister} from './book/bookregister/bookregister';
import {Books} from './book/books/books';
import {Booksdetail} from './book/books/booksdetail/booksdetail';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full', title: 'Ana Sayfa' },
  { path: 'login', component: Login, title: 'Giriş' },
  { path: 'register', component: Register, title: 'Kayıt Sayfası' },
  {path: 'bookregister',component:Bookregister,title: 'Book Register'},
  {path: 'books',component:Books,title: 'Books'},
  {path: 'books/:id',component:Booksdetail,title: 'Book Detail'},
];

