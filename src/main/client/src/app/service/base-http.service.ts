import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from 'selenium-webdriver/http';
import { Observable, of } from 'rxjs';

export class BaseHttpService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/hal+json' })
  };

  constructor(protected serviceName: string) { }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  protected handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.error}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ListingService message with the MessageService */
  protected log(message: string) {
    console.log(`${this.serviceName}: ${message}`);
  }
}
