import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { Role } from '../enum/role';
import { CostumHttpResponse } from '../model/custom-http-response';
import { LigneProduction } from '../model/ligneProduction';
import { Poste } from '../model/poste';
import { UniteFabrication } from '../model/uniteFabrication';
import { AuthenticationService } from '../service/authentication.service';
import { LigneProdctionService } from '../service/ligneProdction.service';
import { NotificationService } from '../service/notification.service';
import { PosteService } from '../service/poste.service';

@Component({
  selector: 'app-ligne-production',
  templateUrl: './ligne-production.component.html',
  styleUrls: ['./ligne-production.component.css']
})
export class LigneProductionComponent implements OnInit , OnDestroy  {

  private subscriptions : Subscription [] = [] ;
  public lignesProds : LigneProduction [];
  public editLigneProd= new LigneProduction () ;
  public ligneProd= new LigneProduction () ;
  public uniteFab : UniteFabrication ;
  public ligneProduction : LigneProduction;
  public poste = new Poste() ;
  public codeLp : string ;
  public nomPoste : string ;
  public postes :Poste[] ;

  p: number = 1;
  itemsPerPage : number = 6;
  totalElements: any ;


  constructor ( private ligneProdctionService : LigneProdctionService ,  private notificationService : NotificationService , 
                private authenticationService : AuthenticationService  , private posteService :PosteService ){}

  ngOnInit(): void {
    this.getAllLignesProds(true) ;
  }

  public getAllLignesProds (showNotification : boolean ):void {
    this.subscriptions.push(
    this.ligneProdctionService.getAllLignesProds().subscribe(
        (response : LigneProduction[] ) => {
          this.lignesProds  = response ;
          this.totalElements=response.length;
          if (showNotification){
            this.sendNotification(NotificationType.SUCCESS, `${response.length} lignes de production trouvee . `) ;
          }
        },
        (errorResponse : HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }

  public searchLigneProd(searchTerm: string): void {
    const results: LigneProduction[] = [];
    for (const lignePro of this.lignesProds) {
      if (lignePro.codeLp.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1
      || lignePro.robotTraitement.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1
      || lignePro.uniteFab.codeUf.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1) {
        results.push(lignePro);
      }
    }
    this.lignesProds = results;
    if (results.length === 0 || !searchTerm) {
      this.getAllLignesProds(false);
    }
  }



  public addLigneProd (ligneProdForm : NgForm) : void {
    const formData = this.ligneProdctionService.createFormData(ligneProdForm.value ); 
    this.subscriptions.push(
      this.ligneProdctionService.addNewLigneProd(formData).subscribe(
        (response : LigneProduction) => {
         this.clickButton('new-ligneProd-close'); 
          this.getAllLignesProds(false);
          ligneProdForm.reset();
          this.sendNotification(NotificationType.SUCCESS , `${response.uniteFab.codeUf} line production added successfully`) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }

  public saveLigneProd () : void {
    this.clickButton('new-ligneProd-save');
  }


  public updateLigneProd () : void{
    const formData = this.ligneProdctionService.createFormData( this.editLigneProd) ;
    this.subscriptions.push(
      this.ligneProdctionService.updateLigneProd(formData).subscribe(
        (response : LigneProduction) => {
         this.clickButton('edit-ligneProd-close') ; 
          this.getAllLignesProds(false);
          this.sendNotification(NotificationType.SUCCESS ,`${response.uniteFab.codeUf} Robot Traitement Changed successfully `) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
      );
  }
  public editLigneProdInfo (editLigneProd : LigneProduction):void{
    this.editLigneProd =editLigneProd  ;
    this.clickButton('openEditLigneProd') ; 
  }

  public deleteLigneProd (codeLp : string ):void{
    this.subscriptions.push(
      this.ligneProdctionService.deleteLigneProd(codeLp).subscribe(
        (response : CostumHttpResponse) => {
        this.sendNotification(NotificationType.WARNING , response.message);
        this.getAllLignesProds(false);
      },
       (errorResponse : HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
       }
      )
    )
  }


  public assignPosteToLigneProd ( codeLp: string , nomPoste : string) : void{
    const formData = this.posteService.createPosteLigneForm(this.nomPoste , this.codeLp , this.poste ,  this.ligneProd);
    this.subscriptions.push(
      this.posteService.assignPosteToLigneProd(formData).subscribe(
        (response : CostumHttpResponse) => {
         this.clickButton('poste-ligne-close') ; // id de button edit groupe 
          //this.getGroupMembers(false);
          this.sendNotification(NotificationType.SUCCESS , response.message) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
      );
  }

  public AddPosteToLigneModal (ligneProd : LigneProduction):void{
    this.ligneProd = ligneProd ;
    this.codeLp=ligneProd.codeLp;
    this.clickButton('openAddPosteToLigne') ; // button id = (openUserEdit) hya data-target ta modal #EditGroupModal
  }


  public getPostesByLigne (showNotification : boolean):void {
    this.subscriptions.push(
    this.posteService.getPostesByLigneProd(this.ligneProd.codeLp).subscribe(
        (response : Poste[] ) => {
          this.postes  = response ;
          if (showNotification){
            this.sendNotification(NotificationType.SUCCESS, `${response.length} postes appartient a cette ligne trouvee . `) ;
          }
        },
        (errorResponse : HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }
  
  public lignePostesList (ligneProd :LigneProduction):void{
    this.ligneProd = ligneProd ;
    this.codeLp=ligneProd.codeLp;
    this.clickButton('openPostesList') ; //  id of button hidden 
  }


  public removePosteFromLigne (nomPoste : string ):void{
    this.subscriptions.push(
      this.posteService.removePosteFromLigneProd(nomPoste).subscribe(
        (response : CostumHttpResponse) => {
        this.sendNotification(NotificationType.WARNING , response.message);
        this.getPostesByLigne(false);
      },
       (errorResponse : HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
       }
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

  private clickButton(buttonId : string) : void {
    document.getElementById(buttonId).click();
  }


  ngOnDestroy(): void {
    this.subscriptions.forEach(sub=>sub.unsubscribe());
  }

}
