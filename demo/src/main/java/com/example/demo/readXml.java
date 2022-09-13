package com.example.demo;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.input.*;
import org.jdom2.*;

public class readXml {

    static String msg="";
    //algo 1
	public String verificationTemporelles() throws ParseException {
	
		try 
		{    
			  //obtenir une instance de document xml sérialisé 
			  SAXBuilder builder = new SAXBuilder();
		      builder.setIgnoringElementContentWhitespace(true);
		      Document document = builder.build(new File("test2.xml"));
              //recuperer la racine du document 
		      Element elementRacine = document.getRootElement();
			  	msg = verifier_CoherenceTemporelle ( elementRacine )+""+verifier_RecouvrementDates ( elementRacine);
			  
		}
		catch(JDOMException e)
		{
			 e.printStackTrace();
		 }
		catch (IOException e)
		{
		   e.printStackTrace();
		}
        return msg;
		
	} 

	//algo 2
	//Phase 1 de vérification temporelle : verifier la coherence temporelle
	private static String  verifier_CoherenceTemporelle ( Element element ){
        String msg1=""; String msg2=""; String msg3 =""; String msg4 ="";

		//Etape 1 : verifier la progression temporelle entre process et ses fils direct 
		msg1 = verifier_EnchainementDates_Process_fils ( element);

		//Etape 2 : verifier la progression temporelle entre les fils directs de process et leurs fils 
		msg2= verifier_EnchainementDates_fils_suivant( element );

		//Etape 3 : verifier la progression temporelle entre les activités structurés et leurs fils
		msg3= verifier_EnchainementDates_ElementStructure_Fils (element,"0");

		//Etape 4 : verifier la progression temporelle entre les fils directs des activités structurés
		msg4= verifier_EnchainementDates_SousElementStructure (element,"0");
  
        return msg1+msg2+msg3+msg4;
	}

	//algo 3
	private static String verifier_EnchainementDates_Process_fils (Element element){
		int num=0; String Dur_precedent = ""; String Instant_Debut_precedent =""; String InstantDebut="";
		String d = ""; String dur_pére = "";  int somme_duree=0; String InstantDebut_pere = "";
        Boolean OK=true; String msg=""; String name_pére=""; String name_fils="";
		

		//System.out.println(element.getName());
        //parcourir les attributs de process et recuperer dur et instant debut 
		List attributs = element.getAttributes();
		ListIterator iterAttributes = attributs.listIterator();
		while (iterAttributes.hasNext()) {
		  Attribute attribut = (Attribute) iterAttributes.next();
            


                //modifier la valeur de instant debut de la racine
                if(attribut.getName() == "InstantDebut" ){

                    attribut.setValue("0");
                    
                    InstantDebut_pere = attribut.getValue();
                    //System.out.println(attribut.getName()+" = "+InstantDebut_pere);
                }
                
                if(attribut.getName() == "DUR"){
                    dur_pére = attribut.getValue();
                   // System.out.println(attribut.getName()+" = "+Calculer_Dur(dur_pére));
                }

				if(attribut.getName() == "name"){
                    name_pére = attribut.getValue();
                   
                }
            
        }

		//parcourir l'arbre et recuperer les fils directs de process
		/************** */
	    List fils = element.getContent();
	    Iterator iterator = fils.iterator();
		
	    /**** */
	    while (iterator.hasNext()) { 
	      Object objetFils = iterator.next();
		  
	      if (objetFils instanceof Element) {
			  
	        Element elementFils = (Element) objetFils;
        
			//ignorer variables
			if(elementFils.getName()== "variables" || elementFils.getName()== "variable" || elementFils.getName()=="condition")
			{
				iterator.next(); } 
			else
			{   
                //ignorer extension activity et prendre en considération les activités REST
                if( elementFils.getName()=="extensionActivity"){
                    //recuperer l'activité REST et la considiré comme fille direct à la place de extension activity
                    List fille = elementFils.getChildren();
                    Iterator iterFille = fille.iterator();
                    while (iterFille.hasNext()) { 
                        elementFils = (Element) iterFille.next();
                    }
                }
				num++;
				//System.out.println(elementFils.getName());
			}

            

			/********* */
			/*recuperer les attributs des elements fils */
			List attributsFils= elementFils.getAttributes();
			ListIterator iterAttributesFils = attributsFils.listIterator();
			while (iterAttributesFils.hasNext()) {
				Attribute attributFils = (Attribute) iterAttributesFils.next();
			
				if(attributFils.getName() == "name"){
                    name_fils = attributFils.getValue();
                   
                }
                //modifier la valeur de instant debut du premier fils direct
				if(attributFils.getName() == "InstantDebut" && num == 1){

					attributFils.setValue(InstantDebut_pere);
					//System.out.println(attributFils.getName()+" = "+attributFils.getValue());
					Instant_Debut_precedent = attributFils.getValue();
					
				} 

				if(attributFils.getName() == "InstantDebut" && num > 1){
                  
					InstantDebut = Calculer_Instant_Debut(Instant_Debut_precedent, Dur_precedent);

					//verifier le processus de progression temporelle entre process et fils direct
					if( Integer.parseInt( InstantDebut_pere ) > Integer.parseInt( InstantDebut ) ) {
                        OK = false;
						msg =msg+"Erreur! progression temporelle non respectée:  l'instant début de "+element.getName()+"("+name_pére+") doit etre avant l'instant debut de"+elementFils.getName()+"("+name_fils+")";
					}
					attributFils.setValue(InstantDebut);
					//System.out.println(attributFils.getName()+" = "+attributFils.getValue());
					Instant_Debut_precedent = attributFils.getValue();
						
				}
			
				if(attributFils.getName() == "DUR" ){
					d = attributFils.getValue();
					//System.out.println(attributFils.getName()+" = "+Calculer_Dur(d));
					somme_duree = somme_duree + Calculer_Dur(d);
				}
			}
			     
				Dur_precedent = Integer.toString(Calculer_Dur(d));
				
		  	}
		} 
		//verifier si enchainement durées est respectés ! 
        /* 
		if( Calculer_Dur(dur_pére) < somme_duree)
		{
            OK = false;
			msg = "Conflit detecté, concernant l'enchainement des durées! "+Calculer_Dur(dur_pére)+"<"+somme_duree;
		}*/

        /*si aucune erreur n'a été détecté on continue la vérification
        if(OK == true){
            System.out.println("Bonne nouvelle ! aucun conflit n'a été détécté entre process et ses fils !");
            //System.out.println("Nous pouvons maintenant passer à la vérification de l'enchainement des instants début entre les fils de process !");
            //verifier_EnchainementDates_fils_suivant ( element );
        }
        else{
            //System.out.println("Corriger les erreurs detéctées pour pouvoir continuer la vérification");
        }*/
        return msg;
	}

