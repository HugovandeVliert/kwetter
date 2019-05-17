import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from "ngx-toastr";
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AuthenticationService } from "../_services/authentication.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService, private toastrService: ToastrService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(error => {
      // if (error.status === 403) {
      //   // auto logout if 403 response returned from api
      //   this.authenticationService.logout();
      //   location.reload();
      // }

      if (error.status === 0) {
        this.toastrService.error('Could not connect to server')
      }

      return throwError(error);
    }))
  }
}
