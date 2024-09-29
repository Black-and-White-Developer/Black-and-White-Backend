package bw.growingcode.global.config.security;

import bw.growingcode.global.dto.CustomOAuth2User;
import bw.growingcode.global.dto.CustomUserInfoDTO;
import bw.growingcode.user.domain.User;
import bw.growingcode.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    @Value("${login.redirect.url}")
    private String corsFrontend;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        CustomUserInfoDTO userInfo = new CustomUserInfoDTO(oAuth2User.getUserId());
        String kakaoUserId = oAuth2User.getAttributes().get("id").toString();
        Optional<User> optionalUser = userRepository.findBySocialId(kakaoUserId);
        Long userId = null;
        if (optionalUser.isEmpty()) {
            User user = User.builder()
                .socialId(kakaoUserId)
                .name(oAuth2User.getName())
                .build();
            userRepository.save(user);
            userId = user.getId();
        } else {
            userId = optionalUser.get().getId();
        }
        String accessToken = jwtProvider.createAccessToken(new CustomUserInfoDTO(userId));
        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(corsFrontend, accessToken));
    }

    private String getRedirectUrl(String targetUrl, String token) {
        return UriComponentsBuilder.fromUriString(targetUrl)
//            .queryParam("token", token)
            .build().toUriString();
    }

}