import {CompetitionElement} from "../../competition/models/competition-element";
import {MemberElement} from "./member-element";

export interface RegisterElement {
  rank: number;
  score: number;
  competition: CompetitionElement | null;
  member: MemberElement | null;
}
