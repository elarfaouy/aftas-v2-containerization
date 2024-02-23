import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {RegisterComponent} from "./components/register/register.component";
import {authenticateGuard} from "../guards/authenticate/authenticate.guard";
import {userDataResolver} from "../resolver/user-data/user-data.resolver";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "profile", component: ProfileComponent, canActivate: [authenticateGuard], resolve: {userData: userDataResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
