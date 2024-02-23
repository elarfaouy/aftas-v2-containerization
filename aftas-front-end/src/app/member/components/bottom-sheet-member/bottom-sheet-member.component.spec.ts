import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottomSheetMemberComponent } from './bottom-sheet-member.component';

describe('BottomSheetMemberComponent', () => {
  let component: BottomSheetMemberComponent;
  let fixture: ComponentFixture<BottomSheetMemberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BottomSheetMemberComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BottomSheetMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
