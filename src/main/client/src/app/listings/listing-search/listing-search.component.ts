import { Component, OnInit } from '@angular/core';
import { SearchFilter } from '../../class/search-filter';
import { DataService } from '../../service/data.service';
import { Listing } from '../../class/listing';
import { ListingService } from '../../service/listing.service';
import { SearchTermsService } from '../../service/search-terms.service';
import { AlertService } from '../../service/alert.service';

declare var google: any;

@Component({
  selector: 'app-listing-search',
  templateUrl: './listing-search.component.html',
  styleUrls: ['./listing-search.component.css']
})
export class ListingSearchComponent implements OnInit {

  constructor(private dataService: DataService,
              private listingService: ListingService,
              private searchTermsService: SearchTermsService,
              private alertService: AlertService) { }

  private searchResults: Listing[] = [];

  get filter(): SearchFilter {
    return this.searchTermsService.searchFilter;
  }
  set filter(filter: SearchFilter) {
    this.searchTermsService.searchFilter = filter;
  }

  get hasResult(): boolean {
    return this.searchTermsService.hasResults;
  }

  set hasResult(value: boolean) {
    this.searchTermsService.hasResults = value;
  }

  private failedSearch = false;

  private selectedTypes: string[] = [];

  private map: any; // google map
  private mapMarkers = []; // markers for properties on the map
  private infowindow = new google.maps.InfoWindow({content: ''}); // popup for map markers
  private autocomplete: any; // google maps api autocomplete input (for searching)

  // holds results of google maps autocomplete search
  private addressMapping = {
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

  searchSubmit(): void {
    const typesToAdd = [];
    for (const type of this.selectedTypes) {
      if (type && type !== '') {
        typesToAdd.push(type);
      }
    }

    const modifiedFilter = this.filter;
    modifiedFilter.listingTypes = typesToAdd;
    this.filter = modifiedFilter;

    this.search();
  }

  private search() {
    this.listingService.searchListings(this.filter).subscribe(
      result => {
        this.searchResults = result;
        this.clearMarkers();
        if (this.searchResults.length > 0) {
          this.hasResult = true;
          this.failedSearch = false;

          // bounding for the map
          const latlngbounds = new google.maps.LatLngBounds();

          // handle map stuff for each search result
          for (let i = 0; i < this.searchResults.length; i++) {
            const latLng = new google.maps.LatLng(
              this.searchResults[i].latitude,
              this.searchResults[i].longitude
            );

            // add point to bounds
            latlngbounds.extend(latLng);

            // build map marker, with some listing info in it
            const marker = new google.maps.Marker({
              position: latLng,
              map: this.map,
              title: '$' + this.searchResults[i].listingPrice,
              listingId: this.searchResults[i].id,
              listingTitle: this.searchResults[i].title,
              address: this.searchResults[i].getAddress()
            });

            // register a listener to open a window when the marker is clicked
            marker.addListener('click', () => {
              this.infowindow.setContent(
                // `<a [routerLink]="['/listing', ${marker.listingId}]">${marker.listingTitle}</a>` +
                `<h5>${marker.listingTitle}</h5>` +
                `<p>${marker.address}</p>` +
                `<p>${marker.title}</p>`
              );
              this.infowindow.open(this.map, marker);
            });

            this.mapMarkers.push(marker);
          }

          // adjust the map to the bounding
          this.map.setCenter(latlngbounds.getCenter());
          this.map.fitBounds(latlngbounds);

        } else {
          this.hasResult = false;
          this.failedSearch = true;
        }
      }
    );
  }

  clearMarkers() {
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.mapMarkers.length; i++ ) {
      this.mapMarkers[i].setMap(null);
    }
    this.mapMarkers = [];
  }

  setLocationSearchTerms() {
    const place = this.autocomplete.getPlace();

    // clear out old search
    this.addressMapping.street_number.value = '';
    this.addressMapping.route.value = '';
    this.addressMapping.locality.value = '';
    this.addressMapping.administrative_area_level_1.value = '';
    this.addressMapping.postal_code.value = '';

    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < place.address_components.length; i++) {
      const addressType = place.address_components[i].types[0];
      // check if this is a component we care about
      if (this.addressMapping[addressType]) {
        // store the value based on the desired format
        this.addressMapping[addressType].value = place.address_components[i][this.addressMapping[addressType].format];
      }
    }

    const modifiedFilter = this.filter;
    modifiedFilter.street = this.addressMapping.street_number.value && this.addressMapping.route.value ?
      this.addressMapping.street_number.value + ' ' + this.addressMapping.route.value : '';
    modifiedFilter.city = this.addressMapping.locality.value;
    modifiedFilter.state = this.addressMapping.administrative_area_level_1.value;
    modifiedFilter.zipCode = this.addressMapping.postal_code.value;
    this.filter = modifiedFilter;
  }

  ngOnInit(): void {
    console.log(this.selectedTypes);
    this.selectedTypes = this.filter.listingTypes;
    console.log(this.selectedTypes);
    if (this.hasResult) {
      this.search();
    }

    this.map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: 41.752189, lng: -92.711301},
      zoom: 8,
      mapTypeControl: false,
      fullscreenControl: false
    });

    this.autocomplete = new google.maps.places.Autocomplete(
      document.getElementById('autocomplete'), {types: ['geocode']});

    this.autocomplete.setFields(['address_component']);
    this.autocomplete.setComponentRestrictions({'country': 'us'});

    this.autocomplete.addListener('place_changed', this.setLocationSearchTerms.bind(this));
  }
}
