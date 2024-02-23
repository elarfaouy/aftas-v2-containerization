import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottomSheetUpdateMemberComponent } from './bottom-sheet-update-member.component';

describe('BottomSheetUpdateMemberComponent', () => {
  let component: BottomSheetUpdateMemberComponent;
  let fixture: ComponentFixture<BottomSheetUpdateMemberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BottomSheetUpdateMemberComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BottomSheetUpdateMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
