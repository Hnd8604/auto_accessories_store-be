//package app.store.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long orderId;
//
//    @ManyToOne
//    User customer;
//
//    BigDecimal totalAmount;
//
//    @Enumerated(EnumType.STRING)
//  //  OrderStatus status; // PENDING, CONFIRMED, SHIPPED, DELIVERED
//
//    String shippingAddress;
//    LocalDateTime orderDate;
//}
