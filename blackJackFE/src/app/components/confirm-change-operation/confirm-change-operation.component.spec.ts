import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmChangeOperationComponent } from './confirm-change-operation.component';

describe('ConfirmChangeOperationComponent', () => {
  let component: ConfirmChangeOperationComponent;
  let fixture: ComponentFixture<ConfirmChangeOperationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmChangeOperationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmChangeOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
