package korotchenko.logic.mappers

import korotchenko.logic.models.CategoryModel

abstract class CategoryModelMapper<T: Any> {
    abstract fun fromCategoryModel(model: CategoryModel): T
    abstract fun toCategoryModel(model: T): CategoryModel
}