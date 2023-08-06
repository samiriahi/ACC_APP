import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpResponse} from '@angular/common/http' ;
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { CostumHttpResponse } from '../model/custom-http-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host  = environment.apiUrl ;

  constructor(private http : HttpClient ) { }

  public getUsers ():Observable < User[] > {
  return this.http.get<User[]>(`${this.host}/user/list`);
}

public addUser (formData :FormData):Observable < User> {
  return this.http.post<User>(`${this.host}/user/add` , formData );
}


public updateUser (formData :FormData):Observable <User > {
  return this.http.post<User>(`${this.host}/user/update`, formData );
}


public resetPassword (email :string ):Observable <CostumHttpResponse > {
  return this.http.get<CostumHttpResponse>(`${this.host}/user/resetPassword/${email}`);
}

public updateProfileImage (formData :FormData ):Observable < HttpEvent<User>> {
  return this.http.post <User>(`${this.host}/user/updateProfileImage`, formData, {reportProgress : true,  observe : 'events'});
}

public deleteUser (username :String ):Observable <CostumHttpResponse > {
  return this.http.delete<CostumHttpResponse>(`${this.host}/user/delete/${username}`);
  }

public addUsersToLocalCache(users :User[]) : void {
  localStorage.setItem('users',JSON.stringify(users));
}

public getusersFromLocalCache() : User [] {
  if (localStorage.getItem('users')){
  return JSON.parse(localStorage.getItem('users')) ;
 } 
return null ;
} 

public getTotalUsers(): Observable<number> {
  return this.http.get<number>(`${this.host}/user/count`);
}

public getActiveUsers(): Observable<number> {
  return this.http.get<number>(`${this.host}/user/active/count`);
}

public getInactiveUsers(): Observable<number> {
  return this.http.get<number>(`${this.host}/user/inactive/count`);
}

public getNotLockedUsersCount():Observable<number> {
  return this.http.get<number>(`${this.host}/not-locked-count`);
}

public getLockedUsersCount() :Observable<number> {
  return this.http.get<number>(`${this.host}/locked-count`);
}

public createUserFormData(loggedInUsername: string, user: User, profileImage: File) : FormData {
  const formData = new FormData();
  formData.append('currentUsername', loggedInUsername) ;
  formData.append('firstName', user.firstName) ;
  formData.append('lastName', user. lastName) ;
  formData.append('username', user.username) ;
  formData.append('email', user.email);
  formData.append('role', user.role);
  formData.append('profileImage', profileImage) ;
  formData.append('isActive', JSON.stringify(user.active)) ;
  formData.append('isNotLocked', JSON.stringify(user.notLocked)) ;
  return formData;
}


}
