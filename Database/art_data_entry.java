public class art_data_entry extends data_entry{
	private String name;
	private String artist;
	private String image_path;

	public art_data_entry (int newid, String newName, String newArtist, String newImagePath){
		super(newid);
		name = newName;
		artist = newArtist;
		image_path = newImagePath;
	}
	
	public String get_name(){
		return name;
	}

	public void set_name(String newName){
		name= newName;
	}

	public String get_artist(){
		return artist;
	}

	public void set_artist(String newArtist){
		artist=newArtist;
	}

	public String get_image_path(){
		return image_path;
	}

	public void set_image_path(String newImagePath){
		image_path=newImagePath;
	}

}