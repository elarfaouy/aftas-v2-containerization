import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PageableResponse} from "../../competition/models/pageable-response";
import {CompetitionElement} from "../../competition/models/competition-element";
import {StandardApiResponse} from "../../models/standard-api-response";
import {RegisterElement} from "../../member/models/register-element";

@Injectable({
  providedIn: 'root'
})
export class CompetitionService {
  private url: string = "http://localhost:8888/api/competition";

  constructor(private _http: HttpClient) { }

  getCompetitions(): Observable<PageableResponse> {
    return this._http.get<PageableResponse>(this.url + "?page=0&size=50");
  }

  getCompetition(code: string): Observable<CompetitionElement> {
    return this._http.get<CompetitionElement>(this.url + `/${code}`);
  }

  saveCompetition(competition: CompetitionElement): Observable<CompetitionElement>{
    return this._http.post<CompetitionElement>(this.url, competition);
  }

  calculateScore(code: string): Observable<RegisterElement[]>{
    return this._http.get<RegisterElement[]>(this.url + `/score/${code}`);
  }
}
