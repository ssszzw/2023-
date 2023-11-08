package com.example20231016.todo_3.Mapper;

import com.example20231016.todo_3.Table.Character;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CharacterMapper {

    Character check(Character character);

    int post(Character character);
    int delete(Character character);
    int put(Character character);
    List<Character> getAllCharacters();
    List<Character> search(Character character);
    List<Integer> getCharacterIdByUserId(int user_id);
//    List<Character> getCharacterByCharacterId(int character_id);
}
