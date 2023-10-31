package pl.dockerguardimage.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileAccessId implements Serializable {

    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "image_scan_id", nullable = false)
    private Long imageScanId;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        FileAccessId fileAccessId = (FileAccessId) o;
        return Objects.equals(fileAccessId.getUserId() , getUserId())
                && Objects.equals(fileAccessId.getImageScanId() , getImageScanId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId,imageScanId);
    }
}
