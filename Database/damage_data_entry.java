import java.util.Date;

public class damage_data_entry extends data_entry{
	private int painting_id;
	private int user_id;
	private String damage_type;
	private String layer_path;
	private Date edit_date;
	private int archived;

	public damage_data_entry (int newid, int newPaintingId, int newUserId, String newDamageType, String newLayerPath, Date newDate, int archiveState){
		//adding println to verify writing is happening in the function
		super(newid);
		System.out.println("Wrote newid");
		painting_id = newPaintingId;
		System.out.println("Wrote painting_id");
		user_id = newUserId;
		System.out.println("Wrote user_id");
		damage_type = newDamageType;
		System.out.println("Wrote damage_type");
		layer_path = newLayerPath;
		System.out.println("Wrote layer_path");
		edit_date = newDate;
		System.out.println("Wrote edit_date");
		archived = archiveState;
		System.out.println("Wrote archived");
	}
	
	public int get_painting_id(){
		return painting_id;
	}

	public int	get_user_id(){
		return user_id;
	}

	public String get_damage_type(){
		return damage_type;
	}

	public String get_layer_path(){
		return layer_path;
	}

	public Date get_edit_date(){
		return edit_date;
	}
}