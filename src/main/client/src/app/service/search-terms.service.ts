import { Injectable } from '@angular/core';
import { SearchFilter } from '../class/search-filter';

@Injectable({
  providedIn: 'root'
})
export class SearchTermsService {
  searchFilter: SearchFilter = new SearchFilter();
  hasResults = false;

  constructor() {
    this.searchFilter.listingTypes = [];
   }
}
