import {FishElement} from "./fish-element";
import {MemberElement} from "../../member/models/member-element";
import {CompetitionElement} from "../../competition/models/competition-element";

export interface HuntElement {
  id: number,
  numberOfFish: number,
  fish: FishElement| null,
  member: MemberElement | null,
  competition: CompetitionElement | null,
}
