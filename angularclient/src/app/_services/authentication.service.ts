import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';

import {User} from "../_models/user";
import {environment} from "../../environments/environment";
import {UserService} from "./user.service";

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient, private userService: UserService) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${environment.apiEndpoint}/users/login`, {username, password}, {observe: 'response'})
      .pipe(
        map((response: any) => {
            // login successful if there's a jwt token in the response header
            if (response.headers.get('Authorization')) {
              let user = response.body;
              if (user) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                user.token = response.headers.get('Authorization');
                localStorage.setItem('currentUser', JSON.stringify(user));
                this.currentUserSubject.next(user);
              }
              console.log(this.currentUserSubject);
            }

            return response;
          }
        ));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
