import { UniteFabrication } from "./uniteFabrication";

export class LigneProduction {
    public codeLp : string ;
    public robotTraitement : string ;
    public observation : string ;
    public dateMaj : Date ;
    public status : Boolean ;
    public uniteFab : UniteFabrication ;
    public codeUf :string ;

    constructor(){
        this.codeLp= '' ;
        this.robotTraitement = '' ;
        this.observation = '' ;
        this.dateMaj=null;
        this.status= false ;
        this.codeUf='' ;
    }


}