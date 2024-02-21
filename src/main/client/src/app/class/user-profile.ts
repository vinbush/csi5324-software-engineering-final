import { Offer } from './offer';
import { InfoRequest } from './info-request';

export class UserProfile {
  constructor()
  constructor(
    public requests?: InfoRequest[],
    public offers?: Offer[]
  ) {}
}
