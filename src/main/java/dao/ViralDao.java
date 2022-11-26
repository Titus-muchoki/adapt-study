package dao;

import models.Viral;

import java.util.List;

public interface ViralDao {
    //LIST

    List<Viral> getAll();

    //CREATE

    void add(Viral viral);

    //READ

    Viral findById(int id);

    //UPDATE

    void update( int id, String content, int categoryId);

    //DELETE

    void deleteById(int id);

//    void clearAllViral(int id);

    void clearAllViral();

}
