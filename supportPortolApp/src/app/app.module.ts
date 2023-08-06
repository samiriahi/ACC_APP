import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule ,  HTTP_INTERCEPTORS }  from '@angular/common/http' ;
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthenticationService } from './service/authentication.service';
import { UserService } from './service/user.service';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { AuthenticationGuard } from './guard/authentication.guard';
import { NotificationModule } from './notification.module';
import { NotificationService } from './service/notification.service';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { GroupComponent } from './group/group.component';
import { SettingComponent } from './setting/setting.component';
import { UniteFabricationComponent } from './unite-fabrication/unite-fabrication.component';
import { LigneProductionComponent } from './ligne-production/ligne-production.component';
import {NgxPaginationModule} from 'ngx-pagination' ;
import { PosteComponent } from './poste/poste.component' ;
import { ProfileComponent } from './profile/profile.component';
import { PicklisteComponent } from './pickliste/pickliste.component';
import { DashbordChartComponent } from './dashbord-chart/dashbord-chart.component' ;
import { NgChartsModule  } from 'ng2-charts';
import { SidenavigationComponent } from './sidenavigation/sidenavigation.component';
import { AcceuilPageComponent } from './acceuil-page/acceuil-page.component';




@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    UserComponent,
    GroupComponent,
    SettingComponent,
    UniteFabricationComponent,
    LigneProductionComponent,
    PosteComponent,
    ProfileComponent,
    PicklisteComponent,
    DashbordChartComponent,
    SidenavigationComponent,
    AcceuilPageComponent,
   
    
  ],
  imports: [
    BrowserModule,

    AppRoutingModule,
    FormsModule,
    HttpClientModule ,






    
    NotificationModule,
    BrowserAnimationsModule,
    FontAwesomeModule,
    NgxPaginationModule,
    NgChartsModule,
    
    
    
      
    
  ],
  providers: [NotificationService , AuthenticationGuard ,AuthenticationService , UserService ,
     { provide :HTTP_INTERCEPTORS , useClass : AuthInterceptor , multi :true }] ,
  bootstrap: [ AppComponent ]
})
export class AppModule {}


