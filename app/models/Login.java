package models;


public class Login {
	
	public String email;
	public String password;
	
	public String validate() {
		
	try{
		
		if(User.authenticate(email, password) != null){
          return null;
		}else{
			return "Credenciais inválidas!";
		}
		
      }catch(Exception ex){
    	  return "Credenciais inválidas!";
      }
	
    }

}
