import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListingDetailsComponent } from './listings/listing-details/listing-details.component';
import { ListingFormComponent } from './listings/listing-form/listing-form.component';
import { LoginComponent } from './login/login.component';
import { RoleGuardService } from './guards/role-guard.service';
import { RegisterComponent } from './register/register.component';
import { ListingSearchComponent } from './listings/listing-search/listing-search.component';
import { RealtorDetailComponent } from './realtors/realtor-detail/realtor-detail.component';
import { ReviewFormComponent } from './realtors/review-form/review-form.component';
import { UserProfileComponent } from './profile/user-profile/user-profile.component';
import { RequestFormComponent } from './listings/request-form/request-form.component';
import { OfferFormComponent } from './listings/offer-form/offer-form.component';
import { HomeComponent } from './home/home.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'addlisting', component: ListingFormComponent, canActivate: [RoleGuardService], data: {role: 'ROLE_REALTOR'} },
  { path: 'listing/:id/request', component: RequestFormComponent, canActivate: [RoleGuardService], data: {role: 'ROLE_BUYER'} },
  { path: 'listing/:id/offer', component: OfferFormComponent, canActivate: [RoleGuardService], data: {role: 'ROLE_BUYER'} },
  { path: 'listing/:id', component: ListingDetailsComponent },
  { path: 'realtor/:id/review', component: ReviewFormComponent, canActivate: [RoleGuardService], data: {role: 'ROLE_BUYER'} },
  { path: 'realtor/:id', component: RealtorDetailComponent },
  { path: 'search', component: ListingSearchComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: UserProfileComponent, canActivate: [RoleGuardService] },
  { path: 'register', component: RegisterComponent },
  { path: '', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
