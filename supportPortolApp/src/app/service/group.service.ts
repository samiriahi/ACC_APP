import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CostumHttpResponse } from '../model/custom-http-response';
import { Group } from '../model/group';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private host  = environment.apiUrl ;

  constructor(private http : HttpClient ) { }

  public getGroups ():Observable < Group[] > {
    return this.http.get<Group[]>(`${this.host}/groupe/list`);
  }

  public addGroup (formData :FormData):Observable < Group> {
    return this.http.post<Group>(`${this.host}/groupe/add` , formData );
  }
  
  
  public updateGroup (formData :FormData):Observable <Group > {
    return this.http.post<Group>(`${this.host}/groupe/update `, formData );
  }

  public deleteGroup (nomGroupe :String ):Observable <CostumHttpResponse > {
    return this.http.delete<CostumHttpResponse>(`${this.host}/groupe/delete/${nomGroupe}`);
  }

  public assignUSerTogroupe (formData:FormData) : Observable<CostumHttpResponse>{
    return this.http.post<CostumHttpResponse>(`${this.host}/groupe/assignUserToGroup`, formData);
  }

  public getGroupMembers (nomGroupe :String):Observable < User[] > {
    return this.http.get<User[]>(`${this.host}/groupe/members/${nomGroupe}`);
  }

  public removeUserFromGroup (username :String ):Observable <CostumHttpResponse > {
    return this.http.delete<CostumHttpResponse>(`${this.host}/groupe/removeUserFromGroup/${username}`);
  }




  public createGroupFormData( currentGroupeName : string ,  group : Group) : FormData {
    const formData = new FormData();
    formData.append('currentGroupeName' , currentGroupeName ) ;
    formData.append('nomGroupe',group.nomGroupe) ;
    formData.append('isActive', JSON.stringify(group.isActive)) ;
    return formData ; 
  }

  public createUserGroupForm( username : string , nomGroupe : string , group : Group , user : User ) : FormData {
    const formData = new FormData();
    formData.append('nomGroupe',group.nomGroupe) ;
    formData.append('username' ,user.username ) ;
    return formData ; 
  }

}
