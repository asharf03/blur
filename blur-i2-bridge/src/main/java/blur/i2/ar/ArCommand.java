package blur.i2.ar;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;

import com.i2group.apollo.externaldata.connector.IExternalDataItemEditor;
import com.i2group.apollo.externaldata.loader.AnalysisRepositoryLoader;
import com.i2group.apollo.externaldata.loader.IDataLoader;

public class ArCommand {
	public static final String GENERATE_ID = null;

	Random f_random = new Random();
	Map<String, Long> f_purgeList = new HashMap<String, Long>();
	
	List<String> f_personList = new ArrayList<String>();
	List<String> f_eventList = new ArrayList<String>();
	List<String> f_addressList = new ArrayList<String>();
	List<String> f_vehicleList = new ArrayList<String>();
	List<String> f_orgList = new ArrayList<String>();

	List<String> f_associateList = new ArrayList<String>();
	List<String> f_accessToList = new ArrayList<String>();
	List<String> f_memberOfList = new ArrayList<String>();
	List<String> f_involvedInList = new ArrayList<String>();
	List<String> f_communicationList = new ArrayList<String>();
	
	String f_queryTemplate;
	String f_entityTemplate;
	String f_linkTemplate;
	
	String f_eventTemplate;
	String f_personTemplate;
	String f_addressTemplate;
	String f_vehicleTemplate;
	String f_orgTemplate;

	String f_associateTemplate;
	String f_accessToTemplate;
	String f_memberOfTemplate;
	String f_involvedInTemplate;
	String f_communicationTemplate;
	
	public ArCommand() {
		f_queryTemplate  = fileToString("scheme/query.template.xml");
		f_entityTemplate = fileToString("scheme/entity.template.xml");
		f_linkTemplate = fileToString("scheme/link.template.xml");
		
		f_eventTemplate  = fileToString("scheme/event.template.xml");
		f_personTemplate = fileToString("scheme/person.template.xml");
		f_addressTemplate = fileToString("scheme/address.template.xml");
		f_vehicleTemplate = fileToString("scheme/vehicle.template.xml");
		f_orgTemplate = fileToString("scheme/org.template.xml");
		
		f_associateTemplate = fileToString("scheme/associate.template.xml");
		f_accessToTemplate = fileToString("scheme/accessto.template.xml");
		f_memberOfTemplate = fileToString("scheme/memberof.template.xml");
		f_involvedInTemplate = fileToString("scheme/involvedin.template.xml");
		f_communicationTemplate = fileToString("scheme/communication.template.xml");
	}

	public void execute() {
		String command = f_queryTemplate
				.replaceAll("#person_list", String.join("\n", f_personList))
				.replaceAll("#event_list", String.join("\n", f_eventList))
				.replaceAll("#address_list", String.join("\n", f_addressList))
				.replaceAll("#vehicle_list", String.join("\n", f_vehicleList))
				.replaceAll("#org_list", String.join("\n", f_orgList))
				.replaceAll("#associate_list", String.join("\n", f_associateList))
				.replaceAll("#memberof_list", String.join("\n", f_memberOfList))
				.replaceAll("#accessto_list", String.join("\n", f_accessToList))
				.replaceAll("#involvedin_list", String.join("\n", f_involvedInList))
				.replaceAll("#communication_list", String.join("\n", f_communicationList));

		// Debug
		//System.out.println( command );
		//System.exit(1);
		
		System.setProperty("ApolloServerSettingsResource", "blur/i2/ar/loader.properties");
		IDataLoader dl = new AnalysisRepositoryLoader();
		IExternalDataItemEditor itemEditor = new ExampleItemEditor();

		dl.createItems(new StreamSource(new StringReader(command)), itemEditor);
		dl.purgeItems(f_purgeList);
	}

	public void purgeItem(String id) {
		f_purgeList.put(id, null);
	}
	
	public void createPerson(String id, String firstName, String familyName, String dob) {
		String instance = f_entityTemplate
				.replaceAll("#entity_type", "Person")
				.replaceAll("#entity_content", f_personTemplate)
				.replaceAll("#id", id)
				.replaceAll("#first_name", firstName)
				.replaceAll("#family_name", familyName)
				.replaceAll("#dob", dob);

		f_personList.add(instance);
	}

	public void createOrganization(String id, String name, String type) {
		String instance = f_entityTemplate
				.replaceAll("#entity_type", "Organization")
				.replaceAll("#entity_content", f_orgTemplate)
				.replaceAll("#id", id)
				.replaceAll("#name", name)
				.replaceAll("#type", type);

		f_orgList.add(instance);
	}

