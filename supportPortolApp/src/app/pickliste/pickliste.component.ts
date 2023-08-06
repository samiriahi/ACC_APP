import { Component } from '@angular/core';
import { Role } from '../enum/role';
import { NotificationService } from '../service/notification.service';
import { AuthenticationService } from '../service/authentication.service';
import { Subscription } from 'rxjs';
import { Pickliste } from '../model/pickliste';
import { PicklisteService } from '../service/pickliste.service';
import { NotificationType } from '../enum/notification-type.enum';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import * as XLSX from 'xlsx';


@Component({
  selector: 'app-pickliste',
  templateUrl: './pickliste.component.html',
  styleUrls: ['./pickliste.component.css']
})
export class PicklisteComponent {

  public show = false ;
  private subscriptions : Subscription [] = [] ;
  public refreshing : boolean ; 
  public picklistes : Pickliste[];
  public codeUf : string ; 
  public mag : string ; 
  p: number = 1;
  itemsPerPage : number = 6;
  totalElements: any ;
  
  constructor( private authenticationService :AuthenticationService ,  private notificationService : NotificationService ,
               private picklisteService :PicklisteService ){}


public getPicklistes (showNotification : boolean , picklisteForm : NgForm ):void {
  this.refreshing = true ;
  const codeUf = picklisteForm.value['codeUf'] ;
  const mag = picklisteForm.value['mag'] ;
  this.subscriptions.push(
  this.picklisteService.getPicklistes(codeUf,mag).subscribe(
      (response : Pickliste[] ) => {
        this.picklistes  = response ;
        this.refreshing = false ;
        this.totalElements=response.length;
        if (!showNotification || this.picklistes.length==0){
          this.show=false;
          this.sendNotification(NotificationType.ERROR , `aucune  pickliste trouvee avec ces code UF et Magasin !`) ;
        }else {
          this.sendNotification(NotificationType.SUCCESS, `${response.length} Pickliste(s) loaded successfully . `) ;
          this.show=true;
        }
      },
      (errorResponse : HttpErrorResponse) => {
        this.sendNotification(NotificationType.ERROR , errorResponse.error.message) ;
        this.refreshing = false ;
      }
    )
  );
}

exportExcel(): void {
  const element = document.getElementById('my-table'); // récupère le tableau HTML
  const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element); // transforme le tableau en un objet utilisable par xlsx
  const wb: XLSX.WorkBook = XLSX.utils.book_new(); // crée un nouveau fichier Excel
  XLSX.utils.book_append_sheet(wb, ws, 'Feuille 1'); // ajoute la feuille au fichier Excel
  XLSX.writeFile(wb, 'export.xlsx'); // télécharge le fichier Excel
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
}
