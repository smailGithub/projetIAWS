/**
 * 
 */
package inscription;


/**
 * @author Ismail
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		InscriptionInt inscription=new InscriptionImpl();
		System.out.println(inscription.verificationPersonnel("carassus","vincent","carassus.vincent@univ-tlse3.fr",31000));

	}

}
