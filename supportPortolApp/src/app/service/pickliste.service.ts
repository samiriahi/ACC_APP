import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pickliste } from '../model/pickliste';

@Injectable({
  providedIn: 'root'
})
export class PicklisteService {

  private host  = environment.apiUrl ;

  constructor(private http : HttpClient ) { }

  public getPicklistes (codeUf :String , mag :String):Observable < Pickliste[] > {
  return this.http.get<Pickliste[]>(`${this.host}/pickliste/${codeUf}/${mag}`);
  }

  public getNombrePicklistesDemander() : Observable <number> {
    return this.http.get<number>(`${this.host}/pickliste/demandes/count`);
  }

  public getNombrePicklistesServi() : Observable <number> {
    return this.http.get<number>(`${this.host}/pickliste/servis/count`);
  }

}