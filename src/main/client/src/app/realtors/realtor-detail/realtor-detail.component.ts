import { Component, OnInit, Input } from '@angular/core';
import { Realtor } from '../../class/realtor';
import { ActivatedRoute } from '@angular/router';
import { RealtorService } from '../../service/realtor.service';
import { RealtorDisplay } from '../../class/realtor-display';
import { Location } from '@angular/common';
import { ReviewService } from '../../service/review.service';
import { Review } from '../../class/review';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-realtor-detail',
  templateUrl: './realtor-detail.component.html',
  styleUrls: ['./realtor-detail.component.css']
})
export class RealtorDetailComponent implements OnInit {

  @Input() realtor: RealtorDisplay;
  reviews: Review[];
  photo;

  constructor(
    private route: ActivatedRoute,
    private realtorService: RealtorService,
    private reviewService: ReviewService,
    private sanitizer: DomSanitizer,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getRealtor();
    this.getPhoto();
    this.getReviews();
  }

  getRealtor(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.realtorService.getRealtor(id)
      .subscribe(result => this.realtor  = result);
  }

  getReviews(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.reviewService.getReviewsByRealtorId(id)
      .subscribe(result => this.reviews = result);
  }

  getPhoto(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.realtorService.getRealtorPhoto(id)
      .subscribe(result => {
        const mimeType = result.contentType;
        this.photo = ('data:' + mimeType + ';base64,' + result.picture);
      });
  }
}
