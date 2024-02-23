import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { existCompetitionGuard } from './exist-competition.guard';

describe('existCompetitionGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => existCompetitionGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
