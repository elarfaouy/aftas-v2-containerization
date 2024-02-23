import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FishElement} from "../../hunt/models/fish-element";

@Injectable({
  providedIn: 'root'
})
export class FishService {
  private url: string = "http://localhost:8888/api/fish";

  constructor(private _http: HttpClient) { }

  getFishes(): Observable<FishElement[]> {
    return this._http.get<FishElement[]>(this.url);
  }
}
