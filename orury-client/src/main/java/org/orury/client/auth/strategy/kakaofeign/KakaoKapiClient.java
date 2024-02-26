package org.orury.client.auth.strategy.kakaofeign;

import org.orury.client.global.config.FeignConfig;
import org.orury.domain.auth.dto.kakao.KakaoAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(
        name = "kakao-kapi",
        url = "${oauth-login.client.kakao-kapi.url}",
        configuration = FeignConfig.class
)
public interface KakaoKapiClient {

    @PostMapping("/v2/user/me")
    KakaoAccountDto getInfo(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("Content-type") String contentType
    );
}