import { Component } from '@angular/core';
import { AuthService } from './service/auth.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'propertypro-ui';

  constructor(private app: AuthService, private http: HttpClient, private router: Router) {
    this.app.authenticate(undefined, undefined);
  }
}