	//algo 4
	private static String verifier_EnchainementDates_fils_suivant (Element element ){
		int num=0; String Dur_precedent = ""; String Instant_Debut_precedent =""; String InstantDebut="";
		String d = ""; Boolean OK=true; String msg=""; String name_fils=""; String name_suivant="";
		
		//parcourir l'arbre et recuperer les fils directs de process
		/************** */
	    List fils = element.getContent();
	    Iterator iterator = fils.iterator();
		
	    /**** */
	    while (iterator.hasNext()) { 
	      Object objetFils = iterator.next();
		  
	      if (objetFils instanceof Element) {
			  
	        Element elementFils = (Element) objetFils;
        
			//ignorer variables
			if(elementFils.getName()== "variables" || elementFils.getName()== "variable" || elementFils.getName()=="condition")
			{
				iterator.next(); } 
			else
			{
                //ignorer extension activity et prendre en considération les activités REST
                if( elementFils.getName()=="extensionActivity"){
                    //recuperer l'activité REST et la considiré comme fille direct à la place de extension activity
                   List fille = elementFils.getChildren();
                   Iterator iterFille = fille.iterator();
                   while (iterFille.hasNext()) { 
                       elementFils = (Element) iterFille.next();
                   }
                   
               
               }
				num++;
				//System.out.println(elementFils.getName());
			}

			/********* */
			/*recuperer les attributs des elements fils */
			List attributsFils= elementFils.getAttributes();
			ListIterator iterAttributesFils = attributsFils.listIterator();
			
			while (iterAttributesFils.hasNext()) {
				Attribute attributFils = (Attribute) iterAttributesFils.next();
			
                //modifier la valeur de instant debut du premier fils direct
				if(attributFils.getName() == "InstantDebut" && num == 1){

					attributFils.setValue("0");
					//System.out.println(attributFils.getName()+" = "+attributFils.getValue());
					Instant_Debut_precedent = attributFils.getValue();
					
				} 

				if(attributFils.getName() == "InstantDebut" && num > 1){
					InstantDebut = Calculer_Instant_Debut(Instant_Debut_precedent, Dur_precedent);

					//verifier le processus de progression temporelle entre process et fils direct
					if( Integer.parseInt( Instant_Debut_precedent ) > Integer.parseInt( InstantDebut ) ) {
                        OK = false;
						msg=msg+"Erreur! progression temporelle non respectée: l'instant début de "+element.getName()+" doit etre inferieur à l'instant debut de "+elementFils.getName()+"";
					}
					
					attributFils.setValue(InstantDebut);
					//System.out.println(attributFils.getName()+" = "+attributFils.getValue());
					Instant_Debut_precedent = attributFils.getValue();
						
				}
				if(attributFils.getName() == "DUR" ){
					d = attributFils.getValue();
					//System.out.println(attributFils.getName()+" = "+Calculer_Dur(d));
					
				}
			}
			     
				Dur_precedent = Integer.toString(Calculer_Dur(d));
				
		  	}
            
		} 

        /* 
            //si aucune erreur n'a été détecté on continue la vérification
            if(OK == true){
                System.out.println("Bonne nouvelle ! aucun conflit n'a été détécté entre les fils de process!");
                //System.out.println("Nous pouvons maintenant passer à la vérification de l'enchainement des instants début entre les fils des elements structurés !");
                //verifier_EnchainementDates_SousElementStructure (element, "0");
            }
            else{
                //System.out.println("Corriger les erreurs detéctées pour pouvoir continuer la vérification");
            }*/

            return msg;
		
	}

