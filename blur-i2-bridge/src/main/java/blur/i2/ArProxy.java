package blur.i2;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.transform.stream.StreamSource;

import com.i2group.apollo.externaldata.connector.IExternalDataItemEditor;
import com.i2group.apollo.externaldata.loader.AnalysisRepositoryLoader;
import com.i2group.apollo.externaldata.loader.IDataLoader;

import blur.simulator.Utils;

public class ArProxy {
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

	public ArProxy() {
		String currentDir = this.getClass().getPackage().getName().replace('.', '/');
		String locationTemplate = new StringBuffer().append('/').append(currentDir).append("/scheme/%s").toString();

		f_queryTemplate = Utils.fileReader(String.format(locationTemplate, "query.template.xml"));
		f_entityTemplate = Utils.fileReader(String.format(locationTemplate, "entity.template.xml"));
		f_linkTemplate = Utils.fileReader(String.format(locationTemplate, "link.template.xml"));

		f_eventTemplate = Utils.fileReader(String.format(locationTemplate, "event.template.xml"));
		f_personTemplate = Utils.fileReader(String.format(locationTemplate, "person.template.xml"));
		f_addressTemplate = Utils.fileReader(String.format(locationTemplate, "address.template.xml"));
		f_vehicleTemplate = Utils.fileReader(String.format(locationTemplate, "vehicle.template.xml"));
		f_orgTemplate = Utils.fileReader(String.format(locationTemplate, "org.template.xml"));

		f_associateTemplate = Utils.fileReader(String.format(locationTemplate, "associate.template.xml"));
		f_accessToTemplate = Utils.fileReader(String.format(locationTemplate, "accessto.template.xml"));
		f_memberOfTemplate = Utils.fileReader(String.format(locationTemplate, "memberof.template.xml"));
		f_involvedInTemplate = Utils.fileReader(String.format(locationTemplate, "involvedin.template.xml"));
		f_communicationTemplate = Utils.fileReader(String.format(locationTemplate, "communication.template.xml"));
	}

	public void execute() {
		String command = f_queryTemplate.replaceAll("#person_list", String.join("\n", f_personList))
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
		//System.out.println(command);

		String currentDir = this.getClass().getPackage().getName().replace('.', '/');
		System.setProperty("ApolloServerSettingsResource", String.format("%s/%s", currentDir, "ArProxy.properties"));
		IDataLoader dl = new AnalysisRepositoryLoader();
		IExternalDataItemEditor itemEditor = new ArItemEditor();

		dl.createItems(new StreamSource(new StringReader(command)), itemEditor);
		dl.purgeItems(f_purgeList);
	}

	public void purgeItem(String id) {
		f_purgeList.put(id, null);
	}

	public void createPerson(String id, String firstName, String familyName, String dob) {
		String instance = f_entityTemplate.replaceAll("#entity_type", "Person")
				.replaceAll("#entity_content", f_personTemplate).replaceAll("#id", id)
				.replaceAll("#first_name", firstName).replaceAll("#family_name", familyName).replaceAll("#dob", dob);

		f_personList.add(instance);
	}

	public void createOrganization(String id, String name, String type) {
		String instance = f_entityTemplate.replaceAll("#entity_type", "Organization")
				.replaceAll("#entity_content", f_orgTemplate).replaceAll("#id", id).replaceAll("#name", name)
				.replaceAll("#type", type);

		f_orgList.add(instance);
	}

	public void createEvent(String id, String type, String timestamp, String description, String location) {
		if (id == GENERATE_ID) {
			id = String.valueOf(Math.abs(f_random.nextLong()));
		}

		String instance = f_entityTemplate.replaceAll("#entity_type", "Event")
				.replaceAll("#entity_content", f_eventTemplate).replaceAll("#id", id).replaceAll("#type", type)
				.replaceAll("#timestamp", timestamp).replaceAll("#description", description)
				.replaceAll("#location", location);

		f_eventList.add(instance);
	}

	public void createAddress(String id, String type, String latitude, String longitude) {
		String instance = f_entityTemplate.replaceAll("#entity_type", "Address")
				.replaceAll("#entity_content", f_addressTemplate).replaceAll("#id", id).replaceAll("#type", type)
				.replaceAll("#latitude", latitude).replaceAll("#longitude", longitude);

		f_addressList.add(instance);
	}

	public void createVehicle(String id, String make, String model, String year, String type) {
		String instance = f_entityTemplate.replaceAll("#entity_type", "Vehicle")
				.replaceAll("#entity_content", f_vehicleTemplate).replaceAll("#id", id).replaceAll("#make", make)
				.replaceAll("#model", model).replaceAll("#year", year).replaceAll("#type", type);

		f_vehicleList.add(instance);
	}

	public void createAssociate(String id, String type, String fromId, String toId) {
		String instance = f_linkTemplate.replaceAll("#link_type", "Associate")
				.replaceAll("#link_content", f_associateTemplate).replaceAll("#id", id).replaceAll("#type", type)
				.replaceAll("#from", fromId).replaceAll("#to", toId);

		f_associateList.add(instance);
	}

	public void createAccessTo(String id, String type, String fromId, String toId, String startDate, String info) {
		String instance = f_linkTemplate.replaceAll("#link_type", "AccessTo")
				.replaceAll("#link_content", f_accessToTemplate).replaceAll("#id", id).replaceAll("#type", type)
				.replaceAll("#from", fromId).replaceAll("#to", toId).replaceAll("#info", info);

		f_accessToList.add(instance);
	}

	public void createMemberOf(String id, String role, String fromId, String toId) {
		String instance = f_linkTemplate.replaceAll("#link_type", "MemberOf")
				.replaceAll("#link_content", f_memberOfTemplate).replaceAll("#id", id).replaceAll("#role", role)
				.replaceAll("#from", fromId).replaceAll("#to", toId);

		f_memberOfList.add(instance);
	}

	public void createInvolvedIn(String id, String type, String fromId, String toId, String startDate) {
		if (id == GENERATE_ID) {
			id = String.valueOf(Math.abs(f_random.nextLong()));
		}

		String instance = f_linkTemplate.replaceAll("#link_type", "InvolvedIn")
				.replaceAll("#link_content", f_involvedInTemplate).replaceAll("#id", id).replaceAll("#type", type)
				.replaceAll("#from", fromId).replaceAll("#to", toId).replaceAll("#start_date", startDate);

		f_involvedInList.add(instance);
	}

	public void createCommunication(String id, String type, String fromId, String toId, String startDate,
			String duration) {
		if (id == GENERATE_ID) {
			id = String.valueOf(Math.abs(f_random.nextLong()));
		}

		String instance = f_linkTemplate.replaceAll("#link_type", "Communication")
				.replaceAll("#link_content", f_communicationTemplate).replaceAll("#id", id).replaceAll("#type", type)
				.replaceAll("#from", fromId).replaceAll("#to", toId).replaceAll("#start_date", startDate)
				// .replaceAll("#end_date", startDate)
				.replaceAll("#duration", startDate);

		f_communicationList.add(instance);
	}
}