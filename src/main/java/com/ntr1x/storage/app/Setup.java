package com.ntr1x.storage.app;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ntr1x.storage.core.services.IBatchService;
import com.ntr1x.storage.core.services.ISerializationService;
import com.ntr1x.storage.security.services.IUserService;

@Component
@Profile("setup")
public class Setup {
	
	@Inject
	private IUserService users;
	
	@Inject
	private IBatchService batch;
	
	@Inject
	private ISerializationService serialization;
	
	@PostConstruct
    public void init(){
		
		IBatchService.Context context = new IBatchService.Context()
			.on("create-user", (args) -> {
				users.create(
					serialization.parseJSONNodeJackson(IUserService.CreateUser.class, args.get(0))
				);
			})
		;
		
		batch.execute(context, this.getClass().getResource("/jobs-setup.json"));
    }
}
