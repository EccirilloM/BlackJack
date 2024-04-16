import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EconomoDashboardComponent } from './economo-dashboard.component';

describe('EconomoDashboardComponent', () => {
  let component: EconomoDashboardComponent;
  let fixture: ComponentFixture<EconomoDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EconomoDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EconomoDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
