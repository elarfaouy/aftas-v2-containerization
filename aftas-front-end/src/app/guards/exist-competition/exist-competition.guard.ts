import {CanActivateFn, Router} from '@angular/router';
import {catchError, map, of} from "rxjs";
import {inject} from "@angular/core";
import {CompetitionService} from "../../services/competition/competition.service";

export const existCompetitionGuard: CanActivateFn = (route, state) => {
  const competitionService = inject(CompetitionService);
  const router = inject(Router);

  const code = route.paramMap.get('code') ?? '';

  return competitionService.getCompetition(code).pipe(
    map(competition => !!competition),
    catchError(() => of(router.createUrlTree(['/hunt/table']))),
  );
};
