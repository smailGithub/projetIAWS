package inscription;

public class InscriptionImpl implements InscriptionInterface{

	public int verificationPersonnel(String nom, String prenom, String mail,
			String adrPostale) {
		
		//vérification du mail
		if(mail.contains("@") && mail.substring(mail.lastIndexOf('@') + 1).equals("univ-tlse3.fr")) {
			return 110; 
		}
		
		return 0;
	}
	
	//
	
	
	public static void main(String args[]) {
		InscriptionImpl i = new InscriptionImpl();
		System.out.println(i.verificationPersonnel("", "", "test@univ-tlse3.fr", ""));
		System.out.println(i.verificationPersonnel("", "", "testuniv-tlse3.fr", ""));
		System.out.println(i.verificationPersonnel("", "", "univ-tlse3.fr", ""));
		System.out.println(i.verificationPersonnel("", "", "test@univ-tle3.fr", ""));
	}

}
