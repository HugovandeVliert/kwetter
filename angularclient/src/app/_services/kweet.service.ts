import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

import { Kweet } from '../_models/kweet';

@Injectable({providedIn: 'root'})
export class KweetService {
  constructor(private http: HttpClient) {
  }

  getAllFromUser(userId: number): Observable<Kweet[]> {
    return this.http.get<Kweet[]>(`${environment.apiEndpoint}/users/${userId}/kweets`);
  }

  getTimeline(userId: number): Observable<Kweet[]> {
    return this.http.get<Kweet[]>(`${environment.apiEndpoint}/users/${userId}/timeline`);
  }

  getTrendingTopics(): Observable<string[]> {
    return this.http.get<string[]>(`${environment.apiEndpoint}/kweets/trending`);
  }

  getByText(text: string): Observable<Kweet[]> {
    return this.http.get<Kweet[]>(`${environment.apiEndpoint}/kweets?text=${text}`);
  }

  delete(id: number) {
    return this.http.delete(`${environment.apiEndpoint}/users/${id}`);
  }

  create(userId: number, text: string): Observable<Object> {
    const kweet: Kweet = new Kweet();
    kweet.text = text;

    return this.http.post(`${environment.apiEndpoint}/users/${userId}/kweets`, JSON.stringify(kweet));
  }
}
