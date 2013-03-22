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
		System.out.println(inscription.verificationPersonnel("senhaji","ismail","ismail.senhaji@univ-tls3.fr",31000));

	}

}
