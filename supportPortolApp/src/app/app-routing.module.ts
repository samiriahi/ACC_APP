import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GroupComponent } from './group/group.component';
import { AuthenticationGuard } from './guard/authentication.guard';
import { LigneProductionComponent } from './ligne-production/ligne-production.component';
import { LoginComponent } from './login/login.component';
import { PosteComponent } from './poste/poste.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { SettingComponent } from './setting/setting.component';
import { UniteFabricationComponent } from './unite-fabrication/unite-fabrication.component';
import { UserComponent } from './user/user.component';
import { PicklisteComponent } from './pickliste/pickliste.component';
import { DashbordChartComponent } from './dashbord-chart/dashbord-chart.component';
import { SidenavigationComponent } from './sidenavigation/sidenavigation.component';
import { AcceuilPageComponent } from './acceuil-page/acceuil-page.component';


const routes: Routes = [
  {path : 'register' , component : RegisterComponent} ,
  {path : 'user/management' , component : UserComponent , canActivate : [AuthenticationGuard]} ,
  {path : 'group' , component : GroupComponent} ,
  {path : 'setting' , component : SettingComponent} ,
  {path : 'unite-fabrication' , component : UniteFabricationComponent }  ,
  {path : 'ligneProduction' , component : LigneProductionComponent }  ,
  {path : 'poste' , component : PosteComponent } ,
  {path : 'profile' , component : ProfileComponent } ,
  {path : 'pickliste' , component : PicklisteComponent } ,
  {path : 'dashbord-chart' , component : DashbordChartComponent } ,
  {path : '' , redirectTo : '/acceuil' , pathMatch : 'full'},
  {path : 'side-nav' , component : SidenavigationComponent },
  {path : 'acceuil' , component : AcceuilPageComponent }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
