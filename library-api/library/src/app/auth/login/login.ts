import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username = '';
  password = '';

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    this.auth.login({
      username: this.username,
      password: this.password
    }).subscribe({
      next: () => {
        // login başarılıysa nereye gidecek?
        this.router.navigateByUrl('/books'); // sende kitap listesi route’u neyse onu yaz
      },
      error: (err) => {
        console.log('Login hata:', err);
        alert(err?.error?.message ?? 'Giriş başarısız');
      }
    });
  }
}




