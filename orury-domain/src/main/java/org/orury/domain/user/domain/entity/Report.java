package org.orury.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.orury.domain.base.db.AuditingField;

@Slf4j
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity(name = "report")
public class Report extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "reason", nullable = false)
    private int reason;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reportUser;

    private Report(
            Long id,
            int type,
            int reason,
            String description,
            Long targetId,
            User user,
            User reportUser
    ) {
        this.id = id;
        this.type = type;
        this.reason = reason;
        this.description = description;
        this.targetId = targetId;
        this.user = user;
        this.reportUser = reportUser;
    }

    public static Report of(
            Long id,
            int type,
            int reasonCode,
            String description,
            Long targetId,
            User user,
            User reportUser
    ) {
        return new Report(
                id,
                type,
                reasonCode,
                description,
                targetId,
                user,
                reportUser
        );
    }
}
