import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Listing } from '../class/listing';
import { environment } from '../../environments/environment';
import { SearchFilter } from '../class/search-filter';
import { BaseHttpService } from './base-http.service';
import { InfoRequestCreation } from '../class/info-request-creation';
import { InfoRequest } from '../class/info-request';
import { OfferCreation } from '../class/offer-creation';
import { Offer } from '../class/offer';
import { Photo } from '../class/photo';
import { ResponseCreation } from '../class/response-creation';
import { Response } from '../class/response';
import { FavoriteCreation } from '../class/favorite-creation';
import { Favorite } from '../class/favorite';

@Injectable({
  providedIn: 'root'
})
export class ListingService extends BaseHttpService {
  private listingUrl = environment.apiUrl + '/listings';

  constructor(private http: HttpClient) {
    super('ListingService');
  }

  /** POST a search request and get listings from the server */
  searchListings(filter: SearchFilter): Observable<Listing[]> {

    return this.http.post<Listing[]>(this.listingUrl + '/search', filter, this.httpOptions)
      .pipe(
        map((response: Listing[]) => {
          let listings: Listing[] = [];
          for (let i = 0; i < response.length; i++) {
            listings[i] = Object.assign(new Listing(), response[i] as Listing)
          }
          return listings;
        }),
        tap(_ => this.log('fetched Listings')),
        catchError(this.handleError<Listing[]>('getListings', []))
      );
  }

  /** GET Listing by id. Return `undefined` when id not found */
  getListingNo404<Data>(id: number): Observable<Listing> {
    const url = `${this.listingUrl}/?id=${id}`;
    return this.http.get<Listing[]>(url)
      .pipe(
        map(Listings => Listings[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} Listing id=${id}`);
        }),
        catchError(this.handleError<Listing>(`getListing id=${id}`))
      );
  }

  /** GET Listing by id. Will 404 if id not found */
  getListing(id: number): Observable<Listing> {
    const url = `${this.listingUrl}/${id}`;

    return this.http.get<Listing>(url).pipe(
      tap(_ => this.log(`fetched Listing id=${id}`)),
      catchError(this.handleError<Listing>(`getListing id=${id}`))
    );
  }

  getListingPhotos(id: number, firstOnly: boolean = false): Observable<Photo[]> {
    const url = `${this.listingUrl}/${id}/photos`;
    const params = new HttpParams().set('firstOnly', String(firstOnly));

    // tslint:disable-next-line:object-literal-shorthand
    return this.http.get<Photo[]>(url, {params: params}).pipe(
      tap(_ => this.log(`fetched photos for listing id=${id}`)),
      catchError(this.handleError<Photo[]>(`getListingPhotos id=${id}`))
    );
  }

  //////// Save methods //////////

  /** POST: add a new Listing to the server */
  addListing(formData: FormData): Observable<Listing> {
    return this.http.post<Listing>(this.listingUrl, formData).pipe(
      tap((newListing: Listing) => this.log(`added Listing w/ id=${newListing.id}`)),
      catchError(this.handleError<Listing>('addListing'))
    );
  }

  /** DELETE: delete the Listing from the server */
  deleteListing(listing: Listing | number): Observable<Listing> {
    const id = typeof listing === 'number' ? listing : listing.id;
    const url = `${this.listingUrl}/${id}`;

    return this.http.delete<Listing>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted Listing id=${id}`)),
      catchError(this.handleError<Listing>('deleteListing'))
    );
  }

  /** PUT: update the Listing on the server */
  updateListing(listing: Listing): Observable<any> {
    return this.http.put(this.listingUrl, listing, this.httpOptions).pipe(
      tap(_ => this.log(`updated Listing id=${listing.id}`)),
      catchError(this.handleError<any>('updateListing'))
    );
  }

  ///////// Related entities: requests, offers, favorites //////////

  /** POST: make a request */
  makeRequest(request: InfoRequestCreation, listingId: number): Observable<InfoRequest> {
    const url = `${this.listingUrl}/${listingId}/request`;

    return this.http.post<InfoRequest>(url, request, this.httpOptions).pipe(
      tap((newRequest: InfoRequest) => this.log(`added InfoRequest w/ id=${newRequest.id}`)),
      catchError(this.handleError<InfoRequest>('makeRequest'))
    );
  }

  /** POST: make a offer */
  makeOffer(offer: OfferCreation, listingId: number): Observable<Offer> {
    const url = `${this.listingUrl}/${listingId}/offer`;

    return this.http.post<Offer>(url, offer, this.httpOptions).pipe(
      tap((newOffer: Offer) => this.log(`added Offer w/ id=${newOffer.id}`)),
      catchError(this.handleError<Offer>('makeRequest'))
    );
  }

  /** POST: add listing to favorites */
  addFavorite(fav: FavoriteCreation, listingId: number): Observable<Favorite> {
    const url = `${this.listingUrl}/${listingId}/favorite`;

    return this.http.post<Favorite>(url, fav, this.httpOptions).pipe(
      tap((newFavorite: Favorite) => this.log(`added Favorite w/ id=${newFavorite.id}`)),
      catchError(this.handleError<Favorite>('makeRequest'))
    );
  }
}

/*
Based on code from Angular tutorial, license below:
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
