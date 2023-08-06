import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CostumHttpResponse } from '../model/custom-http-response';
import { LigneProduction } from '../model/ligneProduction';

@Injectable({
  providedIn: 'root'
})
export class LigneProdctionService {

  private host  = environment.apiUrl ;

  constructor(private http : HttpClient ) {}


  public getAllLignesProds ():Observable <LigneProduction[] > {
    return this.http.get<LigneProduction[]>(`${this.host}/ligneProd/list`);
  }

  public getSommesCadences ():Observable <any[] > {
    return this.http.get<any[]>(`${this.host}/ligneProd/sommes-cadences`);
  }

  public addNewLigneProd (formData :FormData):Observable < LigneProduction> {
    return this.http.post<LigneProduction>(`${this.host}/ligneProd/add`, formData );
  }
  
  
  public updateLigneProd (formData :FormData):Observable <LigneProduction > {
    return this.http.post<LigneProduction>(`${this.host}/ligneProd/update `, formData );
  }

  public deleteLigneProd (codeLp :String ):Observable <CostumHttpResponse > {
    return this.http.delete<CostumHttpResponse>(`${this.host}/ligneProd/delete/${codeLp}`);
  }


  public createFormData(ligneProd : LigneProduction) : FormData {
    const formData = new FormData();
    formData.append('codeLp',ligneProd.codeLp) ;
    formData.append('robotTraitement',ligneProd.robotTraitement) ;
    formData.append('observation',ligneProd.observation) ;
    formData.append('status', JSON.stringify(ligneProd.status)) ;
    formData.append('codeUf', ligneProd.codeUf) ;
    return formData ; 
  }





}
