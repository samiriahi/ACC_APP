import { HttpErrorResponse, HttpEvent, HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BehaviorSubject, Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { CostumHttpResponse } from '../model/custom-http-response';
import { User } from '../model/user';
import { NotificationService } from '../service/notification.service';
import { AuthenticationService } from '../service/authentication.service';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { FileUploadStatus } from '../model/file-upload.status';
import { Role } from '../enum/role';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit , OnDestroy {

  private subscriptions : Subscription [] =[]; 
  private titleSubject = new BehaviorSubject <string>('Users') ;
  public titleAction$ = this.titleSubject.asObservable() ;
  public users :User[] ; 
  public selectedUser : User ;
  public user : User ;
  public refreshing : boolean ; 
  public fileName: string;
  public profileImage: File ;
  public editUser= new User();
  private currentUsername : string ;
  public fileStatus = new FileUploadStatus () ;

  p: number = 1;
  itemsPerPage : number = 6;
  totalElements: any ;

 

  constructor(  private router : Router , private userService : UserService  , private authenticationService :AuthenticationService ,
              private notificationService : NotificationService ){}

  ngOnInit(): void {
    this.user=this.authenticationService.getUserFromLocalCache();
    this.getUsers(true) ;
  }

  public changeTitle(title :string) :void {
    this.titleSubject.next(title) ;
  }

  public getUsers (showNotification : boolean ):void {
    this.refreshing = true ;
    this.subscriptions.push(
    this.userService.getUsers().subscribe(
        (response : User[] ) => {
          this.userService.addUsersToLocalCache(response);
          this.users  = response ;
          this.refreshing = false ;
          this.totalElements=response.length;
          if (showNotification){
            this.sendNotification(NotificationType.SUCCESS, `${response.length} user(s) loaded successfully . `) ;
          }
        },
        (errorResponse : HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
          this.refreshing = false ;
        }
      )
    );
  
  }

  public searchUsers (searchTerm : string) : void {
    const results : User[] = [] ;
    for (const user of this.userService.getusersFromLocalCache()){
      if ( user.firstName.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1 ||
           user.lastName.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1 ||
           user.username.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1 ||
           user.userId.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1  ||
           user.email.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1 )    {
            results.push(user) ;
      }
    }
    this.users=results ;
    if (results.length === 0 || ! searchTerm){
      this.users = this.userService.getusersFromLocalCache();
    }
  }

  public onProfileImageChange (fileName : string , profileImage : File ) : void {
    this.fileName = fileName ;
    this.profileImage = profileImage ;
  }


  public onSelectUser(selectedUser: User): void {
    this.selectedUser = selectedUser;
    this.clickButton('openUserInfo'); // button id = (openUserInfo) data-target = #viewUserModal
  }

  public saveNewUser () : void { // open newUserModal 
    this.clickButton('new-user-save');
  }
 

  public addNewUser (userForm : NgForm) : void {
    const formData = this.userService.createUserFormData(null , userForm.value , this.profileImage);
    this.subscriptions.push(
      this.userService.addUser(formData).subscribe(
        (response : User) => {
         this.clickButton('new-user-close');
          this.getUsers(false);
          this.fileName = null ;
          this.profileImage = null ;
          userForm.reset();
          this.sendNotification(NotificationType.SUCCESS , `${response.firstName} ${response.lastName} added successfully`) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
         this.profileImage = null ;
        }
      )
    );
  }

  
  public editUserInfo (editUser :User):void{
    this.editUser = editUser ;
    this.currentUsername = editUser.username ;
    this.clickButton('openUserEdit') ;
  }

  public updateUser () : void{
    const formData = this.userService.createUserFormData(this.currentUsername , this.editUser , this.profileImage) ;
    this.subscriptions.push(
      this.userService.updateUser(formData).subscribe(
        (response : User) => {
         this.clickButton('edit-user-close');
          this.getUsers(false);
          this.fileName = null ;
          this.profileImage = null ;
          this.sendNotification(NotificationType.SUCCESS , `${response.firstName} ${response.lastName} updated successfully`) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
         this.profileImage = null ;
        }
      )
      );
  }

  public deleteUser (username : string ):void{
    this.subscriptions.push(
      this.userService.deleteUser(username).subscribe(
        (response : CostumHttpResponse) => {
        this.sendNotification(NotificationType.SUCCESS , response.message);
        this.getUsers(false);
      },
       (errorResponse : HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        this.profileImage = null ;
       }
      )
    )
  }

  public updateCurrentUser(user : User ) : void {
    this.refreshing= true ;
    this.currentUsername=this.authenticationService.getUserFromLocalCache().username ;
    const formData = this.userService.createUserFormData(this.currentUsername , this.user , this.profileImage);
    this.subscriptions.push(
      this.userService.updateUser(formData).subscribe(
        (response : User) => {
          this.authenticationService.addUserToLocalCache(response);
          this.getUsers(false);
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

  public onLogOut() : void {
    this.authenticationService.lougOut() ;
    this.router.navigate(['/login']) ;
    this.sendNotification(NotificationType.SUCCESS , `you have been Successfully Logout`) ;
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
        this.sendNotification(NotificationType.ERROR ,`unable to upload image please try again`) ;
        break ;
      }
     default : `Finished All Progress` ; 
   } 
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
