import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { BookDto, BookService } from '../../book.service';

@Component({
  selector: 'app-booksdetail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './booksdetail.html',
  styleUrl: './booksdetail.css',
})
export class Booksdetail {
  id!: number;

  book: BookDto | null = null;   // ✅ ilk açılışta null
  loading = false;

  errorMsg = '';
  successMsg = '';

  categories: string[] = [
    '📖 Roman','🏛️ Klasik','🚀 Bilim Kurgu','🧙 Fantastik','🕵️ Polisiye / Gerilim',
    '🏺 Tarih','👤 Biyografi','🧠 Psikoloji','🤔 Felsefe','🌱 Kişisel Gelişim',
    '🔬 Bilim','🖥️ Teknoloji','💻 Yazılım / Programlama','💼 İş Dünyası / Ekonomi',
    '🏛️ Siyaset','🎨 Sanat','🏗️ Mimarlık','🧩 Tasarım','📝 Şiir','📚 Deneme',
    '🎓 Eğitim','🧸 Çocuk','🧑‍🎓 Gençlik','🕌 Din','🩺 Sağlık','🍽️ Yemek / Gastronomi',
    '✈️ Seyahat','🏅 Spor','🎯 Hobi','🏷️ Diğer',
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService,
    private cdr: ChangeDetectorRef
  ) {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.load(); // ✅ sayfa açılır açılmaz yükle
  }

  load() {
    this.loading = true;
    this.errorMsg = '';
    this.successMsg = '';

    this.bookService.getById(this.id).subscribe({
      next: (data) => {
        this.book = data;
        this.loading = false;
        this.cdr.detectChanges(); // ✅ zoneless
      },
      error: (err) => {
        console.error('GET /api/books/{id} error:', err);
        this.errorMsg = 'Kitap bulunamadı.';
        this.loading = false;
        this.cdr.detectChanges();
        this.router.navigateByUrl('/books');
      }
    });
  }

  update(form: NgForm) {
    if (!this.book) return;

    this.errorMsg = '';
    this.successMsg = '';

    if (form.invalid) {
      this.errorMsg = 'Zorunlu alanları doldurun.';
      this.cdr.detectChanges();
      return;
    }

    // ✅ boş loanDate -> null gönder
    const payload = {
      ...this.book,
      loanDate: this.book.loanDate ? this.book.loanDate : null
    };

    this.bookService.update(this.id, payload).subscribe({
      next: () => {
        this.successMsg = 'Güncellendi ✅';
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('PUT /api/books/{id} error:', err);
        this.errorMsg = 'Güncelleme başarısız.';
        this.cdr.detectChanges();
      }
    });
  }

  delete() {
    if (!confirm('Bu kitabı silmek istiyor musun?')) return;

    this.bookService.delete(this.id).subscribe({
      next: () => this.router.navigateByUrl('/books'),
      error: () => alert('Silme başarısız.')
    });
  }
}






