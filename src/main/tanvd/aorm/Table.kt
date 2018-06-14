package tanvd.aorm

import ru.yandex.clickhouse.ClickHouseUtil
import tanvd.aorm.expression.Column
import java.util.*
import kotlin.collections.LinkedHashSet
import kotlin.reflect.KClass


abstract class Table(name: String) {
    var name: String = ClickHouseUtil.escape(name)

    internal val _columns: LinkedHashSet<Column<*, DbType<*>>> = linkedSetOf()
    val columns: Set<Column<*, DbType<*>>> get() = _columns

    abstract val engine: Engine

    val columnsWithDefaults
        get() = columns.filter { it.defaultFunction != null }

    private fun <T: Any, E : DbType<T>> registerColumn(column: Column<T, E>): Column<T, E> {
        return column.apply { _columns.add(this) }
    }

    //date
    fun date(name: String) = registerColumn(Column(name, DbDate(), this))

    fun arrayDate(name: String) = registerColumn(Column(name, DbArrayDate(), this))

    //datetime
    fun datetime(name: String) = registerColumn(Column(name, DbDateTime(), this))
//    fun arrayDateTime(name: String) = registerColumn(Column(name, DbArrayDateTime()))

    //enums
    fun <T : Enum<*>> enum8(name: String, enumType: KClass<T>) = registerColumn(Column(name, DbEnum8(enumType), this))

    fun <T : Enum<*>> enum8(name: String, enumType: KClass<T>, enumMapping: LinkedHashMap<String, Int>) = registerColumn(Column(name, DbEnum8(enumMapping, enumType), this))

    fun <T : Enum<*>> enum16(name: String, enumType: KClass<T>) = registerColumn(Column(name, DbEnum16(enumType), this))
    fun <T : Enum<*>> enum16(name: String, enumType: KClass<T>, enumMapping: LinkedHashMap<String, Int>) = registerColumn(Column(name, DbEnum16(enumMapping, enumType), this))

    //int8
    fun int8(name: String) = registerColumn(Column(name, DbInt8(), this))

    fun arrayInt8(name: String) = registerColumn(Column(name, DbArrayInt8(), this))

    fun uint8(name: String) = registerColumn(Column(name, DbUInt8(), this))
    fun arrayUInt8(name: String) = registerColumn(Column(name, DbArrayUInt8(), this))

    //int16
    fun int16(name: String) = registerColumn(Column(name, DbInt16(), this))

    fun arrayInt16(name: String) = registerColumn(Column(name, DbArrayInt16(), this))

    fun uint16(name: String) = registerColumn(Column(name, DbUInt16(), this))
    fun arrayUInt16(name: String) = registerColumn(Column(name, DbArrayUInt16(), this))

    //int32
    fun int32(name: String) = registerColumn(Column(name, DbInt32(), this))

    fun arrayInt32(name: String) = registerColumn(Column(name, DbArrayInt32(), this))

    fun uint32(name: String) = registerColumn(Column(name, DbUInt32(), this))
    fun arrayUInt32(name: String) = registerColumn(Column(name, DbArrayUInt32(), this))

    //int64
    fun int64(name: String) = registerColumn(Column(name, DbInt64(), this))

    fun arrayInt64(name: String) = registerColumn(Column(name, DbArrayInt64(), this))

    fun uint64(name: String) = registerColumn(Column(name, DbUInt64(), this))
    fun arrayUInt64(name: String) = registerColumn(Column(name, DbArrayUInt64(), this))

    //boolean
    fun boolean(name: String) = registerColumn(Column(name, DbBoolean(), this))

    fun arrayBoolean(name: String) = registerColumn(Column(name, DbArrayBoolean(), this))

    //string
    fun string(name: String) = registerColumn(Column(name, DbString(), this))

    fun arrayString(name: String) = registerColumn(Column(name, DbArrayString(), this))

}