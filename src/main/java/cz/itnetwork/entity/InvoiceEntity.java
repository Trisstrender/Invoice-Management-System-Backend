package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity(name = "invoice")
@Getter
@Setter
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer invoiceNumber;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate issued;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer vat;

    private String note;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private PersonEntity buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private PersonEntity seller;
}