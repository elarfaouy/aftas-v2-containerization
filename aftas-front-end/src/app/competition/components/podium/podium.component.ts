import {Component, OnDestroy, OnInit} from '@angular/core';
import {CompetitionElement} from "../../models/competition-element";
import {ActivatedRoute} from "@angular/router";
import {CompetitionService} from "../../../services/competition/competition.service";
import {RegisterElement} from "../../../member/models/register-element";

@Component({
  selector: 'app-podium',
  templateUrl: './podium.component.html',
  styleUrl: './podium.component.css'
})
export class PodiumComponent implements OnInit, OnDestroy {
  competition!: CompetitionElement;
  scores: RegisterElement[] = [];
  constructor(private _activeRoute: ActivatedRoute,
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

    this._competitionService.calculateScore(code).subscribe(
      data => {
        this.scores = data.sort((a, b) => a.rank - b.rank);
        console.log(this.scores)
      },
      error => console.error(error)
    )
  }

  ngOnDestroy(): void {
  }

}
