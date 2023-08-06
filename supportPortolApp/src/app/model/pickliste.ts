import { LigneProduction } from "./ligneProduction";
import { Status } from "./status";
import { UniteFabrication } from "./uniteFabrication";

export class Pickliste {
    public numPickList  : string ;
    public magasin  : string ;
    public codeProduit  : string ;
    public typePickList  : string ;
    public hostame : string ;
    public observation : string ;
    public dateCreation: Date ;
    public dateMaj: Date ;
    public dateLivraison: Date ;
    public dateServi: Date ;
    public ligneProduction : LigneProduction ;
    public status : Status ;

    constructor(){ 
     
    }

}