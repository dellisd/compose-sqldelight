package io.github.dellisd

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.await

suspend fun provideDbDriver(schema: SqlDriver.Schema): SqlDriver {
    return initSqlDriver(schema).await()
}
