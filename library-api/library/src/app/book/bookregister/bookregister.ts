import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { BookIU, BookService } from '../book.service';

@Component({
  selector: 'app-bookregister',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './bookregister.html',
  styleUrl: './bookregister.css',
})
export class Bookregister {
  book: BookIU = {
    bookName: '',
    pageCount: 0,
    author: '',
    category: '',
    registerDate: null,
    loanDate: null,
  };

  errorMsg = '';
  successMsg = '';

  categories: string[] = [
    '📖 Roman','🏛️ Klasik','🚀 Bilim Kurgu','🧙 Fantastik','🕵️ Polisiye / Gerilim','🏺 Tarih',
    '👤 Biyografi','🧠 Psikoloji','🤔 Felsefe','🌱 Kişisel Gelişim','🔬 Bilim','🖥️ Teknoloji',
    '💻 Yazılım / Programlama','💼 İş Dünyası / Ekonomi','🏛️ Siyaset','🎨 Sanat','🏗️ Mimarlık',
    '🧩 Tasarım','📝 Şiir','📚 Deneme','🎓 Eğitim','🧸 Çocuk','🧑‍🎓 Gençlik','🕌 Din','🩺 Sağlık',
    '🍽️ Yemek / Gastronomi','✈️ Seyahat','🏅 Spor','🎯 Hobi','🏷️ Diğer',
  ];

  constructor(private bookService: BookService, private router: Router) {}

  openPicker(el: HTMLInputElement) {
    const anyEl: any = el as any;
    if (anyEl?.showPicker) anyEl.showPicker();
    else el.focus();
  }

  submit(form: NgForm) {
    this.errorMsg = '';
    this.successMsg = '';

    if (form.invalid) {
      this.errorMsg = 'Lütfen zorunlu alanları doldurun.';
      return;
    }

    this.bookService.create(this.book).subscribe({
      next: () => {
        this.successMsg = 'Kitap kaydedildi ✅';
        setTimeout(() => this.router.navigateByUrl('/books'), 400);
      },
      error: (err) => {
        console.error('POST /api/books error:', err);
        this.errorMsg = 'Kayıt başarısız.';
      }
    });
  }
}





