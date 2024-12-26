package com.example.uas_paba_klmpk6.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface budgetingDAO {
    // --- Budgeting Table Methods ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBudget(budget: budgeting): Long

    @Update
    fun updateBudget(budget: budgeting)

    @Delete
    fun deleteBudget(budget: budgeting)

    @Query("SELECT * FROM budgeting ORDER BY id_budget asc")
    fun getAllBudgets(): MutableList<budgeting>

    @Query("SELECT * FROM budgeting WHERE id_budget = :id")
    fun getBudgetById(id: Int): budgeting?

    @Query("UPDATE budgeting SET  saved_amount=:isi_saved_amount WHERE id_budget=:pilih_id_budget")
    fun updateBudgetFixed( isi_saved_amount: Int,pilih_id_budget: Int)


    // --- BudgetTransaction Table Methods ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: budgetTransaction): Long

    @Query("SELECT * FROM budgetTransaction WHERE id_budget = :budgetId")
    fun getTransactionsByBudgetId(budgetId: Int): List<budgetTransaction>

    @Query("SELECT * FROM budgetTransaction")
    fun getAllTransactions(): MutableList<budgetTransaction>

    // --- Combined Operations ---
    @Transaction
    fun addTransactionAndUpdateBudget(transaction: budgetTransaction) {
        insertTransaction(transaction)
        val budget = getBudgetById(transaction.id_budget) ?: return
        budget.saved_amount += transaction.amount
        updateBudget(budget)
    }
}
