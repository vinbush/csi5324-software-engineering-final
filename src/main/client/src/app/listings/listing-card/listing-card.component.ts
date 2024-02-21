import { Component, OnInit, Input } from '@angular/core';
import { Listing } from '../../class/listing';
import { DataService } from '../../service/data.service';
import { ListingService } from '../../service/listing.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-listing-card',
  templateUrl: './listing-card.component.html',
  styleUrls: ['./listing-card.component.css']
})
export class ListingCardComponent implements OnInit {

  @Input() listing: Listing;
  photo;
  hasPhoto = false;

  constructor(private dataService: DataService,
              private listingService: ListingService,
              private sanitizer: DomSanitizer) { }

  getFirstListingPhoto() {
    this.listingService.getListingPhotos(this.listing.id, true)
      .subscribe(result => {
        if (result.length > 0) {
          this.hasPhoto = true;
          const mimeType = result[0].contentType;
          this.photo = ('data:' + mimeType + ';base64,' + result[0].picture);
        } else {
          this.hasPhoto = false;
        }
      });
  }

  ngOnInit() {
    this.getFirstListingPhoto();
  }
}
