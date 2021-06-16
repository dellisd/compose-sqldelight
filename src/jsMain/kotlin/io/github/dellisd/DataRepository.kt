package io.github.dellisd

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.github.dellisd.db.Database
import io.github.dellisd.db.Note
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect

class DataRepository {
    private var database: Database? = null

    private val _data = MutableStateFlow<List<Note>>(emptyList())
    val data = _data.asStateFlow()

    suspend fun initDatabase() {
        if (database == null) {
            database = Database(provideDbDriver(Database.Schema))
        }
    }

    suspend fun <R> withDatabase(block: suspend (Database) -> R): R {
        initDatabase()
        return block(database!!)
    }

    suspend fun loadData() = withDatabase { database ->
        // Uncomment to simulate long loading time
        // delay(2000)
        database.noteQueries.getAll().asFlow().mapToList().collect {
            _data.value = it
        }
    }

    /**
     * Can't easily be used in Compose because of `suspend` requirement
     */
    suspend fun getNotes(): Flow<List<Note>> = withDatabase { database ->
        database.noteQueries.getAll().asFlow().mapToList()
    }
}
