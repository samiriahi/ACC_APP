import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CostumHttpResponse } from '../model/custom-http-response';
import { LigneProduction } from '../model/ligneProduction';
import { Poste } from '../model/poste';

@Injectable({
  providedIn: 'root'
})
export class PosteService {

  private host  = environment.apiUrl ;

  constructor(private http : HttpClient ) {}

  
public getTotalPostes(): Observable<number> {
  return this.http.get<number>(`${this.host}/poste/count`);
}

  public getPostes ():Observable < Poste[] > {
    return this.http.get<Poste[]>(`${this.host}/poste/list`);
  }

  public addPoste (formData :FormData):Observable < Poste> {
    return this.http.post<Poste>(`${this.host}/poste/add` , formData );
  }
  
  
  public updatePoste (formData :FormData):Observable <Poste > {
    return this.http.post<Poste>(`${this.host}/poste/update `, formData );
  }

  public deletePoste (nomPoste :String ):Observable <CostumHttpResponse > {
    return this.http.delete<CostumHttpResponse>(`${this.host}/poste/delete/${nomPoste}`);
  }

  public assignPosteToLigneProd (formData:FormData) : Observable<CostumHttpResponse>{
    return this.http.post<CostumHttpResponse>(`${this.host}/poste/assignPosteToLigneProd`, formData);
  }
  

  public getPostesByLigneProd (codeLp :String):Observable < Poste[] > {
    return this.http.get<Poste[]>(`${this.host}/poste/PostesByLigneProd/${codeLp}`);
  }

  public removePosteFromLigneProd (nomPoste :String ):Observable <CostumHttpResponse > {
    return this.http.delete<CostumHttpResponse>(`${this.host}/poste/removePosteFromLigneProd/${nomPoste}`);
  }

  public createFormData(poste : Poste) : FormData {
    const formData = new FormData();
    formData.append('nomPoste',poste.nomPoste) ;
    formData.append('nomReseau',poste.nomReseau) ;
    formData.append('cadence',poste.cadence) ;
    return formData ; 
  }

  public createPosteLigneForm( nomPoste : string , codeLp : string , poste : Poste , ligneProd : LigneProduction ) : FormData {
    const formData = new FormData();
    formData.append('nomPoste',poste.nomPoste) ;
    formData.append('codeLp' ,ligneProd.codeLp ) ;
    return formData ; 
  }


}
