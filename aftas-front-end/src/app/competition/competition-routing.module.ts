import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CompetitionComponent} from "./components/competition/competition.component";
import {TableComponent} from "./components/table/table.component";
import {PodiumComponent} from "./components/podium/podium.component";
import {authenticateGuard} from "../guards/authenticate/authenticate.guard";
import {hasRightAuthorityGuard} from "../guards/has-right-authority/has-right-authority.guard";
import {existCompetitionGuard} from "../guards/exist-competition/exist-competition.guard";

const routes: Routes = [
  {
    path: "competition", component: CompetitionComponent, canActivateChild: [authenticateGuard], children: [
      {
        path: "result/:code",
        component: PodiumComponent,
        canActivate: [hasRightAuthorityGuard, existCompetitionGuard],
        data: {authority: "READ_COMPETITION"}
      },
      {
        path: "table",
        component: TableComponent,
        canActivate: [hasRightAuthorityGuard],
        data: {authority: "READ_COMPETITION"}
      },
      {path: "", redirectTo: "table", pathMatch: "full"}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompetitionRoutingModule {
}
