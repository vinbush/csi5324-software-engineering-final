export class Listing {
  public id: number;
  public isUserFavorite: boolean;
  constructor(
    public title?: string,
    public description?: string,
    public listingType?: string,
    public listingPrice?: number,
    public propertySize?: number,
    public houseSize?: number,
    public numBed?: number,
    public numBath?: number,
    public basement?: boolean,
    public numFloors?: number,
    public latitude?: number,
    public longitude?: number,
    public street?: string,
    public unitNum?: string,
    public city?: string,
    public state?: string,
    public zipCode?: string,
    public realtorId?: number,
    public realtorName?: string,
    public realtorAgency?: string
  ) { }

  public getAddress() {
    return this.street +
           (this.unitNum && this.unitNum !== '' ? ' ' + this.unitNum : '') +
           ', ' +
           this.city + ', ' + this.state + ' ' + this.zipCode;
  }
}
