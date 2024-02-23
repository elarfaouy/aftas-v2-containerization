import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {StandardApiResponse} from "../../../models/standard-api-response";
import {MAT_BOTTOM_SHEET_DATA, MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {MemberService} from "../../../services/member/member.service";

@Component({
  selector: 'app-bottom-sheet-update-member',
  templateUrl: './bottom-sheet-update-member.component.html',
  styleUrl: './bottom-sheet-update-member.component.css'
})
export class BottomSheetUpdateMemberComponent {
  memberForm!: FormGroup;
  errorResponse?: StandardApiResponse;

  roles: any[] = [
    {id: 1, name: 'Manager'},
    {id: 2, name: 'Jury'},
    {id: 3, name: 'Adherent'}
  ];

  constructor(private _bottomSheetRef: MatBottomSheetRef<BottomSheetUpdateMemberComponent>,
              private _memberService: MemberService,
              private _fb: FormBuilder,
              @Inject(MAT_BOTTOM_SHEET_DATA) public data: { user: any }) {
  }

  ngOnInit(): void {
    this.memberForm = this._fb.group({
      num: [this.data.user.num],
      role: [this.roles.at(2)],
    });
  }

  updateMember() {
    this._memberService.updateMember(this.memberForm.value).subscribe(
      data => {
        this._bottomSheetRef.dismiss();
      },
      error => this.errorResponse = error.error
    )
  }
}
