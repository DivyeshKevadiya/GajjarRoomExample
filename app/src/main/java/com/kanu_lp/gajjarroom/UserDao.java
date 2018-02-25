package com.kanu_lp.gajjarroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Kanu on 2/25/2018.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user ORDER By id ASC")
    List<User> orderById();

    @Query("SELECT * FROM user ORDER By age ASC")
    List<User> orderByAge();


    @Query("SELECT * FROM user WHERE first_name LIKE :first")
    List<User> findLikeFirstName(String first);

    @Query("SELECT * FROM user WHERE last_name LIKE :last")
    List<User> findLikeLastName(String last);

    @Query("SELECT * FROM user WHERE age = :userAge")
    List<User> findInAge(int userAge);


    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Update
    void updateOne(User... user);


    @Delete
    void delete(User user);
}
