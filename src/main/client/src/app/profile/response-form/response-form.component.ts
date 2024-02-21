import { Component, Input, OnChanges, EventEmitter, Output } from '@angular/core';
import { InfoRequest } from '../../class/info-request';
import { Offer } from '../../class/offer';
import { ResponseCreation } from '../../class/response-creation';
import { ResponseService } from '../../service/response.service';
import { AlertService } from '../../service/alert.service';

@Component({
  selector: 'app-response-form',
  templateUrl: './response-form.component.html',
  styleUrls: ['./response-form.component.css']
})
export class ResponseFormComponent implements OnChanges {
  @Input() request: InfoRequest;
  response: ResponseCreation = new ResponseCreation();
  isOffer: boolean;
  @Output() responseSent = new EventEmitter<boolean>(); // tell the parent to close the modal

  constructor(private responseService: ResponseService, private alertService: AlertService) { }

  ngOnChanges() {
    this.response = new ResponseCreation();
    // tslint:disable-next-line:no-string-literal
    if (this.request['offerPrice']) {
      this.isOffer = true;
    } else {
      this.isOffer = false;
    }
  }

  sendResponse() {
    this.response.originalRequestId = this.request.id;
    this.response.isOffer = this.isOffer;
    this.responseService.makeResponse(this.response)
      .subscribe(result => {
        if (result) {
          this.alertService.addAlert('Resonse sent successfully!', 1);
          this.responseSent.emit(true);
        }
      });
  }

}
