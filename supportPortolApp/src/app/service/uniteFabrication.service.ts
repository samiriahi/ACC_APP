import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CostumHttpResponse } from '../model/custom-http-response';
import { LigneProduction } from '../model/ligneProduction';
import { UniteFabrication } from '../model/uniteFabrication';

@Injectable({
  providedIn: 'root'
})
export class UniteFabricationService {
  
  private host  = environment.apiUrl ;

  constructor(private http : HttpClient ) {}


  public getAllUniteFab ():Observable < UniteFabrication[] > {
    return this.http.get<UniteFabrication[]>(`${this.host}/uniteFab/list`);
  }

  public addUniteFab (formData :FormData):Observable < UniteFabrication> {
    return this.http.post<UniteFabrication>(`${this.host}/uniteFab/add` , formData );
  }
  
  
  public updateUniteFab (formData :FormData):Observable <UniteFabrication > {
    return this.http.post<UniteFabrication>(`${this.host}/uniteFab/update `, formData );
  }

  public deleteUniteFab (codeUf :String ):Observable <CostumHttpResponse > {
    return this.http.delete<CostumHttpResponse>(`${this.host}/uniteFab/delete/${codeUf}`);
  }

  

  public getLignesProdsByUF (codeUf :String):Observable < LigneProduction[] > {
    return this.http.get<LigneProduction[]>(`${this.host}/uniteFab/lignesProds/${codeUf}`);
  }




  public createGroupFormData( currentCodeUf : string ,  uniteFab : UniteFabrication) : FormData {
    const formData = new FormData();
    formData.append('currentCodeUf' , currentCodeUf ) ;
    formData.append('codeUf',uniteFab.codeUf) ;
    formData.append('mapa',uniteFab.mapa) ;
    formData.append('status', JSON.stringify(uniteFab.status)) ;
    return formData ; 
  }






}
