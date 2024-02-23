import { Component } from '@angular/core';
import {AuthenticationService} from "../../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {User} from "../../../models/user";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  num: number = 0;
  name: string = "";
  familyName: string = "";
  username: string = "";
  password: string = "";

  constructor(private auth: AuthenticationService,
              private router: Router) {
  }

  register() {
    let user: User = {
      num: this.num,
      name: this.name,
      familyName: this.familyName,
      username: this.username,
      password: this.password,
      role: null,
      permissions: null
    };

    this.auth.register(user).subscribe(
      (value) => {
        console.log(value);
        localStorage.setItem("access-token", value["access-token"]);
        localStorage.setItem("token-expiration", value["token-expiration"])
        localStorage.setItem("refresh-token", value["refresh-token"]);

        this.router.navigate(["/competition"]);
      }
    );
  }
}
