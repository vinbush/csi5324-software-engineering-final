import { Component, OnInit, Input } from '@angular/core';
import { Offer } from '../../class/offer';

@Component({
  selector: 'app-offer-card',
  templateUrl: './offer-card.component.html',
  styleUrls: ['./offer-card.component.css']
})
export class OfferCardComponent {

  @Input() offer: Offer;

  constructor() { }

}
