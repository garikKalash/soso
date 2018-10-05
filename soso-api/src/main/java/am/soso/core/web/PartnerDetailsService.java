package am.soso.core.web;

import am.soso.core.models.Partner;
import am.soso.core.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PartnerDetailsService implements UserDetailsService {

    @Autowired
    private PartnerService partnerService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Partner partner = partnerService.getPartnerByUsername(s);
        if(partner == null){
            throw new UsernameNotFoundException("Username not found: " + s);
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

        User user = new User(partner.getUsername(), partner.getPassword(), authorities);

        return user;
    }
}
