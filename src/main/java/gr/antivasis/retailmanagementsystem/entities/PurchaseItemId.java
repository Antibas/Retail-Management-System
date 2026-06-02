package gr.antivasis.retailmanagementsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PurchaseItemId implements Serializable {
    @Serial
    private static final long serialVersionUID = -8091760575306385714L;

    @Column(name = "purchase_id", nullable = false)
    private UUID purchaseId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;


}