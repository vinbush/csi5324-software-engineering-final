import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Buyer } from '../class/buyer';
import { Realtor } from '../class/realtor';
import { User } from '../class/user';
import { Router } from '@angular/router';
import { AlertService } from '../service/alert.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(private authService: AuthService, private router: Router, private alertService: AlertService) { }

  userType: string;

  buyer: Buyer = new Buyer('', '', '', '', '', '', '', '', '', '', '', 0);
  realtor: Realtor = new Realtor('', '', '', '', '', '', '', '', '', '', '', 0, '', '');

  file: File;
  @ViewChild('fileUpload', {static: false}) fileUpload: ElementRef;

  onFileChanged(event) {
    this.file = event.target.files[0];
  }

  createUser() {
    let user: User;
    const formData = new FormData();
    if (this.userType === 'buyer') {
      user = this.buyer;
    } else if (this.userType === 'realtor') {
      user = this.realtor;
      formData.append('file', this.file, this.file.name);
    }

    formData.append('user', JSON.stringify(user));

    this.authService.register(formData, this.userType)
      .subscribe(
        response => {
          console.log(response);
          this.alertService.addAlert('Registered user ' + user.username, 1);
          this.router.navigateByUrl('/login');
        },
        error => {
          console.log(error);
        }
      );
  }

}