	//algo 5
	private static String verifier_EnchainementDates_ElementStructure_Fils (Element element, String InstantDebut_pere)
 	{  int num =0; int duree_precedent=0; String InstantDebut_precedent=""; int InstantDebut=0; int duree=0; String msg="";
		//System.out.println("Pere = "+element.getName());
		/********* */
			/*recuperer les attributs des elements fils */
			List attributs= element.getAttributes();
			ListIterator iterAttributes = attributs.listIterator();
			
			while (iterAttributes.hasNext()) {
				Attribute attribut = (Attribute) iterAttributes.next();
			
                //modifier la valeur de instant debut du premier fils direct
				if(attribut.getName() == "InstantDebut" ){
					attribut.setValue(InstantDebut_pere);
					//System.out.println("Pére: "+element.getName()+"--->"+attribut.getName()+" = "+attribut.getValue());
					
				}
			}
		List fils = element.getContent();
		Iterator iterator = fils.iterator();
		while (iterator.hasNext()) {
		Object objetFils = iterator.next();
			if (objetFils instanceof Element) {
				Element elementFils = (Element) objetFils;

				//ignorer variables
			if(elementFils.getName()== "variables" || elementFils.getName()== "variable" || elementFils.getName()=="condition")
			{
				objetFils=iterator.next();
				
				 } 
			else
			{
                //ignorer extension activity et prendre en considération les activités REST
                if( elementFils.getName()=="extensionActivity"){
                    //recuperer l'activité REST et la considiré comme fille direct à la place de extension activity
                   List fille = elementFils.getChildren();
                   Iterator iterFille = fille.iterator();
                   while (iterFille.hasNext()) { 
                       elementFils = (Element) iterFille.next();
                   }
            	}
				//System.out.println("Fils = "+elementFils.getName());
			
				
					num++;
					//recuperer les attributs
					List attributsFils= elementFils.getAttributes();
						ListIterator iterAttributesFils = attributsFils.listIterator();
						while (iterAttributesFils.hasNext()) {
							Attribute attributFils = (Attribute) iterAttributesFils.next();
							//si le pére est une activité strcuturé en paralléle
							if(element.getName()=="flow"|| element.getName()=="if" || element.getName()=="pick"){
								if(attributFils.getName()=="InstantDebut"){
									attributFils.setValue(InstantDebut_pere);
									InstantDebut_precedent = attributFils.getValue();
								}
							}
							else{

							
							if(num==1 && attributFils.getName()=="InstantDebut"){
								attributFils.setValue(InstantDebut_pere);
								InstantDebut_precedent = attributFils.getValue();
							}else{
									if(num==1 && attributFils.getName()=="DUR")
									{
										duree = Calculer_Dur(attributFils.getValue());
										duree_precedent = duree;
									}else{
											if(num>1 && attributFils.getName()=="DUR")
												{
													duree = Calculer_Dur(attributFils.getValue());
													//duree_precedent= duree;
												}
												else
												{
												
													if(num>1 && attributFils.getName()=="InstantDebut"){
														InstantDebut = Integer.parseInt(InstantDebut_precedent) + duree_precedent;
														//System.out.println("pére: "+element.getName()+"fils : "+elementFils.getName());
														
														attributFils.setValue(Integer.toString(InstantDebut));
														InstantDebut_precedent = Integer.toString(InstantDebut);
														duree_precedent= duree;
													}
												}
										}
								}
							}
							
							
						}
					}
					//comparer entre l'instant debut du pére avec ses fils
					if(elementFils.getName()!= "variables" && elementFils.getName()!="condition"){

						//System.out.println("pére: "+element.getName()+" ID-pére: "+InstantDebut_pere+" ----> fils : "+elementFils.getName()+" ID-fils: "+InstantDebut_precedent);
						if(Integer.parseInt(InstantDebut_pere) > Integer.parseInt(InstantDebut_precedent)){
							msg= msg + "Conflit inter-element détecté! entre L'instant debut du pére: "+element.getName()+"avec l'instant debut de son fils : "+elementFils.getName()+"";
						}
					}
					
					
					
					if(elementFils.getName() == "sequence" || elementFils.getName() == "forEach" || elementFils.getName() == "while" 
					|| elementFils.getName() == "repeatUntil"|| elementFils.getName() == "if" || elementFils.getName() == "pick" || elementFils.getName() == "flow" || elementFils.getName() == "loop" ){
					
						msg = msg+verifier_EnchainementDates_ElementStructure_Fils(elementFils, InstantDebut_precedent);
					}
				}
				
		}
        return msg;
		
	}

