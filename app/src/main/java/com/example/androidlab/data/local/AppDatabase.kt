// Define the package where this database class is located
package com.example.androidlab.data.local

// Import the Context class which is needed to initialize the Room database instance
import android.content.Context
// Import the Database annotation to configure our Room database
import androidx.room.Database
// Import the Room class which provides the database builder
import androidx.room.Room
// Import the RoomDatabase base class that all Room databases must extend
import androidx.room.RoomDatabase
// Import the DAO (Data Access Object) interface for UserProfile operations
import com.example.androidlab.data.local.dao.UserProfileDao
// Import the UserProfile entity class which represents the database table
import com.example.androidlab.data.local.entity.UserProfile

// Annotation to define the database: entities lists the tables, version is for migrations, 
// and exportSchema = false skips generating a JSON schema file
@Database(entities = [UserProfile::class], version = 1, exportSchema = false)
// The main database class must be abstract and extend RoomDatabase
abstract class AppDatabase : RoomDatabase() {

    // Abstract function that returns the DAO; Room will generate the implementation
    abstract fun userProfileDao(): UserProfileDao

    // Companion object allows us to implement a singleton pattern for the database instance
    companion object {
        // @Volatile ensures that the value of INSTANCE is always up-to-date across all threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Function to get the singleton instance of the database, creating it if it doesn't exist
        fun getDatabase(context: Context): AppDatabase {
            // If INSTANCE is not null, return it. If it is, create the database in a thread-safe way
            return INSTANCE ?: synchronized(this) {
                // Double-check if another thread created the instance while we were waiting for the lock
                val instance = Room.databaseBuilder(
                    // Use applicationContext to avoid memory leaks if the activity is destroyed
                    context.applicationContext,
                    // The class of the database we want to create
                    AppDatabase::class.java,
                    // The name of the database file stored on the device
                    "android_lab_database"
                ).build()
                // Assign the newly created instance to our static INSTANCE variable
                INSTANCE = instance
                // Return the initialized instance
                instance
            }
        }
    }
}