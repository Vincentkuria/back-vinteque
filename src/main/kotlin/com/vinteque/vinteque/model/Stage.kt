package com.vinteque.vinteque.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType
import java.util.UUID

//funnels where a particular is allowed
enum class FunnelType { INBOUND, OUTBOUND, INOUTBOUND }

@Entity
@Table(name = "stages")
class Stage(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null,

    @Column(name = "name", nullable = false, unique = true)
    var name: String,

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @Column(name = "funnel", nullable = false, columnDefinition = "funnel_type")
    var funnel: FunnelType,
)
