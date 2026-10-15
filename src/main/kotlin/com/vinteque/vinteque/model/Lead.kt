package com.vinteque.vinteque.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.*
import org.hibernate.annotations.Generated
import org.hibernate.annotations.JdbcType
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType
import org.hibernate.generator.EventType
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.UUID

enum class LeadFunnelType { INBOUND, OUTBOUND }

data class Comment(
    val id: String,
    val text: String,
    val dateTime: String,
)

data class StageComments(
    val stageName: String,
    val comments: List<Comment> = emptyList(),
)

@Entity
@Table(name = "leads")
class Lead(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "phone")
    var phone: String? = null,

    @Column(name = "company")
    var company: String? = null,

    @Column(name = "role")
    var role: String? = null,

    @Column(name = "source")
    var source: String? = null,

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @Column(name = "funnel", columnDefinition = "lead_funnel_type")
    var funnel: LeadFunnelType? = null,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "comments", nullable = false, columnDefinition = "jsonb")
    var comments: MutableList<StageComments> = mutableListOf(),

    /** References stages.name (FK enforced by the database) */
    @Column(name = "current_stage", nullable = false)
    var currentStage: String,

    @Column(name = "date_added", nullable = false, updatable = false)
    var dateAdded: Instant = Instant.now(),

    @Column(name = "last_contacted")
    var lastContacted: Instant? = null,

    /**
     * Managed by the database trigger. Hibernate never writes it, but re-reads
     * the value after every INSERT/UPDATE so the entity stays in sync.
     */
    @Generated(event = [EventType.INSERT, EventType.UPDATE])
    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    var updatedAt: Instant? = null,
)
