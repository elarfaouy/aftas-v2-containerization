import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {StandardApiResponse} from "../../../models/standard-api-response";
import {MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {MemberService} from "../../../services/member/member.service";

@Component({
  selector: 'app-bottom-sheet-member',
  templateUrl: './bottom-sheet-member.component.html',
  styleUrl: './bottom-sheet-member.component.css'
})
export class BottomSheetMemberComponent {
  memberForm!: FormGroup;
  errorResponse?: StandardApiResponse;
  constructor(private _bottomSheetRef: MatBottomSheetRef<BottomSheetMemberComponent>,
              private _memberService: MemberService,
              private _fb: FormBuilder) {}

  ngOnInit(): void {
    this.memberForm = this._fb.group({
      num: [''],
      name: [''],
      familyName: [''],
      nationality: [''],
      identityNumber: [''],
      identityDocument: [''],
    });
  }

  saveMember(){
    console.log(this.memberForm.value)
    this._memberService.saveMember(this.memberForm.value).subscribe(
      data => {
        this._bottomSheetRef.dismiss();
      },
      error => this.errorResponse = error.error
    )
  }
}
