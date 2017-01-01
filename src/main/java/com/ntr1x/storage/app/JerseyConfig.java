package com.ntr1x.storage.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ntr1x.storage.core.converter.ConverterProvider;
import com.ntr1x.storage.core.filtering.ResourceFilteringFeature;
import com.ntr1x.storage.core.jersey.ExceptionMapperProvider;
import com.ntr1x.storage.core.jersey.ObjectMapperProvider;
import com.ntr1x.storage.security.filters.AuthenticationFilter;
import com.ntr1x.storage.security.filters.AuthorizationFilter;
import com.ntr1x.storage.security.filters.CORSRequestFilter;
import com.ntr1x.storage.security.filters.CORSResponseFilter;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@Component
@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	protected ServiceLocator serviceLocator;
	
	@Value("${app.public.host}")
    private String host;

	@Value("${app.public.schemes}")
    private String[] schemes;
	
	@Bean
	@Scope("singleton")
	public ServiceLocatorProvider getServiceLocator() {
	    return new ServiceLocatorProvider(this);
	}
	
    public JerseyConfig() {
		
		packages("com.ntr1x.storage");
		
		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
		
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);
		register(CORSRequestFilter.class);
		register(CORSResponseFilter.class);
		register(ConverterProvider.class);
		register(ObjectMapperProvider.class);
		register(ExceptionMapperProvider.class);
		register(MultiPartFeature.class);
//		register(MoxyXmlFeature.class);
//		register(MoxyJsonFeature.class);
		register(JacksonFeature.class);
		register(EntityFilteringFeature.class);
		register(ResourceFilteringFeature.class);
		register(RolesAllowedDynamicFeature.class);
		register(AuthenticationFilter.class);
		register(AuthorizationFilter.class);
		
//		register(new MoxyJsonConfig().setAttributePrefix("@").resolver());
//		register(
//			new LoggingFeature(
//				Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
//				Level.SEVERE,
//				LoggingFeature.Verbosity.PAYLOAD_ANY,
//				LoggingFeature.DEFAULT_MAX_ENTITY_SIZE
//			)
//		);
		
		register(new ContainerLifecycleListener() {
		    
            public void onStartup(Container container) {
                serviceLocator = container.getApplicationHandler().getServiceLocator();
            }

            public void onReload(Container container) {
                serviceLocator = container.getApplicationHandler().getServiceLocator();
            }
            public void onShutdown(Container container) {
                serviceLocator = null;
            }
        });
		
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(schemes);
		beanConfig.setHost(host);
		beanConfig.setBasePath("");
		beanConfig.setResourcePackage("com.ntr1x.storage");
		beanConfig.setScan(true);
	}
    
    public static class ServiceLocatorProvider {
        
        private JerseyConfig config;

        private ServiceLocatorProvider(JerseyConfig config) {
            this.config = config;
        }
        
        public ServiceLocator get() {
            
            return config.serviceLocator;
        }
    }
}