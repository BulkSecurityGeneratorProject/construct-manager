package fr.mathieugeissler.constructmanager.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(fr.mathieugeissler.constructmanager.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Home.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Home.class.getName() + ".rooms", jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Room.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Room.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.RoomSize.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.RoomType.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.RoomGenericProduct.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.GenericProduct.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Division.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Division.class.getName() + ".estimates", jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.GenericProductCategory.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.GenericProductCategory.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Shop.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Shop.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Estimate.class.getName(), jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.Estimate.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(fr.mathieugeissler.constructmanager.domain.EstimateProduct.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
