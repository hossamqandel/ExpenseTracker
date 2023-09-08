package dev.hossam.expensetracker.core.mapper

interface Mapper<Entity, DTO> {

    fun fromEntity(entity: Entity): DTO
    fun toEntity(dto: DTO): Entity
}