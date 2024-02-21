import { Component, OnInit, Input } from '@angular/core';
import { InfoRequestCreation } from '../../class/info-request-creation';
import { ListingService } from '../../service/listing.service';
import { Listing } from '../../class/listing';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from '../../service/alert.service';

@Component({
  selector: 'app-request-form',
  templateUrl: './request-form.component.html',
  styleUrls: ['./request-form.component.css']
})
export class RequestFormComponent implements OnInit {
  @Input() listing: Listing = new Listing();
  request: InfoRequestCreation = new InfoRequestCreation();

  constructor(private route: ActivatedRoute, private listingService: ListingService, private router: Router,
              private alertService: AlertService) { }

  getListing(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.listingService.getListing(id)
      .subscribe(result => this.listing = result);
  }

  ngOnInit(): void {
    this.getListing();
  }

  submitRequest() {
    this.listingService.makeRequest(this.request, this.listing.id)
      .subscribe(result => {
        if (result) {
          this.alertService.addAlert('Request created!', 1);
          this.router.navigate([`/listing/${this.listing.id}`]);
        }
      });
  }
}
