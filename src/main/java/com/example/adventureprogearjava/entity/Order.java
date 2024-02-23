package com.example.adventureprogearjava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "city",nullable = false)
    String city;

    @Column(name = "post_address", nullable = false)
    String postAddress;

    @Column(name = "comment")
    String comment;

    @Column(name = "price")
    Long price;

    @Column(name = "is_paid", nullable = false)
    boolean isPaid;

    @Column(name = "is_delivered", nullable = false)
    boolean isDelivered;

    @Column(name = "is_canceled", nullable = false)
    boolean isCanceled;

    @OneToMany(mappedBy = "order")
    List<OrdersList> ordersLists;

}
