Use of the Database Object
(Descriptions, Use, and Info)

1: What Classes Are In This File?
There are 8 files to use in this database. Only 6 of them will need to be called by a developer. These files are:
user_model.java, 
user_data_entry.java, 
art_model.java, 
art_data_entry.java, 
damage_model.java, &
damage_data_entry.java.


2: What does a model file do? 
Model files preform basic CRUD* commands with the SQL database, there is a model file for each table that you will use. These files are an extension of DB_Master, that holds all connection info for a given SQL database. This was done to make switching from one database to another simple and easy.


3: What does a data entry file do?
Data Entry files contain information for a single row taken from the SQL database. Because Java does not allow for unordered lists, this was made an object instead. 

Data can be accessed with get_data_name() and optionally changed with set_data_name(type newdata). It is important to note that setting data this way does NOT change the data in the SQL database in any way.

It is also important to note that all data held by a damage_data_entry object cannot be changed, only retrieved. This was done on purpose.

4: How are Images Stored?
Currently there are two Database Tables that are supposed to hold image data: "art" and "damages". These tables hold a list of Strings that path to actual images, whether stored locally in relation to the DB file, or online.

5: What values does the "users" table hold?
The users table lists all valid users in the database, and holds 4 values: 

id, which holds the user's unique id.

name, which holds the user's username.
(Note: More work needs to be done to ensure that identical usernames can not be added.)

pass, which holds the user's password. 
(Note: More work needs to be done to ensure this data is stored in the SQL database with an encrypted format.)

status, which holds the user's account status in an int. (admin=1, user=2, guest=3, etc.)


6: What values does the "art" table hold?
The art table lists all existing paintings in the database, and holds 4 values:

id, which holds the painting's unique id.

name, which holds the name of the painting.
(Note: More work needs to be done to ensure that identical painting names can not be added.)

artist, which lists the artist of a painting.
(Note: if time permits, a seperate table for artists can be added to ensure that fake ones are not listed.)

image_path, which holds a URI to a stored image.

7: What values does the "damage" table hold?

id, which holds the damage report's unique id.

user_id, which holds a unique id from the "user" table, for refrencing who reported the damage.

painting_id, which holds a unique id from the "art" table, for referencing the art piece in question.
(Note: Functionality needs to be added to ensure that the painting id exists.)

damage_type, which holds a string that states what type of damage was caused to the art piece referenced in painting_id.

layer_path, which holds a URI to a stored image file.

archived, a Boolean(int 1 or 0) that holds whether or not a damage report is visible. Since damage reports are to never be removed under any circumstances, they can be "archived" instead.


8: Other Notes
Functions are still needed to Update existing entries in the database. As of now, functions should only be able Create and Read from a database, or add full entries. There will likely be no functionality to delete an item from the Database. This would have to be done manually.


*CRUD: Create, Read, Update, Destroy