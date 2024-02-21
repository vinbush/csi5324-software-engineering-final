import { Component, OnInit } from '@angular/core';
import { AlertService } from '../service/alert.service';
import { Alert } from '../class/alert';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-alert-display',
  templateUrl: './alert-display.component.html',
  styleUrls: ['./alert-display.component.css']
})
export class AlertDisplayComponent implements OnInit {

  getAlertClass(level: number) {
    let className;
    switch (level) {
      case 0:
        className = 'alert-info';
        break;
      case 1:
        className = 'alert-success';
        break;
      case 2:
        className = 'alert-warning';
        break;
      case 3:
        className = 'alert-danger';
        break;
      default:
        className = 'alert-info';
        break;
    }

    return className;
  }

  constructor(private alertService: AlertService, private sanitizer: DomSanitizer) { }

  ngOnInit() {
  }

}
