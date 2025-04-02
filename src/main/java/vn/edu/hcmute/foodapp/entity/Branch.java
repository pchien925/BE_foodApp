package vn.edu.hcmute.foodapp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_branch")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends AbstractEntity<Integer>{
    @Column(name = "name")
    private String name;


    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "operating_hours")
    private String operatingHours;
}
