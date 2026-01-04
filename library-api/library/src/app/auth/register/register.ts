import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
	
  // form alanları
  name = '';
  surname = '';
  email = '';
  username = '';
  password = '';

  // HTML’de @if(error) var
  error = '';

  // HTML’de @if(toastVisible) var
  toastVisible = false;
  toastMessage = '';

  constructor(private auth: AuthService, private router: Router) {}

  register() {
	console.log('REGISTER BUTTON CLICKED'); // <-- bunu ekle

    this.error = '';
    this.hideToast();

    // backend DTO alanların farklıysa burada eşleştireceğiz
    this.auth.register({
      name: this.name,
      surname: this.surname,
      email: this.email,
      username: this.username,
      password: this.password,
    }).subscribe({
      next: () => {
        this.showToast('Kayıt başarılı! Giriş sayfasına yönlendiriliyorsun...');

      },
      error: (err) => {
        this.error = err?.error?.message ?? 'Kayıt başarısız';
        this.showToast(this.error);
      }
    });
  }

  private showToast(message: string) {
    this.toastMessage = message;
    this.toastVisible = true;

  }

  private hideToast() {
    this.toastVisible = false;
    this.toastMessage = '';
  }
}





