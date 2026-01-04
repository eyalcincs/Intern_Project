import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable, tap } from 'rxjs';

export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token?: string;       // bazı projelerde token
  accessToken?: string; // bazı projelerde accessToken
}

export interface DtoUserIU {
  username: string;
  password: string;
  name?: string;
  surname?: string;
  email?: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = '/api/auth';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: object
  ) {}

  register(dto: DtoUserIU): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, dto);
  }

  login(req: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, req).pipe(
      tap(res => {
        if (isPlatformBrowser(this.platformId)) {
          const t = res?.token ?? res?.accessToken ?? null;
          if (t) localStorage.setItem('token', t);
        }
      })
    );
  }

  getToken(): string | null {
    return isPlatformBrowser(this.platformId) ? localStorage.getItem('token') : null;
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) localStorage.removeItem('token');
  }
}

