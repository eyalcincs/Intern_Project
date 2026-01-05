import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export type BookDto = {
  id: number;
  bookName: string;
  pageCount: number;
  author: string;
  category: string;
  registerDate?: string;
  loanDate?: string | null;
};

export type BookIU = {
  bookName: string;
  pageCount: number;
  author: string;
  category: string;
  registerDate?: string;
  loanDate?: string | null;
};

@Injectable({ providedIn: 'root' })
export class BookService {
  private baseUrl = '/api/books';

  constructor(private http: HttpClient) {}

  getAll(): Observable<BookDto[]> {
    return this.http.get<BookDto[]>(this.baseUrl);
  }

  getById(id: number): Observable<BookDto> {
    return this.http.get<BookDto>(`${this.baseUrl}/${id}`);
  }

  create(dto: BookIU): Observable<BookDto> {
    return this.http.post<BookDto>(this.baseUrl, dto);
  }

  update(id: number, dto: BookIU): Observable<BookDto> {
    return this.http.put<BookDto>(`${this.baseUrl}/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}


