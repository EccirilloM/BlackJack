package it.polimi.blackjackbe.security.config;

import it.polimi.blackjackbe.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configurazione di Spring Security per gestire l'autenticazione e l'autorizzazione.
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configura la catena di filtri di sicurezza HTTP.
     * @param http Istanza di {@link HttpSecurity} utilizzata per configurare la sicurezza.
     * @return Un {@link SecurityFilterChain} configurato.
     * @throws Exception se si verifica un errore nella configurazione.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura CORS per permettere richieste da domini diversi
                .cors()
                .and()
                // Disabilita la protezione CSRF per semplicità in questo esempio
                .csrf().disable()
                // Configura le regole di autorizzazione delle richieste
                .authorizeHttpRequests()
                // Consente l'accesso senza autenticazione agli endpoint dell'API di autenticazione
                .requestMatchers("/api/v1/auth/**").permitAll()
                // Richiede l'autenticazione per tutte le altre richieste
                .anyRequest().authenticated()
                .and()
                // Configura la gestione della sessione per essere stateless (senza sessioni)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Imposta il provider di autenticazione personalizzato
                .authenticationProvider(authenticationProvider)
                // Aggiunge il filtro JWT prima del filtro di autenticazione di base di Spring
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
