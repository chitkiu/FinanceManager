package korotchenko.common.data.base

abstract class BaseInMemoryRequestWrapper<U: Any> : BaseRequestWrapper<U>() {

    protected val modelList: MutableList<U> = mutableListOf()

    override fun getAllModel(): List<U> = modelList

    override fun save(model: U) {
        modelList.add(model)
    }

    override fun saveAll(list: List<U>) {
        modelList.addAll(list)
    }

    override fun delete(model: U) {
        modelList.remove(model)
    }
}