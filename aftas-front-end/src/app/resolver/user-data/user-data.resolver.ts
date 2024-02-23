import { ResolveFn } from '@angular/router';
import {inject} from "@angular/core";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {User} from "../../models/user";

export const userDataResolver: ResolveFn<User> = (route, state) => {
  let authService = inject(AuthenticationService);
  return authService.profile();
};
