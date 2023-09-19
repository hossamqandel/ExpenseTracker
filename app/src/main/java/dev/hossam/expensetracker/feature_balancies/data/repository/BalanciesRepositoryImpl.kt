package dev.hossam.expensetracker.feature_balancies.data.repository

import dev.hossam.expensetracker.core.data.room.base_dao.BaseTransactionDao
import dev.hossam.expensetracker.core.data.room.dto.TransactionDTO
import dev.hossam.expensetracker.core.data.room.entities.TransactionMapper
import dev.hossam.expensetracker.core.enums.TransactionTypeEnum.EXPENSE
import dev.hossam.expensetracker.core.enums.TransactionTypeEnum.INCOME
import dev.hossam.expensetracker.feature_balancies.data.local.BalanciesDao
import dev.hossam.expensetracker.feature_balancies.domain.repository.BalanciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BalanciesRepositoryImpl @Inject constructor(
    private val baseDao: BaseTransactionDao,
    private val balanciesDao: BalanciesDao,
) : BalanciesRepository {

    override suspend fun getUserBalance(): Double {
        val incomeBalance = balanciesDao.sumAllTransactionsByType(INCOME.value) ?: 0.0
        val expenseBalance = balanciesDao.sumAllTransactionsByType(EXPENSE.value) ?: 0.0
        return incomeBalance - expenseBalance
    }

    override suspend fun getSumOfAllIncomeTransactions(): Double {
        return balanciesDao.sumAllTransactionsByType(INCOME.value) ?: 0.0
    }

    override suspend fun getSumOfAllExpenseTransactions(): Double {
        return balanciesDao.sumAllTransactionsByType(EXPENSE.value) ?: 0.0
    }

    override fun getRecentlyTransactions(limit: Int): Flow<List<TransactionDTO>> {
        val recentlyTransactions = balanciesDao.getLimitedRecentlyTransactions(limit)
        return recentlyTransactions.map { transactions ->
            transactions.map { transEntity ->
                TransactionMapper.fromEntity(transEntity)
            }
        }
    }

    override suspend fun deleteTransactionById(id: Int) {
        baseDao.deleteTransactionById(id = id)
    }

}