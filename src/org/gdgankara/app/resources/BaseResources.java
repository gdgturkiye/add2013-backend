package org.gdgankara.app.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gdgankara.app.model.Session;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Path("/")
public class BaseResources {

	@GET
	@Path("sessions/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Session> getSessions(@PathParam("lang") String lang) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query sessionQuery = new Query(Session.KIND);
		Filter filter = new FilterPredicate(Session.LANG, FilterOperator.EQUAL,
				lang);
		sessionQuery.setFilter(filter);

		PreparedQuery preparedQuery = dataStore.prepare(sessionQuery);
		List<Entity> eSessionList = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		List<Session> sessionList = new ArrayList<Session>();
		for (Entity entity : eSessionList) {
			Session session = new Session(entity.getKey().getId(),
					(String) entity.getProperty(Session.LANG),
					(String) entity.getProperty(Session.DAY),
					(String) entity.getProperty(Session.START_HOUR),
					(String) entity.getProperty(Session.END_HOUR),
					(String) entity.getProperty(Session.HALL),
					(String) entity.getProperty(Session.TITLE),
					(String) entity.getProperty(Session.DESCRIPTION),
					(String) entity.getProperty(Session.SPEAKER));
			sessionList.add(session);
		}
		return sessionList;
	}
	
	@GET
	@Path("version")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVersion(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Session session;		
		Entity eSession = new Entity("version");
		return null;
	}

	@GET
	@Path("godkiller")
	@Produces(MediaType.TEXT_PLAIN)
	public String godKiller() {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query godKillerQuery = new Query();
		PreparedQuery preparedGodKillerQuery = dataStore
				.prepare(godKillerQuery);
		for (Entity entity : preparedGodKillerQuery.asIterable()) {
			dataStore.delete(entity.getKey());
		}
		return "hayırlısı be gülüm";
	}

	String[] hourArray = { "09:00", "09:30", "10:00", "10:30", "11:00",
			"11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
			"15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00",
			"18:30", "19:00", "19:30", "20:00" };

	@GET
	@Path("samples")
	@Produces(MediaType.TEXT_PLAIN)
	public String samples() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Session session;		
		Entity eSession;
		
