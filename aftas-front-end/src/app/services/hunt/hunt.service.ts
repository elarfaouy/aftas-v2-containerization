import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HuntElement} from "../../hunt/models/hunt-element";

@Injectable({
  providedIn: 'root'
})
export class HuntService {
  private url: string = "http://localhost:8888/api/hunting";

  constructor(private _http: HttpClient) { }

  getHuntingOfCompetition(code: string): Observable<HuntElement[]> {
    return this._http.get<HuntElement[]>(this.url + `/competition/${code}`);
  }

  saveHunting(hunt: any): Observable<HuntElement>{
    return this._http.post<HuntElement>(this.url, hunt);
  }
}
