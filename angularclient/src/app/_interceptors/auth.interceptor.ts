import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthenticationService } from '../_services/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private authenticationService: AuthenticationService;

  constructor(authenticationService: AuthenticationService) {
    this.authenticationService = authenticationService;
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.token) {
      req = req.clone({
        setHeaders: {
          'Authorization': currentUser.token,
          'Content-Type': 'application/json',
        }
      });
    }

    return next.handle(req);
  }
}
