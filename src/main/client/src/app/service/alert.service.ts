import { Injectable } from '@angular/core';
import { Alert } from '../class/alert';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  // tslint:disable-next-line:variable-name
  private alerts: Alert[] = [];

  public getAlerts(): Alert[] {
    return this.alerts;
  }

  public addAlert(message: string, alertLevel: number, routerLink: string = null) {
    const alert = new Alert();
    alert.alertLevel = alertLevel;
    alert.message = message;
    alert.routerLink = routerLink;
    this.alerts.push(alert);
  }

  public dismissAlert(index: number) {
    this.alerts.splice(index, 1);
  }

  constructor() { }
}
