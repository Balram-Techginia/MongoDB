
//import java.util.ArrayList;
//import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;

@Path("/callservice")
public class mongodb {
	@GET
	@Path("/call")
	@Produces(MediaType.APPLICATION_JSON)

	public static MongoClient getConnection() {
		try {
			return new MongoClient(new MongoClientURI("mongodb://admin:myadminpassword@enter your DB IP"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GET
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes
	public String insertOne() {
		JSONObject returnobject = new JSONObject();
		MongoClient client = getConnection();
		MongoCollection<Document> userCollection = client.getDatabase("QuickLookDB").getCollection("nikhil");
		System.out.println("RUNNING");
		// ----------- Inserting single Data ------------------------------//
		Document doc = new Document("name", "ketan");
		doc.append("id", 14);
		userCollection.insertOne(doc);
		returnobject.put("inserted", "True");
		return returnobject.toString();
	}

	@GET
	@Path("/insertmany")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes
	public String insertmany() {
		JSONObject returnobject = new JSONObject();
		MongoClient client = getConnection();
		MongoCollection<Document> userCollection = client.getDatabase("QuickLookDB").getCollection("nikhil");
		System.out.println("RUNNING");
		// ----------- Inserting Multiple data Data ------------------------------//
		ArrayList<Document> doc = new ArrayList<>();
		Document stDocument = new Document("name", "ankur");
		Document ndtDocument = new Document("name", "balram");
		Document rdDocument = new Document("name", "yogesh");

		doc.add(stDocument);
		doc.add(ndtDocument);
		doc.add(rdDocument);

		Iterator<Document> iterator = userCollection.find().iterator();
		JSONObject returnObject = new JSONObject();
		while (iterator.hasNext()) {
			Document temporaryDocument = iterator.next();
			returnObject.put("first_name", temporaryDocument.getString("name"));
		}

		userCollection.insertMany(doc);
		returnobject.put("inserted", "True");
		return returnobject.toString();
	}

	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete() {
		JSONObject returnobject = new JSONObject();
		MongoClient client = getConnection();
		MongoCollection<Document> userCollection = client.getDatabase("QuickLookDB").getCollection("nikhil");
		System.out.println("RUNNING");
		// ----------- deleting Data ------------------------------//

		new Document();
		Document query = new Document();
		query.append("name", "ketan");
		userCollection.find(query);
		userCollection.deleteMany(query);
		returnobject.put("Deleted", "True");
		return returnobject.toString();

	}

	@GET
	@Path("/find")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes
	public String find() {
		JSONObject returnobject = new JSONObject();
		MongoClient client = getConnection();
		MongoCollection<Document> userCollection = client.getDatabase("QuickLookDB").getCollection("nikhil");
		System.out.println("RUNNING");

		// ----------- Find ------------------------------//

		Document data = userCollection.find(new Document("name", "ketan")).first();
		System.out.println("1st data: " + data.toJson());

		returnobject.put("1st data: " + data.toJson(), false);
		return returnobject.toString();
	}

	@GET
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes
	public String update() {
		JSONObject returnobject = new JSONObject();
		MongoClient client = getConnection();
		MongoCollection<Document> userCollection = client.getDatabase("QuickLookDB").getCollection("nikhil");
		System.out.println("RUNNING");

		// ----------- update ------------------------------//

		// update one document
		Document query = new Document();
		query.append("name", "ketan");
		userCollection.find(query);
		Document updateFilter = new Document();
		Document updateDocument = new Document();
		updateFilter.append("name", "ketan");
		updateDocument.append("surname", "singh");
		System.out.println(updateDocument);
		returnobject.put("1st data changed: " + updateDocument, false);
		return returnobject.toString();
	}
	@GET
	@Path("/paramupdate")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes
	public String paramupdate(String data) {
		JSONObject json = new JSONObject(data);
		String name = json.getString("name");
		JSONObject returnobject = new JSONObject();
		MongoClient client = getConnection();
		MongoCollection<Document> userCollection = client.getDatabase("QuickLookDB").getCollection("nikhil");
		System.out.println("RUNNING");
		
		// ----------- insert by user ------------------------------//
		
		// update one document
		Document query = new Document();
		query.append("name", name);
	
		userCollection.insertOne(query);
		
		returnobject.put("Inserted by User: " + json, false);
		return returnobject.toString();
	}
	
	@GET
	@Path("/paramdelete")
	@Produces(MediaType.APPLICATION_JSON)
	public String paramdelete(String data) {
		JSONObject returnobject = new JSONObject();
		JSONObject json = new JSONObject(data);
		String name = json.getString("name");
		MongoClient client = getConnection();
		MongoCollection<Document> userCollection = client.getDatabase("QuickLookDB").getCollection("nikhil");
		System.out.println("RUNNING");
		// ----------- deleting Data ------------------------------//

		new Document();
		Document query = new Document();
		query.append("name", name);
		//userCollection.find(query);
		userCollection.deleteMany(query);
		returnobject.put("Deleted", "True");
		return returnobject.toString();

	}

}
