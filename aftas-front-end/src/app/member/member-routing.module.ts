import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from "./components/table/table.component";
import {MemberComponent} from "./components/member/member.component";
import {authenticateGuard} from "../guards/authenticate/authenticate.guard";
import {hasRightAuthorityGuard} from "../guards/has-right-authority/has-right-authority.guard";

const routes: Routes = [
  {
    path: "member", component: MemberComponent, canActivateChild: [authenticateGuard], children: [
      {
        path: "table",
        component: TableComponent,
        canActivate: [hasRightAuthorityGuard],
        data: {authority: "READ_MEMBER"}
      },
      {path: "", redirectTo: "table", pathMatch: "full"}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MemberRoutingModule {
}
