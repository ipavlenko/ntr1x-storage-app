package com.ntr1x.storage.app;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.ntr1x.storage.archery.model.Portal;
import com.ntr1x.storage.archery.services.IPortalService;
import com.ntr1x.storage.archery.services.IPortalService.PortalCreate;
import com.ntr1x.storage.core.services.ISerializationService;
import com.ntr1x.storage.core.services.ITransactionService;
import com.ntr1x.storage.security.model.User;
import com.ntr1x.storage.security.services.IUserService;
import com.ntr1x.storage.security.services.IUserService.UserCreate;

@Component
@Profile("setup")
public class Setup {
	
	@Inject
	private IUserService users;
	
	@Inject
	private IPortalService portals;
	
	@Inject
	private ITransactionService transactions;
	
	@Inject
	private ISerializationService serialization;
	
	@PostConstruct
    public void init() {
		
		transactions.execute(() -> {
			setup();
		});
    }

	@SuppressWarnings("unused")
	private void setup() {
		
		long scope = 4L;
		
		JsonNode setupUsers = serialization.readJSONNodeJackson(this.getClass().getResource("/setup-users.json"));
		
		User admin = null; {
			UserCreate u = serialization.parseJSONNodeJackson(UserCreate.class, setupUsers.get("admin"));
			admin = users.create(scope, u);
		}
		
		User user = null; {
			UserCreate u = serialization.parseJSONNodeJackson(UserCreate.class, setupUsers.get("user"));
			user = users.create(scope, u);
		}
		
		JsonNode setupPortals = serialization.readJSONNodeJackson(this.getClass().getResource("/setup-portals.json"));
		
		Portal portal = null; {
			PortalCreate p = serialization.parseJSONNodeJackson(PortalCreate.class, setupPortals.get("archery"));
			p.user = admin.getId();
			portal = portals.create(scope, p);
		}
	}
}
