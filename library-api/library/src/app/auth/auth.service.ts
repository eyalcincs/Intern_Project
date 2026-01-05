import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable, tap } from 'rxjs';

export interface AuthRequest {
  username: string;                   	// login yaparken backend'e göndereceğimiz json bilgileri.
  password: string;						// format daşu şekilde olmalı { "username": "...", "password": "..." }

}

export interface AuthResponse {
  token?: string;       				// backenden bize response olarak token dönecek
  accessToken?: string; // bazı projelerde accessToken
}

export interface DtoUserIU {
  
  name: string;
  username: string;
  surname: string;
  password: string;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = '/api/auth';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: object              //Taracıyı kontrol eder
  ) {}

  register(dto: DtoUserIU): Observable<any> {					//Backende post atarız
    return this.http.post(`${this.baseUrl}/register`, dto);
  }
																
  login(req: AuthRequest): Observable<AuthResponse> {			// token alınır ve alınan token saklanır.
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, req).pipe(
      tap(res => {
        if (isPlatformBrowser(this.platformId)) {
          const t = res?.token ?? res?.accessToken ?? null;
          if (t) localStorage.setItem('token', t);				//Token Kaydedilir.
        }
      })
    );
  }

  getToken(): string | null {
    return isPlatformBrowser(this.platformId) ? localStorage.getItem('token') : null;			//Tokenın geri okunmasını yaparız.
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) localStorage.removeItem('token');					//Tokenı sileriz
  }
}

