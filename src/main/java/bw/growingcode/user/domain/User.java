package bw.growingcode.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 사용자 이름

    @Column(nullable = false)
    private String socialId;

    @Builder
    public User(Long id, String name, String socialId) {
        this.id = id;
        this.name = name;
        this.socialId = socialId;
    }
}
