import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AppComponent} from "./app.component";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatButtonModule} from "@angular/material/button";
import {CompetitionModule} from "./competition/competition.module";
import {MemberModule} from "./member/member.module";
import {HuntModule} from "./hunt/hunt.module";
import {AuthModule} from "./auth/auth.module";
import {ToastrModule} from "ngx-toastr";
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {authenticationInterceptor} from "./interceptor/authentication/authentication.interceptor";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatButtonModule,
    CompetitionModule,
    MemberModule,
    HuntModule,
    AuthModule,
    ToastrModule.forRoot(
      {
        timeOut: 2000,
      }
    ),
  ],
  providers: [
    provideHttpClient(withInterceptors([authenticationInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
