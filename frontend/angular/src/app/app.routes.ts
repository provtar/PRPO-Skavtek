import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { SkupinaComponent } from './components/skupina/skupina.component';
import { SrecanjaComponent } from './components/srecanja/srecanja.component';
import { NgModule } from '@angular/core';
import { loggedGuard } from './logged.guard';
import { AboutComponent } from './components/about/about.component';
import { VarovanciComponent } from './components/varovanci/varovanci.component';
import { ClanComponent } from './components/clan/clan.component';
import { PrisotnostiComponent } from './components/prisotnosti/prisotnosti.component';

export const routes: Routes = [
  { path: '', component: AboutComponent},
  { path: 'about', component: AboutComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'jaz', component: HomeComponent, canActivate: [loggedGuard]},
  { path: 'skupina', component: SkupinaComponent, canActivate: [loggedGuard]},
  { path: 'srecanje', component: SrecanjaComponent, canActivate: [loggedGuard]}, // TODO dodat parametre na klic spa, EASY
  { path: 'clan', component: ClanComponent, canActivate: [loggedGuard]},
  { path: 'varovanci', component:VarovanciComponent, canActivate: [loggedGuard]},
  { path: 'prisotnosti', component: PrisotnostiComponent, canActivate: [loggedGuard]},
  { path: '**', component: NotFoundComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutesModule {}
