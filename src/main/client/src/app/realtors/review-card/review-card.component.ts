import { Component, OnInit, Input } from '@angular/core';
import { Review } from '../../class/review';

@Component({
  selector: 'app-review-card',
  templateUrl: './review-card.component.html',
  styleUrls: ['./review-card.component.css']
})
export class ReviewCardComponent {

  @Input() review: Review;

  constructor() { }

}
