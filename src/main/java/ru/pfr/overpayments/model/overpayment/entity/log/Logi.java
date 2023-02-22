package ru.pfr.overpayments.model.overpayment.entity.log;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
//@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Logi implements Comparable<Logi> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    @Column(length = 255)
    private String user;
    @Enumerated(EnumType.STRING)
    private TypeLog type;
    @Column(columnDefinition = "TEXT")
    private String text;
    @CreatedDate
    protected LocalDateTime sysCreateDate;
    @LastModifiedDate
    protected LocalDateTime sysUpdateDate;

    @Builder
    public Logi(Long id, LocalDateTime date, String user, TypeLog type, String text) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.type = type;
        this.text = text;
    }

    public Logi(LocalDateTime date, String user, TypeLog type, String text) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.type = type;
        this.text = text;
    }

    @Override
    public int compareTo(Logi o) {
        if (this.id.equals(o.id)) {
            return 0;
        } else if (this.id < o.id) {
            return -1;
        } else {
            return 1;
        }
    }


}
