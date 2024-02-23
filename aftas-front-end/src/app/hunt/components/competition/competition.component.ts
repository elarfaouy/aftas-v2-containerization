import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HuntService} from "../../../services/hunt/hunt.service";
import {CompetitionService} from "../../../services/competition/competition.service";
import {CompetitionElement} from "../../../competition/models/competition-element";
import {HuntElement} from "../../models/hunt-element";
import {MemberElement} from "../../../member/models/member-element";
import {MatBottomSheet} from "@angular/material/bottom-sheet";
import {BottomSheetHuntComponent} from "../bottom-sheet-hunt/bottom-sheet-hunt.component";

@Component({
  selector: 'app-competition',
  templateUrl: './competition.component.html',
  styleUrl: './competition.component.css'
})
export class CompetitionComponent implements OnInit, OnDestroy {
  competition!: CompetitionElement;
  huntingCompetition: HuntElement[] = [];
  customHunts: CustomDataOfHunting[] = [];

  constructor(private _activeRoute: ActivatedRoute,
              private _bottomSheet: MatBottomSheet,
              private _huntService: HuntService,
              private _competitionService: CompetitionService) {
  }

  ngOnInit(): void {
    const code = this._activeRoute.snapshot.paramMap.get("code") ?? "";
    this._competitionService.getCompetition(code).subscribe(
      data => {
        this.competition = data;
      },
      error => console.error(error)
    )

    this._huntService.getHuntingOfCompetition(code).subscribe(
      data => {
        this.huntingCompetition = data;

        for (const hunt of this.huntingCompetition) {
          const memberIndex = this.customHunts.findIndex(member => member.member?.num === hunt.member?.num);
          if (memberIndex === -1) {
            const customHunt: CustomDataOfHunting = {
              member: hunt.member,
              hunts: [],
            };
            customHunt.hunts?.push(hunt);
            this.customHunts.push(customHunt)
          } else {
            this.customHunts.at(memberIndex)?.hunts?.push(hunt)
          }

        }
        console.log(this.customHunts)
      },
      error => console.error(error)
    )
  }

  ngOnDestroy(): void {
  }

  openBottomSheet(): void {
    this._bottomSheet.open(BottomSheetHuntComponent, {
      data: { competition: this.competition },
    });
  }
}

interface CustomDataOfHunting {
  member: MemberElement | null;
  hunts: HuntElement[] | null;
}
