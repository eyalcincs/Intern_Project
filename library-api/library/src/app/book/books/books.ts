import { Component, OnInit, OnDestroy, inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { BookDto, BookService } from '../book.service';

@Component({
  selector: 'app-books',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './books.html',
  styleUrl: './books.css',
})
export class Books implements OnInit, OnDestroy {
  searchTerm = '';
  books: BookDto[] = [];
  loading = false;
  errorMsg = '';

  private sub?: Subscription;

  private platformId = inject(PLATFORM_ID);
  private cdr = inject(ChangeDetectorRef);

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.load();
    }
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  load(): void {
    this.sub?.unsubscribe();

    this.loading = true;
    this.errorMsg = '';

    this.sub = this.bookService.getAll().subscribe({
      next: (data: BookDto[]) => {
        const list = Array.isArray(data) ? data : [];

        // ✅ yeni eklenen (id büyük) en üstte
        this.books = [...list].sort((a, b) => Number(b.id) - Number(a.id));

        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('GET /api/books error:', err);

        this.errorMsg = err?.status === 403
          ? '403: Yetkisiz (token yok)'
          : 'Kitaplar alınamadı.';

        this.loading = false;

        // İstersen burada books'u sıfırlama, ekranda son hali kalsın:
        // this.books = this.books;

        this.cdr.detectChanges();
      }
    });
  }

  get filteredBooks(): BookDto[] {
    const q = (this.searchTerm ?? '').trim().toLowerCase();
    if (!q) return this.books;

    return this.books.filter(b =>
      (b.bookName ?? '').toLowerCase().includes(q) ||
      (b.author ?? '').toLowerCase().includes(q) ||
      (b.category ?? '').toLowerCase().includes(q)
    );
  }

  clearSearch(): void {
    this.searchTerm = '';
  }

  trackById(_: number, b: BookDto): number {
    return b.id;
  }

  deleteBook(id: number): void {
    if (!confirm('Bu kitabı silmek istiyor musun?')) return;

    this.bookService.delete(id).subscribe({
      next: () => this.load(),
      error: (err: any) => {
        console.error('DELETE /api/books error:', err);
        alert('Silme başarısız.');
      }
    });
  }
}











