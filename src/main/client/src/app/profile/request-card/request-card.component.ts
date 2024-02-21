import { Component, OnInit, Input } from '@angular/core';
import { InfoRequest } from '../../class/info-request';

@Component({
  selector: 'app-request-card',
  templateUrl: './request-card.component.html',
  styleUrls: ['./request-card.component.css']
})
export class RequestCardComponent {

  @Input() request: InfoRequest;

  expandedText = false;

  toggleText() {
    this.expandedText = !this.expandedText;
  }

  constructor() { }
}
