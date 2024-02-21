import { Injectable } from '@angular/core';
import { BaseHttpService } from './base-http.service';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../class/user-profile';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService extends BaseHttpService {
  private profileUrl = environment.apiUrl + '/profile';

  constructor(private http: HttpClient) {
    super('UserProfileService');
  }

  getUserProfile(): Observable<UserProfile> {
    const url = `${this.profileUrl}`;

    return this.http.get<UserProfile>(url).pipe(
      tap(_ => {this.log(`fetched Profile`); console.log(_); }),
      catchError(this.handleError<UserProfile>(`getProfile`))
    );
  }
}
