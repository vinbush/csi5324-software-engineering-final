import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Listing } from '../../class/listing';
import { ListingService } from '../../service/listing.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthService } from '../../service/auth.service';
import { FavoriteCreation } from '../../class/favorite-creation';

@Component({
  selector: 'app-listing-details',
  templateUrl: './listing-details.component.html',
  styleUrls: ['./listing-details.component.css']
})
export class ListingDetailsComponent implements OnInit {
  @Input() listing: Listing = new Listing();
  listingPhotos = [];
  favoriteNote: string;
  @ViewChild('openModalButton', {static: false}) openModalButton: ElementRef;
  @ViewChild('closeModalButton', {static: false}) closeModalButton: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private listingService: ListingService,
    private sanitizer: DomSanitizer,
    private auth: AuthService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getListing();
    this.getListingPhotos();
  }

  getListing(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.listingService.getListing(id)
      .subscribe(result => {this.listing = result; console.log(result); });
  }

  getListingPhotos(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.listingService.getListingPhotos(id)
      .subscribe(result => {
        // tslint:disable-next-line:prefer-for-of
        for (let i = 0; i < result.length; i++) {
          const mimeType = result[i].contentType;
          this.listingPhotos.push('data:' + mimeType + ';base64,' + result[i].picture);
        }
      });
  }

  addFavorite() {
    const favoriteCreation = new FavoriteCreation();
    favoriteCreation.note = this.favoriteNote;
    this.listingService.addFavorite(favoriteCreation, this.listing.id)
      .subscribe(result => {
        if (result.id) {
          console.log(result);
          this.closeModal();
          this.listing.isUserFavorite = true;
        }
      });
  }

  openFavoriteModal() {
    this.openModalButton.nativeElement.click();
  }

  closeModal() {
    this.closeModalButton.nativeElement.click();
  }

  goBack(): void {
    this.location.back();
  }

 save(): void {
    this.listingService.updateListing(this.listing)
      .subscribe(() => this.goBack());
  }

}
