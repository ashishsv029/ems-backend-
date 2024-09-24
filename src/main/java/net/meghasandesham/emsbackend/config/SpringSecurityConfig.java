package net.meghasandesham.emsbackend.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration//defining this annotation makes the class as spring configuration class.
//This class is used to define the custom spring beans using @Bean
@EnableMethodSecurity //This allows you to use annotations such as @PreAuthorize, @PostAuthorize, @Secured, and @RolesAllowed to secure methods.
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder() { //This class overrides the default functionality of spring security to store the passwords in a raw format
        return new BCryptPasswordEncoder(); //This ensures all the passwords of Users are BCrypt encoded
    }

    //How bcrypt works under the hood:- Bcrypt is a hashing algorithm
    // This is how plain text is stored in DB - $2a$12$BgapafxRITC6XuupQiF3gObUHfK8AGdyjBtJUGgvwQAvXntFc.I7O
    /*
        1. Version Identifier ($2a$): The first part $2a$ indicates the bcrypt algorithm version. There are also $2b$, $2y$, and other variations indicating slightly different versions or implementations of the algorithm.
        2. Cost Factor ($12$): The next part 12 is the cost factor, also known as the work factor. It specifies the logarithmic number of rounds of hashing to apply. The number 12 means 2^12 rounds. The higher the number, the more computationally intensive the hashing process, making it harder to brute-force.
        3. Salt (BgapafxRITC6XuupQiF3gO): The salt is a 128-bit (16-byte) base64-encoded value. In this example, BgapafxRITC6XuupQiF3gO is the salt. The salt ensures that even if two identical passwords are hashed, they produce different hashes.
        4. Hashed Password (bUHfK8AGdyjBtJUGgvwQAvXntFc.I7O): The final part is the hashed password itself, also base64-encoded. This is the result of the bcrypt algorithm applied to the password and the salt with the specified cost factor.

        generally the salt part ends at O
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //SecurityFilterChain: The return type, which is a chain of security filters that will be applied to incoming HTTP requests. (Builder pattern)
    //HttpSecurity http: A parameter that allows configuring web-based security for specific HTTP requests.

    /*
    As of Spring Security 5.0, it's no longer recommended to extend WebSecurityConfigurerAdapter for configuring security settings.
    Instead, you should use a more component-based approach by creating beans of type SecurityFilterChain
    This bean defines the security filter chain. It configures HTTP security by specifying which requests are allowed, login configuration, and logout configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //here we are just configuring authorization and authentication logic on http requests
        http
                .csrf().disable() //CSRF is deprecated and enabled by spring boot by default making POST requested inaccessible without sending csrf token in header.. So disabling it manually
                .authorizeHttpRequests((authorize)->{
                    // 1. We are enforcing an authorization rule, which says every authenticated request is authorized to use all resources
                    authorize.anyRequest().authenticated();
                    // 2. Logic to authorize apis based on user roles -  ROLE BASED SECURITY
                    /*
                    authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
                    */
                    // 3. For public APIs, we need to provide access even if they are not authenticated
                    // authorize.requestMatchers(HttpMethod.GET, "/api/public/**").permitAll();
                    // 4. Instead of Role based Authorization, we can also enforce Method Level security using annotations like @EnableMethodSecurity which provides another annotation called @PreAuthorize to provide security on Method level
                    // --> see top for annotation and see controller class for preAuthorize annotation
                })
                .httpBasic(Customizer.withDefaults()); // we are overriding the default form based authentication with this basic authentication

        return http.build(); // Builder pattern
    }
    //When authentication fails, it throws 401 Unauthenticated
    //When authenticated, but not authorized to access api, it throws 403 Forbidden

    /*
    SPRING SECURITY OFFERS AN IN MEMORY USER MANAGEMENT STORE (UserDetailsService, InMemoryUserDetailsManager)
    Here we can use this database for managing user information. It has default rules for securing passwords and other storage related things
    We can also override any basic rules like password algorithms
     */

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails ramesh = User.builder().username("ramesh").password("ramesh").roles("USER").build(); // THIS CREDS COMBO NOT WORK AS PASSWORD SHOULD BE ENCODED IN BCRYPT AS WE HAVE OVERRIDDEN THE PASSWORD ENCODER ABOVE WITH BCRYPT  ENCODER
//        UserDetails ashish = User.builder().username("ashish").password(passwordEncoder().encode("qwerty")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(ramesh, ashish, admin);
//    } // - As we have authentication from database, we can comment this in memory handling code

    // Instead of In memory authentication like above, we can also store user data in Database as well and perform authentication on that data
    // So we need to create 2 entities UserEntity and RoleEntity and as the relation is many to many we can create a mapping table as well on these 2 entities
    // Lets use JPARepository as our DAL layer
    // Check the database authentication flow mentioned in the images folder
    // Spring boot security has a class called AuthenticationFilter which manages all this Authentication
    // The incoming HTTP requests reaches this class even before hitting our REST controller
    // It calls Authentication Manager, which is responsible to use One of many AuthenticationProvider implementations like (OAuth2, LDAP, DAO)
    // All these AuthenticationProvider Implementations, relies on UserDetailService Abstract class, Hence we need tp Override the class as per our customization using a child class extending the above class
    // The child class is defined in security folder and it overrides a method called loadUserByUsername
    // By default, if you don't configure a custom UserDetailsService, Spring Security will use an in-memory user store (defined in the security configuration).
    // The overriden implementation should talk to a DAL implemented using JPA Repository to fetch users from DB



}
