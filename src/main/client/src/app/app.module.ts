import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injectable } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  HttpClientModule, HttpEvent, HttpInterceptor, HttpRequest, HttpHandler, HTTP_INTERCEPTORS, HttpClientXsrfModule, HttpErrorResponse
} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { ListingFormComponent } from './listings/listing-form/listing-form.component';
import { ListingDetailsComponent } from './listings/listing-details/listing-details.component';
import { LoginComponent } from './login/login.component';
import { AuthService } from './service/auth.service';
import { RoleGuardService } from './guards/role-guard.service';
import { RegisterComponent } from './register/register.component';
import { ListingSearchComponent } from './listings/listing-search/listing-search.component';
import { ListingCardComponent } from './listings/listing-card/listing-card.component';
import { RealtorDetailComponent } from './realtors/realtor-detail/realtor-detail.component';
import { ReviewFormComponent } from './realtors/review-form/review-form.component';
import { ReviewCardComponent } from './realtors/review-card/review-card.component';
import { UserProfileComponent } from './profile/user-profile/user-profile.component';
import { RequestCardComponent } from './profile/request-card/request-card.component';
import { OfferCardComponent } from './profile/offer-card/offer-card.component';
import { RequestFormComponent } from './listings/request-form/request-form.component';
import { OfferFormComponent } from './listings/offer-form/offer-form.component';
import { StarRatingComponent } from './realtors/star-rating/star-rating.component';
import { ResponseFormComponent } from './profile/response-form/response-form.component';
import { HomeComponent } from './home/home.component';
import { AlertDisplayComponent } from './alert-display/alert-display.component';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AlertService } from './service/alert.service';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router, private alertService: AlertService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(req.url);
    if (req.url.indexOf('api/auth/user') > -1) {
      return next.handle(req);
    }
    return next.handle(req).pipe(
      catchError( (error) => {
        if (error instanceof HttpErrorResponse) {
          if (error.error instanceof ErrorEvent) {
              console.error('Error Event: ' + error.error);
          } else {
              console.log(`error status : ${error.status} ${error.statusText}`);
              switch (error.status) {
                  case 401:      // login
                    this.alertService.addAlert('You must be logged in to do that', 2);
                    this.router.navigateByUrl('/login');
                    break;
                  case 403:     // forbidden
                    this.alertService.addAlert('You aren\'t allowed to do that!', 3);
                    this.router.navigateByUrl('/');
                    break;
                  case 400:
                    this.alertService.addAlert(error.error, 3)
                    break;
                  default:
                    this.alertService.addAlert('Something went wrong!', 3);
                    break;
              }
          }
        } else {
            console.error('Unknown error');
        }
        return throwError(error);
      })
    );
  }
}

@NgModule({
  declarations: [
    AppComponent,
    MainMenuComponent,
    ListingFormComponent,
    ListingDetailsComponent,
    LoginComponent,
    RegisterComponent,
    ListingSearchComponent,
    ListingCardComponent,
    RealtorDetailComponent,
    ReviewFormComponent,
    ReviewCardComponent,
    UserProfileComponent,
    RequestCardComponent,
    OfferCardComponent,
    RequestFormComponent,
    OfferFormComponent,
    StarRatingComponent,
    ResponseFormComponent,
    HomeComponent,
    AlertDisplayComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    HttpClientXsrfModule
  ],
  providers: [RoleGuardService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
