package korotchenko.common.data.base

abstract class BaseRequestWrapper<U: Any> {

    abstract fun getAllModel(): List<U>

    abstract fun getModelById(id: Long): U?

    abstract fun save(model: U)

    abstract fun saveAll(list: List<U>)

    abstract fun delete(model: U)

    abstract fun deleteById(id: Long)
}