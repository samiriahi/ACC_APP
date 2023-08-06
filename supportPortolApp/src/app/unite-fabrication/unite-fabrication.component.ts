import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { Role } from '../enum/role';
import { CostumHttpResponse } from '../model/custom-http-response';
import { LigneProduction } from '../model/ligneProduction';
import { UniteFabrication } from '../model/uniteFabrication';
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../service/notification.service';
import { UniteFabricationService } from '../service/uniteFabrication.service';

@Component({
  selector: 'app-unite-fabrication',
  templateUrl: './unite-fabrication.component.html',
  styleUrls: ['./unite-fabrication.component.css']
})
export class UniteFabricationComponent  implements OnInit , OnDestroy {

  private subscriptions : Subscription [] = [] ;
  public uniteFabs : UniteFabrication [] ;
  public editUf = new UniteFabrication() ;
  private currentCodeUf : string ;
  public unitefab =new UniteFabrication() ;
  public lignes: LigneProduction [] ;
  public codeUf: string ;
  p: number = 1;
  itemsPerPage : number = 6;
  totalElements: any ;

  constructor( private uniteFabService : UniteFabricationService , private notificationService : NotificationService ,
               private authenticationService : AuthenticationService){}
  
  ngOnInit(): void {
    this.getUniteFabs(false) ;
  }
  

  public getUniteFabs (showNotification : boolean ):void {
    this.subscriptions.push(
    this.uniteFabService.getAllUniteFab().subscribe(
        (response : UniteFabrication[] ) => {
          this.uniteFabs  = response ;
          this.totalElements=response.length;
          if (showNotification){
            this.sendNotification(NotificationType.SUCCESS, `${response.length} UF(s) loaded successfully . `) ;
          }
        },
        (errorResponse : HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }

  public searchUniteFabs(searchTerm: string): void {
    const results: UniteFabrication[] = [];
    for (const uf of this.uniteFabs) {
      if (uf.codeUf.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1
      || uf.mapa.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1) {
        results.push(uf);
      }
    }
    this.uniteFabs = results;
    if (results.length === 0 || !searchTerm) {
      this.getUniteFabs(false);
    }
  }


  

  public addNewUF (uniteFabForm : NgForm) : void {
    const formData = this.uniteFabService.createGroupFormData(null , uniteFabForm.value ); 
    this.subscriptions.push(
      this.uniteFabService.addUniteFab(formData).subscribe(
        (response : UniteFabrication) => {
         this.clickButton('new-UF-close'); 
          this.getUniteFabs(false);
          uniteFabForm.reset();
          this.sendNotification(NotificationType.SUCCESS , `${response.codeUf} UF added successfully`) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }

  public saveNewUF () : void {
    this.clickButton('new-UF-save');
  }


  public updateUniteFab () : void{
    const formData = this.uniteFabService.createGroupFormData( this.currentCodeUf  , this.editUf) ;
    this.subscriptions.push(
      this.uniteFabService.updateUniteFab(formData).subscribe(
        (response : UniteFabrication) => {
         this.clickButton('edit-uf-close') ; 
          this.getUniteFabs(false);
          this.sendNotification(NotificationType.SUCCESS , `${response.codeUf} UF updated successfully `) ;
        },
        (errorResponse : HttpErrorResponse) => {
         this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
      );
  }

  public editUfInfo (editUf : UniteFabrication):void{
    this.editUf =editUf  ;
    this.currentCodeUf=editUf.codeUf;
    this.clickButton('openEditUF') ; 
  }

  public deleteUF (codeUf : string ):void{
    this.subscriptions.push(
      this.uniteFabService.deleteUniteFab(codeUf).subscribe(
        (response : CostumHttpResponse) => {
        this.sendNotification(NotificationType.WARNING , response.message);
        this.getUniteFabs(false);
      },
       (errorResponse : HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
       }
      )
    )
  }


  public getLignesProdByUF (showNotification : boolean):void {
    this.subscriptions.push(
    this.uniteFabService.getLignesProdsByUF(this.unitefab.codeUf).subscribe(
        (response : LigneProduction[] ) => {
          this.lignes  = response ;
          if (showNotification){
            this.sendNotification(NotificationType.SUCCESS, `${response.length} lignes of this UF loaded successfully . `) ;
          }
        },
        (errorResponse : HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        }
      )
    );
  }

  public getLignesList (unitefab :UniteFabrication):void{
    this.unitefab = unitefab ;
    this.codeUf=unitefab.codeUf;
    this.clickButton('openLignesProdList') ; //  id  du button hidden (list membre du groupe)
  }



  private sendNotification(notificationType: NotificationType, message: string) :void{
    if(message){
        this.notificationService.notify(notificationType,message) ;
      } else {
        this.notificationService.notify(notificationType, 'An error occure . please try again ');
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

  private clickButton(buttonId : string) : void {
    document.getElementById(buttonId).click();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub=>sub.unsubscribe());
  }
}
