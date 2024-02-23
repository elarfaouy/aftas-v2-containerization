import {CompetitionElement} from "./competition-element";

export interface PageableResponse {
  content: CompetitionElement[];
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}
