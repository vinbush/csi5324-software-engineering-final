import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User } from '../class/user';
import { Observable } from 'rxjs';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root'
 })
export class AuthService {

  authenticated = false;
  userName = '';
  userAuthorities: string[] = [];

  constructor(private http: HttpClient, private router: Router, private alertService: AlertService) {}

  isRealtor(): boolean {
    return this.userAuthorities.indexOf('ROLE_REALTOR') > -1;
  }

  isBuyer(): boolean {
    return this.userAuthorities.indexOf('ROLE_BUYER') > -1;
  }

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(credentials ? {
        authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});
    console.log(credentials);

    this.http.get('api/auth/user', {headers})
      .subscribe(
        response => {
          if (response && response['name']) {
            this.authenticated = true;
            this.userName = response['name'];
            this.userAuthorities = response['authorities'];
            console.log('We authed with roles: ' + this.userAuthorities);
          } else {
            this.authenticated = false;
            this.userName = '';
            this.userAuthorities = [];
          }
        },
        error => {
          this.authenticated = false;
          this.userName = '';
          this.userAuthorities = [];
          this.alertService.addAlert('Login failed!', 3);
          console.log('Authentication failed: 401' + error);
        },
        () => {
          return callback && callback();
        }
      );
  }

  logout() {
    // this.http.post('logout', {}).finally(() => {
    //     this.app.authenticated = false;
    //     this.router.navigateByUrl('/login');
    // }).subscribe();
    this.http.post('logout', {}).pipe(
      finalize(() => {
        this.authenticated = false;
        this.userAuthorities = [];
        this.router.navigateByUrl('/login');
      })
    ).subscribe();
  }

  register(formData: FormData, userType: string) {
    return this.http.post<object>('api/auth/register/' + userType, formData);
  }

  // register(user: User, userType: string) {
  //   return this.http.post<object>('api/auth/register/' + userType, user);
  // }
}