	public void createEvent(String id, String type, String timestamp, String description, String location) {
		if( id == GENERATE_ID ) { id = String.valueOf(Math.abs(f_random.nextLong())); }
		
		String instance = f_entityTemplate
				.replaceAll("#entity_type", "Event")
				.replaceAll("#entity_content", f_eventTemplate)
				.replaceAll("#id", id)
				.replaceAll("#type", type)
				.replaceAll("#timestamp", timestamp)
				.replaceAll("#description", description)
				.replaceAll("#location", location);

		f_eventList.add(instance);
	}

	public void createAddress(String id, String type, String latitude, String longitude) {
		String instance = f_entityTemplate
				.replaceAll("#entity_type", "Address")
				.replaceAll("#entity_content", f_addressTemplate)
				.replaceAll("#id", id)
				.replaceAll("#type", type)
				.replaceAll("#latitude", latitude)
				.replaceAll("#longitude", longitude);
		
		f_addressList.add(instance);
	}

	public void createVehicle(String id, String make, String model, String year, String type) {
		String instance = f_entityTemplate
				.replaceAll("#entity_type", "Vehicle")
				.replaceAll("#entity_content", f_vehicleTemplate)
				.replaceAll("#id", id)
				.replaceAll("#make", make)
				.replaceAll("#model", model)
				.replaceAll("#year", year)
				.replaceAll("#type", type);
		
		f_vehicleList.add(instance);
	}

	public void createAssociate(String id, String type, String fromId, String toId) {
		String instance = f_linkTemplate
				.replaceAll("#link_type", "Associate")
				.replaceAll("#link_content", f_associateTemplate)
				.replaceAll("#id", id)
				.replaceAll("#type", type)
				.replaceAll("#from", fromId)
				.replaceAll("#to", toId);
	
		f_associateList.add(instance);
	}
	
	public void createAccessTo(String id, String type, String fromId, String toId) {
		String instance = f_linkTemplate
				.replaceAll("#link_type", "AccessTo")
				.replaceAll("#link_content", f_accessToTemplate)
				.replaceAll("#id", id)
				.replaceAll("#type", type)
				.replaceAll("#from", fromId)
				.replaceAll("#to", toId);
		
		f_accessToList.add(instance);
	}

	public void createMemberOf(String id, String role, String fromId, String toId) {
		String instance = f_linkTemplate
				.replaceAll("#link_type", "MemberOf")
				.replaceAll("#link_content", f_memberOfTemplate)
				.replaceAll("#id", id)
				.replaceAll("#role", role)
				.replaceAll("#from", fromId)
				.replaceAll("#to", toId);
		
		f_memberOfList.add(instance);
	}

	public void createInvolvedIn(String id, String type, String fromId, String toId, String startDate) {
		if( id == GENERATE_ID ) { id = String.valueOf(Math.abs(f_random.nextLong())); }
		
		String instance = f_linkTemplate
				.replaceAll("#link_type", "InvolvedIn")
				.replaceAll("#link_content", f_involvedInTemplate)
				.replaceAll("#id", id)
				.replaceAll("#type", type)
				.replaceAll("#from", fromId)
				.replaceAll("#to", toId)
				.replaceAll("#start_date", startDate);
		
		f_involvedInList.add(instance);
	}

	public void createCommunication(String id, String type, String fromId, String toId, String startDate, String endDate, String duration) {
		if( id == GENERATE_ID ) { id = String.valueOf(Math.abs(f_random.nextLong())); }
		
		String instance = f_linkTemplate
				.replaceAll("#link_type", "Communication")
				.replaceAll("#link_content", f_communicationTemplate)
				.replaceAll("#id", id)
				.replaceAll("#type", type)
				.replaceAll("#from", fromId)
				.replaceAll("#to", toId)
				.replaceAll("#start_date", startDate)
				.replaceAll("#end_date", startDate)
				.replaceAll("#duration", startDate);
		
		f_communicationList.add(instance);
	}

	private String fileToString(String name) {
		try {
			String dir = this.getClass().getPackage().toString().replace('.', '/').substring(8);
			StringBuffer sb = new StringBuffer().append('/').append(dir).append('/').append(name);

			InputStream is = this.getClass().getResourceAsStream(sb.toString());
			return IOUtils.toString(is, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}
}