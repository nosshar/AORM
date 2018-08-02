package tanvd.aorm.expression

import tanvd.aorm.DbInt64
import tanvd.aorm.DbIntPrimitiveType
import tanvd.aorm.DbType

//Count

class Count<E : Any, out T : DbType<E>>(val expression: Expression<E, T>) : Expression<Long, DbInt64>(DbInt64()) {
    override fun toSelectListDef(): String = "COUNT(${expression.toQueryQualifier()})"

    override fun toQueryQualifier(): String = "COUNT(${expression.toQueryQualifier()})"
}

fun <E : Any, T : DbType<E>> count(column: Column<E, T>): Count<E, T> = Count(column)

//Max (State and Merge)
class MaxState<E : Number, out T : DbIntPrimitiveType<E>>(val expression: Expression<E, T>, type: T) : Expression<E, T>(type) {
    override fun toSelectListDef(): String = "maxState(${expression.toQueryQualifier()})"
    override fun toQueryQualifier(): String = "maxState(${expression.toQueryQualifier()})"
}
fun <E : Number, T : DbIntPrimitiveType<E>> maxState(column: Column<E, T>): MaxState<E, T> = MaxState(column, column.type)


class MaxMerge<E : Number, out T : DbIntPrimitiveType<E>>(val expression: MaxState <E, T>) : Expression<E, T>(expression.type) {
    override fun toSelectListDef(): String = "maxMerge(${expression.toQueryQualifier()})"
    override fun toQueryQualifier(): String = "maxMerge(${expression.toQueryQualifier()})"
}

class MaxMergeMaterialized<E : Number, T : DbIntPrimitiveType<E>>(val expression: AliasedExpression<E, T, MaxState<E, T>>) : Expression<E, T>(expression.type) {
    override fun toSelectListDef(): String = "maxMerge(${expression.toQueryQualifier()})"
    override fun toQueryQualifier(): String = "maxMerge(${expression.toQueryQualifier()})"
}
fun <E : Number, T : DbIntPrimitiveType<E>> maxMerge(expression: AliasedExpression<E, T, MaxState<E, T>>): MaxMergeMaterialized<E, T> = MaxMergeMaterialized(expression)
fun <E : Number, T : DbIntPrimitiveType<E>> maxMerge(expression: MaxState<E, T>): MaxMerge<E, T> = MaxMerge(expression)