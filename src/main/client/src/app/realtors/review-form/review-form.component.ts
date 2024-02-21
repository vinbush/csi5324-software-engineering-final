import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ListingService } from '../../service/listing.service';
import { RealtorDisplay } from '../../class/realtor-display';
import { ReviewCreation } from '../../class/review-creation';
import { ReviewService } from '../../service/review.service';
import { RealtorService } from '../../service/realtor.service';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent implements OnInit {

  @Input() realtor: RealtorDisplay;
  review: ReviewCreation = new ReviewCreation();

  constructor(
    private route: ActivatedRoute,
    private reviewService: ReviewService,
    private realtorService: RealtorService,
    private router: Router
  ) {}

  submitReview(): void {
    this.review.realtorId = this.realtor.id;
    this.reviewService.addReview(this.review)
      .subscribe(result => {
        if (result) {
          this.router.navigate([`/realtor/${this.realtor.id}`]);
        }
      });
  }

  getRealtor(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.realtorService.getRealtor(id)
      .subscribe(result => this.realtor = result);
  }

  ngOnInit(): void {
    this.getRealtor();
  }

}
