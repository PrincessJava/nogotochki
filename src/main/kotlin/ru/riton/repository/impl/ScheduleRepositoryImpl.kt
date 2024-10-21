package ru.riton.ru.riton.repository.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.*
import ru.riton.model.AppointmentEntity
import ru.riton.model.ScheduleEntity
import ru.riton.model.UserEntity
import ru.riton.ru.riton.model.enums.ScheduleType
import ru.riton.ru.riton.repository.ScheduleRepositoryCustom
import java.sql.Timestamp

class ScheduleRepositoryImpl(private val entityManager: EntityManager) : ScheduleRepositoryCustom {
    override fun getFreeByRangeMasterAndDescription(
        type: ScheduleType,
        start: Timestamp,
        finish: Timestamp,
        descriptionId: Int?,
        masterId: Int?
    ): List<ScheduleEntity> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cq: CriteriaQuery<ScheduleEntity> = cb.createQuery(ScheduleEntity::class.java)
        val root: Root<ScheduleEntity> = cq.from(ScheduleEntity::class.java)

        val predicates = mutableListOf<Predicate>()

        // Основные условия
        predicates.add(cb.between(root.get("start"), start, finish))
        predicates.add(cb.equal(root.get<ScheduleType>("type"), type))

        // Подзапрос для проверки capacity > числа уникальных пользователей
        val subquery: Subquery<Long> = cq.subquery(Long::class.java)
        val subqueryRoot = subquery.from(AppointmentEntity::class.java)
        subquery.select(cb.countDistinct(subqueryRoot.get<UserEntity>("user")))
            .where(cb.equal(subqueryRoot.get<ScheduleEntity>("slot").get<Long>("id"), root.get<Long>("id")))

        // Приводим 'capacity' и результат подзапроса к Long для корректного сравнения
        predicates.add(
            cb.greaterThan(
                root.get<Long>("capacity").`as`(Long::class.java), // Приведение capacity к Long
                subquery
            )
        )

        // Дополнительные условия
        if (descriptionId != null) {
            predicates.add(cb.equal(root.get<Int>("description").get<Int>("id"), descriptionId))
        }

        if (masterId != null) {
            predicates.add(cb.equal(root.get<Int>("master").get<Int>("id"), masterId))
        }

        // Применение условий
        cq.select(root).where(cb.and(*predicates.toTypedArray()))

        return entityManager.createQuery(cq).resultList
    }

}