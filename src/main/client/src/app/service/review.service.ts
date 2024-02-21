import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { BaseHttpService } from './base-http.service';
import { ReviewCreation } from '../class/review-creation';
import { Review } from '../class/review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService extends BaseHttpService {
  private reviewUrl = environment.apiUrl + '/reviews';

  constructor(private http: HttpClient) {
    super('ReviewService');
  }

  /** POST: add a new review to the server */
  addReview(review: ReviewCreation): Observable<Review> {
    return this.http.post<Review>(this.reviewUrl, review, this.httpOptions).pipe(
      tap((newReview: Review) => this.log(`added Review w/ id=${newReview.id}`)),
      catchError(this.handleError<Review>('addReview'))
    );
  }

  getReviewsByRealtorId(id: number): Observable<Review[]> {
    const url = `${this.reviewUrl}/realtor/${id}`;
    return this.http.get<Review[]>(url).pipe(
      tap(res => this.log(`fetched ${res.length} reviews for Realtor id=${id}`)),
      catchError(this.handleError<Review[]>(`getReviewsByRealtorId id=${id}`, []))
    );
  }
}
