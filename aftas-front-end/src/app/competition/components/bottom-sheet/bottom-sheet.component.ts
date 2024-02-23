import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {FormBuilder, FormGroup} from "@angular/forms";
import {CompetitionService} from "../../../services/competition/competition.service";
import {StandardApiResponse} from "../../../models/standard-api-response";

@Component({
  selector: 'app-bottom-sheet',
  templateUrl: './bottom-sheet.component.html',
  styleUrl: './bottom-sheet.component.css'
})
export class BottomSheetComponent implements OnInit {
  competitionForm!: FormGroup;
  errorResponse?: StandardApiResponse;
  constructor(private _bottomSheetRef: MatBottomSheetRef<BottomSheetComponent>,
              private competitionService: CompetitionService,
              private _fb: FormBuilder) {}

  ngOnInit(): void {
    this.competitionForm = this._fb.group({
      code: [''],
      date: [''],
      startTime: [''],
      endTime: [''],
      location: [''],
      amount: [''],
    });
  }

  saveCompetition(){
    console.log(this.competitionForm.value)
    this.competitionService.saveCompetition(this.competitionForm.value).subscribe(
      data => {
        this._bottomSheetRef.dismiss();
      },
      error => this.errorResponse = error.error
    )
  }
}