	//algo 6
	private static String verifier_EnchainementDates_SousElementStructure (Element element, String InstantDebut_pere)
 	{  
		int num =0; int duree_precedent=0; String InstantDebut_precedent=""; int InstantDebut=0; int duree=0; String msg="";
		//System.out.println("Pere = "+element.getName());
		/********* */
			/*recuperer les attributs des elements fils */
			List attributs= element.getAttributes();
			ListIterator iterAttributes = attributs.listIterator();
			
			while (iterAttributes.hasNext()) {
				Attribute attribut = (Attribute) iterAttributes.next();
			
                //modifier la valeur de instant debut du premier fils direct
				if(attribut.getName() == "InstantDebut" ){
					attribut.setValue(InstantDebut_pere);
					
					
				}
			}
		List fils = element.getContent();
		Iterator iterator = fils.iterator();
		while (iterator.hasNext()) {
		Object objetFils = iterator.next();
			if (objetFils instanceof Element) {
				Element elementFils = (Element) objetFils;

				//ignorer variables
			if(elementFils.getName()== "variables" || elementFils.getName()== "variable" || elementFils.getName()=="condition")
			{
				objetFils=iterator.next();
				
				 } 
			else
			{
                //ignorer extension activity et prendre en considération les activités REST
                if( elementFils.getName()=="extensionActivity"){
                    //recuperer l'activité REST et la considiré comme fille direct à la place de extension activity
                   List fille = elementFils.getChildren();
                   Iterator iterFille = fille.iterator();
                   while (iterFille.hasNext()) { 
                       elementFils = (Element) iterFille.next();
                   }
            	}
				//System.out.println("Fils = "+elementFils.getName());
			
				
					num++;
					//recuperer les attributs
					List attributsFils= elementFils.getAttributes();
						ListIterator iterAttributesFils = attributsFils.listIterator();
						while (iterAttributesFils.hasNext()) {
							Attribute attributFils = (Attribute) iterAttributesFils.next();
							//si le pére est une activité strcuturé en paralléle
							if(element.getName()=="flow"|| element.getName()=="if" || element.getName()=="pick"){
								if(attributFils.getName()=="InstantDebut"){
									attributFils.setValue(InstantDebut_pere);
									InstantDebut_precedent = attributFils.getValue();
								}
							}
							else{

							
							if(num==1 && attributFils.getName()=="InstantDebut"){
								attributFils.setValue(InstantDebut_pere);
								InstantDebut_precedent = attributFils.getValue();
							}else{
									if(num==1 && attributFils.getName()=="DUR")
									{
										duree = Calculer_Dur(attributFils.getValue());
										duree_precedent = duree;
									}else{
											if(num>1 && attributFils.getName()=="DUR")
												{
													duree = Calculer_Dur(attributFils.getValue());
													//duree_precedent= duree;
												}
												else
												{
												
													if(num>1 && attributFils.getName()=="InstantDebut"){
														InstantDebut = Integer.parseInt(InstantDebut_precedent) + duree_precedent;
														//comparer entre l'instant debut du fils avec son frére
														if(elementFils.getName()!= "variables" && elementFils.getName()!="condition"){

															//System.out.println( "ID: "+InstantDebut_precedent+" ID-fils: "+InstantDebut);
															if(Integer.parseInt(InstantDebut_precedent) > InstantDebut){
																msg = msg +"Conflit inter-element! entre les fils de "+element.getName()+"";
															}
														}
														attributFils.setValue(Integer.toString(InstantDebut));
														InstantDebut_precedent = Integer.toString(InstantDebut);
														duree_precedent= duree;
													}
												}
										}
								}
							}
							
							
						}
					}
					
					
					
					
					if(elementFils.getName() == "sequence" || elementFils.getName() == "forEach" || elementFils.getName() == "while" 
					|| elementFils.getName() == "repeatUntil"|| elementFils.getName() == "if" || elementFils.getName() == "pick" || elementFils.getName() == "flow" || elementFils.getName() == "loop" ){
					
						msg = msg+ verifier_EnchainementDates_SousElementStructure(elementFils, InstantDebut_precedent);
					}
				}
				
		}
        return msg;
	}

