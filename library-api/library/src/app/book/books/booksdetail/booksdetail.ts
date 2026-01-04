import { Component, ChangeDetectorRef, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { BookDto, BookService } from '../../book.service';

type BookIU = {
  bookName: string;
  pageCount: number;
  author: string;
  category: string;
  registerDate: string;      // YYYY-MM-DD
  loanDate: string | null;   // null olabilir
};

@Component({
  selector: 'app-booksdetail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './booksdetail.html',
  styleUrl: './booksdetail.css',
})
export class Booksdetail implements OnInit {
  id!: number;

  book: BookDto | null = null;
  loading = false;
  saving = false;          // ✅ kritik

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
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (!this.id) {
      this.router.navigateByUrl('/books');
      return;
    }
    this.load();
  }

  private toIsoDate(v: any): string {
    // hem "YYYY-MM-DD" hem "DD.MM.YYYY" gelirse toparla
    const s = (v ?? '').toString().trim();
    if (!s) return '';
    if (/^\d{4}-\d{2}-\d{2}$/.test(s)) return s; // already ISO
    const m = s.match(/^(\d{2})\.(\d{2})\.(\d{4})$/);
    if (m) return `${m[3]}-${m[2]}-${m[1]}`;     // DD.MM.YYYY -> ISO
    return s.slice(0, 10);
  }

  private toIsoNullable(v: any): string | null {
    const s = (v ?? '').toString().trim();
    if (!s) return null;
    const iso = this.toIsoDate(s);
    return iso ? iso : null;
  }

  load() {
    this.loading = true;
    this.errorMsg = '';
    this.successMsg = '';

    this.bookService.getById(this.id).subscribe({
      next: (data) => {
        this.book = data;
        this.loading = false;
        this.cdr.detectChanges();
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

    // ✅ aynı anda 2 istek atılmasın
    if (this.saving) return;

    this.errorMsg = '';
    this.successMsg = '';

    if (form.invalid) {
      this.errorMsg = 'Zorunlu alanları doldurun.';
      this.cdr.detectChanges();
      return;
    }

    // ✅ sadece IU gönder (id vs yok)
    const payload: BookIU = {
      bookName: (this.book.bookName ?? '').trim(),
      pageCount: Number(this.book.pageCount ?? 0),
      author: (this.book.author ?? '').trim(),
      category: (this.book.category ?? '').trim(),
      registerDate: this.toIsoDate(this.book.registerDate),
      loanDate: this.toIsoNullable(this.book.loanDate),
    };

    this.saving = true;

    this.bookService.update(this.id, payload as any).subscribe({
      next: () => {
        this.successMsg = 'Güncellendi ✅';
        this.saving = false;

        // ✅ sayfayı güncel veriyle tazele (görsel senkron)
        this.load();

        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('PUT /api/books/{id} error:', err);
        this.errorMsg = 'Güncelleme başarısız.';
        this.saving = false;
        this.cdr.detectChanges();
      }
    });
  }

  delete() {
    if (this.saving || this.loading) return;
    if (!confirm('Bu kitabı silmek istiyor musun?')) return;

    this.saving = true;

    this.bookService.delete(this.id).subscribe({
      next: () => this.router.navigateByUrl('/books'),
      error: (err) => {
        console.error('DELETE /api/books/{id} error:', err);
        this.saving = false;
        alert('Silme başarısız.');
      }
    });
  }
}







