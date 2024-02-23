import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {StandardApiResponse} from "../../../models/standard-api-response";
import {MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {MemberService} from "../../../services/member/member.service";
import {RegisterElement} from "../../models/register-element";
import {CompetitionService} from "../../../services/competition/competition.service";
import {CompetitionElement} from "../../../competition/models/competition-element";
import {map, Observable, startWith} from "rxjs";
import {MemberElement} from "../../models/member-element";

@Component({
  selector: 'app-bottom-sheet-register',
  templateUrl: './bottom-sheet-register.component.html',
  styleUrl: './bottom-sheet-register.component.css'
})
export class BottomSheetRegisterComponent {
  registerForm: FormGroup = new FormGroup<any>({});
  errorResponse?: StandardApiResponse;
  competitionControl = new FormControl<string | CompetitionElement>('');
  memberControl = new FormControl<string | MemberElement>('');
  competitions: CompetitionElement[] = [];
  members: MemberElement[] = [];
  filteredCompetitions!: Observable<CompetitionElement[]>;
  filteredMembers!: Observable<MemberElement[]>;
  constructor(private _bottomSheetRef: MatBottomSheetRef<BottomSheetRegisterComponent>,
              private _memberService: MemberService,
              private _competitionService: CompetitionService,
              private _fb: FormBuilder) {}

  ngOnInit(): void {
    this._competitionService.getCompetitions().subscribe(
      data => {
        this.competitions = data.content.filter(value => value.status == "UPCOMING");
        this.filteredCompetitions = this.competitionControl.valueChanges.pipe(
          startWith(''),
          map(value => {
            const name = typeof value === 'string' ? value : value?.code;
            return name ? this._filterCompetition(name as string) : this.competitions.slice();
          }),
        );
      },
      error => console.error(error)
    );

    this._memberService.getMembers().subscribe(
      data => {
        this.members = data;
        this.filteredMembers = this.memberControl.valueChanges.pipe(
          startWith(''),
          map(value => {
            const num = typeof value === 'number' ? value : value;
            return num ? this._filterMember(num as string) : this.members.slice();
          }),
        );
      },
      error => console.error(error)
    )
  }

  displayFnForCompetition(competition: CompetitionElement): string {
    return competition && competition.code ? competition.code : '';
  }

  displayFnForMember(member: MemberElement): string {
    return member && member.num ? member.num.toString() : '';
  }

  private _filterCompetition(code: string): CompetitionElement[] {
    const filterValue = code.toLowerCase();
    return this.competitions.filter(option => option.code.toLowerCase().includes(filterValue));
  }

  private _filterMember(num: string): MemberElement[] {
    const filterValue = num;
    return this.members.filter(option => option.num.toString().includes(filterValue));
  }

  registerMemberToCompetition(){
    const register: RegisterElement = {
      rank: 0,
      score: 0,
      competition: this.competitionControl.value as CompetitionElement,
      member: this.memberControl.value as MemberElement,
    };
    console.log(register)
    this._memberService.registerMember(register).subscribe(
      data => {
        this._bottomSheetRef.dismiss();
      },
      error => this.errorResponse = error.error
    )
  }
}
