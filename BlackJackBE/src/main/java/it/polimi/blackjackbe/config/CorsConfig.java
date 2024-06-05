package it.polimi.blackjackbe.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Bean utili per la gestione dei CORS. Grazie all'annotazione "@Configuration",
 * il contenuto di questa classe viene eseguito appena il server viene avviato.
 */
@Configuration
public class CorsConfig {

   /**
    * Bean utile per la configurazione base dei CORS.
    * @return Istanza di CorsFilter inizializzata con la configurazione CORS.
    */
   @Bean
   public CorsFilter corsFilter() {
      // Configurazione CORS basata su URL
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();

      // Disabilita la richiesta di credenziali
      config.setAllowCredentials(false);
      // Consente l'accesso da qualsiasi origine
      config.addAllowedOrigin("*");
      // Consente tutti i metodi HTTP
      config.addAllowedMethod("*");
      // Consente tutti gli header
      config.addAllowedHeader("*");

      // Registra la configurazione precedente per tutti i percorsi
      source.registerCorsConfiguration("/**", config);

      // Ritorna un filtro CORS configurato
      return new CorsFilter(source);
   }

   /**
    * Configurazione avanzata dei CORS.
    * @return Oggetto FilterRegistrationBean configurato.
    */
   @Bean
   public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
      // Configurazione CORS basata su URL
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();

      // Disabilita la richiesta di credenziali
      config.setAllowCredentials(false);
      // Consente l'accesso dalla porta locale 8080 e da qualsiasi origine
      config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "*"));
      // Consente tutti i metodi HTTP
      config.setAllowedMethods(Collections.singletonList("*"));
      // Consente tutti gli header
      config.setAllowedHeaders(Collections.singletonList("*"));

      // Registra la configurazione precedente per tutti i percorsi
      source.registerCorsConfiguration("/**", config);

      // Crea un filtro di registrazione con la configurazione CORS
      FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
      // Imposta l'ordine di esecuzione del filtro al valore più alto
      bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

      return bean;
   }
}

