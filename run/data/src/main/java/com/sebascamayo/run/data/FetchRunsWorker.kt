package com.sebascamayo.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sebascamayo.core.domain.run.RunRepository
import com.sebascamayo.core.domain.util.DataError
import com.sebascamayo.core.domain.util.Result

class FetchRunsWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: RunRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if(runAttemptCount >= 5) {
            return Result.failure()
        }
        return when(val result = repository.fetchRuns()) {
            is com.sebascamayo.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }
            is com.sebascamayo.core.domain.util.Result.Success -> Result.success()
        }
    }

}