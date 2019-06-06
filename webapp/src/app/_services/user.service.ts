import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

import { User } from '../_models/user';

@Injectable({providedIn: 'root'})
export class UserService {
  constructor(private http: HttpClient) {
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.apiEndpoint}/users`);
  }

  getById(id: number): Observable<User> {
    return this.http.get<User>(`${environment.apiEndpoint}/users/${id}`);
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${environment.apiEndpoint}/users`, user);
  }

  update(user: User): Observable<void> {
    return this.http.put<void>(`${environment.apiEndpoint}/users/${user.id}`, user);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiEndpoint}/users/${id}`);
  }
}
