import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  public readonly LISTING_TYPES: Map<string, string>
    = new Map([
      ['HOUSE', 'House'],
      ['APT', 'Apartment'],
      ['CONDO', 'Condo'],
      ['TOWNHOME', 'Townhome']
    ]);

  constructor() { }
}
