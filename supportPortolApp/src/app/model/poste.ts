import { LigneProduction } from "./ligneProduction";

export class Poste {
    public nomPoste  : string ;
    public nomReseau  : string ;
    public cadence  : string ;
    public dateCreation: Date ;
    public dateMaj : Date ;
    public ligneProd : LigneProduction ;

    constructor(){
        this.nomPoste= '' ;
        this.nomReseau = '' ;
        this.cadence = '' ;
        this.dateCreation=null;
        this.dateMaj=null ;
    }

}