import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { RealtorDisplay } from '../class/realtor-display';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { BaseHttpService } from './base-http.service';
import { Photo } from '../class/photo';

@Injectable({
  providedIn: 'root'
})
export class RealtorService extends BaseHttpService {
  private realtorUrl = environment.apiUrl + '/realtors';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/hal+json' })
  };

  constructor(private http: HttpClient) {
    super('RealtorService');
   }

  /** GET Realtor by id. Will 404 if id not found */
  getRealtor(id: number): Observable<RealtorDisplay> {
    const url = `${this.realtorUrl}/${id}`;

    return this.http.get<RealtorDisplay>(url).pipe(
      tap(_ => this.log(`fetched Realtor id=${id}`)),
      catchError(this.handleError<RealtorDisplay>(`getRealtor id=${id}`))
    );
  }

  getRealtorPhoto(id: number): Observable<Photo> {
    const url = `${this.realtorUrl}/${id}/photo`;

    return this.http.get<Photo>(url).pipe(
      tap(_ => this.log(`fetched photos for realtor id=${id}`)),
      catchError(this.handleError<Photo>(`getRealtorPhotos id=${id}`))
    );
  }
}
