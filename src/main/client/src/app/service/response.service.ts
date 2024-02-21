import { Injectable } from '@angular/core';
import { BaseHttpService } from './base-http.service';
import { environment } from '../../environments/environment';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseCreation } from '../class/response-creation';
import { catchError, tap } from 'rxjs/operators';
import { Response } from '../class/response';

@Injectable({
  providedIn: 'root'
})
export class ResponseService extends BaseHttpService {
  private responseUrl = environment.apiUrl + '/responses';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/hal+json' })
  };

  constructor(private http: HttpClient) {
    super('ResponseService');
  }

  /** POST: respond to a request */
  makeResponse(response: ResponseCreation): Observable<Response> {
    return this.http.post<Response>(this.responseUrl, response, this.httpOptions).pipe(
      tap((newResponse: Response) => this.log(`added Response w/ id=${newResponse.id}`)),
      catchError(this.handleError<Response>('makeResponse'))
    );
  }
}
