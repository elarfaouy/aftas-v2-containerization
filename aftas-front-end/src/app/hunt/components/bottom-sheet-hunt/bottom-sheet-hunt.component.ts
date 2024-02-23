import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {MemberElement} from "../../../member/models/member-element";
import {map, Observable, startWith} from "rxjs";
import {StandardApiResponse} from "../../../models/standard-api-response";
import {MAT_BOTTOM_SHEET_DATA, MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {MemberService} from "../../../services/member/member.service";
import {FishElement} from "../../models/fish-element";
import {FishService} from "../../../services/fish/fish.service";
import {HuntService} from "../../../services/hunt/hunt.service";
import {CompetitionElement} from "../../../competition/models/competition-element";

@Component({
  selector: 'app-bottom-sheet-hunt',
  templateUrl: './bottom-sheet-hunt.component.html',
  styleUrl: './bottom-sheet-hunt.component.css'
})
export class BottomSheetHuntComponent {
  huntForm: FormGroup = new FormGroup<any>({});
  errorResponse?: StandardApiResponse;
  memberControl = new FormControl<string | MemberElement>('');
  fishControl = new FormControl<string | FishElement>('');
  fishWeightControl = new FormControl<number>(0);
  members: MemberElement[] = [];
  fishes: FishElement[] = [];
  filteredMembers!: Observable<MemberElement[]>;
  filteredFishes!: Observable<FishElement[]>;

  constructor(private _bottomSheetRef: MatBottomSheetRef<BottomSheetHuntComponent>,
              @Inject(MAT_BOTTOM_SHEET_DATA) public data: {competition: CompetitionElement},
              private _memberService: MemberService,
              private _fishService: FishService,
              private _huntService: HuntService,
              private _fb: FormBuilder) {
  }

  ngOnInit(): void {
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

    this._fishService.getFishes().subscribe(
      data => {
        this.fishes = data;
        this.filteredFishes = this.fishControl.valueChanges.pipe(
          startWith(''),
          map(value => {
            const num = typeof value === 'number' ? value : value;
            return num ? this._filterFish(num as string) : this.fishes.slice();
          }),
        );
      },
      error => console.error(error)
    )
  }

  displayFnForMember(member: MemberElement): string {
    return member && member.num ? member.num.toString() : '';
  }

  displayFnForFish(fish: FishElement): string {
    return fish && fish.name ? fish.name : '';
  }

  private _filterMember(num: string): MemberElement[] {
    const filterValue = num;
    return this.members.filter(option => option.num.toString().includes(filterValue));
  }

  private _filterFish(name: string): FishElement[] {
    const filterValue = name;
    return this.fishes.filter(option => option.name.includes(filterValue));
  }

  saveHunt() {
    const hunt = {
      fishWeight: this.fishWeightControl.value,
      fish: this.fishControl.value,
      competition: this.data.competition,
      member: this.memberControl.value,
    };

    console.log(hunt)
    this._huntService.saveHunting(hunt).subscribe(
      data => {
        this._bottomSheetRef.dismiss();
      },
      error => this.errorResponse = error.error
    )
  }
}
