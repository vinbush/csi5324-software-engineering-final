import { Component, OnInit, Input } from '@angular/core';
import { Listing } from '../../class/listing';
import { OfferCreation } from '../../class/offer-creation';
import { ActivatedRoute, Router } from '@angular/router';
import { ListingService } from '../../service/listing.service';
import { AlertService } from '../../service/alert.service';

@Component({
  selector: 'app-offer-form',
  templateUrl: './offer-form.component.html',
  styleUrls: ['./offer-form.component.css']
})
export class OfferFormComponent implements OnInit {
  @Input() listing: Listing = new Listing();
  offer: OfferCreation = new OfferCreation();

  constructor(private route: ActivatedRoute, private listingService: ListingService, private router: Router,
              private alertService: AlertService) { }

  getListing(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.listingService.getListing(id)
      .subscribe(result => this.listing = result);
  }

  ngOnInit(): void {
    this.getListing();
  }

  submitOffer() {
    this.listingService.makeOffer(this.offer, this.listing.id)
      .subscribe(result => {
        if (result) {
          this.alertService.addAlert('Offer created!', 1);
          this.router.navigate([`/listing/${this.listing.id}`]);
        }
      });
  }
}
