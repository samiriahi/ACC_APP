import { User } from "./user";

export class Group {
    public nomGroupe : string ;
    public dateCreation : Date ;
    public isActive : Boolean ;
    public members : User[];


    constructor(){
        this.nomGroupe= '' ;
        this.dateCreation = null ;
        this.isActive= false ;
        this.members= [] ;
    }


}