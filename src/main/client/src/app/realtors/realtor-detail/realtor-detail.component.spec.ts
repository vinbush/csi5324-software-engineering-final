import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RealtorDetailComponent } from './realtor-detail.component';

describe('RealtorDetailComponent', () => {
  let component: RealtorDetailComponent;
  let fixture: ComponentFixture<RealtorDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RealtorDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RealtorDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
