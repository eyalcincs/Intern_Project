import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], // ✅ CommonModule eklendi
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username = '';
  password = '';
  
  
  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  login(f: NgForm) {
    this.error = '';

    if (f.invalid) {
      this.error = 'Kullanıcı adı ve şifre zorunlu.';		//Form Geçersiz ise
      return;
    }

    this.auth.login({
      username: this.username.trim(),							// 
      password: this.password
    }).subscribe({												//isteğin sonucu gelince ne yapacağımızı belirtiriz
      next: (res) => {											// başarılı cevap gelirse bu çalışır
        const token = res?.token ?? res?.accessToken ?? null;
        if (!token) {
          this.error = 'Giriş başarısız (token alınamadı).';
          return;
        }
        this.router.navigateByUrl('/books');					// login başarılıysa /books'a gidecek.
      },
      error: (err) => {											
        this.error = err?.error?.message ?? 'Giriş başarısız';
      }
    });
  }
}







