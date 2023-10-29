package com.example.piggywallet.manager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.piggywallet.MainApplication
import com.example.piggywallet.manager.db.datamodel.BookMenus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [BookMenus::class],version = 1,exportSchema = false)
abstract class RoomDatabaseManager : RoomDatabase() {

    abstract fun masterBookMenuDao(): BookMenusDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabaseManager? = null

        fun getInstance(context:Context):RoomDatabaseManager{
            /** Multiple Thread cannot access to DB at the Same time*/
            synchronized(this){
                //smart cast : Copy Current Value
                var instance = INSTANCE
                // if instance exits don't create again return this but if not Create the Database
                /** Migration Object is an Object that define how take all row with old schema
                 * and Convert them to rows in new Schema
                 * Migration  make your data not loss
                 *
                 * But in this lab We don't use migration,if anything change?? destroy and rebuild
                 * */

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDatabaseManager::class.java,
                        "book_menu_table").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }



//        fun getDatabase(
//            context: Context,
//            scope: CoroutineScope
//        ): RoomDatabaseManager {
//            // if the INSTANCE is not null, then return it,
//            // if it is, then create the database
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    RoomDatabaseManager::class.java,
//                    "word_database"
//                )
//                    // Wipes and rebuilds instead of migrating if no Migration object.
//                    // Migration is not part of this codelab.
//                    .fallbackToDestructiveMigration()
//                    .addCallback(WordDatabaseCallback(scope))
//                    .build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }

//        private class WordDatabaseCallback(
//            private val scope: CoroutineScope
//        ) : RoomDatabase.Callback() {
//            /**
//             * Override the onCreate method to populate the database.
//             */
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                // If you want to keep the data through app restarts,
//                // comment out the following line.
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.masterBookMenuDao())
//                    }
//                }
//            }
//        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
//        suspend fun populateDatabase(masterBookmenuDao: MasterBookMenuDao) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            masterBookmenuDao.deleteAll()
//
//            var mBookMenu = MasterBookMenus("IT001" , "Food","")
//            masterBookmenuDao.insert(mBookMenu)
//            mBookMenu = MasterBookMenus("IT002" , "Traffic","")
//            masterBookmenuDao.insert(mBookMenu)
//            mBookMenu = MasterBookMenus("IT003" , "Social","")
//            masterBookmenuDao.insert(mBookMenu)
//        }
    }
}
