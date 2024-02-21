import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UserProfile } from '../../class/user-profile';
import { UserProfileService } from '../../service/user-profile.service';
import { InfoRequest } from '../../class/info-request';
import { Response } from '../../class/response';
import { AuthService } from '../../service/auth.service';
import { Stomp, Client, Message } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Offer } from '../../class/offer';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  private profile: UserProfile = new UserProfile();
  private chosenRequest: InfoRequest = new InfoRequest();
  private chosenResponse: Response = new Response();
  private modalShouldViewResponse: boolean;
  private stompClient = null;

  @ViewChild('openModalButton', {static: false}) openModalButton: ElementRef;
  @ViewChild('closeModalButton', {static: false}) closeModalButton: ElementRef;

  constructor(private profileService: UserProfileService, private auth: AuthService) { }

  ngOnInit() {
    this.getProfile();
    this.sockConnect();
  }

  getProfile() {
    this.profileService.getUserProfile()
      .subscribe(result => { this.profile  = result; });
  }

  respondToRequest(id: number, isOffer: boolean) {
    for (const offer of this.profile.offers) {
      console.log(offer.response);
    }
    this.modalShouldViewResponse = false;
    if (isOffer) {
      this.chosenRequest = this.profile.offers.filter(r => r.id === id)[0];
    } else {
      this.chosenRequest = this.profile.requests.filter(r => r.id === id)[0];
    }

    this.openModalButton.nativeElement.click();
  }

  readResponse(id: number, isOffer: boolean) {
    this.modalShouldViewResponse = true;
    if (isOffer) {
      this.chosenRequest = this.profile.offers.filter(r => r.id === id)[0];
    } else {
      this.chosenRequest = this.profile.requests.filter(r => r.id === id)[0];
    }
    this.chosenResponse = this.chosenRequest.response;

    this.openModalButton.nativeElement.click();
  }

  closeModal(input: any) {
    if (input) {
      this.closeModalButton.nativeElement.click();
    }
  }

  sockConnect() {
    const socket = new SockJS('http://' + window.location.host + '/propertypro-stomp');
    this.stompClient = Stomp.over(socket);

    let queuename;

    if (this.auth.isBuyer()) {
      queuename = '/user/queue/buyerprofile';
    } else if (this.auth.isRealtor()) {
      queuename = '/user/queue/realtorprofile';
    }

    this.stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);

      this.stompClient.subscribe(queuename, res => {
        console.log(res);
        const parse = JSON.parse(res.body);
        console.log(parse);
        if (parse.offerPrice) {
          this.profile.offers.unshift(parse);
        } else if (parse.requestId) {
          if (this.profile.offers.filter(r => r.id === parse.requestId).length > 0) {
            this.profile.offers.filter(r => r.id === parse.requestId)[0].response = parse;
          } else {
            this.profile.requests.filter(r => r.id === parse.requestId)[0].response = parse;
          }
        } else if (parse.id) {
          this.profile.requests.unshift(parse);
        }
      });
    });
  }
}
