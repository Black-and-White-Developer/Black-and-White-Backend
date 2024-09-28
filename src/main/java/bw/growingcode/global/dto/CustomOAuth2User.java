package bw.growingcode.global.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2User(OAuth2User user) {
        this.attributes = user.getAttributes();
        this.authorities = user.getAuthorities();
    }

    public CustomOAuth2User(Long userId, Collection<? extends GrantedAuthority> authorities) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        this.attributes = map;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        HashMap<String, String> map = (HashMap) attributes.get("properties");
        if (map == null) return "";
        return map.get("nickname");
    }

    public Long getUserId() {
        return (Long) attributes.get("userId");
    }

}
