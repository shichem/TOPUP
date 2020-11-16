/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author HITCHI
 */
public class EnumTopopResponse {
    /*
    **************************************************  OOREDOO  ************************************************************************
    *************SUCCESS*************
    +CUSD: 1,"Vous voulez transf�rer 100 DA de votre compte vers le num�ro 0555555555, tapez 1 pour confirmer. Ou tapez 2 pour annuler",15
     CUSD: 2,"Recharge de l'abonne 0555555555 100.00 Dinar realisee. Numero de l'operation 564088491.",15
    
    *************UNDER_MIN_AMOUNT*************
    +CUSD: 1,"Num�ro incorrect\nEntez la somme � recharger",15
    
    *************INSUFFICIENT_CREDIT*************
    +CUSD: 1,"Vous voulez transf�rer 9999 DA de votre compte vers le num�ro 0554702040, tapez 1 pour confirmer. Ou tapez 2 pour annuler",15
    +CUSD: 2,"Valeur minimale autorisee du stock atteinte pour le solde debite. ",15
    *****************************************************************************************************************************************
    */
    
    
    /*
    **************************************************  MOBILIS  ************************************************************************
    *************SUCCESS*************
    +CUSD: 2,"credit transmis",15
    
    *************UNDER_MIN_AMOUNT*************
    +CUSD: 2,"echec de transfert: montant inferieur a  40DZD.",15    
    
    *************OVER_MAX_AMOUNT*************
    +CUSD: 2,"echec de transfert: montant superieur a  4999DZD.",15
    
    *************INSUFFICIENT_CREDIT*************
    +CUSD: 2,"Le montant Areselli demande est insuffisant.",15
    *****************************************************************************************************************************************
    */
    
        
    /*
    **************************************************  DJEZZY  ************************************************************************
    *************SUCCESS*************
    +CUSD: 1,"VOUS VOULEZ TRANSFERER 100 DA DE VOTRE COMPTE VERS LE NUMERO 0795555555.\n1:POUR CONFIRMER\n2:POUR ANNULER",15
    
    *************UNDER_MIN_AMOUNT*************
    +CUSD: 1,"VOUS VOULEZ TRANSFERER 20 DA DE VOTRE COMPTE VERS LE NUMERO 0795555555.\n1:POUR CONFIRMER\n2:POUR ANNULER",15
    +CUSD: 2,"VOUS NE POUVEZ PAS RECHARGER LE COMPTE DE VOTRE CORRESPONDANT MONTANT NON AUTORISE",15
    
    *************OVER_MAX_AMOUNT*************
    +CUSD: 2,"echec de transfert: montant superieur a  4999DZD.",15
    
    *************INSUFFICIENT_CREDIT*************
    +CUSD: 2,"Le montant Areselli demande est insuffisant.",15
    *****************************************************************************************************************************************
    */
}
