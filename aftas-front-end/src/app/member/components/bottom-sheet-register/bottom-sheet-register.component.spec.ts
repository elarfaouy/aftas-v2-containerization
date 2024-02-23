import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottomSheetRegisterComponent } from './bottom-sheet-register.component';

describe('BottomSheetRegisterComponent', () => {
  let component: BottomSheetRegisterComponent;
  let fixture: ComponentFixture<BottomSheetRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BottomSheetRegisterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BottomSheetRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
