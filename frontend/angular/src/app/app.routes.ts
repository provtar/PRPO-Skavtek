import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/pages/home/home.component';
import { NotFoundComponent } from './components/pages/public/not-found/not-found.component';
import { LoginComponent } from './components/pages/public/login/login.component';
import { RegisterComponent } from './components/pages/public/register/register.component';
import { SkupinaComponent } from './components/pages/skupina/skupina.component';
import { SrecanjaComponent } from './components/pages/srecanja/srecanja.component';
import { NgModule } from '@angular/core';
import { loggedGuard } from './logged.guard';
import { AboutComponent } from './components/pages/public/about/about.component';
import { ClanComponent } from './components/pages/clan/clan.component';

export const routes: Routes = [
  { path: '', component: AboutComponent},
  { path: 'about', component: AboutComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'jaz', component: HomeComponent, canActivate: [loggedGuard]},
  { path: 'skupina', component: SkupinaComponent, canActivate: [loggedGuard]}, // TODO query param za skupino
  { path: 'srecanje', component: SrecanjaComponent, canActivate: [loggedGuard]}, // TODO dodat parametre na klic spa, EASY
  { path: 'clan', component: ClanComponent, canActivate: [loggedGuard]}, //TODO id clana v parametre
  // TODO sem pridejo se termini
  { path: 'not-found', component: NotFoundComponent},
  { path: '**', component: NotFoundComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutesModule {}
