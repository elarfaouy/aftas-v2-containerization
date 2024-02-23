import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from "./components/table/table.component";
import {HuntComponent} from "./components/hunt/hunt.component";
import {CompetitionComponent} from "./components/competition/competition.component";
import {authenticateGuard} from "../guards/authenticate/authenticate.guard";
import {hasRightAuthorityGuard} from "../guards/has-right-authority/has-right-authority.guard";
import {existCompetitionGuard} from "../guards/exist-competition/exist-competition.guard";

const routes: Routes = [
  {
    path: "hunt", component: HuntComponent, canActivateChild: [authenticateGuard], children: [
      {
        path: "competition/:code",
        component: CompetitionComponent,
        canActivate: [hasRightAuthorityGuard, existCompetitionGuard],
        data: {authority: "READ_HUNTING"}
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
export class HuntRoutingModule {
}
