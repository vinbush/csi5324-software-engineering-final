import { InfoRequest } from './info-request';

export class Offer extends InfoRequest {
  public offerPrice: number;
  constructor() {
    super();
  }
}
