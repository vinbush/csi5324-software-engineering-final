export class SearchFilter {
  constructor()
  constructor(
    public street?: string,
    public unitNum?: string,
    public city?: string,
    public state?: string,
    public zipCode?: string,
    public listingTypes?: string[],
    public maxPrice?: number,
    public minPrice?: number,
    public maxPropertySize?: number,
    public minPropertySize?: number,
    public maxHouseSize?: number,
    public minHouseSize?: number,
    public maxNumBed?: number,
    public minNumBed?: number,
    public maxNumBath?: number,
    public minNumBath?: number,
    public numFloors?: number
  ) {}
}
