import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { Role } from '../enum/role';
import { FileUploadStatus } from '../model/file-upload.status';
import { User } from '../model/user';
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../service/notification.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent  implements OnInit , OnDestroy{

  private subscriptions : Subscription [] =[]; 
  public refreshing : boolean ;
  public user : User ; 
  public fileName: string;
  public profileImage: File ;
  private currentUsername : string ;
  public fileStatus = new FileUploadStatus () ;


  constructor( private userService : UserService  , private authenticationService :AuthenticationService ,
    private notificationService : NotificationService ){}

    
  ngOnInit(): void {
    this.user=this.authenticationService.getUserFromLocalCache();
  }

  public updateCurrentUser(user : User ) : void {
    this.refreshing= true ;
    this.currentUsername=this.authenticationService.getUserFromLocalCache().username ;
    const formData = this.userService.createUserFormData(this.currentUsername , this.user , this.profileImage);
    this.subscriptions.push(
      this.userService.updateUser(formData).subscribe(
        (response : User) => {
          this.authenticationService.addUserToLocalCache(response);
          this.fileName = null ;
          this.profileImage = null ;
          this.sendNotification(NotificationType.SUCCESS , ` Profile Informations Updated Successfully`) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
         this.refreshing= false ;
         this.profileImage = null ;
        }
      )
    );
  }

  public onUpdateProfileImage():void {
    const formData = new FormData () ;
    formData.append('username' , this.user.username) ;
    formData.append('profileImage' , this.profileImage) ;
    this.subscriptions.push(
      this.userService.updateProfileImage(formData).subscribe(
        (event : HttpEvent<any> ) => {
          this.reportUploadProgress(event) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
         this.fileStatus.status = 'done' ;
        }
      )
    );
  }
  private reportUploadProgress(event: HttpEvent<any>) : void{
    switch (event.type){
      case HttpEventType.UploadProgress : 
        this.fileStatus.percentage= Math.round( 100 * event.loaded / event.total) ;
        this.fileStatus.status='progress' ;
        break ;
      case HttpEventType.Response : 
       if(event.status===200){
         this.user.profileImageUrl = `${event.body.profileImageUrl}?time= ${ new Date().getTime() }` ;
         this.sendNotification(NotificationType.SUCCESS , ` ${event.body.firstName}\'s Profile Image Uploaded Successfully`) ;
         this.fileStatus.status ='done' ;
        break ;      
      } else {
        this.sendNotification(NotificationType.ERROR ,`enable to upload image please try again`) ;
        break ;
      }
     default : `Finished All Progress` ; 
   } 
  }

  public onProfileImageChange (fileName : string , profileImage : File ) : void {
    this.fileName = fileName ;
    this.profileImage = profileImage ;
  }

  public updateProfileImage():void {
    this.clickButton('profile-image-input') ;  
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


   private clickButton(buttonId : string) : void {
    document.getElementById(buttonId).click();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub=>sub.unsubscribe());
  }


}
