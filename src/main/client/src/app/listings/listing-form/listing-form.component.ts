import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';

import { Listing } from '../../class/listing';
import { ListingService } from '../../service/listing.service';
import { DataService } from '../../service/data.service';
import { AlertService } from '../../service/alert.service';

declare var google: any;

@Component({
  selector: 'app-listing-form',
  templateUrl: './listing-form.component.html',
  styleUrls: ['./listing-form.component.css']
})
export class ListingFormComponent implements OnInit {

  constructor(
    private listingService: ListingService,
    private dataService: DataService,
    private ref: ChangeDetectorRef,
    private alertService: AlertService
  ) {}

  model: Listing = new Listing();
  isSubmitting = false;
  hasLink = false;
  linkId: number;
  files: FileList;
  autocomplete: any; // google maps api autocomplete input
  hasAddress = false; // disable address input fields until they use autocomplete

  addressMapping = {
    street_number: {
      format: 'short_name',
      value: ''
    },
    route: {
      format: 'long_name',
      value: ''
    },
    locality: {
      format: 'long_name',
      value: ''
    },
    administrative_area_level_1: {
      format: 'short_name',
      value: ''
    },
    postal_code: {
      format: 'short_name',
      value: ''
    },
  };

  @ViewChild('fileUpload', {static: false}) fileUpload: ElementRef;

  save(): void {
    const formData = new FormData();
    formData.append('listing', JSON.stringify(this.model));

    if (!this.files || this.files.length === 0) {
      this.alertService.addAlert('You must upload at least 1 photo!', 3);
      return;
    }

    Array.from(this.files).forEach(file => {
      formData.append('files', file, file.name);
    });
    this.isSubmitting = true;
    this.listingService.addListing(formData)
      .subscribe((returnListing: Listing) => {
        this.isSubmitting = false;
        if (returnListing && returnListing.id) {
          this.linkId = returnListing.id;
          this.alertService.addAlert("Listing created! View it here", 1, "/listing/" + this.linkId);
        }
        // this.hasLink = true;
      });
  }

  onFileChanged(event) {
    this.files = event.target.files;
  }

  ngOnInit() {
    this.autocomplete = new google.maps.places.Autocomplete(
      document.getElementById('autocomplete'), {types: ['address']});

    this.autocomplete.setFields(['address_component', 'geometry']);
    this.autocomplete.setComponentRestrictions({'country': 'us'});

    this.autocomplete.addListener('place_changed', this.fillInAddress.bind(this));

    // this.fileUpload.nativeElement.onchange = () => {
    //   this.files = this.fileUpload.nativeElement.files;
    // };
  }

  fillInAddress() {
    this.hasAddress = true;
    const place = this.autocomplete.getPlace();
    console.log(place);

    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < place.address_components.length; i++) {
      const addressType = place.address_components[i].types[0];
      // check if this is a component we care about
      if (this.addressMapping[addressType]) {
        // store the value based on the desired format
        this.addressMapping[addressType].value = place.address_components[i][this.addressMapping[addressType].format];
      }
    }

    this.model.street = this.addressMapping.street_number.value + ' ' + this.addressMapping.route.value;
    this.model.city = this.addressMapping.locality.value;
    this.model.state = this.addressMapping.administrative_area_level_1.value;
    this.model.zipCode = this.addressMapping.postal_code.value;
    this.model.latitude = place.geometry.location.lat();
    this.model.longitude = place.geometry.location.lng();
    this.ref.detectChanges();
  }
}
