package dev.hossam.expensetracker.core.data.room.entities

import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.core.mapper.Mapper

object TransactionMapper : Mapper<TransactionEntity, TransactionDTO> {
    
    override fun fromEntity(entity: TransactionEntity): TransactionDTO {
        return TransactionDTO(
            id = entity.id,
            title = entity.title,
            amount = entity.amount,
            type = entity.type,
            category = entity.category,
            date = entity.date,
            note = entity.note
        )
    }

    override fun toEntity(dto: TransactionDTO): TransactionEntity {
        return TransactionEntity(
            id = dto.id,
            title = dto.title,
            amount = dto.amount,
            type = dto.type,
            category = dto.category,
            date = dto.date,
            note = dto.note
        )
    }

    fun fromEntities(entities: List<TransactionEntity>): List<TransactionDTO> {
        return entities.map { fromEntity(it) }
    }

    fun toEntities(dtos: List<TransactionDTO>): List<TransactionEntity> {
        return dtos.map { toEntity(it) }
    }

}