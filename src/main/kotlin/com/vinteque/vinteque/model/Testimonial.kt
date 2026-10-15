package com.vinteque.vinteque.model

import jakarta.persistence.*

@Entity
@Table(name = "testimonials")
class Testimonial(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(nullable = false, length = 100)
    var role: String,

    @Column(nullable = false, length = 100)
    var company: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var testimony: String,

    @Column(name = "profile_pic", nullable = false, length = 255)
    var profilePic: String
)
