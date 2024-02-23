import { CanActivateFn } from '@angular/router';
import {inject} from "@angular/core";
import {AuthenticationService} from "../../services/authentication/authentication.service";

export const authenticateGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthenticationService);
  return authService.isAuthenticate();
};
