import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const loggedGuard: CanActivateFn = (route, state) => {
  if(localStorage.getItem('user'))return true;
  else {
    const router: Router = inject(Router);

    router.navigate(["/about"]);
    return false;
  }
};