	//algo 7
	//Phase 2 de vérification temporelle : verifier le recouvrement de dates
	private static String verifier_RecouvrementDates ( Element element ) throws ParseException{
 
        String msg1=""; String msg2=""; String msg3=""; 
		//Etape 1 : verifier pour chaque element structuré si les contraintes dates sont respectés
		msg1=verifier_Contraintes_EltStructure (element);

		/*Etape 2 : vérifier les contraintes temporelles [CD,CF] de chaque activité structurée 
		avec les dates effectives [DD,DF] de toutes les sous activités*/
		msg2=verifier_Contraintes_mére_filles( element );

		/*Etape 3 : vérification de l'enchaînement des dates des sous-activités de chaque activité mère en comparant DF de chaque sous-activité
		 (nœud fils) avec DD de la prochaine (nœud frère). */
	    msg3=verifier_Contraintes_sousElt_Structure( element );
  
        return msg1+msg2+msg3;
	}

	//algo 8
	private static String verifier_Contraintes_EltStructure (Element element) throws ParseException
	{
		Date DD = new Date(); Date DF = new Date(); Date CD = new Date(); Date CF = new Date(); 
		String TC = ""; String DD_String=""; String DF_String=""; String CD_String=""; String CF_String="";
		Boolean ok = true; String msg=""; String name="";
		// creer le tableau de string 
		String[] activite_structurés = {"sequence", "forEach","while","repeatUntil","if","pick","flow","loop" };

        //System.out.println("pére = "+element.getName());
		// verifier pour process
		 //parcourir les attributs de process et recuperer dur et instant debut 
		 if(element.getName()=="process"){
			List attributs = element.getAttributes();
			ListIterator iterAttributes = attributs.listIterator();
			while (iterAttributes.hasNext()) {
				Attribute attribut = (Attribute) iterAttributes.next();
				switch (attribut.getName()){
					case "DD": try {
							DD=stringToDate(attribut.getValue());
							DD_String = attribut.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "DF": try {
							DF= stringToDate(attribut.getValue());
							DF_String = attribut.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "TC": TC= attribut.getValue(); break;
					case "CD": try {
							CD= stringToDate(attribut.getValue());
							CD_String = attribut.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "CF": try {
							CF=stringToDate(attribut.getValue());
							CF_String = attribut.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "name": 
						name=attribut.getValue(); break;
					
				}
				//comparer entre les dates selon le type de contraintes
				switch (TC){
					case "Avant": if(DD.compareTo(CD)>0 || DF.compareTo(CD)>0 )
						{ ok = false;
							msg="Conflit de recouvrement de dates détecté : DD ="+DD_String+" et DF ="+DF_String+" de l'element "+element.getName()+" ("+name+") doivent etre "+TC+" CD ="+CD_String+"";}
						break;
					case "Entre": if(DD.compareTo(CD)<0 || DF.compareTo(CF)>0 )
						{ ok = false;
						  msg="Conflit de recouvrement de dates détecté : DD ="+DD_String+" et DF ="+DF_String+" de l'element "+element.getName()+" ("+name+") doivent etre "+ TC +" CD ="+CD_String+" et CF ="+CF_String+"";} 
						break;
					case "Apres": if(DD.compareTo(CF)<0 || DF.compareTo(CF)<0)
						{ ok = false;
						  msg="Conflit de recouvrement de dates détecté : DD ="+DD_String+" et DF ="+DF_String+" de l'element "+element.getName()+" ("+name+") doivent etre "+TC+" CF ="+CF_String+"";} 
						break; 
				}
				
			}
		 } 
		 
		//parcourir les elements structurés
		List fils = element.getChildren();
		Iterator iterator = fils.iterator();
		while (iterator.hasNext()) {
			name="";
			Element elementFils = (Element) iterator.next();

            // chercher les elements structurés
			String a = "";
				for(int i = 0; i< activite_structurés.length ; i++)
				{   a = activite_structurés[i];
					if(elementFils.getName() == a){
						//recuperer les attributs pour pouvoir les comparer
						/*recuperer les attributs des elements fils */
                        
						List attributsFils= elementFils.getAttributes();
						ListIterator iterAttributesFils = attributsFils.listIterator();
						while (iterAttributesFils.hasNext()) {
							Attribute attributFils = (Attribute) iterAttributesFils.next();
							switch (attributFils.getName()){
								case "DD": try {
										DD=stringToDate(attributFils.getValue());
										DD_String = attributFils.getValue();
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} break;
								case "DF": try {
										DF= stringToDate(attributFils.getValue());
										DF_String = attributFils.getValue();
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} break;
								case "TC": TC= attributFils.getValue(); break;
								case "CD": try {
										CD= stringToDate(attributFils.getValue());
										CD_String = attributFils.getValue();
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} break;
								case "CF": try {
										CF=stringToDate(attributFils.getValue());
										CF_String = attributFils.getValue();
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} break;

									case "name": name= attributFils.getValue();
										break;
								
							}
						}
					
						//comparer entre les dates selon le type de contraintes
				switch (TC){
					case "Avant": if(DD.compareTo(CD)>0 || DF.compareTo(CD)>0 )
						{ ok = false;
						  msg="Conflit de recouvrement de dates détecté : DD ="+DD_String+" et DF ="+DF_String+" de l'element "+elementFils.getName()+" ("+name+") doivent etre "+TC+" CD ="+CD_String+"";} 
						break;
					case "Entre": if(DD.compareTo(CD)<0 || DF.compareTo(CF)>0 )
						{ ok = false;
						  msg="Conflit de recouvrement de dates détecté : DD ="+DD_String+" et DF ="+DF_String+" de l'element "+elementFils.getName()+" ("+name+") doivent etre "+ TC +" CD ="+CD_String+" et CF ="+CF_String+"";} 
						break;
					case "Apres": if(DD.compareTo(CF)<0 || DF.compareTo(CF)<0)
						{ ok = false;
						  msg="Conflit de recouvrement de dates détecté : DD ="+DD_String+" et DF ="+DF_String+" de l'element "+elementFils.getName()+" ("+name+") doivent etre "+TC+" CF ="+CF_String+"";} 
						break; 
				}
				}
			}
			msg= msg+verifier_Contraintes_EltStructure(elementFils);
		} 

		/*//si aucune erreur n'a été détecté on continue la vérification
        if(ok == true){
            System.out.println("Bonne nouvelle ! aucun conflit n'a été détécté dans les contraintes des elements strucutrés !");
            System.out.println("Nous pouvons maintenant passer à la vérification de [CD,CF] de chaque activité structuré avec [DD,DF] de toutes ses activités filles !");
        }
        else{
            System.out.println("Corriger les erreurs detéctées pour pouvoir continuer la vérification");
        }*/
       return msg;
	}

	//algo 9
	private static String verifier_Contraintes_mére_filles (Element element ) throws ParseException
	{
		Date DD = new Date(); Date DF = new Date(); Date CD = new Date(); Date CF = new Date(); 
		String TC = ""; String msg=""; String name_pere=""; String name_fils=""; String DD_String=""; String DF_String=""; String CD_String=""; String CF_String="";
		Boolean ok = true;

		if(element.getName() == "process" || element.getName() == "sequence" || element.getName() == "forEach" || element.getName() == "while" 
		|| element.getName() == "repeatUntil"|| element.getName() == "if" || element.getName() == "pick" || element.getName() == "flow" || element.getName() == "loop" ){

	
            //System.out.println("pére = "+element.getName());
			List attributs = element.getAttributes();
			ListIterator iterAttributes = attributs.listIterator();
			while (iterAttributes.hasNext()) {
				Attribute attribut = (Attribute) iterAttributes.next();
				switch (attribut.getName()){
					case "TC": TC= attribut.getValue(); break;
					case "CD": try {
							CD= stringToDate(attribut.getValue());
							CD_String=attribut.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "CF": try {
							CF=stringToDate(attribut.getValue());
							CF_String=attribut.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "name": name_pere= attribut.getValue();
						break;
					
				}
			}

			// parcourir les fils
		List fils = element.getChildren();
		Iterator iterator = fils.iterator();
		while (iterator.hasNext()) {
			name_fils="";
			Element elementFils = (Element) iterator.next();
			//ignorer variables
			if(elementFils.getName()== "variables" || elementFils.getName()== "variable" || elementFils.getName()=="condition")
			{
				elementFils = (Element) iterator.next(); } 
			else
			{   
                //ignorer extension activity et prendre en considération les activités REST
                if( elementFils.getName()=="extensionActivity"){
                    //recuperer l'activité REST et la considiré comme fille direct à la place de extension activity
                    List fille = elementFils.getChildren();
                    Iterator iterFille = fille.iterator();
                    while (iterFille.hasNext()) { 
                        elementFils = (Element) iterFille.next();
                    }
                }
			}

			//System.out.println(elementFils.getName());
			List attributsFils = elementFils.getAttributes();
			ListIterator iterAttributesFils = attributsFils.listIterator();
			while (iterAttributesFils.hasNext()) {
				Attribute attributFils = (Attribute) iterAttributesFils.next();
				switch (attributFils.getName()){
					case "DD": try {
							DD= stringToDate(attributFils.getValue());
							DD_String=attributFils.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "DF": try {
							DF=stringToDate(attributFils.getValue());
							DF_String=attributFils.getValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
					case "name": name_fils=attributFils.getValue();
					
				}
			}

			//comparer [CD,CF] du pére avec [DD,DF] de ses fils
			switch (TC){
				case "Avant": if(DD.compareTo(CD)>0 || DF.compareTo(CD)>0 )
					{ ok = false;
					  msg ="Conflit de recouvrement de dates détecté: DD= "+DD_String+" et DF="+DF_String+" de "+elementFils.getName()+"("+name_fils+") doivent etre "+TC+" CD="+CD_String+" de "+element.getName()+"("+name_pere+")";} 
					break;
				case "Entre": if(DD.compareTo(CD)<0 || DF.compareTo(CF)>0 )
					{ ok = false;
						msg="Conflit de recouvrement de dates détecté: DD= "+DD_String+" et DF="+DF_String+" de "+elementFils.getName()+"("+name_fils+") doivent etre "+TC+" CD="+CD_String+" et CF="+ CF_String+" de "+element.getName()+"("+name_pere+")";}  
					break;
				case "Apres": if(DD.compareTo(CF)<0 || DF.compareTo(CF)<0)
					{ ok = false;
						msg="Conflit de recouvrement de dates détecté: DD= "+DD_String+" et DF="+DF_String+" de "+elementFils.getName()+"("+name_fils+") doivent etre "+TC+" CF="+CF_String+" de "+element.getName()+"("+name_pere+")";}  
					break; 
			}

			msg = msg +verifier_Contraintes_mére_filles(elementFils);
			
		}
			
		}

		/*//si aucune erreur n'a été détecté on continue la vérification
        if(ok == true){
            System.out.println("Bonne nouvelle ! aucun conflit n'a été détécté dans les contraintes des elements strucutrés et les dates effectives de leurs fils !");
            System.out.println("Nous pouvons maintenant passer à la vérification de l'enchianement de  DF et DD entre les fils des activités structurés en séquence !");
			
        }
        else{
            System.out.println("Corriger les erreurs detéctées pour pouvoir continuer la vérification");
        }*/
        return msg;
	}

	//algo 10
	private static String verifier_Contraintes_sousElt_Structure (Element element ) throws ParseException
	{
		Date DD = new Date(); Date DF = new Date(); String name=""; String name_preced=""; 
		Boolean ok = true;
		Date DF_precedent = new Date();
		int num=0; String DD_String=""; String DF_String=""; String DF_prec_String=""; String msg="";

		if(element.getName() == "process" || element.getName() == "sequence" || element.getName() == "forEach" || element.getName() == "while" 
		|| element.getName() == "repeatUntil"||element.getName() == "loop" ){

	
            //System.out.println("pére = "+element.getName());
			
			// parcourir les fils
		List fils = element.getChildren();
		Iterator iterator = fils.iterator();
		String elt_preced="";
		while (iterator.hasNext()) {
			Element elementFils = (Element) iterator.next();
			//ignorer variables
			if(elementFils.getName()== "variables" || elementFils.getName()== "variable" || elementFils.getName()=="condition")
			{
				elementFils = (Element) iterator.next(); } 
			else
			{   
                //ignorer extension activity et prendre en considération les activités REST
                if( elementFils.getName()=="extensionActivity"){
                    //recuperer l'activité REST et la considiré comme fille direct à la place de extension activity
                    List fille = elementFils.getChildren();
                    Iterator iterFille = fille.iterator();
                    while (iterFille.hasNext()) { 
                        elementFils = (Element) iterFille.next();
                    }
                }
			}

			//System.out.println(elementFils.getName());
			num++; 
					
					if(num==1){
						//recuperer le nom du fils
						elt_preced = elementFils.getName();
						//recuperer les attributs pour pouvoir les comparer
						/*recuperer les attributs des elements fils */
						List attributsFils= elementFils.getAttributes();
						ListIterator iterAttributesFils = attributsFils.listIterator();
						while (iterAttributesFils.hasNext()) {
							Attribute attributFils = (Attribute) iterAttributesFils.next();
							if(attributFils.getName()=="DF")
							{
								
									DF_prec_String = attributFils.getValue();
									//System.out.println("DF precedent"+DF_prec_String);
							}
							if(attributFils.getName()=="name"){
								name_preced = attributFils.getValue();
							}
							
						}
						
					
					} else {
						//recuperer le nom de suivant
						String elt_suivant = elementFils.getName();
						//System.out.println("cc");
						//recuperer les attributs pour pouvoir les comparer
						/*recuperer les attributs des elements fils */
						List attributsFils= elementFils.getAttributes();
						ListIterator iterAttributesFils = attributsFils.listIterator();
						
						while (iterAttributesFils.hasNext()) {
							Attribute attributFils = (Attribute) iterAttributesFils.next();
							if(attributFils.getName()=="name"){
								 name=attributFils.getValue();
							}
							if(attributFils.getName()=="DD")
							{
								    
									DD_String = attributFils.getValue();
									//System.out.println("DD "+DD_String);
							}
							if(attributFils.getName()=="DF")
							{

									DF_String = attributFils.getValue();
									//System.out.println("DF "+DF_String);
									
							}		
									

						}
						//convertir string to date
						DF_precedent = stringToDate(DF_prec_String);
						DD = stringToDate(DD_String);
						//System.out.println("DFprecedent= "+DF_precedent);
						//System.out.println("DD = "+DD);
						
						//comparer entre df du précedent et dd du svuivant
						if(DF_precedent.compareTo(DD)>0){
							msg= msg+"Conflit dans le recouvrement de date : enchainement des dates non respecté: DF="+DF_prec_String+" de "+elt_preced+"("+name_preced+") doit etre avant DD="+DD_String+" de "+elt_suivant+"("+name+")";
							//System.out.println("DFprecedent= "+DF_precedent);
							//System.out.println("DD = "+DD);
							ok = false;
						}else{
							DF_prec_String = DF_String;
							ok = true;
						}		
						
					}

			msg = msg+verifier_Contraintes_sousElt_Structure (elementFils);
			
		}
			/*//si aucune erreur n'a été détecté on continue la vérification
			if(iterator.hasNext() == false){
				if(ok == true){
					System.out.println("End of File! aucun conflit n'a été détécté dans tout le scénario , vous pouvez maintenant simuler l'execution !");
				}
				else{
					System.out.println("Corriger les erreurs detéctées pour pouvoir continuer la vérification");
				}
			}*/
		}

		
        return msg;

	}

	//Calculer instant debut de l'element courant
	static String Calculer_Instant_Debut(String Instant_Debut_precedent, String Dur_precedent){
		int value =Integer.parseInt(Instant_Debut_precedent)+ Integer.parseInt(Dur_precedent);
		String result = Integer.toString(value);
		return (result) ;
	}

	//convertir la duree en secondes
	static int Calculer_Dur(String DUR){
		Integer year=0, month=0, day=0, hour=0, minute=0, seconde=0; 
		String pattern = "P(.+)Y(.+)M(.+)DT(.+)H(.+)M(.+)S";
		//create pattern object
		Pattern r = Pattern.compile(pattern);
		 
		//create matcher object
		Matcher m = r.matcher(DUR);

		if(m.find()){
			year = Integer.parseInt(m.group(1));
			month = Integer.parseInt(m.group(2));
			day = Integer.parseInt(m.group(3));
			hour = Integer.parseInt(m.group(4));
			minute = Integer.parseInt(m.group(5));
			seconde = Integer.parseInt(m.group(6));
			
		} /*else {
			System.out.println("NO MATCH FIND !");
		}*/
        return(year*3600*24*256+month*3600*24*30+day*3600*24+hour*3600+minute*60+seconde);
	}

	// convertir un string to date
	private static Date stringToDate(String date) throws ParseException{
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdFormat.parse(date);
		return (d);
	}
    
}
