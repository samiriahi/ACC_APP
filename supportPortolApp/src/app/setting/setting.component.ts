import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { Role } from '../enum/role';
import { CostumHttpResponse } from '../model/custom-http-response';
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../service/notification.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.css']
})
export class SettingComponent implements OnInit , OnDestroy {

  public refreshing : boolean ; 
  private subscriptions : Subscription [] = [] ; 

  
  constructor( private authenticationService :AuthenticationService ,private userService :UserService ,
    private notificationService : NotificationService ){}
  
    ngOnInit(): void {}

  public resetPassword (emailForm : NgForm) : void {
    this.refreshing=true ;
    const emailAdress = emailForm.value['reset-password-email'] ;
    this.subscriptions.push(
      this.userService.resetPassword(emailAdress).subscribe(
        (response : CostumHttpResponse) => {
        this.sendNotification(NotificationType.SUCCESS , response.message);
        this.refreshing=false ;
      },
       (errorResponse : HttpErrorResponse) => {
        this.sendNotification(NotificationType.WARNING , errorResponse.error.message) ;
        this.refreshing=false ;
       },
        () => emailForm.reset() 
        
      )
    )
  }
  
  public get isAdmin() : boolean {
    return this.getUserRole() === Role.ADMIN  || this.getUserRole() ===  Role.SUPER_ADMIN ;
   }
 
   public get isManager() : boolean {
     return this.isAdmin || this.getUserRole() ===  Role.MANAGER ;
   }
    public get isAdminOrManager() : boolean {
     return this.isManager || this.isAdmin ;
   }
 
   private getUserRole() : string {
     return this.authenticationService.getUserFromLocalCache().role ;
   }

   private sendNotification(notificationType: NotificationType, message: string) :void{
    if(message){
        this.notificationService.notify(notificationType,message) ;
      } else {
        this.notificationService.notify(notificationType, 'An error occure . please try again ');
      }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub=>sub.unsubscribe());
  }

}
