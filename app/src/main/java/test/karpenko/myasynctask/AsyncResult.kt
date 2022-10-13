package test.karpenko.myasynctask

sealed class AsyncResult<out T>(
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {
    class Success<out T>(data: T?) : AsyncResult<T>(data = data)
    class Error<out T>(message: String?, throwable: Throwable?) :
        AsyncResult<T>(message = message, throwable = throwable)
}