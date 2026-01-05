import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  name = '';
  surname = '';
  email = '';
  username = '';
  password = '';

  error = '';

  toastVisible = false;
  toastMessage = '';

  constructor(private auth: AuthService, private router: Router) {}

  register(f: NgForm) {
    console.log('REGISTER SUBMIT');

    this.error = '';
    this.hideToast();

    // Form geçersizse backend'e hiç gitme
    if (f.invalid) {
      this.showToast('Lütfen tüm zorunlu alanları doldurun.');
      return;
    }

    this.auth
      .register({
        name: this.name.trim(),
        surname: this.surname.trim(),
        email: this.email.trim(),
        username: this.username.trim(),
        password: this.password,
      })
      .subscribe({
        next: () => {
          this.showToast('Kayıt başarılı! Giriş sayfasına yönlendiriliyorsun...');

          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 1200);
        },
        error: (err) => {
          const msg =
            err?.error?.message ??
            err?.error?.error ??
            (typeof err?.error === 'string' ? err.error : null) ??
            'Kayıt başarısız';

          this.error = msg;
          this.showToast(msg);
        },
      });
  }

  private showToast(message: string) {
    this.toastMessage = message;
    this.toastVisible = true;


    setTimeout(() => this.hideToast(), 2500);
  }

  private hideToast() {
    this.toastVisible = false;
    this.toastMessage = '';
  }
}






