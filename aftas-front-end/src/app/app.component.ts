import { Component } from '@angular/core';
import {AuthenticationService} from "./services/authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'aftas-front-end';

  constructor(private authService: AuthenticationService,
              private router: Router) {
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["/login"]);
  }
}
