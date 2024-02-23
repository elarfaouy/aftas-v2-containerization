import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './components/login/login.component';
import {FormsModule} from "@angular/forms";
import { ProfileComponent } from './components/profile/profile.component';
import { RegisterComponent } from './components/register/register.component';


@NgModule({
  declarations: [
    LoginComponent,
    ProfileComponent,
    RegisterComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule
  ]
})
export class AuthModule { }
