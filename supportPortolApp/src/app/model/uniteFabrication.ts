import { LigneProduction } from "./ligneProduction";

export class UniteFabrication {
    public codeUf : string ;
    public mapa : string ;
    public status : Boolean ;
    public lignesProds : LigneProduction[];


    constructor(){
        this.codeUf= '' ;
        this.mapa = '' ;
        this.status= false ;
        this.lignesProds= [] ;
    }


}