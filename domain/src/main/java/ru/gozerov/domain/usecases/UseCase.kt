package ru.gozerov.domain.usecases

abstract class UseCase<T, R> {

    protected abstract suspend fun loadData(arg: T) : R

    suspend fun execute(
        arg: T,
        onSuccess: suspend (R) -> Unit,
        onError: (suspend (e: Exception) -> Unit)? = null
    ) {
        try {
            val result = loadData(arg)
            onSuccess(result)
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

}