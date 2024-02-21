import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { AlertService } from '../service/alert.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    const result = this.authenticateWrapper().then(() => {
      console.log('Role guard says: authenticated = ' + this.authService.authenticated);
      if (!this.authService.authenticated) {
        this.alertService.addAlert('You must be logged in to do that', 2);
        this.router.navigate(['/login']);
        return false;
      }

      if (!route.data.role || this.authService.userAuthorities.find(role => role === route.data.role)) {
        return true;
      }

      this.alertService.addAlert("You aren't allowed to do that!", 3);
      this.router.navigate(['/']);
      return false;
    });
    return result;
  }

  private async authenticateWrapper() {
    return await new Promise((resolve, reject) =>  {
      this.authService.authenticate(undefined, (res) => {
        resolve(res);
      });
    });
  }

  constructor(private authService: AuthService, private alertService: AlertService, private router: Router) { }
}
