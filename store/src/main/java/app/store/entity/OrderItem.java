//package app.store.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.math.BigDecimal;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long orderItemId;
//
//    @ManyToOne
//    Order order;
//
//    @ManyToOne
//    Product product;
//
//    int quantity;
//    BigDecimal unitPrice;
//}
