import { Response } from './response';

export class InfoRequest {
  public id: number;
  public createdAt: Date;
  public textBody: string;
  public hasResponse: boolean;
  public listingId: number;
  public buyerName: string;
  public listingTitle: string;
  public response: Response;
  constructor() {}
}
