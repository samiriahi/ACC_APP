import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { Role } from '../enum/role';
import { CostumHttpResponse } from '../model/custom-http-response';
import { Poste } from '../model/poste';
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../service/notification.service';
import { PosteService } from '../service/poste.service';

@Component({
  selector: 'app-poste',
  templateUrl: './poste.component.html',
  styleUrls: ['./poste.component.css']
})
export class PosteComponent implements OnInit , OnDestroy  {

  private subscriptions : Subscription [] = [] ;
  public postes :Poste[] ;
  public editPoste= new Poste () ;
  public poste = new Poste() ;
  
  p: number = 1;
  itemsPerPage : number = 6;
  totalElements: any ;


  constructor( private posteService : PosteService  ,  private notificationService : NotificationService ,
    private authenticationService : AuthenticationService){}


  ngOnInit(): void {
    this.getPostes(true) ;
  }

  public getPostes(showNotification : boolean ):void {
    this.subscriptions.push(
    this.posteService.getPostes().subscribe(
        (response : Poste[] ) => {
          this.postes  = response ;
          this.totalElements=response.length;
          if (showNotification){
            this.sendNotification(NotificationType.SUCCESS, `${response.length} Postes trouvee . `) ;
          }
        },
        (errorResponse : HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }

  public addPoste (posteForm : NgForm) : void {
    const formData = this.posteService.createFormData(posteForm.value ); 
    this.subscriptions.push(
      this.posteService.addPoste(formData).subscribe(
        (response : Poste) => {
         this.clickButton('new-Poste-close'); 
          this.getPostes(false);
          posteForm.reset();
          this.sendNotification(NotificationType.SUCCESS , `${response.nomPoste} Poste ajoutee avec success`) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }

  public savePoste () : void {
    this.clickButton('new-Poste-save');
  }


  public updatePoste() : void{
    const formData = this.posteService.createFormData( this.editPoste) ;
    this.subscriptions.push(
      this.posteService.updatePoste(formData).subscribe(
        (response : Poste) => {
         this.clickButton('edit-Poste-close') ; 
          this.getPostes(false);
          this.sendNotification(NotificationType.SUCCESS , `${response.nomPoste}  Poste successfully `) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
      );
  }

  public editPosteInfo (editPoste : Poste):void{
    this.editPoste =editPoste  ;
    this.clickButton('openEditPoste') ; 
  }


  public deletePoste (nomPoste : string ):void{
    this.subscriptions.push(
      this.posteService.deletePoste(nomPoste).subscribe(
        (response : CostumHttpResponse) => {
        this.sendNotification(NotificationType.SUCCESS , response.message);
        this.getPostes(false);
      },
       (errorResponse : HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
       }
      )
    )
  }

  public searchPoste (searchTerm : string) : void {
    const results : Poste[] = [] ;
    for (const poste of this.postes){
      if ( poste.nomPoste.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1  ||
           poste.nomReseau.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1  ||
           poste.cadence.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1   ){
            results.push(poste) ;
      }
    }
    this.postes=results ;
    if (results.length === 0 || ! searchTerm){
      this.getPostes(false) ;
    }
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
