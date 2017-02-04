package com.ntr1x.storage.app;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.ntr1x.storage.archery.model.Portal;
import com.ntr1x.storage.archery.services.IPortalService;
import com.ntr1x.storage.archery.services.IPortalService.PortalCreate;
import com.ntr1x.storage.core.services.IRendererService;
import com.ntr1x.storage.core.services.ISerializationService;
import com.ntr1x.storage.core.services.ITransactionService;
import com.ntr1x.storage.core.utils.ResourceLoader;
import com.ntr1x.storage.security.model.User;
import com.ntr1x.storage.security.services.IUserService;
import com.ntr1x.storage.security.services.IUserService.UserCreate;
import com.ntr1x.storage.store.services.IOfferService;
import com.ntr1x.storage.store.services.IOfferService.OfferCreate;

@Component
@Profile("setup")
public class Setup {
	
	@Inject
	private IUserService users;
	
	@Inject
	private IPortalService portals;
	
	@Inject
	private IOfferService offers;
	
	@Inject
	private IRendererService renderer;
	
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
		
		JsonNode setupUsers = serialization.readJSONNodeJackson(
			renderer.renderer("/setup-users.json")
				.with("resources", ResourceLoader.class)
				.render(this.getClass().getResource("/setup-users.json"))
		);
		
		User admin = null; {
			UserCreate u = serialization.parseJSONNodeJackson(UserCreate.class, setupUsers.get("admin"));
			admin = users.create(scope, u);
		}
		
		User user = null; {
			UserCreate u = serialization.parseJSONNodeJackson(UserCreate.class, setupUsers.get("user"));
			user = users.create(scope, u);
		}
		
		JsonNode setupPortals = serialization.readJSONNodeJackson(
			renderer.renderer("/setup-portals.json")
				.with("resources", ResourceLoader.class)
				.render(this.getClass().getResource("/setup-portals.json"))
		);
		
		Portal archery = null; {
			PortalCreate p = serialization.parseJSONNodeJackson(PortalCreate.class, setupPortals.get("archery"));
			p.user = admin.getId();
			archery = portals.create(scope, p);
		}
		
		JsonNode setupOffers = serialization.readJSONNodeJackson(
			renderer.renderer("/setup-offers.json")
				.with("resources", ResourceLoader.class)
				.render(this.getClass().getResource("/setup-offers.json"))
		);
		
		{
			OfferCreate o = serialization.parseJSONNodeJackson(OfferCreate.class, setupOffers.get("starter"));
			o.user = admin.getId();
			o.relate = archery.getId();
			offers.create(scope, o);
		}
		
		{
			OfferCreate o = serialization.parseJSONNodeJackson(OfferCreate.class, setupOffers.get("business"));
			o.user = admin.getId();
			o.relate = archery.getId();
			offers.create(scope, o);
		}
		
		{
			OfferCreate o = serialization.parseJSONNodeJackson(OfferCreate.class, setupOffers.get("server"));
			o.user = admin.getId();
			o.relate = archery.getId();
			offers.create(scope, o);
		}
		
		{
			OfferCreate o = serialization.parseJSONNodeJackson(OfferCreate.class, setupOffers.get("advanced"));
			o.user = admin.getId();
			o.relate = archery.getId();
			offers.create(scope, o);
		}
	}
}
