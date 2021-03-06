package org.gdgankara.app.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Version;
import org.gdgankara.app.utils.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/speaker")
public class SpeakerResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Path("create")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void newSpeakerJson(JAXBElement<Speaker> jaxbSpeaker)
			throws IOException {
		Speaker speaker = jaxbSpeaker.getValue();
		Entity eSpeaker = new Entity(Speaker.KIND);
		eSpeaker = Util.setSpeakerEntityProperties(eSpeaker, speaker);
		DatastoreServiceFactory.getDatastoreService().put(eSpeaker);
		Version.setVersion();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Speaker getSpeakerByID(@PathParam("id") Long id)
			throws EntityNotFoundException {
		Entity eSpeaker = DatastoreServiceFactory.getDatastoreService().get(
				KeyFactory.createKey(Speaker.KIND, id));
		Speaker speaker = Util.getSpeakerFromEntity(eSpeaker);
		return speaker;
	}

	@POST
	@Path("update/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Speaker updateSpeakerByID(@PathParam("id") Long id,
			JAXBElement<Speaker> jaxbSpeaker) throws EntityNotFoundException {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Speaker speaker = jaxbSpeaker.getValue();
		Entity eSpeaker = new Entity(Speaker.KIND, id);
		eSpeaker = Util.setSpeakerEntityProperties(eSpeaker, speaker);
		dataStore.put(eSpeaker);
		Version.setVersion();
		return speaker;
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteSpeakerByID(@PathParam("id") Long id) {
		DatastoreServiceFactory.getDatastoreService().delete(
				KeyFactory.createKey(Speaker.KIND, id));
		Version.setVersion();
	}
}
