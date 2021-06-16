package io.github.dellisd

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.github.dellisd.db.Database
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class DataRepository {
    private var database: Database? = null

    val data = flow {
        withDatabase { database ->
            emitAll(database.noteQueries.getAll().asFlow().mapToList())
        }
    }

    private suspend fun initDatabase() {
        if (database == null) {
            database = Database(provideDbDriver(Database.Schema))
        }
    }

    private suspend fun <R> withDatabase(block: suspend (Database) -> R): R {
        initDatabase()
        return block(database!!)
    }
}