		try {
			for (int i = 0; i < 11; i++) {
				session = new Session("tr", "14 Haziran 2013 Cuma", hourArray[i],
						hourArray[i + 1], "A", "Baslik_" + i + "_A_14", "Aciklama_"
								+ i, "Konusmaci_" + i);
				eSession = new Entity(Session.KIND);
				eSession.setProperty(Session.LANG, session.getLang());
				eSession.setProperty(Session.DAY, session.getDay());
				eSession.setProperty(Session.START_HOUR, session.getStartHour());
				eSession.setProperty(Session.END_HOUR, session.getEndHour());
				eSession.setProperty(Session.HALL, session.getHall());
				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
				eSession.setProperty(Session.TITLE, session.getTitle());
				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
				datastore.put(eSession);

				session = new Session("en", "14 June 2013 Friday", hourArray[i],
						hourArray[i + 1], "A", "Title_" + i + "_A_14",
						"Description_" + i, "Speaker_" + i);
				eSession = new Entity(Session.KIND);
				eSession.setProperty(Session.LANG, session.getLang());
				eSession.setProperty(Session.DAY, session.getDay());
				eSession.setProperty(Session.START_HOUR, session.getStartHour());
				eSession.setProperty(Session.END_HOUR, session.getEndHour());
				eSession.setProperty(Session.HALL, session.getHall());
				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
				eSession.setProperty(Session.TITLE, session.getTitle());
				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
				datastore.put(eSession);
//				
//				session = new Session("tr", "14 Haziran 2013 Cuma", hourArray[i],
//						hourArray[i + 1], "B", "Baslik_" + i + "_B_14", "Aciklama_"
//								+ i, "Konusmaci_" + i);
//				eSession = new Entity(Session.KIND);
//				eSession.setProperty(Session.LANG, session.getLang());
//				eSession.setProperty(Session.DAY, session.getDay());
//				eSession.setProperty(Session.START_HOUR, session.getStartHour());
//				eSession.setProperty(Session.END_HOUR, session.getEndHour());
//				eSession.setProperty(Session.HALL, session.getHall());
//				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
//				eSession.setProperty(Session.TITLE, session.getTitle());
//				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
//				datastore.put(eSession);
	//
//				session = new Session("en", "14 June 2013 Friday", hourArray[i],
//						hourArray[i + 1], "B", "Title_" + i + "_B_14",
//						"Description_" + i, "Speaker_" + i);
//				eSession = new Entity(Session.KIND);
//				eSession.setProperty(Session.LANG, session.getLang());
//				eSession.setProperty(Session.DAY, session.getDay());
//				eSession.setProperty(Session.START_HOUR, session.getStartHour());
//				eSession.setProperty(Session.END_HOUR, session.getEndHour());
//				eSession.setProperty(Session.HALL, session.getHall());
//				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
//				eSession.setProperty(Session.TITLE, session.getTitle());
//				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
//				datastore.put(eSession);
	//
//				session = new Session("tr", "15 Haziran 2013 Cuma", hourArray[i],
//						hourArray[i + 1], "A", "Baslik_" + i + "_A_15", "Aciklama_"
//								+ i, "Konusmaci_" + i);
//				eSession = new Entity(Session.KIND);
//				eSession.setProperty(Session.LANG, session.getLang());
//				eSession.setProperty(Session.DAY, session.getDay());
//				eSession.setProperty(Session.START_HOUR, session.getStartHour());
//				eSession.setProperty(Session.END_HOUR, session.getEndHour());
//				eSession.setProperty(Session.HALL, session.getHall());
//				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
//				eSession.setProperty(Session.TITLE, session.getTitle());
//				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
//				datastore.put(eSession);
	//
//				session = new Session("en", "15 June 2013 Friday", hourArray[i],
//						hourArray[i + 1], "A", "Title_" + i + "_A_15",
//						"Description_" + i, "Speaker_" + i);
//				eSession = new Entity(Session.KIND);
//				eSession.setProperty(Session.LANG, session.getLang());
//				eSession.setProperty(Session.DAY, session.getDay());
//				eSession.setProperty(Session.START_HOUR, session.getStartHour());
//				eSession.setProperty(Session.END_HOUR, session.getEndHour());
//				eSession.setProperty(Session.HALL, session.getHall());
//				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
//				eSession.setProperty(Session.TITLE, session.getTitle());
//				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
//				datastore.put(eSession);
	//
//				session = new Session("tr", "15 Haziran 2013 Cuma", hourArray[i],
//						hourArray[i + 1], "B", "Baslik_" + i + "_B_15", "Aciklama_"
//								+ i, "Konusmaci_" + i);
//				eSession = new Entity(Session.KIND);
//				eSession.setProperty(Session.LANG, session.getLang());
//				eSession.setProperty(Session.DAY, session.getDay());
//				eSession.setProperty(Session.START_HOUR, session.getStartHour());
//				eSession.setProperty(Session.END_HOUR, session.getEndHour());
//				eSession.setProperty(Session.HALL, session.getHall());
//				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
//				eSession.setProperty(Session.TITLE, session.getTitle());
//				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
//				datastore.put(eSession);
	//
//				session = new Session("en", "15 June 2013 Friday", hourArray[i],
//						hourArray[i + 1], "B", "Title_" + i + "_B_15",
//						"Description_" + i, "Speaker_" + i);
//				eSession = new Entity(Session.KIND);
//				eSession.setProperty(Session.LANG, session.getLang());
//				eSession.setProperty(Session.DAY, session.getDay());
//				eSession.setProperty(Session.START_HOUR, session.getStartHour());
//				eSession.setProperty(Session.END_HOUR, session.getEndHour());
//				eSession.setProperty(Session.HALL, session.getHall());
//				eSession.setProperty(Session.SPEAKER, session.getSpeaker());
//				eSession.setProperty(Session.TITLE, session.getTitle());
//				eSession.setProperty(Session.DESCRIPTION, session.getDescription());
//				datastore.put(eSession);
				
			}

		} catch(Exception e){
			return e.toString();
		}
		
		
		return "well done";
	}
}
