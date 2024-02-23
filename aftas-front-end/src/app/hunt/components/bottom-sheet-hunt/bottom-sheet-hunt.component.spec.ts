import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottomSheetHuntComponent } from './bottom-sheet-hunt.component';

describe('BottomSheetHuntComponent', () => {
  let component: BottomSheetHuntComponent;
  let fixture: ComponentFixture<BottomSheetHuntComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BottomSheetHuntComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BottomSheetHuntComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
