public class user_data_entry extends data_entry{
	private String name;
	private String pass;
	private int status;
	public user_data_entry(int newid, String newName, String newPass, int newStatus){
		super(newid);
		name= newName;
		pass= newPass;
		status = newStatus;
	}
	
	public String get_name(){
		return name;
	}
	public void set_user(String newName){
		name=newName;
	}
	public String get_pass(){
		return pass;
	}
	public void set_pass(String newPass){
		pass=newPass;
	}
	public int get_status(){
		return status;
	}
	public void set_status(int newStatus){
		status=newStatus;
	}
}